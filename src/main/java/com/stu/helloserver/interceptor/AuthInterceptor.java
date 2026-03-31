package com.stu.helloserver.interceptor;

import com.stu.helloserver.common.Result;
import com.stu.helloserver.common.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 精细化放行逻辑
        if (isAllowedWithoutToken(method, uri)) {
            return true;
        }

        // 获取 Authorization 令牌
        String authorization = request.getHeader("Authorization");

        // 若令牌缺失或为空，返回 401 错误 JSON 并拦截
        if (authorization == null || authorization.trim().isEmpty()) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            Result<ResultCode> result = Result.error(ResultCode.TOKEN_INVALID);
            writer.write(objectMapper.writeValueAsString(result));
            writer.flush();
            writer.close();
            return false;
        }

        // 令牌存在，放行
        return true;
    }

    /**
     * 判断是否需要跳过 Token 校验
     * 放行规则：
     * 1. POST /api/users - 允许注册
     * 2. GET /api/users/{id} - 允许查看用户信息
     */
    private boolean isAllowedWithoutToken(String method, String uri) {
        // POST /api/users 允许注册
        if ("POST".equals(method) && "/api/users".equals(uri)) {
            return true;
        }

        // GET /api/users/{id} 允许查看用户信息
        if ("GET".equals(method) && uri.matches("/api/users/\\d+")) {
            return true;
        }

        return false;
    }
}

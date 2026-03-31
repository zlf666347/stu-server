package com.stu.helloserver.controller;

import com.stu.helloserver.common.Result;
import com.stu.helloserver.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // 模拟用户数据存储
    private Map<Long, User> userDatabase = new HashMap<>();

    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable("id") Long id) {
        User user = userDatabase.get(id);
        if (user == null) {
            return Result.error(404, "未找到 ID 为 " + id + " 的用户");
        }
        return Result.success(user);
    }

    @PostMapping
    public Result<User> createUser(@RequestBody User user) {
        Long id = System.currentTimeMillis();
        user.setId(id);
        userDatabase.put(id, user);
        return Result.success(user);
    }

    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        if (!userDatabase.containsKey(id)) {
            return Result.error(404, "未找到 ID 为 " + id + " 的用户");
        }
        user.setId(id);
        userDatabase.put(id, user);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable("id") Long id) {
        if (!userDatabase.containsKey(id)) {
            return Result.error(404, "未找到 ID 为 " + id + " 的用户");
        }
        userDatabase.remove(id);
        return Result.success();
    }

    // 登录接口（用于测试）
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        response.put("token", "mock-token-" + user.getName());
        response.put("message", "登录成功");
        return Result.success(response);
    }

    // 测试异常的方法：故意除以 0 触发异常
    @GetMapping("/test/error")
    public Result<String> testError() {
        int a = 1 / 0;
        return Result.success("测试成功");
    }
}

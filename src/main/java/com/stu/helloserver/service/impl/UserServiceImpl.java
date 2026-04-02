package com.stu.helloserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu.helloserver.common.Result;
import com.stu.helloserver.common.ResultCode;

import com.stu.helloserver.entity.User;
import com.stu.helloserver.entity.dto.UserDTO;
import com.stu.helloserver.mapper.UserMapper;
import com.stu.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> register(UserDTO userDTO) {
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userDTO.getUsername());
        User existingUser = userMapper.selectOne(queryWrapper);

        if (existingUser != null) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }

        // 创建新用户
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());
        userMapper.insert(newUser);

        return Result.success("注册成功", null);
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        // 检查用户名是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userDTO.getUsername());
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 校验密码
        if (!user.getPassword().equals(userDTO.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }

        // 登录成功，返回 token（这里简单生成一个 token）
        String token = "token-" + user.getUsername() + "-" + System.currentTimeMillis();
        return Result.success("登录成功", token);
    }
}

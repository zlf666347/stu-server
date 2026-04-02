package com.stu.helloserver.controller;

import com.stu.helloserver.common.Result;
import com.stu.helloserver.entity.User;
import com.stu.helloserver.entity.dto.UserDTO;
import com.stu.helloserver.mapper.UserMapper;
import com.stu.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;



    @GetMapping
    public Result<java.util.List<User>> getAllUsers() {
        java.util.List<User> users = userMapper.selectList(null);
        return Result.success(users);
    }

    @PostMapping
    public Result<String> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    @GetMapping("/{id}")
    public Result<User> getUserInfo(@PathVariable("id") Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error(404, "未找到 ID 为 " + id + " 的用户");
        }
        return Result.success(user);
    }
}

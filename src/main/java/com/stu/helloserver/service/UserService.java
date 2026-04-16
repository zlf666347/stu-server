package com.stu.helloserver.service;



import com.stu.helloserver.common.Result;
import com.stu.helloserver.entity.User;
import com.stu.helloserver.entity.dto.UserDTO;


public interface UserService {

    Result<String> register(UserDTO userDTO);

    Result<String> login(UserDTO userDTO);

    Result<User> getUserById(Long id);
}

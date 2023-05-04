package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.UserDto;

public interface UserService {

    Long saveUser(UserDto.Request userRequest);

    void updateUser(Long id, UserDto.Request updateRequest);

    void deleteUser(Long id);

    UserDto.Info getUserByUserId(String email, String password);

    int checkEmail(String email);

    User getLoginUserByEmail(String email);

    User login(UserDto.LoginRequest req);
}

package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.dto.UserDto;

public interface UserService {

    public Long saveUser(UserDto.Request userRequest);

    public void updateUser(Long id, UserDto.Request updateRequest);

    public void deleteUser(Long id);

    public UserDto.Info getUserByUserId(String email, String password);

    public int checkEmail(String email);
}

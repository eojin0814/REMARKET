package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.dto.UserDto;

public interface UserService {

    public Long saveUser(UserDto.SaveRequest userRequest);

    public void updateUser(Long id, UserDto.UpdateRequest updateRequest);

    public void deleteUser(Long id);

    public UserDto.getUserResponse getUserById(Long id);
}

package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.dto.UserDto;
import com.softwareapplication.remarket.dto.UserDto.getUserResponse;
import com.softwareapplication.remarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    @Override
    @Transactional
    public Long saveUser(UserDto.SaveRequest userRequest) {
        return userRepository.save(userRequest.toEntity()).getId();
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserDto.UpdateRequest updateRequest) {

    }

    @Override
    @Transactional
    public void deleteUser(Long id) {

    }

    @Override
    public getUserResponse getUserById(Long id) {
        return null;
    }

}

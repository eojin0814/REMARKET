package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.UserDto;
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
    public Long saveUser(UserDto.Request userRequest) {
        return userRepository.save(userRequest.toEntity()).getUserId();
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserDto.Request dto) {
        User user = userRepository.findById(id).orElseThrow();

        String phone = dto.getPhone1() + dto.getPhone2() + dto.getPhone3();

        if(!dto.getPassword().equals("")) {
            user.updatePassword(dto.getPassword());
        }

        user.updateUser(dto.getName(),
                phone,
                dto.getAddress());
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.deleteById(id);
    }

    @Override
    public UserDto.Info getUserByUserId(String email, String password) {
        User user = userRepository.findUserByEmailAndPassword(email, password);
        return new UserDto.Info(user.getUserId(), user.getEmail(), user.getPassword(),
                user.getName(), user.getPhone(), user.getAddress(), user.getImage());
    }

    @Override
    public int checkEmail(String email){
        if(userRepository.findUserByEmail(email) != null) return 1;
        return 0;
    }

    @Override
    public User getLoginUserByEmail(String email) {
        if(email == null) return null;
        return userRepository.findUserByEmail(email);
    }

    @Override //detailGroupPost 기능을 위해 추가
    public UserDto.Info getUserByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        return new UserDto.Info(user.getUserId(), user.getEmail(), user.getPassword(),
                user.getName(), user.getPhone(), user.getAddress(), user.getImageUrl());
    }

    /**
     *  로그인 기능
     *  화면에서 LoginRequest(email, password)을 입력받아 email, password가 일치하면 User return
     *  email이 존재하지 않거나 password가 일치하지 않으면 null return
     */

    @Override
    public User login(UserDto.LoginRequest req) {
        User user = userRepository.findUserByEmail(req.getEmail());

        // email과 일치하는 User가 없으면 null return
        if (user == null) {
            return null;
        }

        // 찾아온 User의 password와 입력된 password가 다르면 null return
        if (!user.getPassword().equals(req.getPassword())) {
            return null;
        }
        return user;
    }

    @Transactional(readOnly = true)
    public UserDto.Response getUserInfo(Long userId) {
        User userEntity = userRepository.findById(userId).orElseThrow();
        return new UserDto.Response(userEntity);
    }
}
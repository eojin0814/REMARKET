package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.SecondHandDto;
import com.softwareapplication.remarket.repository.SecondHandRepository;
import com.softwareapplication.remarket.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecondHandService {
    private final SecondHandRepository secondHandRepository;
    private final UserRepository userRepository;
    @Transactional
    public Long save(SecondHandDto secondHandDto) {
        User user = userRepository.findById(secondHandDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + secondHandDto.getUserId()));


        return secondHandRepository.save(secondHandDto.toEntity(user)).getSecondHandId();
    }

    public SecondHand findById(Long id) {
       return secondHandRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id:"+id));
    }
    public List<SecondHand> findByAll() {
        return secondHandRepository.findAll();
    }
    @Transactional
    public Long update(Long id, SecondHandDto secondHandDto){
        SecondHand secondHand= secondHandRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id:"+id));
        //secondHand.update(secondHandDto.toEntity());
        return id;
    }

    @Transactional
    public Long delete(Long id){
        SecondHand secondHand= secondHandRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id:"+id));
        secondHandRepository.deleteById(id);
        return id;
    }

    @Transactional
    public List<SecondHand> search(String keyword) {
        List<SecondHand> postList = secondHandRepository.findByTitleContaining(keyword);
        return postList;
    }

}

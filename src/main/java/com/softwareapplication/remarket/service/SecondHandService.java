package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.dto.SecondHandDto;
import com.softwareapplication.remarket.repository.SecondHandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecondHandService {
    private final SecondHandRepository secondHandRepository;

    @Transactional
    public Long save(SecondHandDto secondHandDto) {
        return secondHandRepository.save(secondHandDto.toEntity()).getSecondHandId();
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
        secondHand.update(secondHandDto.getTitle(),secondHandDto.getImage(),secondHandDto.getContent());
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

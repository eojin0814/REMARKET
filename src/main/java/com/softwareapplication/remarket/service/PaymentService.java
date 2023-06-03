package com.softwareapplication.remarket.service;

import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.OrderDto;
import com.softwareapplication.remarket.dto.PaymentDto;
import com.softwareapplication.remarket.dto.SecondHandDto;
import com.softwareapplication.remarket.repository.PaymentRepository;
import com.softwareapplication.remarket.repository.SecondHandRepository;
import com.softwareapplication.remarket.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final SecondHandRepository secondHandRepository;
    @Transactional
    public Long save(String email, OrderDto orderdto, Long secondHandId) {
        User user = userRepository.findUserByEmail(email);
        orderdto.setUserId(user);
        SecondHand secondHand=secondHandRepository.findById(secondHandId).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id:"+secondHandId));
        orderdto.setSecondHandId(secondHand);
        return paymentRepository.save(orderdto.toEntity()).getOrderId();
    }
}

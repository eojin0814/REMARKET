package com.softwareapplication.remarket.dto;

import com.softwareapplication.remarket.domain.Order;
import com.softwareapplication.remarket.domain.Payment;
import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long orderId;

    private Date createdDate;

    private User userId;

    private Payment payment;

    private String address;

    private SecondHand secondHandId;

    public Order toEntity(){
        return Order.builder()
                .orderId(orderId)
                .createdDate(createdDate)
                .user(userId)
                .payment(payment)
                .address(address)
                .secondHandId(secondHandId)
                .build();
    }
}

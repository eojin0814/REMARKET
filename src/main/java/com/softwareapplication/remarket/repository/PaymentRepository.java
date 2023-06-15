package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.Order;
import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.dto.OrderDto;
import com.softwareapplication.remarket.dto.PaymentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);
}

package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.Order;
import com.softwareapplication.remarket.dto.OrderDto;
import com.softwareapplication.remarket.dto.PaymentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Order,Long> {
}

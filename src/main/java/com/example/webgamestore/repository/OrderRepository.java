package com.example.webgamestore.repository;

import com.example.webgamestore.model.Order;
import com.example.webgamestore.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
} 
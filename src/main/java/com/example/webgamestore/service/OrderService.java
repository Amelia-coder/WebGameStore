package com.example.webgamestore.service;

import com.example.webgamestore.model.Order;
import com.example.webgamestore.model.OrderStatus;
import com.example.webgamestore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrdersByUserAndStatus(Long userId, OrderStatus status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
} 
package com.sims.order.service;

import com.sims.order.entity.Order;
import com.sims.order.kafka.OrderProducer;
import com.sims.order.repository.OrderRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository, @Lazy OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);

        // Send Order Event to Kafka
        orderProducer.sendOrderEvent(new com.sims.order.event.OrderEvent(
                savedOrder.getId(),
                savedOrder.getProductId(),
                savedOrder.getQuantity(),
                savedOrder.getStatus()
        ));

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 🔹 FIX: Add getOrderById method
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // 🔹 FIX: Add deleteOrder method
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

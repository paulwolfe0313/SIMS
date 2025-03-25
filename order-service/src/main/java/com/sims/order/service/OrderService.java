package com.sims.order.service;

import com.sims.order.entity.Order;
import com.sims.order.event.OrderEvent;
import com.sims.order.kafka.OrderProducer;
import com.sims.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProducer orderProducer;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        // Set initial status
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);

        // Send order created event
        OrderEvent orderEvent = new OrderEvent(
            savedOrder.getId(),
            savedOrder.getProductId(),
            savedOrder.getQuantity(),
            savedOrder.getStatus()
        );
        orderProducer.sendOrderEvent(orderEvent);

        return savedOrder;
    }

    public Order cancelOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .filter(Order::canCancel)
                .map(order -> {
                    order.setStatus("CANCELLED");
                    Order cancelledOrder = orderRepository.save(order);

                    // Send order cancelled event
                    OrderEvent orderEvent = new OrderEvent(
                        cancelledOrder.getId(),
                        cancelledOrder.getProductId(),
                        cancelledOrder.getQuantity(),
                        cancelledOrder.getStatus()
                    );
                    orderProducer.sendOrderEvent(orderEvent);

                    return cancelledOrder;
                })
                .orElseThrow(() -> new RuntimeException("Order cannot be cancelled"));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public void updateOrderStatus(Long orderId, String status) {
        orderRepository.findById(orderId)
                .ifPresent(order -> {
                    order.setStatus(status);
                    Order updatedOrder = orderRepository.save(order);

                    // Send order status update event
                    OrderEvent orderEvent = new OrderEvent(
                        updatedOrder.getId(),
                        updatedOrder.getProductId(),
                        updatedOrder.getQuantity(),
                        updatedOrder.getStatus()
                    );
                    orderProducer.sendOrderEvent(orderEvent);
                });
    }
}

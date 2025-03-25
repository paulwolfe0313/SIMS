package com.sims.order.controller;

import com.sims.order.entity.Order;
import com.sims.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Order> createOrder(@RequestBody Order order, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId != null) {
            order.setUserId(Long.parseLong(userId));
        }
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId != null && !hasRole(request, "ADMIN")) {
            // Regular users can only see their own orders
            return ResponseEntity.ok(orderService.getOrdersByUserId(Long.parseLong(userId)));
        }
        // Admins can see all orders
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Check if user has access to this order
        if (!hasRole(request, "ADMIN") && !order.getUserId().toString().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Check if user has access to cancel this order
        if (!hasRole(request, "ADMIN") && !order.getUserId().toString().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    private boolean hasRole(HttpServletRequest request, String role) {
        return request.isUserInRole("ROLE_" + role);
    }
}

package com.sims.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)  // Ensure productId is mandatory
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, length = 20)  // Enforce constraints
    private String status = "Pending"; // Default value

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now(); // Default value

    // Constructor for creating an order
    public Order(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderDate = LocalDateTime.now();
        this.status = "Pending";
    }
}

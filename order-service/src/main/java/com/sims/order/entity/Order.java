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

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, length = 20)
    private String status = "PENDING";

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    private Double totalAmount;

    public Order(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    public boolean isPending() {
        return "PENDING".equals(status);
    }

    public boolean canCancel() {
        return "PENDING".equals(status) || "PROCESSING".equals(status);
    }
}

package com.sims.notification.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private Long orderId;
    private Long productId;
    private Long userId;
    private Integer quantity;
    private String status;
    private Double totalAmount;
    private String orderDate;

    // Constructor for basic order events
    public OrderEvent(Long orderId, Long productId, Integer quantity, String status) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }
}

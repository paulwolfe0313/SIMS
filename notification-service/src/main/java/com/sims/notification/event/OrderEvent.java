package com.sims.notification.event;

import java.io.Serializable;

public class OrderEvent implements Serializable {
    private Long orderId;
    private Long productId;
    private int quantity;
    private String status;

    public OrderEvent() {}

    public OrderEvent(Long orderId, Long productId, int quantity, String status) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }

    // Getters & Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                '}';
    }
}

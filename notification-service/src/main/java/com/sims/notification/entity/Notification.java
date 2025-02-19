package com.sims.notification.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String status;
    private String message; // ✅ New field for message
    private LocalDateTime timestamp; // ✅ New field for timestamp

    public Notification() {
        this.timestamp = LocalDateTime.now();
    }

    public Notification(Long orderId, String status, String message) {
        this.orderId = orderId;
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; } // ✅ Getter for message
    public void setMessage(String message) { this.message = message; } // ✅ Setter for message

    public LocalDateTime getTimestamp() { return timestamp; } // ✅ Getter for timestamp
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; } // ✅ Setter for timestamp
}

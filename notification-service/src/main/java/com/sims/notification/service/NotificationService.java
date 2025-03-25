package com.sims.notification.service;

import com.sims.notification.entity.Notification;
import com.sims.notification.event.OrderEvent;
import com.sims.notification.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification markAsRead(Long id) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notification.setRead(true);
                    return notificationRepository.save(notification);
                })
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    public void handleOrderEvent(OrderEvent event) {
        try {
            Notification notification = Notification.builder()
                .type("ORDER_UPDATE")
                .userId(event.getUserId())
                .orderId(event.getOrderId())
                .productId(event.getProductId())
                .status(event.getStatus())
                .message(generateOrderMessage(event))
                .additionalData(String.format(
                    "Quantity: %d, Total Amount: %.2f",
                    event.getQuantity(),
                    event.getTotalAmount() != null ? event.getTotalAmount() : 0.0
                ))
                .build();

            notificationRepository.save(notification);
            logger.info("Created notification for order: {} and user: {}", 
                event.getOrderId(), event.getUserId());
        } catch (Exception e) {
            logger.error("Error processing order event: {}", e.getMessage(), e);
        }
    }

    public void processWebhook(String provider, String payload) {
        try {
            // Handle different webhook providers
            switch (provider.toLowerCase()) {
                case "payment":
                    processPaymentWebhook(payload);
                    break;
                case "shipping":
                    processShippingWebhook(payload);
                    break;
                default:
                    logger.warn("Unknown webhook provider: {}", provider);
            }
        } catch (Exception e) {
            logger.error("Error processing webhook from {}: {}", provider, e.getMessage(), e);
        }
    }

    private String generateOrderMessage(OrderEvent event) {
        String baseMessage = switch (event.getStatus()) {
            case "PENDING" -> "Your order has been received and is pending processing";
            case "PROCESSING" -> "Your order is being processed";
            case "SHIPPED" -> "Your order has been shipped";
            case "DELIVERED" -> "Your order has been delivered";
            case "CANCELLED" -> "Your order has been cancelled";
            default -> "Your order status has been updated to: " + event.getStatus();
        };

        return String.format("%s (Order #%d)", baseMessage, event.getOrderId());
    }

    private void processPaymentWebhook(String payload) {
        // Implementation for payment webhook processing
        logger.info("Processing payment webhook");
        // Add implementation details based on your payment provider
    }

    private void processShippingWebhook(String payload) {
        // Implementation for shipping webhook processing
        logger.info("Processing shipping webhook");
        // Add implementation details based on your shipping provider
    }
}

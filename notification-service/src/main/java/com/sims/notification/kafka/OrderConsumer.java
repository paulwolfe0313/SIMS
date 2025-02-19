package com.sims.notification.kafka;

import com.sims.notification.entity.Notification;
import com.sims.notification.service.NotificationService;
import com.sims.notification.event.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;

@Service
public class OrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);
    private final NotificationService notificationService;

    @Autowired
    public OrderConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void consume(OrderEvent event) {
        logger.info("📩 Received Order Event: {}", event);

        try {
            // ✅ Create Notification entity
            Notification notification = new Notification();
            notification.setOrderId(event.getOrderId());
            notification.setStatus("Order " + event.getOrderId() + " is now " + event.getStatus());
            notification.setMessage("Your order status has been updated to: " + event.getStatus());
            notification.setTimestamp(LocalDateTime.now());

            logger.info("📌 Preparing to save: {}", notification);

            // ✅ Save notification
            Notification savedNotification = notificationService.saveNotification(notification);
            logger.info("✅ Notification successfully saved: {}", savedNotification);

        } catch (Exception e) {
            logger.error("❌ Failed to process Order Event: {}", event, e);
        }
    }
}

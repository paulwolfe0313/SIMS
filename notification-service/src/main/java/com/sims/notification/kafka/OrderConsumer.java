package com.sims.notification.kafka;

import com.sims.notification.event.OrderEvent;
import com.sims.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(
        topics = "${notification.topic.name}",
        groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderEvent event) {
        try {
            logger.info("Received order event: {}", event);
            notificationService.handleOrderEvent(event);
        } catch (Exception e) {
            logger.error("Error processing order event: {}", e.getMessage(), e);
        }
    }
}

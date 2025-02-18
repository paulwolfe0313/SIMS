package com.sims.notification.kafka;

import com.sims.notification.event.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        System.out.println("📩 Received Order Event: " + orderEvent);
        // Here you can add logic to store the notification in the database
    }
}

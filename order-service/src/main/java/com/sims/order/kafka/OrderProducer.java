package com.sims.order.kafka;


import com.sims.order.event.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent orderEvent) {
        kafkaTemplate.send("order-events", orderEvent);
        System.out.println("📨 Sent Order Event: " + orderEvent);
    }
}

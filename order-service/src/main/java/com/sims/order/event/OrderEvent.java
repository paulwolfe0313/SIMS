package com.sims.order.event;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderEvent implements Serializable {
    private Long orderId;
    private Long productId;
    private int quantity;
    private String status;
}

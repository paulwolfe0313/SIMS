package com.sims.notification.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) // ✅ Ignore unknown JSON properties
public class OrderEvent implements Serializable {
    private Long orderId;
    private Long productId;
    private int quantity;
    private String status;
}

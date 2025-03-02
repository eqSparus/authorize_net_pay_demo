package com.bytepace.antiland.antiland_pay_demo.domain;

import com.bytepace.antiland.antiland_pay_demo.database.entities.PaymentDb;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
@Getter
public class Payment {
    UUID id;
    String orderId;
    BigDecimal amount;
    String productId;
    String transId;
    PaymentStatus status;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}

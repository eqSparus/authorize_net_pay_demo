package com.bytepace.antiland.antiland_pay_demo.models;

import com.bytepace.antiland.antiland_pay_demo.database.entities.PaymentDb;
import com.bytepace.antiland.antiland_pay_demo.domain.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class PaymentDto {
    UUID id;
    String orderId;
    BigDecimal amount;
    String productId;
    String transId;
    PaymentStatus status;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;

    public static final PaymentDto of(PaymentDb payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getProductId(),
                payment.getTransId(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}

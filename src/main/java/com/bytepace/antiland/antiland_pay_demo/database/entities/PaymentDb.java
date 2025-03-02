package com.bytepace.antiland.antiland_pay_demo.database.entities;

import com.bytepace.antiland.antiland_pay_demo.domain.Payment;
import com.bytepace.antiland.antiland_pay_demo.domain.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "payments", schema = "public")
@Entity
public class PaymentDb implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id", nullable = false)
    UUID id;

    @Column(name = "order_id", nullable = false, unique = true)
    String orderId;

    @Column(name = "amount", nullable = false)
    BigDecimal amount;

    @Column(name = "product_id", nullable = false)
    String productId;

    @Column(name = "trans_id")
    String transId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    PaymentStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    OffsetDateTime updatedAt;

    public static final PaymentDb of(Payment payment) {
        return new PaymentDb(
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

    public Payment toPayment() {
        return new Payment(
                id,
                orderId,
                amount,
                productId,
                transId,
                status,
                createdAt,
                updatedAt
        );
    }
}

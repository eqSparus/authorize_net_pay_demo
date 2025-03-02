package com.bytepace.antiland.antiland_pay_demo.database.repositories;

import com.bytepace.antiland.antiland_pay_demo.database.entities.PaymentDb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentDb, UUID> {

    Optional<PaymentDb> findByOrderId(String orderId);

}

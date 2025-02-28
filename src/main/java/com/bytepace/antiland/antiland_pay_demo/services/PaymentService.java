package com.bytepace.antiland.antiland_pay_demo.services;

import com.bytepace.antiland.antiland_pay_demo.models.PaymentDto;

public interface PaymentService {

    PaymentDto processBuy(Double amount);

}

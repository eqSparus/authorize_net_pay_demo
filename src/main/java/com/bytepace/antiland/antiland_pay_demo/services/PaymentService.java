package com.bytepace.antiland.antiland_pay_demo.services;

import com.bytepace.antiland.antiland_pay_demo.models.AuthorizeNetRequest;
import com.bytepace.antiland.antiland_pay_demo.models.PaymentDto;
import com.bytepace.antiland.antiland_pay_demo.models.TokenDto;

public interface PaymentService {

    TokenDto getAuthToken(Double amount, String productId);

    PaymentDto getPayment(String orderId);

    void updateStatus(AuthorizeNetRequest request);
}

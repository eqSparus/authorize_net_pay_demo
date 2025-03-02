package com.bytepace.antiland.antiland_pay_demo.controllers;

import com.bytepace.antiland.antiland_pay_demo.models.AuthorizeNetRequest;
import com.bytepace.antiland.antiland_pay_demo.models.PaymentDto;
import com.bytepace.antiland.antiland_pay_demo.models.TokenDto;
import com.bytepace.antiland.antiland_pay_demo.models.PaymentRequest;
import com.bytepace.antiland.antiland_pay_demo.services.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    PaymentService paymentService;

    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping(
            path = "/token",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public TokenDto getAuthToken(@RequestBody PaymentRequest request) {
        return paymentService.getAuthToken(request.getAmount(), request.getProductId());
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(
            params = {"orderId"},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public PaymentDto getPaymentByOrderId(@RequestParam(name = "orderId") String orderId) {
        return paymentService.getPayment(orderId);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping(
            path = "/webhook",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public void paymentWebhook(@RequestBody AuthorizeNetRequest request) {
        paymentService.updateStatus(request);
    }

}

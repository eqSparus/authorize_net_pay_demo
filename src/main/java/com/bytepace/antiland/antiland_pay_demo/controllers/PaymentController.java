package com.bytepace.antiland.antiland_pay_demo.controllers;

import com.bytepace.antiland.antiland_pay_demo.models.PaymentDto;
import com.bytepace.antiland.antiland_pay_demo.models.PaymentRequest;
import com.bytepace.antiland.antiland_pay_demo.services.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
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
    public PaymentDto buyRouter(@RequestBody PaymentRequest request) {
        return paymentService.processBuy(request.getAmount());
    }

}

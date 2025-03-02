package com.bytepace.antiland.antiland_pay_demo.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ToString
@Getter
public class PaymentRequest {

    Double amount;
    String productId;

    @JsonCreator
    public PaymentRequest(
            @JsonProperty(value = "amount", required = true) Double amount,
            @JsonProperty(value = "productId", required = true) String productId) {
        this.amount = amount;
        this.productId = productId;
    }
}

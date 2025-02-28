package com.bytepace.antiland.antiland_pay_demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Value
@ToString
@Builder
public class PaymentDto {
    @JsonProperty("message")
    String message;
    @JsonProperty("token")
    String token;
}
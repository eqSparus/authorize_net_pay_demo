package com.bytepace.antiland.antiland_pay_demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ToString
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ErrorDto {
    @JsonProperty("message")
    String message;
    @JsonProperty("status")
    int status;
}

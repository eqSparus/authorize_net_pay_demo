package com.bytepace.antiland.antiland_pay_demo.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ToString
@Getter
public class AuthorizeNetRequest {

    String transId;
    String transactionStatus;
    

    @JsonCreator
    public AuthorizeNetRequest(
            @JsonProperty(value = "transId", required = true) String transId,
            @JsonProperty(value = "transactionStatus", required = true) String transactionStatus) {
        this.transId = transId;
        this.transactionStatus = transactionStatus;
    }

}

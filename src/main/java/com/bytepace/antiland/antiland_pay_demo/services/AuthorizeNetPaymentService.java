package com.bytepace.antiland.antiland_pay_demo.services;

import com.bytepace.antiland.antiland_pay_demo.models.PaymentDto;
import com.bytepace.antiland.antiland_pay_demo.services.exception.ApiPaymentException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.GetHostedPaymentPageController;
import net.authorize.api.controller.base.ApiOperationBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class AuthorizeNetPaymentService implements PaymentService {

    @Value("${authorizenet.apiLoginId}")
    String apiLoginId;

    @Value("${authorizenet.transactionKey}")
    String apiTransactionKey;

    @Override
    public PaymentDto processBuy(Double amount) {
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        var merchantAuthenticationType = new MerchantAuthenticationType();
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(apiTransactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        var txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        txnRequest.setAmount(new BigDecimal(amount).setScale(2, RoundingMode.CEILING));

        var apiRequest = new GetHostedPaymentPageRequest();
        apiRequest.setTransactionRequest(txnRequest);
        apiRequest.setHostedPaymentSettings(getArrayOfSetting());

        var controller = new GetHostedPaymentPageController(apiRequest);
        controller.execute();

        var response = controller.getApiResponse();

        if (response != null) {
            var code = response.getMessages().getResultCode();
            if (code == MessageTypeEnum.OK) {
                return PaymentDto.builder()
                        .message("payment_page_success")
                        .token(response.getToken())
                        .build();
            } else {
                throw new ApiPaymentException("payment_page_failed_code_" + code);
            }
        } else {
            throw new ApiPaymentException("payment_page_failed_response_null");
        }
    }

    private static ArrayOfSetting getArrayOfSetting() {
        var setting = new SettingTypes();
        setting.append("hostedPaymentReturnOptions", "{\"showReceipt\": true, \"url\": \"https://bhupeshpadiyar.com/poc/authnet/success.html\", \"urlText\": \"Continue\", \"cancelUrl\": \"https://bhupeshpadiyar.com/poc/authnet/error.html\", \"cancelUrlText\": \"Cancel\"}");
        setting.append("hostedPaymentButtonOptions", "{\"text\": \"Pay\"}");
        setting.append("hostedPaymentOrderOptions", "{\"show\": false}");
        setting.append("hostedPaymentStyleOptions", "{\"bgColor\": \"blue\"}");
        setting.append("hostedPaymentPaymentOptions", "{\"cardCodeRequired\": false, \"showCreditCard\": true, \"showBankAccount\": true}");
        setting.append("hostedPaymentSecurityOptions", "{\"captcha\": true}");
        setting.append("hostedPaymentShippingAddressOptions", "{\"show\": true, \"required\": true}");
        setting.append("hostedPaymentBillingAddressOptions", "{\"show\": true, \"required\": true}");
        setting.append("hostedPaymentCustomerOptions", "{\"showEmail\": true, \"requiredEmail\": true, \"addPaymentProfile\": true}");
        setting.append("hostedPaymentOrderOptions", "{\"show\": true, \"merchantName\": \"BhupeshPadiyar.Com Inc.\"}");
        setting.append("hostedPaymentIFrameCommunicatorUrl", "{\"url\": \"https://bhupeshpadiyar.com/\"}");
        return setting.getArrayOfSetting();
    }
}
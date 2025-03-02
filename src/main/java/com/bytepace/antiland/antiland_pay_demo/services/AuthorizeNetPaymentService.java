package com.bytepace.antiland.antiland_pay_demo.services;

import com.bytepace.antiland.antiland_pay_demo.database.entities.PaymentDb;
import com.bytepace.antiland.antiland_pay_demo.database.repositories.PaymentRepository;
import com.bytepace.antiland.antiland_pay_demo.domain.PaymentStatus;
import com.bytepace.antiland.antiland_pay_demo.models.AuthorizeNetRequest;
import com.bytepace.antiland.antiland_pay_demo.models.PaymentDto;
import com.bytepace.antiland.antiland_pay_demo.models.TokenDto;
import com.bytepace.antiland.antiland_pay_demo.services.exception.ApiPaymentException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.GetHostedPaymentPageController;
import net.authorize.api.controller.base.ApiOperationBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
@AllArgsConstructor
public class AuthorizeNetPaymentService implements PaymentService {

    @Value("${authorizenet.apiLoginId}")
    String apiLoginId;

    @Value("${authorizenet.transactionKey}")
    String apiTransactionKey;

    final PaymentRepository paymentRepository;

    @Override
    public TokenDto getAuthToken(Double amount, String productId) {
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        var merchantAuthenticationType = new MerchantAuthenticationType();
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(apiTransactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        var txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        var amountBigDecimal = new BigDecimal(amount).setScale(2, RoundingMode.CEILING);
        txnRequest.setAmount(amountBigDecimal);

        var apiRequest = new GetHostedPaymentPageRequest();
        apiRequest.setTransactionRequest(txnRequest);
        apiRequest.setHostedPaymentSettings(getArrayOfSetting());

        var controller = new GetHostedPaymentPageController(apiRequest);
        controller.execute();

        var response = controller.getApiResponse();

        if (response != null) {
            var code = response.getMessages().getResultCode();
            if (code == MessageTypeEnum.OK) {
                var orderId = UUID.randomUUID().toString();
                var payment = PaymentDb.builder()
                        .orderId(orderId)
                        .amount(amountBigDecimal)
                        .productId(productId)
                        .status(PaymentStatus.AUTHORIZED)
                        .build();
                paymentRepository.save(payment);
                return TokenDto.builder()
                        .message("payment_page_success")
                        .token(response.getToken())
                        .orderId(orderId)
                        .build();
            } else {
                System.err.println(response.getMessages().getMessage().get(0).getText());
                throw new ApiPaymentException("payment_page_failed_code_" + code);
            }
        } else {
            throw new ApiPaymentException("payment_page_failed_response_null");
        }
    }

    @Override
    public PaymentDto getPayment(String orderId) {
        var paymentDb = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ApiPaymentException("payment_not_found"));
        return PaymentDto.of(paymentDb);
    }

    @Override
    public void updateStatus(AuthorizeNetRequest request) {

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
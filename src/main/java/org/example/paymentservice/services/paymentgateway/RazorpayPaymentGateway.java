package org.example.paymentservice.services.paymentgateway;

import com.razorpay.PaymentLink;
import org.example.paymentservice.dtos.OrderDto;
import org.example.paymentservice.dtos.UserDto;
import org.example.paymentservice.thirdpartyclients.orderservice.OrderServiceClient;
import org.example.paymentservice.thirdpartyclients.userservice.UserServiceClient;
import org.json.JSONObject;
import com.razorpay.RazorpayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;

@Service
public class RazorpayPaymentGateway implements PaymentGateway {

    private String apikey;
    private String secret;
    private OrderServiceClient orderServiceClient;
    private UserServiceClient userServiceClient;

    public RazorpayPaymentGateway(@Value("${razorpay.apikey}") String apikey, @Value("${razorpay.secret}") String secret, OrderServiceClient orderServiceClient, UserServiceClient userServiceClient){
        this.apikey = apikey;
        this.secret = secret;
        this.orderServiceClient = orderServiceClient;
        this.userServiceClient = userServiceClient;
    }

    private int getExpiry() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDateTime tomorrowStartOfDay = tomorrow.atStartOfDay();
        Instant instant = tomorrowStartOfDay.toInstant(ZoneId.systemDefault().getRules().getOffset(tomorrowStartOfDay));
        long epochTime = instant.getEpochSecond();
        return (int) epochTime;
    }

    @Override
    public String generateLink(long orderId) {

        OrderDto order = orderServiceClient.getOrderById(orderId);
        UserDto user = userServiceClient.getUserById(order.getUserId());

        try {
            RazorpayClient razorpay =
                    new RazorpayClient(apikey, secret);
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",order.getAmount());
            paymentLinkRequest.put("currency","INR");
            paymentLinkRequest.put("accept_partial",false);
            paymentLinkRequest.put("expire_by",getExpiry());
            paymentLinkRequest.put("reference_id",String.valueOf(order.getId()));
            paymentLinkRequest.put("description","Payment for policy no #23456");

            JSONObject customer = new JSONObject();
            customer.put("contact", user.getPhoneno());
            customer.put("name",user.getName());
            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify = new JSONObject();
            notify.put("sms",true);
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("callback_url","https://google.com/");
            paymentLinkRequest.put("callback_method","get");

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            return payment.toString();
        } catch(Exception e) {
            System.out.println(e);
        }

        return null;
    }
}

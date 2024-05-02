package com.aru.controllers;

import com.aru.models.PlanType;
import com.aru.models.User;
import com.aru.responses.PaymentLinkResponse;
import com.aru.services.UserService;
import com.google.gson.JsonObject;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.stripe.Stripe;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${STRIPE_API_PUBLIC_KEY}")
    private String stripePublicKey;

    @Value("${STRIPE_API_SECRET_KEY}")
    private String stripeSecretKey;

    @Autowired
    private UserService userService;

    @PostMapping("$/{planType}")
    public ResponseEntity <PaymentLinkResponse> createPaymentLink (
            @PathVariable PlanType planType,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        int amount = 799;

        if (planType.equals(PlanType.ANNUALLY)) {
            amount = amount*12;
            amount = (int)(amount*0.7); //30% discount
        }

            RazorpayClient razorpay = new RazorpayClient(stripePublicKey,stripeSecretKey);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer = new JSONObject();
            customer.put("name",user.getFullName());
            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify = new JSONObject();
            notify.put("email",true);
            notify.put("phone",false);
            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("callback_url","http://localhost:5173/upgrage_plan/success?planType"+planType);

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse response = new PaymentLinkResponse();
            response.setPayment_link_url(paymentLinkUrl);
            response.setPayment_link_id(paymentLinkId);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

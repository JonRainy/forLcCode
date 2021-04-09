package com.direct.project.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
public class PayController {

    static Jedis jedis = new Jedis("127.0.0.1", 6379);
    static {
        Stripe.apiKey = "sk_test_51IMarYLsuGPYGhOQBpcFbzA4eeDgSAeXTgWaVvBZGQ1dZNjmo2Gv9FBTkXeyxFLHAnZ9j3vPv13iovwIFRsBcxNe008A5WudVI";
    }

    @ResponseBody
    @PostMapping(value = {"/pay/create-payment-intent"})
    public Map<String, Object> paymentOrder(
            @RequestBody Map<String, String> map,
            HttpServletResponse response
    ) throws StripeException {
        return payments(map, response);
    }

    @ResponseBody
    @PostMapping(value = {"/aliPay/create-payment-intent"})
    public Map<String, Object> paymentAliOrder(
            @RequestBody Map<String, String> map,
            HttpServletResponse response
    ) throws StripeException {
        return charge(map, response);
    }

    @ResponseBody
    @PostMapping(value = {"/source/email"})
    public void sourceEmail(
            @RequestBody Map<String, String> map
    ) throws StripeException {
        jedis.set(map.get("id"), map.get("email"));
    }


    public Map<String, Object> charge(
            Map<String, String> map,
            HttpServletResponse response
    ) throws StripeException {
        ChargeCreateParams chargeCreateParams = new ChargeCreateParams.Builder()
                .setAmount(Long.decode(map.get("price")))
                .setCurrency("hkd")
                .setSource(map.get("id"))
                .setReceiptEmail(jedis.get(map.get("id")))
                .build();
        Charge charge = Charge.create(chargeCreateParams);

        Map<String, Object> responseData = new HashMap<>();
        response.setStatus(200);
        responseData.put("success", true);

        return responseData;

    }

    public Map<String, Object> payments(
            Map<String, String> map,
            HttpServletResponse response
    ) throws StripeException {

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setAmount(Long.decode(map.get("price")))
                .setCurrency("hkd")
                .setDescription("buy: "+map.get("email")+",orderId: "+ map.get("order_no") + ", price: " + map.get("price"))
                .putMetadata("order_id", map.get("order_no"))
                .putMetadata("money", map.get("price"))
                .addPaymentMethodType("card")
                .setReceiptEmail(map.get("email"))
                .build();
        PaymentIntent intent = PaymentIntent.create(createParams);
        return buildResponse(response, intent);
    }

    private Map<String, Object> buildResponse(HttpServletResponse response, PaymentIntent intent) {
        Map<String, Object> responseData = new HashMap<>();
        if (intent.getStatus().equals("succeeded")) {
            // Handle post-payment fulfillment
            response.setStatus(200);
            responseData.put("success", true);
        } else {
            // Any other status would be unexpected, so error
            response.setStatus(500);
            responseData.put("error", "Invalid PaymentIntent status");
        }
        return responseData;
    }


}
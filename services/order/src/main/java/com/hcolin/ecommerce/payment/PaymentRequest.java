package com.hcolin.ecommerce.payment;

import com.hcolin.ecommerce.customer.CustomerResponse;
import com.hcolin.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
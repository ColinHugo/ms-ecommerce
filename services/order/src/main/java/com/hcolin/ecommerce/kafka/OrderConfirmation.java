package com.hcolin.ecommerce.kafka;

import com.hcolin.ecommerce.customer.CustomerResponse;
import com.hcolin.ecommerce.order.PaymentMethod;
import com.hcolin.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (

        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List< PurchaseResponse > products

) {
}
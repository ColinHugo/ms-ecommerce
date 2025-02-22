package com.hcolin.ecommerce.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping( "/api/v1/payments" )
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public ResponseEntity< Integer > createPayment( @Valid @RequestBody PaymentRequest request ) {
        return ResponseEntity.ok( this.service.createPayment( request ) );
    }

}
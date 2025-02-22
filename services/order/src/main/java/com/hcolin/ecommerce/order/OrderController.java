package com.hcolin.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/orders" )
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity< Integer > createOrder( @Valid @RequestBody OrderRequest request ) {
        return ResponseEntity.ok( this.orderService.createOrder( request ) );
    }

    @GetMapping
    public ResponseEntity< List< OrderResponse > > findAll() {
        return ResponseEntity.ok( this.orderService.findAllOrders() );
    }

    @GetMapping( "/{orderId}" )
    public ResponseEntity< OrderResponse > findById( @PathVariable Integer orderId ) {
        return ResponseEntity.ok( this.orderService.findById( orderId ) );
    }

}
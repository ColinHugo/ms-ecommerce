package com.hcolin.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping( "/api/v1/products" )
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity< Integer > createProduct( @Valid @RequestBody ProductRequest request ) {
        return ResponseEntity.ok( productService.createProduct( request ) );
    }

    @PostMapping( "/purchase" )
    public ResponseEntity< List< ProductPurchaseResponse > > purchaseProducts( @RequestBody List< ProductPurchaseRequest > request ) {
        return ResponseEntity.ok( productService.purchaseProducts( request ) );
    }

    @GetMapping( "/{productId}" )
    public ResponseEntity< ProductResponse > findById( @PathVariable Integer productId ) {
        return ResponseEntity.ok( productService.findById( productId ) );
    }

    @GetMapping
    public ResponseEntity< List< ProductResponse > > findAll() {
        return ResponseEntity.ok( productService.findAll() );
    }

}
package com.hcolin.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/v1/customers" )
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity< String > createCustomer( @Valid @RequestBody CustomerRequest request ) {
        return ResponseEntity.ok( customerService.createCustomer( request ) );
    }

    @PutMapping
    public ResponseEntity< Void > updateCustomer( @Valid @RequestBody CustomerRequest request ) {

        customerService.updateCustomer( request );

        return ResponseEntity.accepted().build();

    }

    @GetMapping
    public ResponseEntity< List< CustomerResponse > > findAllCustomer() {
        return ResponseEntity.ok( customerService.findAllCustomer() );
    }

    @GetMapping( "/exists/{customerId}" )
    public ResponseEntity< Boolean > existsById( @PathVariable( "customerId" ) String customerId ) {
        return ResponseEntity.ok( customerService.existsById( customerId ) );
    }

    @GetMapping( "/{customerId}" )
    public ResponseEntity< CustomerResponse > findById( @PathVariable( "customerId" ) String customerId ) {
        return ResponseEntity.ok( customerService.findById( customerId ) );
    }

    @DeleteMapping( "/{customerId}" )
    public ResponseEntity< Void > deleteById( @PathVariable( "customerId" ) String customerId ) {

        customerService.deleteCustomer( customerId );

        return ResponseEntity.accepted().build();

    }

}
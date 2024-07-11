package com.hcolin.ecommerce.order;

import com.hcolin.ecommerce.customer.CustomerClient;
import com.hcolin.ecommerce.exception.BusinessException;
import com.hcolin.ecommerce.kafka.OrderConfirmation;
import com.hcolin.ecommerce.kafka.OrderProducer;
import com.hcolin.ecommerce.orderline.OrderLineRequest;
import com.hcolin.ecommerce.orderline.OrderLineService;
import com.hcolin.ecommerce.payment.PaymentClient;
import com.hcolin.ecommerce.payment.PaymentRequest;
import com.hcolin.ecommerce.product.ProductClient;
import com.hcolin.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder( OrderRequest request ) {

        // check the customer --> OpenFeign
        var customer = this.customerClient.findCustomerById( request.customerId() )
                .orElseThrow( () -> new BusinessException( "Cannot create order:: No customer exists with the provided ID" ) );

        // purchase the products --> product-ms ( RestTemplate )
        var purchasedProducts = productClient.purchaseProducts( request.products() );

        var order = this.orderRepository.save( orderMapper.toOrder( request ) );

        for ( PurchaseRequest purchaseRequest : request.products() ) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        paymentClient.requestOrderPayment( paymentRequest );

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();

    }

    public List< OrderResponse > findAllOrders() {

        return this.orderRepository.findAll()
                .stream()
                .map( this.orderMapper::fromOrder )
                .collect( Collectors.toList() );

    }

    public OrderResponse findById( Integer id ) {

        return this.orderRepository.findById( id )
                .map( this.orderMapper::fromOrder )
                .orElseThrow( () -> new EntityNotFoundException( "No order found with the provided ID: %d" + id ) );

    }

}
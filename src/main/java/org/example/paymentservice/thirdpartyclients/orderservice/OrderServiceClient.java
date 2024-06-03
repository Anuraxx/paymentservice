package org.example.paymentservice.thirdpartyclients.orderservice;

import org.example.paymentservice.dtos.OrderDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderServiceClient {
    public OrderDto getOrderById(long orderId) {
        // Some logic to get order by id
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);
        orderDto.setAmount(1000);
        orderDto.setUserId(1);
        List<Integer> productIds = List.of(2, 3);
        orderDto.setProductIds(productIds);
        return orderDto;
    }
}

package org.example.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private long id;
    private long userId;
    private int amount;
    private List<Integer> productIds;
}

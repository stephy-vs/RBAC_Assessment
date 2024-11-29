package com.Foodease.FoodeaseApp.wrapper;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartWrapper {
    private Integer id;
    private String dishName;
    private BigDecimal perUnitPrice;
    private String restaurantName;
    private Integer qty;
    private BigDecimal totalPrice;


    public CartWrapper(Integer id, String dishName, BigDecimal perUnitPrice, String restaurantName, Integer qty, BigDecimal totalPrice) {
        this.id = id;
        this.dishName = dishName;
        this.perUnitPrice = perUnitPrice;
        this.restaurantName = restaurantName;
        this.qty = qty;
        this.totalPrice = totalPrice;
    }
}

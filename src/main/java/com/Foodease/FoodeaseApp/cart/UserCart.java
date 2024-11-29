package com.Foodease.FoodeaseApp.cart;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "userCart")
public class UserCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "userId")
    private Integer userId;
    @Column(name = "dishId")
    private Integer dishId;
    @Column(name = "qty")
    private Integer qty;
    @Column(name = "totalPrice")
    private BigDecimal totalPrice;
    @Column(name = "isConfirm")
    private boolean isConfirm;
}

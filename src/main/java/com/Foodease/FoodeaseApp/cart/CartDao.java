package com.Foodease.FoodeaseApp.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartDao extends JpaRepository<UserCart,Integer> {
    List<UserCart> findByUserId(Integer userId);

    Optional<UserCart> findByUserIdAndId(Integer userId, Integer cartId);
}

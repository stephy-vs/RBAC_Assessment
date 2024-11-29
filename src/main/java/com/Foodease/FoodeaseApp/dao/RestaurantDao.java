package com.Foodease.FoodeaseApp.dao;

import com.Foodease.FoodeaseApp.POJO.Restaurant;
import com.Foodease.FoodeaseApp.wrapper.RestaurantWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantDao extends JpaRepository<Restaurant,Long> {
    Restaurant findByEmailId(@Param("email") String username);

    List<RestaurantWrapper> getAllRestaurant();


    @Transactional
    @Modifying
    void updateStatus(@Param("status") String status, @Param("id") Long id);
}

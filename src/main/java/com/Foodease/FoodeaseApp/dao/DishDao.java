package com.Foodease.FoodeaseApp.dao;

import com.Foodease.FoodeaseApp.POJO.Dish;
import com.Foodease.FoodeaseApp.wrapper.DishWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DishDao extends JpaRepository<Dish,Integer> {
    List<DishWrapper> getAllDish();

    @Modifying
    @Transactional
    Integer updateDishStatus(@Param("availability") String availability,@Param("id") Integer id);
}

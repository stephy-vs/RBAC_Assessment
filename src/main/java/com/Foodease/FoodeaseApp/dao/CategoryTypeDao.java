package com.Foodease.FoodeaseApp.dao;

import com.Foodease.FoodeaseApp.POJO.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryTypeDao extends JpaRepository<CategoryType,Integer> {
    List<CategoryType> getAllCategoryType();
}

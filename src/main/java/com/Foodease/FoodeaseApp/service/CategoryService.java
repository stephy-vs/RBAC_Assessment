package com.Foodease.FoodeaseApp.service;

import com.Foodease.FoodeaseApp.POJO.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    ResponseEntity<String> addNewCategory(Map<String,String> requestMap);
    ResponseEntity<List<Category>> getAllCategory(String filterValue);
}
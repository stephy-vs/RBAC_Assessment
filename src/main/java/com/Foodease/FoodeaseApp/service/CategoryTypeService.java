package com.Foodease.FoodeaseApp.service;

import com.Foodease.FoodeaseApp.POJO.CategoryType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryTypeService {
    ResponseEntity<String> addType(Map<String,String> requestMap);

    ResponseEntity<List<CategoryType>>getAllType(String filterValue);
}

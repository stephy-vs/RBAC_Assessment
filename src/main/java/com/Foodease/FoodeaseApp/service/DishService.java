package com.Foodease.FoodeaseApp.service;

import com.Foodease.FoodeaseApp.wrapper.DishWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface DishService {
    ResponseEntity<String> addNewDish(Map<String,String> requestMap);
    ResponseEntity<List<DishWrapper>> getAllDish();

    ResponseEntity<String> updateDish(Map<String,String> requestMap);

    ResponseEntity<String> deleteDish(Integer id);

    ResponseEntity<String> updateAvail(Map<String,String> requestMap);
}

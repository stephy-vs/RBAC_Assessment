package com.Foodease.FoodeaseApp.service;

import com.Foodease.FoodeaseApp.wrapper.RestaurantWrapper;
import com.Foodease.FoodeaseApp.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdminService {
    ResponseEntity<String> signUp(Map<String, String> requestMap);

    ResponseEntity<String> adminLogin(Map<String, String> requestMap);

    ResponseEntity<List<UserWrapper>> getAllUsers();

    ResponseEntity<List<RestaurantWrapper>> getAllRestaurant();

    ResponseEntity<String> updateRestaurantStatus(Map<String, String> requestMap);


    ResponseEntity<String> deleteRestaurantAccount(Long id);

    ResponseEntity<String> update(Map<String,String> requestMap);
}

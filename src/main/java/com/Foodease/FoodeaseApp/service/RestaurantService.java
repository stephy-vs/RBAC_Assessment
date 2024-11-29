package com.Foodease.FoodeaseApp.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RestaurantService {
    ResponseEntity<String> signUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);
}

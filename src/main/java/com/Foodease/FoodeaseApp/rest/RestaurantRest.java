package com.Foodease.FoodeaseApp.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/api/restaurant")
public interface RestaurantRest {
    @PostMapping(path = "/signUp")
    public ResponseEntity<String> restaurantSignup(@RequestBody(required = true)Map<String,String> requestMap);
    @PostMapping(path = "/login")
    public ResponseEntity<String>login(@RequestBody(required = true)Map<String,String> requestMap);
}

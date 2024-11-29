package com.Foodease.FoodeaseApp.rest;

import com.Foodease.FoodeaseApp.wrapper.RestaurantWrapper;
import com.Foodease.FoodeaseApp.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/api/admin")
public interface AdminRest {
    @PostMapping(path = "/signup")
    ResponseEntity<String>signup(@RequestBody(required = true)Map<String,String>requestMap);
    @PostMapping(path = "/login")
    ResponseEntity<String>login(@RequestBody(required = true)Map<String,String>requestMap);
    @GetMapping(path = "/getAllUser")
    ResponseEntity<List<UserWrapper>>getAllUser();
    @GetMapping(path = "/getAllRestaurant")
    ResponseEntity<List<RestaurantWrapper>>getAllRestaurant();
    @PutMapping(path = "/updateStatus")
    ResponseEntity<String>updateRestaurantStatus(@RequestBody(required = true)Map<String,String>requestMap);
    @DeleteMapping(path = "/deleteAccount/{id}")
    ResponseEntity<String>deleteRestaurantAccount(@PathVariable Long id);
    @PutMapping(path = "/updateUserStatus")
    public ResponseEntity<String> update(@RequestBody(required = true) Map<String,String> requestMap);

}

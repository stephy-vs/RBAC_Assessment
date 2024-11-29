package com.Foodease.FoodeaseApp.rest;

import com.Foodease.FoodeaseApp.wrapper.DishWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/dish")
public interface DishRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewDish(@RequestBody Map<String,String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<DishWrapper>> getAllDish();

    @PutMapping(path = "/update")
    ResponseEntity<String> updateDish(@RequestBody Map<String,String> requestMap);

    @DeleteMapping(path = "/delete/{id}" )
    ResponseEntity<String> deleteDish(@PathVariable Integer id);

    @PostMapping(path = "/updateAvailability")
    ResponseEntity<String> updateAvail(@RequestBody Map<String,String> requestMap);
}

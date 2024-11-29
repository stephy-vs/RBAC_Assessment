package com.Foodease.FoodeaseApp.rest;

import com.Foodease.FoodeaseApp.POJO.CategoryType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/type")
public interface CategoryTypeRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addType(@RequestBody(required = true)Map<String,String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<CategoryType>> getAllType(@RequestParam(required = false) String filterValue);
}

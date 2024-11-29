package com.Foodease.FoodeaseApp.restImpl;

import com.Foodease.FoodeaseApp.POJO.CategoryType;
import com.Foodease.FoodeaseApp.constant.RestaurantConstants;
import com.Foodease.FoodeaseApp.rest.CategoryRest;
import com.Foodease.FoodeaseApp.rest.CategoryTypeRest;
import com.Foodease.FoodeaseApp.service.CategoryTypeService;
import com.Foodease.FoodeaseApp.utils.RestaurantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryTypeRestImpl implements CategoryTypeRest {
    @Autowired
    CategoryTypeService categoryTypeService;
    @Override
    public ResponseEntity<String> addType(Map<String, String> requestMap) {
        try {
            return categoryTypeService.addType(requestMap);

        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<CategoryType>> getAllType(String filterValue) {
        try {
            return categoryTypeService.getAllType(filterValue);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

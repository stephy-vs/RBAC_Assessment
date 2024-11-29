package com.Foodease.FoodeaseApp.serviceImpl;

import com.Foodease.FoodeaseApp.JWT.JwtFilter;
import com.Foodease.FoodeaseApp.POJO.CategoryType;
import com.Foodease.FoodeaseApp.constant.RestaurantConstants;
import com.Foodease.FoodeaseApp.dao.CategoryTypeDao;
import com.Foodease.FoodeaseApp.service.CategoryTypeService;
import com.Foodease.FoodeaseApp.utils.RestaurantUtils;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CategoryTypeServiceImpl implements CategoryTypeService {
    @Autowired
    CategoryTypeDao categoryTypeDao;
    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addType(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if (validateTypeMap(requestMap,false)){
                    categoryTypeDao.save(getTypeFromMap(requestMap,false));
                    return RestaurantUtils.getResponseEntity("Type Added Successfully",HttpStatus.OK);
                }

            }else {
                return RestaurantUtils.getResponseEntity(RestaurantConstants.Unauthorized_Access,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateTypeMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private CategoryType getTypeFromMap(Map<String,String> requestMap,Boolean isAdd){
        CategoryType categoryType = new CategoryType();
        if (isAdd){
            categoryType.setId(Integer.parseInt(requestMap.get("id")));
        }
        categoryType.setName(requestMap.get("name"));
        return categoryType;
    }

    @Override
    public ResponseEntity<List<CategoryType>> getAllType(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<CategoryType>>(categoryTypeDao.getAllCategoryType(),HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryTypeDao.findAll(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

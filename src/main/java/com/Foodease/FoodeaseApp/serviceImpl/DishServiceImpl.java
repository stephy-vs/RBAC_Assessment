package com.Foodease.FoodeaseApp.serviceImpl;

import com.Foodease.FoodeaseApp.JWT.JwtFilter;
import com.Foodease.FoodeaseApp.POJO.*;
import com.Foodease.FoodeaseApp.constant.RestaurantConstants;
import com.Foodease.FoodeaseApp.dao.DishDao;
import com.Foodease.FoodeaseApp.service.DishService;
import com.Foodease.FoodeaseApp.utils.RestaurantUtils;
import com.Foodease.FoodeaseApp.wrapper.DishWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    DishDao dishDao;



    @Override
    public ResponseEntity<String> addNewDish(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isRestaurant()){
                if (validateDishMap(requestMap,false)){
                    dishDao.save(getDishFromMap(requestMap,false));
                    return RestaurantUtils.getResponseEntity("Dish Added Successfully",HttpStatus.OK);

                }


            }else {
                return RestaurantUtils.getResponseEntity(RestaurantConstants.Invalid_Data,HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }




    private boolean validateDishMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Dish getDishFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category1 = new Category();
        category1.setId(Integer.parseInt(requestMap.get("categ_Id")));

        CategoryType categoryType1 = new CategoryType();
        categoryType1.setId(Integer.parseInt(requestMap.get("catType")));

        Restaurant restaurant = new Restaurant();
        restaurant.setId(Integer.parseInt(requestMap.get("rest_Id")));


        Dish dish = new Dish();
        if (isAdd){
            dish.setId(Integer.parseInt(requestMap.get("id")));
        }else {
            dish.setAvailability("true");
        }
        dish.setCategory(category1);
        dish.setCategoryType(categoryType1);
        dish.setRestaurant(restaurant);
        dish.setName(requestMap.get("name"));
        dish.setDescription(requestMap.get("description"));
        dish.setPrice(new BigDecimal(requestMap.get("price")));
        dish.setAvailability(requestMap.get("availability"));

        //dish.setImageData(requestMap.get("imageData").getBytes());
        return dish;

    }

    @Override
    public ResponseEntity<List<DishWrapper>> getAllDish() {
        try {
            return new ResponseEntity<>(dishDao.getAllDish(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateDish(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isRestaurant()){
                if (validateDishMap(requestMap,true)){
                   Optional<Dish> optional= dishDao.findById(Integer.parseInt(requestMap.get("id")));
                   if (!optional.isEmpty()){
                       Dish dish = getDishFromMap(requestMap,true);
                       dish.setAvailability(optional.get().getAvailability());
                       dishDao.save(dish);
                       return RestaurantUtils.getResponseEntity("Dish updated Successfully",HttpStatus.OK);
                   }else {
                       return RestaurantUtils.getResponseEntity("Dish id Does not Exist",HttpStatus.OK);
                   }

                }else {
                    return RestaurantUtils.getResponseEntity(RestaurantConstants.Invalid_Data,HttpStatus.BAD_REQUEST);
                }

            }else {
                return RestaurantUtils.getResponseEntity(RestaurantConstants.Unauthorized_Access,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteDish(Integer id) {
        try {
            if (jwtFilter.isRestaurant()|| jwtFilter.isAdmin()){
                Optional optional = dishDao.findById(id);
                if (!optional.isEmpty()){
                    dishDao.deleteById(id);
                    return RestaurantUtils.getResponseEntity("Dish Deleted Successfully",HttpStatus.OK);
                }
                return RestaurantUtils.getResponseEntity("Dish id Does not exist",HttpStatus.OK);

            }else {
                return RestaurantUtils.getResponseEntity(RestaurantConstants.Unauthorized_Access,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateAvail(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isRestaurant()){
                Optional optional = dishDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()){
                    dishDao.updateDishStatus(requestMap.get("availability"),Integer.parseInt(requestMap.get("id")));
                    return RestaurantUtils.getResponseEntity("Dish Availability Updated Successfully",HttpStatus.OK);
                }else {
                    return RestaurantUtils.getResponseEntity("Dish Id does not Exist",HttpStatus.OK);
                }
            }else {
                return RestaurantUtils.getResponseEntity(RestaurantConstants.Unauthorized_Access,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.Foodease.FoodeaseApp.serviceImpl;

import com.Foodease.FoodeaseApp.JWT.CustomerUserDetailsService;
import com.Foodease.FoodeaseApp.JWT.JwtFilter;
import com.Foodease.FoodeaseApp.JWT.JwtUtil;
import com.Foodease.FoodeaseApp.POJO.Restaurant;
import com.Foodease.FoodeaseApp.constant.RestaurantConstants;
import com.Foodease.FoodeaseApp.dao.RestaurantDao;
import com.Foodease.FoodeaseApp.service.RestaurantService;
import com.Foodease.FoodeaseApp.utils.RestaurantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    RestaurantDao restaurantDao;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            if (validateSignupMap(requestMap)){
                Restaurant restaurant = restaurantDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(restaurant)){
                    restaurantDao.save(getRestaurantFromMap(requestMap));
                    return RestaurantUtils.getResponseEntity("Restaurant Registered Successfully",HttpStatus.OK);
                }else {
                    return RestaurantUtils.getResponseEntity("EmailId is already exist",HttpStatus.CONFLICT);
                }
            }else {
                return RestaurantUtils.getResponseEntity(RestaurantConstants.Invalid_Data,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignupMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")
                && requestMap.containsKey("location")){
            return true;
        }else {
            return false;
        }
    }

    private Restaurant getRestaurantFromMap(Map<String, String> requestMap) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(requestMap.get("name"));
        restaurant.setContactNumber(requestMap.get("contactNumber"));
        restaurant.setEmail(requestMap.get("email"));
        restaurant.setLocation(requestMap.get("location"));
        restaurant.setPassword(requestMap.get("password"));
        restaurant.setStatus("false");
        restaurant.setRole("restaurant");
        return restaurant;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (requestMap.get("email"),requestMap.get("password")));
            if (auth.isAuthenticated()){
                if (customerUserDetailsService.getRestaurantDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+jwtUtil.generateToken
                            (customerUserDetailsService.getRestaurantDetail().getEmail(),
                                    customerUserDetailsService.getRestaurantDetail().getRole())+"\"}",HttpStatus.OK);
                }else {
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\""+"Authentication failed! ."+"\"}",HttpStatus.BAD_REQUEST);
    }
}

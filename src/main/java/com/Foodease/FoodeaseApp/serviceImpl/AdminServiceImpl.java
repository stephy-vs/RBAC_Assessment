package com.Foodease.FoodeaseApp.serviceImpl;

import com.Foodease.FoodeaseApp.JWT.CustomerUserDetailsService;
import com.Foodease.FoodeaseApp.JWT.JwtFilter;
import com.Foodease.FoodeaseApp.JWT.JwtUtil;
import com.Foodease.FoodeaseApp.POJO.Admin;
import com.Foodease.FoodeaseApp.POJO.Restaurant;
import com.Foodease.FoodeaseApp.POJO.User;
import com.Foodease.FoodeaseApp.constant.RestaurantConstants;
import com.Foodease.FoodeaseApp.dao.AdminDao;
import com.Foodease.FoodeaseApp.dao.RestaurantDao;
import com.Foodease.FoodeaseApp.dao.UserDao;
import com.Foodease.FoodeaseApp.service.AdminService;
import com.Foodease.FoodeaseApp.utils.RestaurantUtils;
import com.Foodease.FoodeaseApp.wrapper.RestaurantWrapper;
import com.Foodease.FoodeaseApp.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;
    @Autowired
    UserDao userDao;
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
                Admin admin = adminDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(admin)){
                    adminDao.save(getAdminFromMap(requestMap));
                    return RestaurantUtils.getResponseEntity("Admin is registered successfully",HttpStatus.OK);
                }else {
                    return RestaurantUtils.getResponseEntity("EmailId is already exist",HttpStatus.OK);
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
        if (requestMap.containsKey("name") && requestMap.containsKey("email")
                && requestMap.containsKey("password")){
            return true;
        }else {
            return false;
        }
    }

    private Admin getAdminFromMap(Map<String, String> requestMap) {
        Admin admin = new Admin();
        admin.setName(requestMap.get("name"));
        admin.setEmail(requestMap.get("email"));
        admin.setPassword(requestMap.get("password"));
        admin.setContactNumber(requestMap.get("contactNumber"));
        admin.setStatus("true");
        admin.setRole("admin");
        return admin;
    }

    @Override
    public ResponseEntity<String> adminLogin(Map<String, String> requestMap) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    requestMap.get("email"),requestMap.get("password")
            ));
            if (auth.isAuthenticated()){
                if (customerUserDetailsService.getAdminDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUserDetailsService.getAdminDetail().getEmail(),
                                    customerUserDetailsService.getAdminDetail().getRole())+"\"}",HttpStatus.OK);
                }else {
                    return new ResponseEntity<String>("{\"message\":\""+"login credential is blocked."+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\""+"Authentication failed! ."+"\"}",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
            return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<RestaurantWrapper>> getAllRestaurant() {
        try {
            return new ResponseEntity<>(restaurantDao.getAllRestaurant(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<RestaurantWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateRestaurantStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                Optional<Restaurant> restaurant = restaurantDao.findById(Long.parseLong(requestMap.get("id")));
                if (restaurant.isPresent()){
                    restaurantDao.updateStatus(requestMap.get("status"),Long.parseLong(requestMap.get("id")));
                    return RestaurantUtils.getResponseEntity("Restaurant status updated successfully",HttpStatus.OK);
                }else {
                    return RestaurantUtils.getResponseEntity("Restaurant Id isn't exist",HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> deleteRestaurantAccount(Long id) {
        try {
            if (jwtFilter.isAdmin()){
                Optional<Restaurant> restaurant = restaurantDao.findById(id);

                if (restaurant.isPresent()){
                    restaurantDao.deleteById(id);
                    return RestaurantUtils.getResponseEntity("Restaurant is deleted",HttpStatus.OK);
                } else {
                    return RestaurantUtils.getResponseEntity("Id is not exist",HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                Optional<User> optional= userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()){
                    userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    return RestaurantUtils.getResponseEntity("User status Updated Successfully",HttpStatus.OK);

                }else {
                    return RestaurantUtils.getResponseEntity("User id does not exist",HttpStatus.OK);
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

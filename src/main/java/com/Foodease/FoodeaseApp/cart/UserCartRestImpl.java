package com.Foodease.FoodeaseApp.cart;

import com.Foodease.FoodeaseApp.constant.RestaurantConstants;
import com.Foodease.FoodeaseApp.utils.RestaurantUtils;
import com.Foodease.FoodeaseApp.wrapper.CartWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/cart")
public class UserCartRestImpl {
    @Autowired
    UserCartServiceImpl userCartService;

    @PostMapping(path = "/addItems")
    public ResponseEntity<?> addItems(@RequestBody UserCart userCart){
        try {
            return userCartService.addCartItems(userCart);
        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<CartWrapper>>getCartItems(@RequestParam Integer userId){
        try {
            return userCartService.getUserCartItem(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>() ,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/updateCartQty")
    public ResponseEntity<?>updateCartQty(@RequestParam Integer userId,@RequestParam Integer cartId,@RequestParam Integer qty){
        try {
            return userCartService.updateCartQty(userId,cartId,qty);
        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(path = "/deleteItem")
    public ResponseEntity<?>deleteItemFromCart(@RequestParam Integer cartId,@RequestParam Integer userId){
        try {
            return userCartService.deleteItem(cartId,userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

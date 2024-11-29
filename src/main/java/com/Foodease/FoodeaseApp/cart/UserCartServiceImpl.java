package com.Foodease.FoodeaseApp.cart;

import com.Foodease.FoodeaseApp.JWT.JwtFilter;
import com.Foodease.FoodeaseApp.POJO.Dish;
import com.Foodease.FoodeaseApp.POJO.Restaurant;
import com.Foodease.FoodeaseApp.constant.RestaurantConstants;
import com.Foodease.FoodeaseApp.dao.DishDao;
import com.Foodease.FoodeaseApp.dao.RestaurantDao;
import com.Foodease.FoodeaseApp.utils.RestaurantUtils;
import com.Foodease.FoodeaseApp.wrapper.CartWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCartServiceImpl {
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    CartDao cartDao;
    @Autowired
    DishDao dishDao;
    @Autowired
    RestaurantDao restaurantDao;

    public ResponseEntity<?> addCartItems(UserCart userCart) {
        try {
            if (jwtFilter.isUser()){
                UserCart user = new UserCart();
                user.setUserId(userCart.getUserId());
                user.setDishId(userCart.getDishId());
                user.setQty(userCart.getQty());

                Optional<Dish> dishOptional = dishDao.findById(userCart.getDishId());
                if (dishOptional.isPresent()){
                    Dish dish = dishOptional.get();
                    BigDecimal qtyItem =new BigDecimal(userCart.getQty().toString());
                    BigDecimal calcPrice = qtyItem.multiply(dish.getPrice());
                    user.setTotalPrice(calcPrice);
                }
                user.setConfirm(false);
                cartDao.save(user);
                return new ResponseEntity<>(user,HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<List<CartWrapper>> getUserCartItem(Integer userId) {
        if (jwtFilter.isUser()){
            List<UserCart> userCartList = cartDao.findByUserId(userId);
            List<CartWrapper> cartWrapperList = new ArrayList<>();
            if (!userCartList.isEmpty()){
                for (UserCart userCart : userCartList){
                    CartWrapper cartWrapper = new CartWrapper();
                    cartWrapper.setId(userCart.getId());
                    Optional<Dish> dishOptional = dishDao.findById(userCart.getDishId());
                    if (dishOptional.isPresent()){
                        Dish dish = dishOptional.get();
                        cartWrapper.setDishName(dish.getName());
                        cartWrapper.setPerUnitPrice(dish.getPrice());
                        Integer restId = dish.getRestaurant().getId();
                        Long restLong = Long.valueOf(restId);
                        Optional<Restaurant> restaurantOptional = restaurantDao.findById(restLong);
                        if (restaurantOptional.isPresent()){
                            Restaurant restaurant = restaurantOptional.get();
                            cartWrapper.setRestaurantName(restaurant.getName());
                        }
                        cartWrapper.setQty(userCart.getQty());
                        cartWrapper.setTotalPrice(userCart.getTotalPrice());
                        cartWrapperList.add(cartWrapper);
                    }

                }
            }
            return new ResponseEntity<>(cartWrapperList,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> updateCartQty(Integer userId, Integer cartId, Integer qty) {
        try {
            if (jwtFilter.isUser()){
                Optional<UserCart>cartOptional = cartDao.findByUserIdAndId(userId,cartId);
                if (cartOptional.isPresent()){
                    UserCart userCart = cartOptional.get();
                    Integer dishId = userCart.getDishId();
                    Optional<Dish> dishOptional = dishDao.findById(dishId);
                    if (dishOptional.isPresent()){
                        Dish dish = dishOptional.get();
                        if (dish.getAvailability().equalsIgnoreCase("true")){
                            userCart.setQty(qty);
                            BigDecimal qtyItem =new BigDecimal(userCart.getQty().toString());
                            BigDecimal perPrice = qtyItem.multiply(dish.getPrice());
                            userCart.setTotalPrice(perPrice);
                            cartDao.save(userCart);
                            return new ResponseEntity<>(userCart,HttpStatus.OK);
                        }else {
                            return new ResponseEntity<>(qty+ " is unavailable",HttpStatus.NO_CONTENT);
                        }
                    }
                }else {
                 return new ResponseEntity<>("Invalid id",HttpStatus.BAD_REQUEST);
                }
            }else {
                return RestaurantUtils.getResponseEntity(RestaurantConstants.Unauthorized_Access,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> deleteItem(Integer cartId,Integer userId) {
        try {
            if (jwtFilter.isUser()){
                Optional<UserCart> userCartOptional = cartDao.findByUserIdAndId(userId,cartId);
                if (userCartOptional.isPresent()){
                    UserCart userCart = userCartOptional.get();
                    cartDao.delete(userCart);
                    return new ResponseEntity<>("Item Removed",HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("Item isn't found",HttpStatus.NOT_FOUND);
                }
            }else {
                return new ResponseEntity<>(RestaurantConstants.Unauthorized_Access,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.Something_Went_Wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.Foodease.FoodeaseApp.JWT;

import com.Foodease.FoodeaseApp.dao.AdminDao;
import com.Foodease.FoodeaseApp.dao.RestaurantDao;
import com.Foodease.FoodeaseApp.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;
    @Autowired
    RestaurantDao restaurantDao;
    @Autowired
    AdminDao adminDao;

    private com.Foodease.FoodeaseApp.POJO.User userDetail;
    private com.Foodease.FoodeaseApp.POJO.Restaurant restaurantDetail;
    private com.Foodease.FoodeaseApp.POJO.Admin adminDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}",username);
        userDetail = userDao.findByEmailId(username);
        restaurantDetail = restaurantDao.findByEmailId(username);
        adminDetail = adminDao.findByEmailId(username);
        if (!Objects.isNull(userDetail)) {
            return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        } else if (!Objects.isNull(restaurantDetail)) {
            return new User(restaurantDetail.getEmail(),restaurantDetail.getPassword(),new ArrayList<>());
        } else if (!Objects.isNull(adminDetail)) {
            return new User(adminDetail.getEmail(),adminDetail.getPassword(),new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public com.Foodease.FoodeaseApp.POJO.User getUserDetail(){
        return userDetail;
    }
    public com.Foodease.FoodeaseApp.POJO.Restaurant getRestaurantDetail(){
        return restaurantDetail;
    }
    public com.Foodease.FoodeaseApp.POJO.Admin getAdminDetail(){
        return adminDetail;
    }
}

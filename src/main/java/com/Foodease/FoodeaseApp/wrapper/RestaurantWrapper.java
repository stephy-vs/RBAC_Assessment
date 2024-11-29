package com.Foodease.FoodeaseApp.wrapper;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantWrapper {
    private Integer id;


    private String name;


    private String contactNumber;


    private String location;


    private String email;


    private String password;


    private String status;

    public RestaurantWrapper(Integer id, String name, String contactNumber, String location, String email, String status) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.location = location;
        this.email = email;
        this.status = status;
    }
}

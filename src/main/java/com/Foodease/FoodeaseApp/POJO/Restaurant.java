package com.Foodease.FoodeaseApp.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name="Restaurant.findByEmailId",query = "select r from Restaurant r where r.email=:email")
@NamedQuery(name = "Restaurant.getAllRestaurant", query = "select new com.Foodease.FoodeaseApp.wrapper.RestaurantWrapper(r.id,r.name,r.contactNumber,r.location,r.email,r.status) from Restaurant r where r.role = 'restaurant'")
@NamedQuery(name = "Restaurant.updateStatus",query = "Update Restaurant r set r.status=:status where r.id=:id")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "restaurant")
public class Restaurant implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "contactNumber",nullable = false,length = 10)
    private String contactNumber;

    @Column(name = "location",nullable = false)
    private String location;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;
}

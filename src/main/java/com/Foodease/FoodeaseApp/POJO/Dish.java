package com.Foodease.FoodeaseApp.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.Serializable;
import java.math.BigDecimal;
@NamedQuery(name = "Dish.getAllDish",query = "select new com.Foodease.FoodeaseApp.wrapper.DishWrapper(d.id,d.name,d.price,d.description,d.availability,d.category.id,d.category.name,d.categoryType.id,d.categoryType.name) from Dish d ")
@NamedQuery(name = "Dish.updateDishStatus",query = "update Dish d set d.availability=:availability where d.id=:id")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "Im_Dish")
@CrossOrigin
public class Dish implements Serializable {
    public static final long serialVersionUID=1234L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "categ_Id",nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "catType",nullable = false)
    private CategoryType categoryType;

    @Column(name = "description",length = 1000)
    private String description ;

    @ManyToOne
    @JoinColumn(name = "rest_Id",nullable = false)
    private Restaurant restaurant;

    @Column(name = "availability")
    private String availability;

}

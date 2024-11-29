package com.Foodease.FoodeaseApp.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "CategoryType.getAllCategoryType",query = "select c from CategoryType c")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "catType")

public class CategoryType implements Serializable {
    private static final long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}

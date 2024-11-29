package com.Foodease.FoodeaseApp.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data

public class DishWrapper {
     Integer id;
     String name;
     BigDecimal price;
     String description;
     String availability;
     Integer categoryId;
     String categoryName;
     Integer categoryTypeId;
     String categoryTypeName;

     public DishWrapper(){

     }
     public DishWrapper(Integer id,String name,BigDecimal price,String description,String availability,
                       Integer categoryId,String categoryName,Integer categoryTypeId,String categoryTypeName){
          this.id=id;
          this.name=name;
          this.price=price;
          this.description =description;
          this.availability=availability;
          this.categoryId=categoryId;
          this.categoryName=categoryName;
          this.categoryTypeId=categoryTypeId;
          this.categoryTypeName=categoryTypeName;

     }



}

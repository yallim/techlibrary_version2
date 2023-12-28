package com.example.firstproject.dto;

import com.example.firstproject.entity.Coffee;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeDto {

    private Long id;
    private String name;
    private int price;

    public Coffee toEntity(Long id, String name,int price){
        return new Coffee(id,name,price);
    }

}

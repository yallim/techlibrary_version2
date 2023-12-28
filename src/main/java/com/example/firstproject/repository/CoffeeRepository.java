package com.example.firstproject.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.example.firstproject.entity.Coffee;

public interface CoffeeRepository extends CrudRepository<Coffee,Long> {
    @Override
    ArrayList<Coffee> findAll();
}

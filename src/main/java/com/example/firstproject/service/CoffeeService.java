package com.example.firstproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CoffeeRepository;

@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    public ArrayList<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee show(@PathVariable Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee create(CoffeeDto dto) {
        Coffee created = dto.toEntity();
        return coffeeRepository.save(created);
    }

    public Coffee updated(CoffeeDto dto, Long id) {
        Coffee updated = dto.toEntity();
        Coffee target = coffeeRepository.findById(id).orElse(null);

        if(target==null|| id!=target.getId()){
            return null;
        }

        target.patch(updated);
        coffeeRepository.save(updated);
        return target;
    }
    
}

package com.example.firstproject.service;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CoffeeRepository;
import com.samskivert.mustache.Mustache.Collector;

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

    public Coffee delete(@PathVariable Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target==null){
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }

    public List<Coffee> createdcoffee(List<CoffeeDto> dtos) {
        //DTO를 엔티티로 변환
        List<Coffee> coffeeList = dtos.stream()
            .map(coffee -> coffee.toEntity())
            .collect(Collectors.toList());

        //엔티티를 DB에 저장
        coffeeList.stream()
            .forEach(coffee -> coffeeRepository.save(coffee));
        
        coffeeRepository.findById(-1L)
        .orElseThrow(()-> new IllegalArgumentException("결제 실패"));
        
        return coffeeList;
        
        
        

    }
    
}

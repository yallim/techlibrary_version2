package com.example.firstproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.CoffeeRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CoffeeApiController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping("/api/coffee")
    public List<Coffee> index(){
        return coffeeRepository.findAll();
    }

    @GetMapping("/api/coffee/{id}")
    public Coffee show(@PathVariable Long id){
        return coffeeRepository.findById(id).orElse(null);
    }

    @PostMapping("/api/coffee")
    public Coffee create(@RequestBody CoffeeDto dto){
        Coffee coffee = dto.toEntity();
        return coffeeRepository.save(coffee);
    }

    @PatchMapping("/api/coffee/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, CoffeeDto dto){
        Coffee coffee = dto.toEntity();
        Coffee target = coffeeRepository.findById(id).orElse(coffee);

        if(target ==null || id!=coffee.getId()){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    @DeleteMapping("/api/coffee/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id){
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target==null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        coffeeRepository.delete(target);
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

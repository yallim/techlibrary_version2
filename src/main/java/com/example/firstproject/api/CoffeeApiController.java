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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.CoffeeRepository;
import com.example.firstproject.service.CoffeeService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CoffeeApiController {

    // @Autowired
    // private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/api/coffee")
    public List<Coffee> index(){
        return coffeeService.index();
    }

    @GetMapping("/api/coffee/{id}")
    public Coffee show(@PathVariable Long id){
        return coffeeService.show(id);
    }

    @PostMapping("/api/coffee")
    public ResponseEntity<Coffee> create(@RequestBody CoffeeDto dto){
        Coffee created = coffeeService.create(dto);
        return (created!=null)?
            ResponseEntity.status(HttpStatus.OK).body(created):
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PatchMapping("/api/coffee/{id}")
    public ResponseEntity<Coffee> update(@RequestBody CoffeeDto dto, @PathVariable Long id){
        Coffee updated = coffeeService.updated(dto,id);

        return (updated!=null)?
             ResponseEntity.status(HttpStatus.OK).body(updated):
             ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    

    @DeleteMapping("/api/coffee/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id){
        Coffee deleted = coffeeService.delete(id);

        return (deleted!=null)?
             ResponseEntity.status(HttpStatus.OK).build():
             ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Transactional
    @PostMapping("/api/transcation--test")
    public ResponseEntity<List<Coffee>> transcationtest(@RequestBody List<CoffeeDto> dtos){
        List<Coffee> transcation_test =coffeeService.createdcoffee(dtos);

        return (transcation_test!=null)?
             ResponseEntity.status(HttpStatus.OK).build():
             ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
}

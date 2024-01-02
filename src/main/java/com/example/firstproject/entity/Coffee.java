package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name ="COFFEE")
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Coffee {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String price;

    /*coffe클래스의 name이 빈값이 아니라면(즉, 갱신할 값이 있다면) 엔티티에 값을 갱신 */
    public void patch(Coffee coffee) {
        if(coffee.name!=null){
            this.name =coffee.name;
        }
        if(coffee.price!=null){
            this.price =coffee.price;
        }
    }
}

 


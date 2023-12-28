package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Table(name ="COFFEE")
@Entity
@ToString
@AllArgsConstructor
public class Coffee {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private int price;
}

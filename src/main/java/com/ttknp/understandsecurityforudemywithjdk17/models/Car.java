package com.ttknp.understandsecurityforudemywithjdk17.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Integer cid;
    private String brand;
    private String model;
    private Double price;
    private Date releaseDate; // it can map date type in sql
}

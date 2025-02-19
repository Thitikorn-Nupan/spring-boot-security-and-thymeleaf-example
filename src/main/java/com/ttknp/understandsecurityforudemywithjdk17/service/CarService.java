package com.ttknp.understandsecurityforudemywithjdk17.service;


import com.ttknp.understandsecurityforudemywithjdk17.models.Car;

public interface CarService {
    Iterable<Car> getCars();
    Boolean addCar(Car car);
    Boolean removeCar(Integer id);
    Boolean editCar(Integer id, Car car);
}

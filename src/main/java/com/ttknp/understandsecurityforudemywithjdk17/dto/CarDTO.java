package com.ttknp.understandsecurityforudemywithjdk17.dto;

import com.ttknp.understandsecurityforudemywithjdk17.models.Car;
import com.ttknp.understandsecurityforudemywithjdk17.repositories.CarRepo;
import com.ttknp.understandsecurityforudemywithjdk17.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

// @Slf4j
@Service
public class CarDTO implements CarService {

    private CarRepo carRepo;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CarDTO(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    @Override
    public Iterable<Car> getCars() {
        Iterable<Car> cars = carRepo.findAll();
        int counter = 0;
        // just want a size
        for (Object i : cars) counter++;
        log.debug("cars size : {}", counter);
        return carRepo.findAll();
    }
}

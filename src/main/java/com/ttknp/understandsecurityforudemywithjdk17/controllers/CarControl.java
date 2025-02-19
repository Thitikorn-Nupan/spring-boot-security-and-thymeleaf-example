package com.ttknp.understandsecurityforudemywithjdk17.controllers;

import com.ttknp.understandsecurityforudemywithjdk17.models.Car;
import com.ttknp.understandsecurityforudemywithjdk17.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/car")
public class CarControl {

    private CarService carService;

    @Autowired
    public CarControl(CarService carService) {
        this.carService = carService;
        log.info("Car controller created");
    }

    @GetMapping(value = {"/all","/"})
    private ResponseEntity retrieveAllCars() {
        log.info("CarControl class created "+this.carService.getCars());

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Content-Type", "application/json")
                .header("Message","All cars have been retrieved") // for testing
                .body(carService.getCars());
    }

    @PostMapping(value = "/new")
    private ResponseEntity addNewCar(@RequestBody Car car) {
        Boolean result = carService.addCar(car);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header("Message","new car have been in database "+result) // for testing
                .body(null);
    }

    @DeleteMapping(value = "/remove")
    private ResponseEntity removeCarByID(@RequestParam Integer id) {
        Boolean result = carService.removeCar(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header("Message","car have removed in database "+result) // for testing
                .body(null);
    }

    // ** if i used @PreAuthorize("hasAuthority('permission.update')") for set mvc matchers
    // ** i have to mark @EnableMethodSecurity() and again all my mvc matchers block inside method security
    // ** won't work
    // @PreAuthorize("hasAuthority('permission.update')") // **** we call security expression
    @PutMapping(value = "/edit")
    private ResponseEntity editCarByID(@RequestParam Integer id, @RequestBody Car car) {
        Boolean result = carService.editCar(id,car);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header("Message","car have edited in database "+result) // for testing
                .body(null);
    }
}

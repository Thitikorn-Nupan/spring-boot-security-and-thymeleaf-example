package com.ttknp.understandsecurityforudemywithjdk17.controllers;

import com.ttknp.understandsecurityforudemywithjdk17.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/car")
public class CarControl {
    private CarService carService;

    @Autowired
    public CarControl(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(value = {"/all","/"})
    private ResponseEntity retrieveAllCars() {
        return ResponseEntity
                .status(202)
                .header("Content-Type", "application/json")
                .header("Message","All cars have been retrieved") // for testing
                .body(carService.getCars());
    }
}

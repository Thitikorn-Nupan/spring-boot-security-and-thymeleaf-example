package com.ttknp.understandsecurityforudemywithjdk17.controllers;

import com.ttknp.understandsecurityforudemywithjdk17.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/template")
public class TemplatesControl {

    private CarService carService;

    @Autowired
    public TemplatesControl(CarService carService) {
        this.carService = carService;
    }


    @GetMapping(value = "/car-list")
    private ModelAndView displayCarList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("car-list");
        modelAndView.addObject("cars",carService.getCars());
        modelAndView.setStatus(HttpStatusCode.valueOf(202));
        return modelAndView;
    }
}

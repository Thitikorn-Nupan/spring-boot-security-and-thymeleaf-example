package com.ttknp.understandsecurityforudemywithjdk17.repositories;

import com.ttknp.understandsecurityforudemywithjdk17.models.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepo extends CrudRepository<Car, Integer> {
}

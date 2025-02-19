package com.ttknp.understandsecurityforudemywithjdk17.rest_api_logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttknp.understandsecurityforudemywithjdk17.models.Car;
import com.ttknp.understandsecurityforudemywithjdk17.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CarControlAuthenticateUserOnDatabaseTest {

    @Autowired
    public WebApplicationContext wac;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @MockBean //
    private CarService carService;

    @Test
    public void testFetchingAllCarOnApiCarAllAndLoginWithHttpBasic() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        given(carService.getCars()).willReturn(getCars());

        /// ** call the provider ** and set basic authenticate on http header
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/car/all")
                .with(httpBasic("user@hotmail.com","12345"));

        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);

        // then - verify the output
        response
                .andExpect(status().isAccepted())
                .andExpect(header().string("Content-Type", "application/json"));
    }

    @Test
    public void testFetchingAllCarOnApiCarAllAndLoginWithApiOnHeader() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/car/all")
                .header("Api-Username","user@hotmail.com")
                .header("Api-Password","12345");

        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);

        // then - verify the output
        response
                .andExpect(status().isAccepted())
                .andExpect(header().string("Message-Filter", "Authentication success"));
    }


    @Test
    public void testAddNewCarOnApiCarNewAndLoginWithHttpBasic() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        Car car = getCar();
        given(carService.addCar(car)).willReturn(true);

        // convert java to json as string
        String requestBody = new ObjectMapper().writeValueAsString(car);
        /// ** call the provider ** and set basic authenticate on http header
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/car/new")
                .contentType("application/json")
                .content(requestBody)
                .with(httpBasic("admin@hotmail.com","12345"));

        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);

        // then - verify the output
        response
                .andExpect(status().isNoContent())
                .andExpect(header().string("Message","new car have been in database true"))
                .andDo(print());
    }

    @Test
    public void testRemoveCarOnApiCarRemoveAndLoginWithHttpBasic() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        given(carService.removeCar(1)).willReturn(true);

        /// ** call the provider ** and set basic authenticate on http header
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/car/remove")
                .param("id", String.valueOf(1))
                .with(httpBasic("admin@hotmail.com","12345"));

        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);

        // then - verify the output
        response
                .andExpect(status().isNoContent())
                .andExpect(header().string("Message","car have removed in database true"))
                .andDo(print());
    }

    @Test
    public void testEditCarOnApiCarEditAndLoginWithHttpBasic() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        Car car = getCar();
        car.setCid(null);
        given(carService.editCar(1,car)).willReturn(true);

        // convert java to json as string
        String requestBody = new ObjectMapper().writeValueAsString(car);
        /// ** call the provider ** and set basic authenticate on http header
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/car/edit")
                .param("id", String.valueOf(1))
                .contentType("application/json")
                .content(requestBody)
                .with(httpBasic("admin@hotmail.com","12345"));

        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);

        // then - verify the output
        response
                .andExpect(status().isNoContent())
                .andExpect(header().string("Message","car have edited in database true"))
                .andDo(print());
    }

    private Iterable<Car> getCars() {
        Car car = new Car(1,"Toyota","Cross",950000.0,new Date());
        Car car2 = new Car(2,"Toyota","Cross",950000.0,new Date());
        Car car3 = new Car(3,"Toyota","Cross",950000.0,new Date());
        return Arrays.asList(car,car2,car3);
    }

    private Car getCar() {
        // Car car = new Car(1,"Toyota","Cross",950000.0,new Date());
        return new Car(1,"Toyota","Cross",950000.0,new Date());
    }

}

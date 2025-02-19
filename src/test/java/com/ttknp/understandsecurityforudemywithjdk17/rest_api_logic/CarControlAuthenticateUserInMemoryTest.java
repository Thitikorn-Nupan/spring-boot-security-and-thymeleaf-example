package com.ttknp.understandsecurityforudemywithjdk17.rest_api_logic;

import com.ttknp.understandsecurityforudemywithjdk17.configuration.SecurityConfig;
import com.ttknp.understandsecurityforudemywithjdk17.controllers.CarControl;
import com.ttknp.understandsecurityforudemywithjdk17.models.Car;
import com.ttknp.understandsecurityforudemywithjdk17.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// ***
// ***
// ***
// *** Note @WebMvcTest , @Import Work for spring security authenticate in memory only ******
// ***
// ***
// ***
// *** JUnit5 test cases for CRUD REST APIs.  *** use the @WebMvcTest annotation to load only UserController class. (can multiple rest controller)
// @WebMvcTest(controllers = {C1.class, C2.class})
@WebMvcTest(CarControl.class)
@Import(SecurityConfig.class)
public class CarControlAuthenticateUserInMemoryTest {

    // *** using MockMvc class to make REST API calls.
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void testFetchingAllCarOnApiCarAllAndLoginWithHttpBasic() throws Exception {
        /// ** provide response if service calls ** it's kinda same as when(...).return(...)
        given(carService.getCars()).willReturn(getCars());

        /// ** call the provider ** and set basic authenticate on http header
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/car/all")
                .with(httpBasic("admin@hotmail.com","12345"));

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
                .header("Api-Username","admin@hotmail.com")
                .header("Api-Password","12345");

        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);

        // then - verify the output
        response
                .andExpect(status().isAccepted())
                .andExpect(header().string("Message-Filter", "Authentication success"));
    }


    private Iterable<Car> getCars() {
        Car car = new Car(1,"Toyota","Cross",950000.0,new Date());
        Car car2 = new Car(2,"Toyota","Cross",950000.0,new Date());
        Car car3 = new Car(3,"Toyota","Cross",950000.0,new Date());
        return Arrays.asList(car,car2,car3);
    }

}

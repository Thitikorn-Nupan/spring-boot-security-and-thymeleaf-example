package com.ttknp.understandsecurityforudemywithjdk17.control_logic;

import com.ttknp.understandsecurityforudemywithjdk17.configuration.SecurityConfig;
import com.ttknp.understandsecurityforudemywithjdk17.controllers.TemplatesControl;
import com.ttknp.understandsecurityforudemywithjdk17.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TemplatesControlAuthenticateUserOnDatabaseTest {
    @Autowired
    public WebApplicationContext wac;

    // *** using MockMvc class to make REST API calls.
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }


    /*
    ***
    This is very importance
    This class never use CarService class but need to @MockBean
    Why ? because on TemplatesControl class have to use CarService class
    ***
    * read the error it'll tell you no inject CarService if you didn't do below
    * @MockBean
    * private CarService carService;
    */
    @MockBean
    private CarService carService;

    @Test
    public void testCarListPageAndLoginWithHttpBasic() throws Exception {

        // when test view control no need to provide

        /// ** call the provider ** and set basic authenticate on http header
        RequestBuilder request = MockMvcRequestBuilders
                .get("/template/car-list")
                .with(httpBasic("admin@hotmail.com","12345"));

        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);

        // then - verify the output
        response
                .andExpect(status().isAccepted())
                .andExpect(view().name("car-list"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"))
                .andDo(print());
    }

    @Test
    public void testLoginPage() throws Exception {

        // when test view control no need to provide

        /// ** call the provider ** and set basic authenticate on http header
        RequestBuilder request = MockMvcRequestBuilders
                .get("/login");
                //.with(httpBasic("admin@hotmail.com","12345"));

        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);

        // then - verify the output
        response
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"))
                .andDo(print());
    }
}

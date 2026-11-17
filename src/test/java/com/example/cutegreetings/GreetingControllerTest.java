package com.example.cutegreetings;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void catStarReturnsGreetingAndImage() throws Exception {
        mockMvc.perform(get("/api/greeting/cat-star"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("星星猫"))
                .andExpect(jsonPath("$.greeting").isNotEmpty())
                .andExpect(jsonPath("$.imageBase64").value(org.hamcrest.Matchers.startsWith("data:image/jpeg;base64,")));
    }

    @Test
    void catSnowReturnsGreetingAndImage() throws Exception {
        mockMvc.perform(get("/api/greeting/cat-snow"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("雪球猫"))
                .andExpect(jsonPath("$.greeting").isNotEmpty());
    }

    @Test
    void catGingerReturnsGreetingAndImage() throws Exception {
        mockMvc.perform(get("/api/greeting/cat-ginger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("橘子猫"))
                .andExpect(jsonPath("$.greeting").isNotEmpty());
    }

    @Test
    void imageEndpointReturnsJpeg() throws Exception {
        mockMvc.perform(get("/api/greeting/cat-star/image"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/jpeg"));
    }

    @Test
    void unknownKittenReturns404() throws Exception {
        mockMvc.perform(get("/api/greeting/nope/image"))
                .andExpect(status().isNotFound());
    }
}

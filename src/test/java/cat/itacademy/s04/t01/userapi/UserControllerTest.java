package cat.itacademy.s04.t01.userapi;


import cat.itacademy.s04.t01.userapi.controllers.UserController;
import cat.itacademy.s04.t01.userapi.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        // No persistencia real → el estado se reinicia por test
    }

    @Test
    void getUsers_returnsEmptyListInitially() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void createUser_returnsUserWithId() throws Exception {
        User user = new User(null, "Ada", "ada@example.com");

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Ada"));
    }

    @Test
    void getUserById_returnsNotFoundIfMissing() throws Exception {
        mockMvc.perform(get("/users/{id}", "123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isNotFound());
    }
}

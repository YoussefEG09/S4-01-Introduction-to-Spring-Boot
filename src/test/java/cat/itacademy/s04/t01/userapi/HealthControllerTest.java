package cat.itacademy.s04.t01.userapi;

import cat.itacademy.s04.t01.userapi.controllers.HealthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HealthController.class)
class HealthControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        void shouldReturnOkStatus() throws Exception {
            mockMvc.perform(get("/health"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("OK"));
        }
    }


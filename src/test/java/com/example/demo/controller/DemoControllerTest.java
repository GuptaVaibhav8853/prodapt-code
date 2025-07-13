package com.example.demo.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.demo.DemoApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test;

/**
 * Skeleton template for a controller test using MockMvc.
 *
 * You can use annotations from JUnit 5 such as @ParameterizedTest, @ValueSauce,
 * @CsvSource and @MethodSource for your test data.
 *
 * Example usage of mockMvc for a GET request
 * mockMvc.perform(get("/path-to-your-endpoint").param("your-query-param", param-value))
 *                 .andExpect(status().whateverStatusCodeYouExpect())
 *                 .andExpect(content().string("string-you-expect-in-response")).
 *                 .andExpect(jsonPath("$.jsonField").value("json-value-you-expect"));
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class DemoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testBasicWord_hello() throws Exception {
        mockMvc.perform(get("/remove").param("original", "hello"))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.data", is("ell")));
    }

    @Test
    public void testThreeCharWord_cat() throws Exception {
        mockMvc.perform(get("/remove").param("original", "cat"))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.data", is("a")));
    }

    @Test
    public void testSpecialChars_input() throws Exception {
        mockMvc.perform(get("/remove").param("original", "@hello!"))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.data", is("hello")));
    }

    @Test
    public void testNumericInput() throws Exception {
        mockMvc.perform(get("/remove").param("original", "12345"))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.data", is("234")));
    }

    @Test
    public void testTwoCharInput_ab() throws Exception {
        mockMvc.perform(get("/remove").param("original", "ab"))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.data", is("")));
    }

    @Test
    public void testTwoCharInput_brackets() throws Exception {
        mockMvc.perform(get("/remove").param("original", "[]"))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.data", is("")));
    }

    @Test
    public void testSingleCharInput_shouldFail() throws Exception {
        mockMvc.perform(get("/remove").param("original", "v"))
                .andExpect(status().isBadRequest())
                .andExpect( jsonPath("$.statusCode", is(400)))
                .andExpect(jsonPath("$.message", containsString("Invalid request")));
    }

    @Test
    public void testEmptyInput_shouldFail() throws Exception {
        mockMvc.perform(get("/remove").param("original", ""))
                .andExpect(status().isBadRequest())
                .andExpect( jsonPath("$.statusCode", is(400)));
    }
}

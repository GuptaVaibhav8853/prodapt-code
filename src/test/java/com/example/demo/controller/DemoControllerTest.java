package com.example.demo.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.demo.APIResponse;
import com.example.demo.DemoApplication;
import com.example.demo.service.StringProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

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
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class DemoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @ParameterizedTest
    @CsvSource({
            "hello, ell",
            "12345, 234",
            "abcdef, bcde",
            "java, av",
            "test123, est12",
            "123, 2",
            "ab,''",
    })
    void testValidStringsWithoutSpecialCharacters(String input, String expectedOutput) {
        StringProcessorService stringProcessorService = new StringProcessorService();
        ResponseEntity<APIResponse> response = stringProcessorService.processString(input);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOutput, response.getBody().getData());
    }

    @ParameterizedTest
    @CsvSource({
            "123%qwerty+, 123_%qwerty",
            "123~qwerty+, 123_~qwerty"
    })
    public void testSpecialChars_input(String input, String expectedOutput) throws Exception {
        StringProcessorService stringProcessorService = new StringProcessorService();
        ResponseEntity<APIResponse> response = stringProcessorService.processString(input);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOutput, response.getBody().getData());
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

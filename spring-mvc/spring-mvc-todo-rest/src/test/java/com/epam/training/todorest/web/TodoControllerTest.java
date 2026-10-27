package com.epam.training.todorest.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("TodoController#")
public class TodoControllerTest {

    private static final String TODO_API_PATH = "/api/todos";
    private static final long DEFAULT_ID = 1;
    private static final long NOT_EXISTING_ID = 100;

    private static final String CREATE_TODO_JSON = "createTodo.json";
    private static final String UPDATE_TODO_JSON = "updateTodo.json";
    private static final String CREATE_TODO_ERROR_JSON = "createTodo-error.json";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void testxD() {
        IntStream.range(0, 10).forEach(i -> {
            System.out.println(i);
        });
    }

    @DisplayName("create should return 201 status code when create is successful")
    @Test
    public void testCreateShouldReturnCREATED() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(
                    post(TODO_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(fromFile())
                )
                .andExpect(status().isCreated())
                .andReturn();

        ObjectNode object = getObjectNode(mvcResult);
        assertFieldValue(object, "title", "Medical checkup");
        assertFieldValue(object, "date", "2024-03-14");
        assertFieldValue(object, "status", "TO_DO");
        assertFieldValue(object, "priority", "HIGH");
    }

    @DisplayName("list should return 200 status code and all the todos when get collection")
    @Test
    public void testListShouldReturnAllTheTodos() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(TODO_API_PATH))
                .andExpect(status().isOk())
                .andReturn();

        ArrayNode array = getArrayNode(mvcResult);
        Assertions.assertEquals(7, array.size(), "number of TODO items");
    }

    @DisplayName("get should return 200 status code and the queried todo object of id: " + DEFAULT_ID)
    @Test
    public void testGetTodoByIdShouldReturnProperTodo() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(TODO_API_PATH + "/" + DEFAULT_ID))
                .andExpect(status().isOk())
                .andReturn();

        ObjectNode object = getObjectNode(mvcResult);
        assertFieldValue(object, "title", "Kids at school");
    }

    @DisplayName("get should return 404 status code when get todo item with not existing id (100)")
    @Test
    public void testGetShouldRaiseException() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/todos/" + NOT_EXISTING_ID))
                .andExpect(status().isNotFound())
                .andReturn();

        ObjectNode object = getObjectNode(mvcResult);
        assertFieldValue(object, "message", "Todo item with id 100 does not exist.");
    }

    private ArrayNode getArrayNode(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String responseString = mvcResult.getResponse().getContentAsString();
        JsonNode jsonResponse = objectMapper.readTree(responseString);
        Assertions.assertInstanceOf(ArrayNode.class, jsonResponse, "response must be json array");
        return (ArrayNode) jsonResponse;
    }

    private ObjectNode getObjectNode(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String responseString = mvcResult.getResponse().getContentAsString();
        JsonNode jsonResponse = objectMapper.readTree(responseString);
        System.out.print(jsonResponse);

        Assertions.assertInstanceOf(ObjectNode.class, jsonResponse, "response must be json object");
        return (ObjectNode) jsonResponse;
    }

    private void assertFieldValue(ObjectNode object, String fieldName, String expected) {
        List<String> values = object.findValuesAsText(fieldName);
        Assertions.assertEquals(1, values.size(), "Number of values for field: " + fieldName);
        Assertions.assertEquals(expected, values.get(0), "value of " + fieldName);
    }

    private byte[] fromFile() throws IOException {
        return new ClassPathResource(TodoControllerTest.CREATE_TODO_JSON).getInputStream().readAllBytes();
    }
}



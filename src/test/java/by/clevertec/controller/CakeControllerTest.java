package by.clevertec.controller;

import by.clevertec.TestCakeBuilder;
import by.clevertec.dto.CakeRequest;
import by.clevertec.dto.CakeResponse;
import by.clevertec.service.CakeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CakeController.class)
class CakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CakeService cakeService;

    @Autowired
    private ObjectMapper objectMapper;

    private static ResultMatcher emptyResponseBody() {
        return content().string("");
    }

    @Test
    void shouldGetAll() throws Exception {
        // given
        final TestCakeBuilder firstCakeBuilder = TestCakeBuilder.newInstance();
        final TestCakeBuilder secondCakeBuilder = TestCakeBuilder.newInstance()
                .withId(UUID.fromString("19c8caab-340d-4722-9414-8933d5da5dd8"))
                .withName("CAKE TWO")
                .withPrice(BigDecimal.valueOf(333, 2));

        CakeResponse firstExpectedResponse = firstCakeBuilder.buildResponse();
        CakeResponse secondExpectedResponse = secondCakeBuilder.buildResponse();
        List<CakeResponse> expectedResponse = List.of(firstExpectedResponse, secondExpectedResponse);

        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        when(cakeService.findAll()).thenReturn(expectedResponse);

        // when, then
        mockMvc.perform(get("/cakes"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldGetById() throws Exception {
        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();

        UUID idToFindBy = testCakeBuilder.getId();
        CakeResponse expectedResponse = testCakeBuilder.buildResponse();
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        when(cakeService.findById(any())).thenReturn(Optional.of(expectedResponse));

        // when, then
        mockMvc.perform(get("/cakes/{id}", idToFindBy))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldGetNotFoundStatus_whenNotPresent() throws Exception {
        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();
        UUID idToFindBy = testCakeBuilder.getId();

        when(cakeService.findById(any())).thenReturn(Optional.empty());

        // when, then
        mockMvc.perform(get("/cakes/{id}", idToFindBy))
                .andExpect(status().isNotFound())
                .andExpect(emptyResponseBody());

        verify(cakeService).findById(idToFindBy);
        verifyNoMoreInteractions(cakeService);
    }

    @Test
    void shouldUpdate() throws Exception {
        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();

        UUID id = testCakeBuilder.getId();
        CakeRequest updateRequest = testCakeBuilder.buildRequest();
        String updateRequestJson = objectMapper.writeValueAsString(updateRequest);

        CakeResponse expectedResponse = testCakeBuilder.buildResponse();
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        when(cakeService.update(any(), any())).thenReturn(expectedResponse);

        // when, then
        mockMvc.perform(
                        put("/cakes/{id}", id)
                                .content(updateRequestJson)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(cakeService).update(id, updateRequest);
        verifyNoMoreInteractions(cakeService);
    }

    @Test
    void shouldCreate() throws Exception {
        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();

        CakeRequest request = testCakeBuilder.buildRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        CakeResponse expectedResponse = testCakeBuilder.buildResponse();
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        when(cakeService.create(any())).thenReturn(expectedResponse);

        // when, then
        mockMvc.perform(
                        post("/cakes")
                                .content(requestJson)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));

        verify(cakeService).create(request);
        verifyNoMoreInteractions(cakeService);
    }


    @Test
    void shouldDelete() throws Exception {
        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();
        UUID idToDeleteBy = testCakeBuilder.getId();

        // when, then
        mockMvc.perform(delete("/cakes/{id}", idToDeleteBy))
                .andExpect(status().isNoContent());

        verify(cakeService).deleteById(idToDeleteBy);
        verifyNoMoreInteractions(cakeService);
    }
}
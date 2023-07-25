package com.maradamark09.typingspeedtest.result;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maradamark09.typingspeedtest.auth.JWTAuthFilter;
import com.maradamark09.typingspeedtest.auth.UserNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ResultController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResultService resultService;

    @MockBean
    private JWTAuthFilter jwtAuthFilter;

    private static final String CONTROLLER_PATH = "/api/v1/results";

    @Test
    public void whenGetByValidUserId_thenReturns200_andListOfResultResponse() throws Exception {

        var validId = ResultDataProvider.USER_ID;

        var expected = ResultDataProvider.LIST_OF_RESULT_DTO;

        when(resultService.getByUserId(validId)).thenReturn(expected);

        var result = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH + "/user/{id}", validId))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        var actual = objectMapper.readValue(content, new TypeReference<List<ResultDTO>>() {
        });

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetByInvalidUserId_thenReturns404() throws Exception {
        var invalidId = ResultDataProvider.USER_ID;

        doThrow(new UserNotFoundException(invalidId)).when(resultService).getByUserId(any(UUID.class));

        mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH + "/user/{id}", invalidId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("\"The given user")));

    }

    @Test
    public void whenGetAmountOfWithValidPageAndSort_thenReturns200_andListOfResultResponse() throws Exception {
        var validPage = ResultDataProvider.VALID_PAGE;
        var validSize = ResultDataProvider.VALID_SIZE;
        var sortField = "accuracy";
        var sortDirection = "desc";

        var expected = ResultDataProvider.PAGE_OF_RESULT_DTO;

        Pageable pageable = new Pageable(validPage, validSize, List.of(sortField, sortDirection));

        when(resultService.getAmountOf(pageable)).thenReturn(expected);

        var response = mockMvc
                .perform(MockMvcRequestBuilders.get(CONTROLLER_PATH)
                        .queryParam("page", String.valueOf(validPage))
                        .queryParam("size", String.valueOf(
                                validSize))
                        .queryParam("sort", sortField + "," + sortDirection))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals(objectMapper.writeValueAsString(expected), response.getContentAsString());

    }

    @Test
    public void whenSaveWithValidValues_thenReturns201() throws Exception {
        var resultRequest = ResultDataProvider.VALID_RESULT_DTO;
        var user = ResultDataProvider.LOGGED_IN_USER;

        doNothing().when(resultService).save(resultRequest, user);

        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resultRequest))
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenSaveWithInvalidValues_thenReturns422() throws Exception {
        var resultRequest = ResultDataProvider.INVALID_RESULT_DTO;

        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resultRequest))
                .characterEncoding("utf-8"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void whenDeleteByExistingId_thenReturns204() throws Exception {
        var validId = ResultDataProvider.VALID_RESULT_ID;

        doNothing().when(resultService).deleteById(validId);

        mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/{id}", validId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenDeleteByIdNonExistingId_thenReturns404() throws Exception {
        var invalidId = ResultDataProvider.INVALID_RESULT_ID;

        doThrow(new ResultNotFoundException(invalidId)).when(resultService).deleteById(invalidId);

        mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/{id}", invalidId))
                .andExpect(status().isNotFound());
    }

}
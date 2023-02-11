package com.maradamark09.typingspeedtest.result;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maradamark09.typingspeedtest.auth.JWTAuthFilter;
import com.maradamark09.typingspeedtest.auth.UserNotFoundException;
import com.maradamark09.typingspeedtest.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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

        var validId = UUID.randomUUID();

        var expected = List.of(
                new ResultResponse(1L, (short) 65, 89.9, "john")
        );

        when(resultService.getByUserId(validId)).thenReturn(expected);

        var result = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH + "/user/{id}", validId))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        var actual = objectMapper.readValue(content, new TypeReference<List<ResultResponse>>() {});

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetByInvalidUserId_thenReturns404() throws Exception {
        var invalidId = UUID.randomUUID();

        doThrow(new UserNotFoundException(invalidId)).when(resultService).getByUserId(any(UUID.class));

        mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH + "/user/{id}", invalidId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("\"The given user")));

    }

    @Test
    public void whenGetAmountOfWithValidPage_thenReturns200_andListOfResultResponse() throws Exception {
        var validPage = 2;
        var validAmount = 3;

        var expected = List.of(
                new ResultResponse(1L, (short) 65, 89.9, "john")
        );

        when(resultService.getAmountOf(validPage, validAmount)).thenReturn(expected);

        var response = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH)
                            .queryParam("page", String.valueOf(validPage))
                            .queryParam("amount", String.valueOf(validAmount)))
                            .andExpect(status().isOk())
                            .andReturn().getResponse();

        assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(expected));

    }

    @Test
    public void whenGetAmountOfWithValidPage_thenReturns400() throws Exception {
        var invalidPage = -99;
        var validAmount = 1;

        doThrow(new IllegalArgumentException("The given page is invalid")).when(resultService).getAmountOf(invalidPage,validAmount);

        mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH)
                        .queryParam("page", String.valueOf(invalidPage))
                        .queryParam("amount", String.valueOf(validAmount)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("\"The given page is invalid")));

    }

    @Test
    public void whenSaveWithValidValues_thenReturns201() throws Exception {
        var resultRequest = new ResultRequest((short)85, 82.45);
        var user = new User();

        doNothing().when(resultService).save(resultRequest,user);

        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resultRequest))
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenSaveWithInvalidValues_thenReturns422() throws Exception {
        var resultRequest = new ResultRequest(null, 711238.1233);

        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resultRequest))
                        .characterEncoding("utf-8"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void whenDeleteByExistingId_thenReturns200() throws Exception {
        var validId = 1L;

        doNothing().when(resultService).deleteById(validId);

        mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH, validId))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteByIdNonExistingId_thenReturns404() throws Exception {
        var invalidId = -123L;

        doThrow(new ResultNotFoundException(invalidId)).when(resultService).deleteById(invalidId);

        mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/{id}", invalidId))
                .andExpect(status().isNotFound());
    }

}
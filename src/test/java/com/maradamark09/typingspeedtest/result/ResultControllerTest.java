package com.maradamark09.typingspeedtest.result;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maradamark09.typingspeedtest.auth.JWTAuthFilter;
import com.maradamark09.typingspeedtest.auth.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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
                new ResultResponse(1L, (short) 65, 89.9, "john"),
                new ResultResponse(2L, (short) 72, 94.00, "jane"),
                new ResultResponse(3L, (short) 44, 65.2, "timmy")
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

}
package com.maradamark09.typingspeedtest.difficulty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maradamark09.typingspeedtest.exception.MyErrorResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DifficultyController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DifficultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DifficultyService difficultyService;

    @Test
    void whenGetAllCalled_thenReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/difficulties"))
                .andExpect(status().isOk());
    }

    @Test
    void whenValidInputGiven_thenReturns201AndDifficulty() throws Exception {

        DifficultyRequest request = new DifficultyRequest("test", (byte) 10);
        Difficulty expected = new Difficulty(1L, "test", (byte) 10, Collections.emptySet());

        when(difficultyService.save(any(DifficultyRequest.class))).thenReturn(expected);

        var result =
                mockMvc.perform(post("/api/v1/difficulties/difficulty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding("utf-8"))
                        .andExpect(status().isCreated())
                        .andReturn();

        var actual = result.getResponse().getContentAsString();

        assertThat(actual).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expected)
        );

    }

    @Test
    void whenNullValueGiven_thenReturns400() throws Exception {

        DifficultyRequest request = new DifficultyRequest(null, (byte) 99);

        mockMvc.perform(post("/api/v1/difficulties/difficulty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());

    }

}
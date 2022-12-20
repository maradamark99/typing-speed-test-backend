package com.maradamark09.typingspeedtest.difficulty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
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
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    void whenGetAll_thenReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/difficulties"))
                .andExpect(status().isOk());
    }

    @Test
    void whenSaveWithValidValue_thenReturns201AndDifficulty() throws Exception {

        DifficultyRequest request = new DifficultyRequest("test", (byte) 10);
        Difficulty expected = new Difficulty(1L, "test", (byte) 10, Collections.emptySet());

        when(difficultyService.save(any(DifficultyRequest.class)))
                .thenReturn(expected);

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
    void whenSaveWithNullValue_thenReturns400() throws Exception {

        DifficultyRequest request = new DifficultyRequest(null, (byte) 99);

        mockMvc.perform(post("/api/v1/difficulties/difficulty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenSaveWithAlreadyExistingValue_thenReturns409() throws Exception {

        DifficultyRequest request = new DifficultyRequest("random", (byte) 1);

        doThrow(new ResourceAlreadyExistsException("The provided difficulty {" + request.getValue()
                + "} already exists.")).when(difficultyService).save(any(DifficultyRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/difficulties/difficulty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("utf-8"))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("\"The provided difficulty")));

    }

    @Test
    void whenDeleteByExistingId_thenReturns200() throws Exception {

        long id = 1L;

        doNothing()
                .when(difficultyService)
                .deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/difficulties/difficulty/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

    }

    @Test
    void whenDeleteByNonExistingId_thenReturns404() throws Exception {

        long id = 99999L;

        doThrow(new ResourceNotFoundException("The difficulty with the given id: {" + id + "} does not exist."))
                .when(difficultyService)
                .deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/difficulties/difficulty/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("\"The difficulty with the given id")));

    }

    @Test
    void whenUpdateWithValidValueAndId_thenReturns200() throws Exception {
        long id = 9L;
        DifficultyRequest request = new DifficultyRequest("yeah", (byte)99);
        Difficulty expected = new Difficulty(id, "yeah", (byte)99, Collections.emptySet());

        when(difficultyService.update(any(DifficultyRequest.class), eq(id))).thenReturn(expected);

        var result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/difficulties/difficulty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding("utf-8"))
                        .andExpect(status().isOk())
                        .andReturn();

        var actual = result.getResponse().getContentAsString();

        assertThat(actual).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expected)
        );
    }

}
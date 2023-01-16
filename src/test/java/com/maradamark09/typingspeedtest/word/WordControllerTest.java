package com.maradamark09.typingspeedtest.word;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maradamark09.typingspeedtest.difficulty.Difficulty;
import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import com.maradamark09.typingspeedtest.jwt.JWTAuthFilter;
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

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(WordController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class WordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WordService wordService;

    @MockBean
    private JWTAuthFilter jwtAuthFilter;

    private static final String CONTROLLER_PATH = "/api/v1/words";
    @Test
    public void whenGetAllByValidDifficulty_thenReturns200() throws Exception{

        var difficulty = "easy";

        mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH +"/{difficulty}", difficulty))
                .andExpect(status().isOk());

    }

    @Test
    public void whenGetAllByInvalidDifficulty_thenReturns404() throws Exception {

        var difficulty = "idk";
        var message = "test";

        doThrow(new ResourceNotFoundException(message))
                .when(wordService)
                .getAllByDifficulty(difficulty);

        mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH +"/{difficulty}", difficulty))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(message)));

    }

    @Test
    public void whenDeleteByExistingId_thenReturns200() throws Exception {
        long id = 1L;

        doNothing()
                .when(wordService)
                .deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

    }

    @Test
    public void whenDeleteByNonExistingId_thenReturns404() throws Exception {
        long id = 929349L;

        doThrow(new ResourceNotFoundException("test"))
                .when(wordService)
                .deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("test")));

    }

    @Test
    public void whenSaveWithValidValue_thenReturns201AndWord() throws Exception {

        var request = new WordRequest("test", 1L);
        var difficulty = new Difficulty(1L, "easy", (byte)12, Collections.emptySet());
        var expected = new Word(1L, "test", difficulty);

        when(wordService.save(any(WordRequest.class)))
                .thenReturn(expected);

        var result =
                mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
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
    public void whenSaveWithNullValue_thenReturns400() throws Exception {

        WordRequest request = new WordRequest(null, 3L);

        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void whenSaveWithAlreadyExistingValue_thenReturns409() throws Exception {

        WordRequest request = new WordRequest("test", 3L);

        doThrow(new ResourceAlreadyExistsException("test"))
                .when(wordService).save(any(WordRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding("utf-8"))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("test")));

    }

}
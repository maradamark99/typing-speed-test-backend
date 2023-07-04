package com.maradamark09.typingspeedtest.word;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maradamark09.typingspeedtest.difficulty.DifficultyNotFoundException;
import com.maradamark09.typingspeedtest.auth.JWTAuthFilter;
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
	public void whenGetAllByValidDifficulty_thenReturns200() throws Exception {

		var difficulty = WordDataProvider.VALID_DIFFICULTY;

		mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH + "/{difficulty}", difficulty))
				.andExpect(status().isOk());

	}

	@Test
	public void whenGetAllByInvalidDifficulty_thenReturns404() throws Exception {

		var difficulty = WordDataProvider.INVALID_DIFFICULTY;

		doThrow(new DifficultyNotFoundException())
				.when(wordService)
				.getAllByDifficulty(difficulty);

		mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH + "/{difficulty}", difficulty))
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString("\"The given difficulty ")));

	}

	@Test
	public void whenGetRandomByInvalidDifficulty_thenReturns404() throws Exception {

		var difficulty = WordDataProvider.INVALID_DIFFICULTY;
		var amount = WordDataProvider.AMOUNT_OF;

		doThrow(new DifficultyNotFoundException())
				.when(wordService)
				.getRandomWordsByDifficulty(difficulty, amount);

		mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH + "/random/{difficulty}", difficulty)
				.queryParam("amount", String.valueOf(amount)))
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString("\"The given difficulty ")));

	}

	@Test
	public void whenDeleteByExistingId_thenReturns204() throws Exception {

		long id = WordDataProvider.VALID_WORD_ID;

		doNothing()
				.when(wordService)
				.deleteById(id);

		mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/{id}", id))
				.andExpect(status().isNoContent())
				.andExpect(content().string(""));

	}

	@Test
	public void whenDeleteByNonExistingId_thenReturns404() throws Exception {

		long id = WordDataProvider.VALID_WORD_ID;

		doThrow(new WordNotFoundException(id))
				.when(wordService)
				.deleteById(id);

		mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString("\"The given word with")));

	}

	@Test
	public void whenSaveWithValidValue_thenReturns201AndWordDTO() throws Exception {

		var request = WordDataProvider.VALID_WORD_REQUEST;
		var expected = WordDTO
				.builder()
				.id(1L)
				.value(request.getValue())
				.difficulty_id(request.getDifficulty_id())
				.build();

		when(wordService.save(any(WordDTO.class)))
				.thenReturn(expected);

		var result = mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("utf-8"))
				.andExpect(status().isCreated())
				.andReturn();

		var actual = result.getResponse().getContentAsString();

		assertThat(actual).isEqualToIgnoringWhitespace(
				objectMapper.writeValueAsString(expected));

	}

	@Test
	public void whenSaveWithNullValue_thenReturns422() throws Exception {

		var request = WordDataProvider.INVALID_WORD_REQUEST;

		mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("utf-8"))
				.andExpect(status().isUnprocessableEntity());

	}

	@Test
	public void whenSaveWithAlreadyExistingValue_thenReturns409() throws Exception {

		var request = WordDataProvider.VALID_WORD_REQUEST;

		doThrow(new WordAlreadyExistsException(request.getValue()))
				.when(wordService).save(any(WordDTO.class));

		mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("utf-8"))
				.andExpect(status().isConflict())
				.andExpect(content().string(containsString(request.getValue())));

	}

}
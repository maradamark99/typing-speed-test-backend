package com.maradamark09.typingspeedtest.difficulty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maradamark09.typingspeedtest.auth.JWTAuthFilter;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = DifficultyController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DifficultyControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private DifficultyService difficultyService;

	@MockBean
	private JWTAuthFilter jwtAuthFilter;

	private static final String CONTROLLER_PATH = "/api/v1/difficulties";

	@Test
	public void whenGetAll_thenReturns200() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_PATH))
				.andExpect(status().isOk());
	}

	@Test
	public void whenSaveWithValidValue_thenReturns201AndDifficulty() throws Exception {

		var request = DifficultyDataProvider.VALID_DIFFICULTY_REQUEST;
		var expected = DifficultyDataProvider.DIFFICULTY_RESPONSE;

		when(difficultyService.save(any(DifficultyDTO.class)))
				.thenReturn(expected);

		var result = mockMvc.perform(post(CONTROLLER_PATH)
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
	public void whenSaveWithInvalidValue_thenReturns422() throws Exception {

		var request = DifficultyDataProvider.INVALID_DIFFICULTY_REQUEST;

		mockMvc.perform(post(CONTROLLER_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("utf-8"))
				.andExpect(status().isUnprocessableEntity());

	}

	@Test
	public void whenSaveWithAlreadyExistingValue_thenReturns409() throws Exception {

		var request = DifficultyDataProvider.VALID_DIFFICULTY_REQUEST;

		doThrow(new DifficultyAlreadyExistsException(request.getValue())).when(difficultyService)
				.save(any(DifficultyDTO.class));

		mockMvc.perform(MockMvcRequestBuilders.post(CONTROLLER_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("utf-8"))
				.andExpect(status().isConflict())
				.andExpect(content().string(containsString("\"The given difficulty")));

	}

	@Test
	public void whenDeleteByExistingId_thenReturns204() throws Exception {

		long id = DifficultyDataProvider.DIFFICULTY_ID;

		doNothing()
				.when(difficultyService)
				.deleteById(id);

		mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/{id}", id))
				.andExpect(status().isNoContent())
				.andExpect(content().string(""));

	}

	@Test
	public void whenDeleteByNonExistingId_thenReturns404() throws Exception {

		long id = DifficultyDataProvider.DIFFICULTY_ID;

		doThrow(new DifficultyNotFoundException(id))
				.when(difficultyService)
				.deleteById(id);

		mockMvc.perform(MockMvcRequestBuilders.delete(CONTROLLER_PATH + "/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString("\"The given difficulty")));

	}

	@Test
	public void whenUpdateWithValidValueAndId_thenReturns200() throws Exception {
		var id = DifficultyDataProvider.DIFFICULTY_ID;
		var request = DifficultyDataProvider.VALID_DIFFICULTY_REQUEST;
		var expected = DifficultyDataProvider.DIFFICULTY_RESPONSE;

		when(difficultyService.update(any(DifficultyDTO.class), eq(id))).thenReturn(expected);

		var result = mockMvc.perform(MockMvcRequestBuilders.put(CONTROLLER_PATH + "/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andReturn();

		var actual = result.getResponse().getContentAsString();

		assertThat(actual).isEqualToIgnoringWhitespace(
				objectMapper.writeValueAsString(expected));
	}

}
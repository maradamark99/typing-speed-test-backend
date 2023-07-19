package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DifficultyServiceImplTest {

    @InjectMocks
    private DifficultyServiceImpl difficultyService;

    @Mock
    private DifficultyRepository difficultyRepository;

    private final DifficultyMapper mapper = new DifficultyMapper();

    @BeforeEach
    void setup() {
        difficultyService = new DifficultyServiceImpl(mapper, difficultyRepository);
    }

    @Test
    public void whenGetAll_thenSuccess() {

        var expected = DifficultyDataProvider.LIST_OF_DIFFICULTIES;

        when(difficultyRepository.findAll()).thenReturn(expected);
        var actual = difficultyService.findAll().stream()
                .map((d -> new Difficulty(d.getId(), d.getValue(), d.getMaxWordLength(), Collections.emptySet())))
                .toList();

        assertThat(actual).containsExactlyElementsOf(expected);
        verify(difficultyRepository).findAll();

    }

    @Test
    public void whenDelete_andIdExists_thenSuccess() {

        long id = DifficultyDataProvider.DIFFICULTY_ID;

        when(difficultyRepository.existsById(id)).thenReturn(true);

        difficultyService.deleteById(id);

        verify(difficultyRepository).deleteById(id);

        when(difficultyRepository.existsById(id)).thenReturn(false);

        assertFalse(difficultyRepository.existsById(id));

    }

    @Test
    public void whenDelete_andIdDoesNotExist_thenThrowsException() {

        Long id = DifficultyDataProvider.DIFFICULTY_ID;
        when(difficultyRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> difficultyService.deleteById(id));
        verify(difficultyRepository, never()).deleteById(id);

    }

    @Test
    public void whenSave_andDifficultyNotExists_thenSuccess() {

        var difficultyDTO = DifficultyDataProvider.VALID_DIFFICULTY_REQUEST;
        when(difficultyRepository.existsByValueIgnoreCase(difficultyDTO.getValue())).thenReturn(false);

        var difficultyToSave = Difficulty.builder()
                .value(difficultyDTO.getValue())
                .maxWordLength(difficultyDTO.getMaxWordLength())
                .build();

        var difficulty = Difficulty.builder()
                .id(1L)
                .value(difficultyDTO.getValue())
                .maxWordLength(difficultyDTO.getMaxWordLength())
                .build();
        var expected = mapper.entityToDto(difficulty);

        when(difficultyRepository.save(difficultyToSave)).thenReturn(difficulty);

        var actual = difficultyService.save(difficultyDTO);
        assertEquals(expected, actual);
        verify(difficultyRepository).existsByValueIgnoreCase(difficultyDTO.getValue());
        verify(difficultyRepository).save(difficultyToSave);

    }

    @Test
    public void whenSave_andDifficultyAlreadyExists_thenThrowsException() {

        var difficultyDTO = DifficultyDataProvider.VALID_DIFFICULTY_REQUEST;
        when(difficultyRepository.existsByValueIgnoreCase(difficultyDTO.getValue())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> difficultyService.save(difficultyDTO));

        verify(difficultyRepository).existsByValueIgnoreCase(difficultyDTO.getValue());
        verify(difficultyRepository, never()).save(any());

    }

    @Test
    public void whenUpdate_andDifficultyExists_thenSuccess() {

        var id = DifficultyDataProvider.DIFFICULTY_ID_TO_UPDATE;
        var difficultyDTO = DifficultyDataProvider.VALID_DIFFICULTY_REQUEST;

        when(difficultyRepository.existsById(id)).thenReturn(true);

        var expectedEntity = Difficulty.builder()
                .id(id)
                .value(difficultyDTO.getValue())
                .maxWordLength(difficultyDTO.getMaxWordLength())
                .build();

        var expectedDTO = mapper.entityToDto(expectedEntity);

        when(difficultyRepository.save(mapper.dtoToEntity(difficultyDTO))).thenReturn(expectedEntity);

        var actual = difficultyService.update(difficultyDTO, id);

        assertEquals(expectedDTO, actual);

    }

    @Test
    public void whenUpdate_andDifficultyDoesntExist_thenThrowsDifficultyNotFoundException() {

        var id = DifficultyDataProvider.DIFFICULTY_ID_TO_UPDATE;
        var difficultyDTO = DifficultyDataProvider.VALID_DIFFICULTY_REQUEST;
        when(difficultyRepository.existsById(id)).thenReturn(false);
        assertThrows(DifficultyNotFoundException.class,
                () -> {
                    difficultyService.update(difficultyDTO, id);
                });
    }
}
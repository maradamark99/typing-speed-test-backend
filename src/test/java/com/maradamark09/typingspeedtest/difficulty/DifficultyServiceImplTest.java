package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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


    @Test
    public void whenGetAll_thenSuccess() {

        var expected = DifficultyDataProvider.LIST_OF_DIFFICULTY_RESPONSES;

        when(difficultyRepository.findAll()).thenReturn(expected);
        var actual = difficultyService.findAll();

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

        DifficultyRequest difficultyRequest = DifficultyDataProvider.VALID_DIFFICULTY_REQUEST;
        when(difficultyRepository.existsByValue(difficultyRequest.value())).thenReturn(false);

        Difficulty difficultyToSave = Difficulty.builder()
                .value(difficultyRequest.value())
                .maxWordLength(difficultyRequest.maxWordLength())
                .build();

        Difficulty expected = Difficulty.builder()
                .id(1L)
                .value(difficultyRequest.value())
                .maxWordLength(difficultyRequest.maxWordLength())
                .build();

        when(difficultyRepository.save(difficultyToSave)).thenReturn(expected);

        Difficulty actual = difficultyService.save(difficultyRequest);

        assertEquals(expected, actual);
        verify(difficultyRepository).existsByValue(difficultyRequest.value());
        verify(difficultyRepository).save(difficultyToSave);

    }

    @Test
    public void whenSave_andDifficultyAlreadyExists_thenThrowsException() {

        DifficultyRequest difficultyRequest = DifficultyDataProvider.VALID_DIFFICULTY_REQUEST;
        when(difficultyRepository.existsByValue(difficultyRequest.value())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> difficultyService.save(difficultyRequest));

        verify(difficultyRepository).existsByValue(difficultyRequest.value());
        verify(difficultyRepository, never()).save(any());

    }

    @Test
    public void whenUpdate_andDifficultyExists_thenSuccess() {

        Long id = DifficultyDataProvider.DIFFICULTY_ID_TO_UPDATE;
        DifficultyRequest difficultyRequest = DifficultyDataProvider.VALID_DIFFICULTY_REQUEST;

        Difficulty difficulty = Difficulty.builder()
                .id(id)
                .value("Hard")
                .maxWordLength((byte)10)
                .build();

        when(difficultyRepository.findById(id)).thenReturn(Optional.of(difficulty));

        Difficulty updatedDifficulty = Difficulty.builder()
                .id(id)
                .value(difficultyRequest.value())
                .maxWordLength(difficultyRequest.maxWordLength())
                .build();

        when(difficultyRepository.save(difficulty)).thenReturn(updatedDifficulty);

        Difficulty result = difficultyService.update(difficultyRequest, id);

        assertEquals(updatedDifficulty, result);
        verify(difficultyRepository).findById(id);
        verify(difficultyRepository).save(difficulty);

    }

    @Test
    public void whenUpdate_andDifficultyNotExists_thenCreatesNewDifficulty() {

        Long id = DifficultyDataProvider.DIFFICULTY_ID_TO_UPDATE;
        DifficultyRequest difficultyRequest = DifficultyDataProvider.VALID_DIFFICULTY_REQUEST;
        when(difficultyRepository.findById(id)).thenReturn(Optional.empty());

        Difficulty difficultyToSave = Difficulty.builder()
                .value(difficultyRequest.value())
                .maxWordLength(difficultyRequest.maxWordLength())
                .build();

        Difficulty expected = Difficulty.builder()
                .id(id)
                .value(difficultyRequest.value())
                .maxWordLength(difficultyRequest.maxWordLength())
                .build();

        when(difficultyRepository.save(difficultyToSave)).thenReturn(expected);

        Difficulty actual = difficultyService.update(difficultyRequest, id);

        assertEquals(expected, actual);
        verify(difficultyRepository).findById(id);
        verify(difficultyRepository).save(difficultyToSave);

    }

}
package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        var expected = List.of(
                new Difficulty(1L,"easy",(byte)10, Collections.emptySet()),
                new Difficulty(2L,"medium",(byte)20, Collections.emptySet()),
                new Difficulty(3L,"hard",(byte)30, Collections.emptySet())
        );

        when(difficultyRepository.findAll()).thenReturn(expected);
        var actual = difficultyService.findAll();

        assertEquals(expected, actual);
        verify(difficultyRepository).findAll();

    }

    @Test
    public void whenDelete_andIdExists_thenSuccess() {

        long id = 1L;

        when(difficultyRepository.existsById(id)).thenReturn(true);

        difficultyService.deleteById(id);

        verify(difficultyRepository).deleteById(id);

        when(difficultyRepository.existsById(id)).thenReturn(false);

        assertFalse(difficultyRepository.existsById(id));

    }

    @Test
    public void whenDelete_andIdDoesNotExist_thenThrowsException() {

        Long id = 1L;
        when(difficultyRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> difficultyService.deleteById(id));
        verify(difficultyRepository, never()).deleteById(id);

    }

    @Test
    public void whenSave_andDifficultyNotExists_thenSuccess() {

        DifficultyRequest difficultyRequest = new DifficultyRequest("Easy", (byte)5);
        when(difficultyRepository.existsByValue(difficultyRequest.getValue())).thenReturn(false);

        Difficulty difficultyToSave = Difficulty.builder()
                .value(difficultyRequest.getValue())
                .maxWordLength(difficultyRequest.getMaxWordLength())
                .build();

        Difficulty savedDifficulty = Difficulty.builder()
                .id(1L)
                .value(difficultyRequest.getValue())
                .maxWordLength(difficultyRequest.getMaxWordLength())
                .build();

        when(difficultyRepository.save(difficultyToSave)).thenReturn(savedDifficulty);

        Difficulty result = difficultyService.save(difficultyRequest);

        assertEquals(savedDifficulty, result);
        verify(difficultyRepository).existsByValue(difficultyRequest.getValue());
        verify(difficultyRepository).save(difficultyToSave);

    }

    @Test
    public void whenSave_andDifficultyAlreadyExists_thenThrowsException() {

        DifficultyRequest difficultyRequest = new DifficultyRequest("Easy", (byte)5);
        when(difficultyRepository.existsByValue(difficultyRequest.getValue())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> difficultyService.save(difficultyRequest));

        verify(difficultyRepository).existsByValue(difficultyRequest.getValue());
        verify(difficultyRepository, never()).save(any());

    }

    @Test
    public void whenUpdate_andDifficultyExists_thenSuccess() {

        Long id = 1L;
        DifficultyRequest difficultyRequest = new DifficultyRequest("Easy", (byte)5);

        Difficulty difficulty = Difficulty.builder()
                .id(id)
                .value("Hard")
                .maxWordLength((byte)10)
                .build();

        when(difficultyRepository.findById(id)).thenReturn(Optional.of(difficulty));

        Difficulty updatedDifficulty = Difficulty.builder()
                .id(id)
                .value(difficultyRequest.getValue())
                .maxWordLength(difficultyRequest.getMaxWordLength())
                .build();

        when(difficultyRepository.save(difficulty)).thenReturn(updatedDifficulty);

        Difficulty result = difficultyService.update(difficultyRequest, id);

        assertEquals(updatedDifficulty, result);
        verify(difficultyRepository).findById(id);
        verify(difficultyRepository).save(difficulty);

    }

    @Test
    public void whenUpdate_andDifficultyNotExists_thenCreatesNewDifficulty() {

        Long id = 1L;
        DifficultyRequest difficultyRequest = new DifficultyRequest("Easy", (byte)5);
        when(difficultyRepository.findById(id)).thenReturn(Optional.empty());

        Difficulty difficultyToSave = Difficulty.builder()
                .value(difficultyRequest.getValue())
                .maxWordLength(difficultyRequest.getMaxWordLength())
                .build();

        Difficulty savedDifficulty = Difficulty.builder()
                .id(id)
                .value(difficultyRequest.getValue())
                .maxWordLength(difficultyRequest.getMaxWordLength())
                .build();

        when(difficultyRepository.save(difficultyToSave)).thenReturn(savedDifficulty);

        Difficulty result = difficultyService.update(difficultyRequest, id);

        assertEquals(savedDifficulty, result);
        verify(difficultyRepository).findById(id);
        verify(difficultyRepository).save(difficultyToSave);

    }

}
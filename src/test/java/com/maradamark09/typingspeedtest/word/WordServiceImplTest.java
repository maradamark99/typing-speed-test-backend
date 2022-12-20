package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.difficulty.DifficultyRepository;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordServiceImplTest {

    @InjectMocks
    WordServiceImpl wordService;

    @Mock
    WordRepository wordRepository;

    @Mock
    DifficultyRepository difficultyRepository;

    @Test
    public void whenGetAllByDifficulty_andDifficultyExists_thenSuccess() {

        String difficulty = "easy";
        when(difficultyRepository.existsByValue(difficulty)).thenReturn(true);
        List<String> expectedWords = List.of("word1", "word2", "word3");
        when(wordRepository.findAllByDifficulty(difficulty)).thenReturn(expectedWords);

        List<String> result = wordService.getAllByDifficulty(difficulty);

        assertEquals(expectedWords, result);
        verify(difficultyRepository).existsByValue(difficulty);
        verify(wordRepository).findAllByDifficulty(difficulty);

    }

    @Test
    public void whenGetAllByDifficulty_andDifficultyDoesNotExist_thenThrowsException() {

        String difficulty = "easy";
        when(difficultyRepository.existsByValue(difficulty)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> wordService.getAllByDifficulty(difficulty));

        verify(difficultyRepository).existsByValue(difficulty);
        verify(wordRepository, never()).findAllByDifficulty(difficulty);

    }



}
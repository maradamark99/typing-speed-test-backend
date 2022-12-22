package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.difficulty.Difficulty;
import com.maradamark09.typingspeedtest.difficulty.DifficultyRepository;
import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import com.maradamark09.typingspeedtest.exception.WordLengthGreaterThanDifficultyException;
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

    @Test
    public void whenDeleteById_andWordExistsById_thenSuccess() {

        long id = 1L;

        when(wordRepository.findById(id)).thenReturn(Optional.of(new Word()));

        wordService.deleteById(id);

        verify(wordRepository).deleteById(id);

        when(wordRepository.findById(id)).thenReturn(Optional.empty());

        assertFalse(wordRepository.findById(id).isPresent());

    }

    @Test
    public void whenDeleteById_andWordDoesNotExistById_thenThrowsException() {

        long id = 1L;
        when(wordRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> wordService.deleteById(id));
        verify(wordRepository, never()).deleteById(id);

    }

    @Test
    public void whenSave_andDifficultyIdDoesNotExist_thenThrowsException() {

        when(difficultyRepository.findById(anyLong())).thenReturn(Optional.empty());

        WordRequest wordRequest = new WordRequest("word", 99L);
        Word word = new Word();

        assertThrows(ResourceNotFoundException.class, () -> wordService.save(wordRequest));
        verify(wordRepository, never()).save(word);

    }

    @Test
    public void whenSave_andWordLengthGreaterThanDifficulty_thenThrowsException() {

        Difficulty difficulty = new Difficulty(1L, "easy", (byte)5, Collections.emptySet());
        Word word = new Word();

        when(difficultyRepository.findById(difficulty.getId())).thenReturn(Optional.of(difficulty));
        WordRequest wordRequest = new WordRequest("iamverylong", difficulty.getId());

        assertThrows(WordLengthGreaterThanDifficultyException.class, () -> wordService.save(wordRequest));
        verify(wordRepository, never()).save(word);

    }

    @Test
    public void whenSave_andWordAlreadyExists_thenThrowsException() {

        Difficulty difficulty = new Difficulty(1L, "easy", (byte)5, Collections.emptySet());
        Word word = new Word();

        when(difficultyRepository.findById(difficulty.getId())).thenReturn(Optional.of(difficulty));
        when(wordRepository.existsByValue("word")).thenReturn(true);

        WordRequest wordRequest = new WordRequest("word", difficulty.getId());

        assertThrows(ResourceAlreadyExistsException.class, () -> wordService.save(wordRequest));
        verify(wordRepository, never()).save(word);

    }

    @Test
    public void whenSave_andWordDoesNotExist_andIsValid_thenSuccess() {

        Difficulty difficulty = new Difficulty(1L, "easy", (byte)5, Collections.emptySet());

        when(difficultyRepository.findById(difficulty.getId())).thenReturn(Optional.of(difficulty));
        when(wordRepository.existsByValue("word")).thenReturn(false);

        WordRequest wordRequest = new WordRequest("word", difficulty.getId());
        Word wordToSave = Word.builder()
                .value(wordRequest.getValue())
                .difficulty(difficulty)
                .build();

        Word savedWord = new Word(1L, "word", difficulty);

        when(wordRepository.save(wordToSave)).thenReturn(savedWord);

        Word result = wordService.save(wordRequest);

        assertEquals(savedWord, result);
        verify(wordRepository).existsByValue(wordRequest.getValue());
        verify(wordRepository).save(wordToSave);

    }

}
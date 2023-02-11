package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResultServiceImplTest {


    @InjectMocks
    private ResultServiceImpl resultService;
    @Mock
    private ResultRepository resultRepository;

    @Test
    public void whenDelete_andIdExists_thenSuccess() {
        var id = 1L;

        when(resultRepository.existsById(id)).thenReturn(true);

        resultService.deleteById(id);

        verify(resultRepository).deleteById(id);

        when(resultRepository.existsById(id)).thenReturn(false);

        assertFalse(resultRepository.existsById(id));

    }

    @Test
    public void whenDelete_andIdDoesntExist_thenThrowsException() {
        var id = -929L;

        when(resultRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> resultService.deleteById(id));
        verify(resultRepository, never()).deleteById(id);
    }

}
package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.auth.UserNotFoundException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;
import com.maradamark09.typingspeedtest.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResultServiceImplTest {

    @InjectMocks
    private ResultServiceImpl resultService;
    @Mock
    private ResultRepository resultRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void whenGetAmountOf_andParametersInvalid_thenThrowsException() {

        var exception = assertThrows(IllegalArgumentException.class, () -> resultService.getAmountOf(PageRequest.of(
                ResultDataProvider.INVALID_PAGE, ResultDataProvider.INVALID_SIZE)));
        assertThat(exception.getMessage()).contains("Page index must not be");

    }

    @Test
    public void whenGetByUserId_andUserExists_thenSuccess() {

        var uuid = ResultDataProvider.USER_ID;
        var expected = ResultDataProvider.LIST_OF_RESULT_DTO;

        when(userRepository.existsById(uuid)).thenReturn(true);

        when(resultRepository.findResultByUserId(uuid)).thenReturn(ResultDataProvider.LIST_OF_RESULT_ENTITIES);

        var actual = resultService.getByUserId(uuid);

        verify(resultRepository).findResultByUserId(uuid);
        assertThat(actual).containsExactlyElementsOf(expected);

    }

    @Test
    public void whenGetByUserId_andUserIdNotExists_thenThrowsException() {

        var uuid = ResultDataProvider.USER_ID;
        when(userRepository.existsById(uuid)).thenReturn(false);
        var exception = assertThrows(UserNotFoundException.class, () -> resultService.getByUserId(uuid));
        assertThat(exception.getMessage()).contains("The given user");

    }

    @Test
    public void whenDelete_andIdExists_thenSuccess() {

        when(resultRepository.existsById(ResultDataProvider.VALID_RESULT_ID)).thenReturn(true);

        resultService.deleteById(ResultDataProvider.VALID_RESULT_ID);

        verify(resultRepository).deleteById(ResultDataProvider.VALID_RESULT_ID);

        when(resultRepository.existsById(ResultDataProvider.VALID_RESULT_ID)).thenReturn(false);

        assertFalse(resultRepository.existsById(ResultDataProvider.VALID_RESULT_ID));

    }

    @Test
    public void whenDelete_andIdDoesntExist_thenThrowsException() {

        when(resultRepository.existsById(ResultDataProvider.INVALID_RESULT_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> resultService.deleteById(ResultDataProvider.INVALID_RESULT_ID));
        verify(resultRepository, never()).deleteById(ResultDataProvider.INVALID_RESULT_ID);

    }

    @Test
    public void whenSave_withValidData_thenSuccess() {

        resultService.save(ResultDataProvider.VALID_RESULT_DTO, ResultDataProvider.LOGGED_IN_USER);

        ArgumentCaptor<Result> resultCaptor = ArgumentCaptor.forClass(Result.class);
        verify(resultRepository).save(resultCaptor.capture());

        Result actual = resultCaptor.getValue();
        assertEquals(actual.getWpm(), ResultDataProvider.VALID_RESULT_DTO.getWpm());
        assertEquals(actual.getAccuracy().doubleValue(), ResultDataProvider.VALID_RESULT_DTO.getAccuracy());
        assertEquals(actual.getUser(), ResultDataProvider.LOGGED_IN_USER);

    }

}
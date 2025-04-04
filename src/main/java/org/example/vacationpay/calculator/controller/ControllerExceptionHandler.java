package org.example.vacationpay.calculator.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.vacationpay.calculator.dto.ErrorMessageDto;
import org.example.vacationpay.calculator.exception.VacationValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDto handleThrowable(final Throwable e) {
        log.error("Unexpected error occurred", e);
        return new ErrorMessageDto("Непредвиденная ошибка.", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        log.error("Invalid request parameter: {}", e.getMessage(), e);
        return new ErrorMessageDto("Некорректный формат введенных данных", e.getMessage());
    }

    @ExceptionHandler(VacationValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessageDto handleValidationException(final VacationValidationException e) {
        log.error("Validation error: {}", e.getMessage(), e);
        return new ErrorMessageDto("Ошибка введенных данных:", e.getMessage());
    }
}

package org.example.vacationpaycalculator.controller;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.example.vacationpaycalculator.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    private final static Logger logger = LoggerFactory.getLogger("org.example.vacationpaycalculator.controller");

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        logger.error(e.getMessage());
        return new ErrorResponse("Непредвиденная ошибка.", e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        logger.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка введенных данных:", e.getMessage());
    }
}

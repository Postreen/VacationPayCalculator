package org.example.vacationpay.calculator.exception;

import jakarta.validation.ValidationException;

public class VacationValidationException extends ValidationException {

    public VacationValidationException(String message) {
        super(message);
    }
}

package org.example.vacationpay.calculator.service.days;

import java.time.LocalDate;

public interface DaysCalculationService {
    int calculateDays(LocalDate startVacationDate, LocalDate endVacationDate, int vacationDays);
}

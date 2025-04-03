package org.example.vacationpay.calculator.service.days;

import org.example.vacationpay.calculator.dto.enums.Region;

import java.time.LocalDate;

public interface DaysCalculationService {
    int calculateDays(LocalDate startVacationDate, LocalDate endVacationDate, int vacationDays, Region region);
}

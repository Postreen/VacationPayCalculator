package org.example.vacationpaycalculator.service.days;

import java.time.LocalDate;

public interface DaysCalculationService {
    int calculateDays(LocalDate startVacationDate, LocalDate endVacationDate, int vacationDays);
}

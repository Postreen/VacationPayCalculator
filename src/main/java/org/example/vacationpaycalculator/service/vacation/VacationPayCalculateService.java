package org.example.vacationpaycalculator.service.vacation;

import org.example.vacationpaycalculator.dto.VacationPayCalculate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VacationPayCalculateService {
    VacationPayCalculate getVacationPayCalculation(BigDecimal averageSalaryPerYear, int vacationDays,
                                                   LocalDate startVacationDate, LocalDate endVacationDate);
}

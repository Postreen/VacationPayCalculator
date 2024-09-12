package org.example.vacationpaycalculator.service.vacation;

import org.example.vacationpaycalculator.dto.VacationPayCalculate;

import java.math.BigDecimal;

public interface VacationPayCalculateService {
    VacationPayCalculate getVacationPayCalculation(BigDecimal averageSalaryPerYear, int vacationDays);
}

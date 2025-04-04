package org.example.vacationpay.calculator.service;

import org.example.vacationpay.calculator.dto.VacationPayCalculate;
import org.example.vacationpay.calculator.dto.enums.Region;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VacationPayCalculateService {
    VacationPayCalculate calculateVacationPay(
            BigDecimal averageSalaryPerMonth,
            Integer vacationDays,
            LocalDate startVacationDate,
            LocalDate endVacationDate,
            Region region);
}

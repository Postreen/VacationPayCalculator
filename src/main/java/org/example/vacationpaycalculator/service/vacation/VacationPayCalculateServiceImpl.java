package org.example.vacationpaycalculator.service.vacation;

import lombok.RequiredArgsConstructor;
import org.example.vacationpaycalculator.dto.VacationPayCalculate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
@Component
@RequiredArgsConstructor
public class VacationPayCalculateService {
    private static final double AVERAGE_NUMBER_OF_CALENDAR_DAY_IN_MONTH = 29.3;
    private static final double NDFL = 0.13;
    public VacationPayCalculate getVacationPayCalculation(BigDecimal averageSalaryPerYear,
                                                         int vacationDays) {

        //Средний заработок за 1 день
        BigDecimal averageEarningsPerDay = averageSalaryPerYear
                .divide(BigDecimal.valueOf(AVERAGE_NUMBER_OF_CALENDAR_DAY_IN_MONTH), 2, RoundingMode.HALF_EVEN);

        //Зарплата отпускных без вычета НДФЛ
        BigDecimal totalPayWithoutNDFL = averageEarningsPerDay.multiply(BigDecimal.valueOf(vacationDays));

        //Сумма НДФЛ
        BigDecimal amountNDFL = totalPayWithoutNDFL
                .multiply(BigDecimal.valueOf(NDFL)).setScale(0, RoundingMode.HALF_UP);

        //Зарплата с вычетом НДФЛ
        BigDecimal totalPay = totalPayWithoutNDFL.subtract(amountNDFL);

        return new VacationPayCalculate(totalPay);
    }
}

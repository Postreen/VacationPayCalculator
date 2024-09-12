package org.example.vacationpaycalculator.service.vacation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vacationpaycalculator.dto.VacationPayCalculate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Component
@RequiredArgsConstructor
public class VacationPayCalculateServiceImpl implements VacationPayCalculateService {
    private static final double AVERAGE_NUMBER_OF_CALENDAR_DAY_IN_MONTH = 29.3;
    private static final double NDFL = 0.13;
    @Override
    public VacationPayCalculate getVacationPayCalculation(BigDecimal averageSalaryPerYear,
                                                         int vacationDays) {
        BigDecimal averageEarningsPerDay = averageSalaryPerYear
                .divide(BigDecimal.valueOf(AVERAGE_NUMBER_OF_CALENDAR_DAY_IN_MONTH), 2, RoundingMode.HALF_EVEN);
        log.info("Средний заработок в день = {} RUB", averageEarningsPerDay);

        BigDecimal totalPayWithoutNDFL = averageEarningsPerDay.multiply(BigDecimal.valueOf(vacationDays));
        log.info("Сумма отпускных без вычета НДФЛ = {} RUB", totalPayWithoutNDFL);

        BigDecimal excludingNDFL = totalPayWithoutNDFL
                .multiply(BigDecimal.valueOf(NDFL)).setScale(0, RoundingMode.HALF_UP);
        log.info("Сумма НДФЛ = {} RUB", excludingNDFL);

        BigDecimal totalPay = totalPayWithoutNDFL.subtract(excludingNDFL);
        log.info("Сумма отпускных с вычетом НДФЛ = {} RUB", totalPay);

        return new VacationPayCalculate(totalPay);
    }
}

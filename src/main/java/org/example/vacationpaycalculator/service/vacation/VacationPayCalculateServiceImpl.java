package org.example.vacationpaycalculator.service.vacation;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vacationpaycalculator.dto.VacationPayCalculate;
import org.example.vacationpaycalculator.service.days.DaysCalculationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class VacationPayCalculateServiceImpl implements VacationPayCalculateService {
    private static final double AVERAGE_NUMBER_OF_CALENDAR_DAY_IN_MONTH = 29.3;
    private static final double NDFL = 0.13;
    private final DaysCalculationServiceImpl daysCalculationService = new DaysCalculationServiceImpl();
    private static final Logger logger = LoggerFactory.getLogger("org.example.vacationpaycalculator.service");

    @Override
    public VacationPayCalculate getVacationPayCalculation(BigDecimal averageSalaryPerYear, int vacationDays,
                                                         LocalDate startVacationDate, LocalDate endVacationDate) {
        if (vacationDays < 0) {
            throw new ValidationException("Количество дней не может быть отрицательным");
        } else if (startVacationDate != null || endVacationDate != null) {
            vacationDays = daysCalculationService.calculateDays(startVacationDate, endVacationDate, vacationDays);
        }

        BigDecimal averageEarningsPerDay = averageSalaryPerYear
                .divide(BigDecimal.valueOf(AVERAGE_NUMBER_OF_CALENDAR_DAY_IN_MONTH), 2, RoundingMode.HALF_EVEN);
        logger.info("Средний заработок в день = {} RUB", averageEarningsPerDay);

        BigDecimal totalPayWithoutNDFL = averageEarningsPerDay.multiply(BigDecimal.valueOf(vacationDays));
        logger.info("Сумма отпускных без вычета НДФЛ = {} RUB", totalPayWithoutNDFL);

        BigDecimal excludingNDFL = totalPayWithoutNDFL
                .multiply(BigDecimal.valueOf(NDFL)).setScale(0, RoundingMode.HALF_UP);
        logger.info("Сумма НДФЛ = {} RUB", excludingNDFL);

        BigDecimal totalPay = totalPayWithoutNDFL.subtract(excludingNDFL);
        logger.info("Сумма отпускных с вычетом НДФЛ = {} RUB", totalPay);

        return new VacationPayCalculate(totalPay);
    }
}

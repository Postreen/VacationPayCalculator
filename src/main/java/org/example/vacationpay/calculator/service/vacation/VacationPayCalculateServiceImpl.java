package org.example.vacationpay.calculator.service.vacation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vacationpay.calculator.config.VacationProperties;
import org.example.vacationpay.calculator.dto.VacationPayCalculate;
import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.exception.VacationValidationException;
import org.example.vacationpay.calculator.service.days.DaysCalculationService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class VacationPayCalculateServiceImpl implements VacationPayCalculateService {

    private final VacationProperties vacationProperties;
    private final DaysCalculationService daysCalculationService;

    @Override
    public VacationPayCalculate calculateVacationPay(
            BigDecimal averageSalaryPerYear,
            int vacationDays,
            LocalDate startVacationDate,
            LocalDate endVacationDate,
            Region region
    ) {
        validateVacationDays(vacationDays);

        if (startVacationDate != null || endVacationDate != null) {
            vacationDays = daysCalculationService.calculateDays(startVacationDate, endVacationDate, vacationDays, region);
        }

        BigDecimal averageEarningsPerDay = calculateAverageEarningsPerDay(averageSalaryPerYear);
        BigDecimal totalPayWithoutNDFL = calculateTotalPayWithoutNDFL(averageEarningsPerDay, vacationDays);
        BigDecimal excludingNDFL = calculateNDFL(totalPayWithoutNDFL);
        BigDecimal totalPay = totalPayWithoutNDFL.subtract(excludingNDFL);

        logVacationCalculation(averageEarningsPerDay, totalPayWithoutNDFL, excludingNDFL, totalPay);

        return new VacationPayCalculate(totalPay);
    }

    private void validateVacationDays(int vacationDays) {
        if (vacationDays < 0) {
            throw new VacationValidationException("Количество дней не может быть отрицательным");
        }
    }

    private BigDecimal calculateAverageEarningsPerDay(BigDecimal averageSalaryPerYear) {
        return averageSalaryPerYear.divide(vacationProperties.getAverageDaysInMonth(), 2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateTotalPayWithoutNDFL(BigDecimal averageEarningsPerDay, int vacationDays) {
        return averageEarningsPerDay.multiply(BigDecimal.valueOf(vacationDays));
    }

    private BigDecimal calculateNDFL(BigDecimal totalPayWithoutNDFL) {
        return totalPayWithoutNDFL.multiply(vacationProperties.getNdfl()).setScale(0, RoundingMode.HALF_UP);
    }

    private void logVacationCalculation(BigDecimal avgEarnings, BigDecimal totalBeforeTax, BigDecimal tax, BigDecimal totalAfterTax) {
        log.info("""
                Расчет отпускных:
                Заработок в день = {} RUB
                Сумма отпускных без вычета НДФЛ = {} RUB
                Сумма НДФЛ = {} RUB
                Сумма отпускных с вычетом НДФЛ = {} RUB
                """, avgEarnings, totalBeforeTax, tax, totalAfterTax);
    }
}

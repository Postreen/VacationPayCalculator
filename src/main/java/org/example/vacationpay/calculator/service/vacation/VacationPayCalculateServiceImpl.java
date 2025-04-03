package org.example.vacationpay.calculator.service.vacation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vacationpay.calculator.dto.VacationPayCalculate;
import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.exception.VacationValidationException;
import org.example.vacationpay.calculator.service.days.DaysCalculationService;
import org.example.vacationpay.calculator.service.tax.TaxCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationPayCalculateServiceImpl implements VacationPayCalculateService {

    private final BigDecimal AVERAGE_DAYS_IN_MONTH = BigDecimal.valueOf(29.3);

    private final DaysCalculationService daysCalculationService;
    private final TaxCalculationService taxCalculationService;

    @Override
    public VacationPayCalculate calculateVacationPay(
            BigDecimal averageSalaryPerMonth,
            int vacationDays,
            LocalDate startVacationDate,
            LocalDate endVacationDate,
            Region region
    ) {
        validateVacationDays(vacationDays);

        if (startVacationDate != null || endVacationDate != null) {
            vacationDays = daysCalculationService.calculateDays(startVacationDate, endVacationDate, vacationDays, region);
        }

        BigDecimal averageEarningsPerDay = calculateAverageEarningsPerDay(averageSalaryPerMonth);
        BigDecimal totalPayWithoutNDFL = calculateTotalPayWithoutNDFL(averageEarningsPerDay, vacationDays);
        BigDecimal excludingNDFL = calculateTaxAmount(totalPayWithoutNDFL, region, averageSalaryPerMonth);
        BigDecimal totalPay = calculateTotalPayWithNDFL(totalPayWithoutNDFL, excludingNDFL);

        logVacationCalculation(averageEarningsPerDay, totalPayWithoutNDFL, excludingNDFL, totalPay);

        return new VacationPayCalculate(totalPay);
    }

    private void validateVacationDays(int vacationDays) {
        if (vacationDays < 0) {
            throw new VacationValidationException("Количество дней не может быть отрицательным");
        }
    }

    private BigDecimal calculateAverageEarningsPerDay(BigDecimal averageSalaryPerMonth) {
        return averageSalaryPerMonth.divide(AVERAGE_DAYS_IN_MONTH, 2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateTotalPayWithoutNDFL(
            BigDecimal averageEarningsPerDay,
            int vacationDays
    ) {
        return averageEarningsPerDay.multiply(BigDecimal.valueOf(vacationDays));
    }

    private BigDecimal calculateTaxAmount(
            BigDecimal totalPayWithoutNDFL,
            Region region,
            BigDecimal averageSalaryPerMonth
    ) {
        BigDecimal ndflRate = taxCalculationService.calculateNdfl(region, averageSalaryPerMonth);
        return totalPayWithoutNDFL.multiply(ndflRate).setScale(0, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTotalPayWithNDFL(
            BigDecimal totalPayWithoutNDFL,
            BigDecimal excludingNDFL
    ) {
        return totalPayWithoutNDFL.subtract(excludingNDFL);
    }

    private void logVacationCalculation(BigDecimal avgEarnings, BigDecimal totalBeforeTax, BigDecimal tax, BigDecimal totalAfterTax) {
        log.info("""
                Расчет отпускных:
                Заработок в день = {} 
                Сумма отпускных без вычета НДФЛ = {} 
                Сумма НДФЛ = {} 
                Сумма отпускных с вычетом НДФЛ = {} 
                """, avgEarnings, totalBeforeTax, tax, totalAfterTax);
    }
}

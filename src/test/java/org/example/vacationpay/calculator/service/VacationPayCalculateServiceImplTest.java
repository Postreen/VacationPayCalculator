package org.example.vacationpay.calculator.service;

import org.example.vacationpay.calculator.dto.VacationPayCalculate;
import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.exception.VacationValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VacationPayCalculateServiceImplTest {

    @Autowired
    private VacationPayCalculateService vacationPayCalculateService;

    @DisplayName("✅ Корректный расчет отпускных, без дат")
    @Test
    void calculateVacationPay_ShouldReturnCorrectValue() {
        BigDecimal averageSalary = BigDecimal.valueOf(50000);
        Integer vacationDays = 10;
        Region region = Region.RU;
        BigDecimal expectedPay = BigDecimal.valueOf(14846.80);

        VacationPayCalculate result = vacationPayCalculateService.calculateVacationPay(
                averageSalary, vacationDays, null, null, region);

        assertThat(result.vacationPay()).isEqualByComparingTo(expectedPay);
    }

    @DisplayName("✅ Корректный расчет отпускных, когда указаны только даты")
    @Test
    void calculateVacationPay_WithOnlyDates_ShouldCalculateCorrectly() {
        BigDecimal averageSalary = BigDecimal.valueOf(50000);
        LocalDate startDate = LocalDate.of(2025, 6, 1);
        LocalDate endDate = LocalDate.of(2025, 6, 15);
        Region region = Region.RU;
        BigDecimal expectedPay = BigDecimal.valueOf(13361.32);

        VacationPayCalculate result = vacationPayCalculateService.calculateVacationPay(
                averageSalary, 0, startDate, endDate, region);

        assertThat(result.vacationPay()).isEqualByComparingTo(expectedPay);
    }

    @DisplayName("✅ Корректный расчет отпускных, когда указаны даты и количество дней")
    @Test
    void calculateVacationPay_WithDates_ShouldUseDaysCalculationService() {
        BigDecimal averageSalary = BigDecimal.valueOf(50000);
        LocalDate startDate = LocalDate.of(2025, 6, 1);
        LocalDate endDate = LocalDate.of(2025, 6, 15);
        Region region = Region.RU;

        VacationPayCalculate result = vacationPayCalculateService.calculateVacationPay(
                averageSalary, 0, startDate, endDate, region);

        assertThat(result.vacationPay()).isNotNull();
    }

    @DisplayName("❌ тест: количество дней отпуска отрицательное")
    @Test
    void calculateVacationPay_WithNegativeVacationDays_ShouldThrowException() {
        BigDecimal averageSalary = BigDecimal.valueOf(50000);
        Integer vacationDays = -5;
        Region region = Region.RU;

        assertThrows(VacationValidationException.class, () ->
                vacationPayCalculateService.calculateVacationPay(averageSalary, vacationDays, null, null, region));
    }
}
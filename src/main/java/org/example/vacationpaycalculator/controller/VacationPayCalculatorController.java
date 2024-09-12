package org.example.vacationpaycalculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Positive;
import org.example.vacationpaycalculator.dto.VacationPayCalculate;
import org.example.vacationpaycalculator.service.days.DaysCalculationServiceImpl;
import org.example.vacationpaycalculator.service.vacation.VacationPayCalculateServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@Validated
public class VacationPayCalculatorController {
    private final VacationPayCalculateServiceImpl vacationPayCalculateService;
    private final DaysCalculationServiceImpl daysCalculationService;

    public VacationPayCalculatorController(VacationPayCalculateServiceImpl vacationPayCalculatorService, DaysCalculationServiceImpl daysCalculationServiceImpl) {
        this.vacationPayCalculateService = vacationPayCalculatorService;
        this.daysCalculationService = daysCalculationServiceImpl;
    }

    @GetMapping("/calculacte")
    @Operation(summary = "Калькулятор отпускных",
            description = "Рассчитывает сумму отпускных на основе введенных данных: " +
                    "если не будут введены даты начала и окончания отпуска, " +
                    "калькулятор посчитает отпускные по количеству введенных дней отпуска")
    public VacationPayCalculate getVacationPay(
            @RequestParam("averageSalary") @Parameter(description = "Средняя зарплата за месяц") BigDecimal averageSalaryPerYear,
            @RequestParam(value = "vacationDays", defaultValue = "0") @Parameter(description = "Количество дней отпуска") int vacationDays,

            @RequestParam @Parameter(description = "Дата начала отпуска")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startVacationDate,

            @RequestParam @Parameter(description = "Дата окончания отпуска")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endVacationDate) {
        if (startVacationDate.isPresent() && endVacationDate.isPresent()) {
            vacationDays = daysCalculationService.calculateDays(startVacationDate.get(), endVacationDate.get(), vacationDays);
        } else if (startVacationDate.isPresent()) {
            vacationDays = daysCalculationService.calculateBusinessDays(vacationDays, startVacationDate.get());
        }
        return vacationPayCalculateService.getVacationPayCalculation(averageSalaryPerYear, vacationDays);
    }
}

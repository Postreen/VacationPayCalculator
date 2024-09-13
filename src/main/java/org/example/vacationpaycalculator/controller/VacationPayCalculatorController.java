package org.example.vacationpaycalculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.example.vacationpaycalculator.dto.VacationPayCalculate;
import org.example.vacationpaycalculator.service.vacation.VacationPayCalculateService;
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
    private final VacationPayCalculateService vacationPayCalculateService;

    public VacationPayCalculatorController(VacationPayCalculateService vacationPayCalculatorService) {
        this.vacationPayCalculateService = vacationPayCalculatorService;
    }

    @GetMapping("/calculacte")
    @Operation(summary = "Калькулятор отпускных",
            description = "Рассчитывает сумму отпускных на основе введенных данных: " +
                    "если не будут введены даты начала и окончания отпуска, " +
                    "калькулятор посчитает отпускные по количеству введенных дней отпуска")
    public VacationPayCalculate getVacationPay(
            @RequestParam("averageSalary") @Parameter(description = "Средняя зарплата за месяц") BigDecimal averageSalaryPerYear,
            @RequestParam(value = "vacationDays", defaultValue = "0") @Parameter(description = "Количество дней отпуска") int vacationDays,

            @RequestParam(required = false) @Parameter(description = "Дата начала отпуска")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startVacationDate,

            @RequestParam(required = false) @Parameter(description = "Дата окончания отпуска")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endVacationDate) {

        return vacationPayCalculateService.getVacationPayCalculation(averageSalaryPerYear, vacationDays,
                startVacationDate, endVacationDate);
    }
}

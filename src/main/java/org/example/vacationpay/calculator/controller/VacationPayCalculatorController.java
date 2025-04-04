package org.example.vacationpay.calculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.vacationpay.calculator.dto.ErrorMessageDto;
import org.example.vacationpay.calculator.dto.VacationPayCalculate;
import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.service.VacationPayCalculateService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@Validated
@RestController
@RequiredArgsConstructor
public class VacationPayCalculatorController {

    private final VacationPayCalculateService vacationPayCalculateService;

    @GetMapping("/calculate")
    @Operation(summary = "Калькулятор отпускных",
            description = "Рассчитывает сумму отпускных на основе введенных данных: " +
                    "если не будут введены даты начала и окончания отпуска, " +
                    "калькулятор посчитает отпускные по количеству введенных дней отпуска")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful calculation of vacation pay",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VacationPayCalculate.class))}),
            @ApiResponse(responseCode = "422", description = "Incorrect format of entered data",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))})})
    public VacationPayCalculate getVacationPay(
            @RequestHeader(value = "Region", defaultValue = "RU")
            @Parameter(description = "Регион работника (RU/EU/USA)")
            Region region,

            @RequestParam("averageSalary")
            @Parameter(description = "Средняя зарплата в месяц")
            BigDecimal averageSalaryPerMonth,

            @RequestParam(value = "vacationDays", defaultValue = "0")
            @Parameter(description = "Количество дней отпуска")
            Integer vacationDays,

            @RequestParam(required = false)
            @Parameter(description = "Дата начала отпуска")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startVacationDate,

            @RequestParam(required = false)
            @Parameter(description = "Дата окончания отпуска")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endVacationDate
    ) {
        return vacationPayCalculateService.calculateVacationPay(
                averageSalaryPerMonth, vacationDays, startVacationDate, endVacationDate, region);
    }
}

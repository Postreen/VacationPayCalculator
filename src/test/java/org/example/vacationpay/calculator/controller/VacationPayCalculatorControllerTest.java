package org.example.vacationpay.calculator.controller;

import org.example.vacationpay.calculator.dto.VacationPayCalculate;
import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.service.VacationPayCalculateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VacationPayCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VacationPayCalculateService vacationPayCalculateService;

    @Test
    @DisplayName("✅ Успешный расчет отпускных")
    void getVacationPay_ShouldReturnVacationPay() throws Exception {
        BigDecimal averageSalary = BigDecimal.valueOf(50000);
        int vacationDays = 10;
        LocalDate startDate = LocalDate.of(2025, 6, 1);
        LocalDate endDate = LocalDate.of(2025, 6, 10);
        Region region = Region.RU;

        VacationPayCalculate vacationPayCalculate = new VacationPayCalculate(BigDecimal.valueOf(10000));

        when(vacationPayCalculateService.calculateVacationPay(averageSalary, vacationDays, startDate, endDate, region))
                .thenReturn(vacationPayCalculate);

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("vacationDays", String.valueOf(vacationDays))
                        .param("startVacationDate", startDate.toString())
                        .param("endVacationDate", endDate.toString())
                        .header("Region", region.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vacationPay").value(10000));
    }

    @Test
    @DisplayName("❌ Неверный формат данных")
    void getVacationPay_ShouldReturn400ForInvalidData() throws Exception {
        mockMvc.perform(get("/calculate")
                        .param("averageSalary", "not_a_number")
                        .param("vacationDays", "ten")
                        .header("Region", "RU"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("❌ Внутренняя ошибка сервиса")
    void getVacationPay_ShouldReturn500ForInternalServerError() throws Exception {
        BigDecimal averageSalary = BigDecimal.valueOf(50000);
        int vacationDays = 10;
        Region region = Region.RU;

        when(vacationPayCalculateService.calculateVacationPay(any(), any(), any(), any(), any()))
                .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("vacationDays", String.valueOf(vacationDays))
                        .header("Region", region.name()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }
}

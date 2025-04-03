package org.example.vacationpay.calculator.service.days;

import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.exception.VacationValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DaysCalculationServiceImplTest {

    @Autowired
    private DaysCalculationServiceImpl daysCalculationService;

    @DisplayName("✅ Корректный расчет дней при указанных датах")
    @Test
    void calculateDays_WithDates_ShouldReturnCorrectNumberOfDays() {
        LocalDate startDate = LocalDate.of(2025, 6, 1);
        LocalDate endDate = LocalDate.of(2025, 6, 15);
        Region region = Region.RU;

        int days = daysCalculationService.calculateDays(startDate, endDate, 0, region);

        assertThat(days).isEqualTo(9);
    }

    @DisplayName("✅ Корректный расчет дней, когда указано только количество дней и конечная дата")
    @Test
    void calculateDays_WithEndDateAndVacationDays_ShouldCalculateCorrectWorkingDays() {
        LocalDate endDate = LocalDate.of(2025, 1, 11);
        int vacationDays = 10;
        Region region = Region.RU;

        int days = daysCalculationService.calculateDays(null, endDate, vacationDays, region);

        assertThat(days).isEqualTo(2);
    }

    @DisplayName("✅ Корректный расчет дней, когда указано только количество дней и начальная дата")
    @Test
    void calculateDays_WithStartDateAndVacationDays_ShouldCalculateCorrectWorkingDays() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        int vacationDays = 10;
        Region region = Region.RU;

        int days = daysCalculationService.calculateDays(startDate, null, vacationDays, region);

        assertThat(days).isEqualTo(2);
    }

    @DisplayName("❌ Ошибка, если не указаны ни даты, ни количество дней")
    @Test
    void calculateDays_WithoutDatesOrDays_ShouldThrowException() {
        Region region = Region.RU;

        Exception exception = assertThrows(VacationValidationException.class, () ->
                daysCalculationService.calculateDays(null, null, null, region)
        );

        assertThat(exception.getMessage()).isEqualTo("Необходимо указать либо даты отпуска, либо количество дней.");
    }

    @DisplayName("❌ Ошибка, если дата начала позже даты окончания")
    @Test
    void calculateDays_StartDateAfterEndDate_ShouldThrowException() {
        LocalDate startDate = LocalDate.of(2025, 6, 15);
        LocalDate endDate = LocalDate.of(2025, 6, 1);
        Region region = Region.RU;

        Exception exception = assertThrows(VacationValidationException.class, () ->
                daysCalculationService.calculateDays(startDate, endDate, null, region)
        );

        assertThat(exception.getMessage()).isEqualTo("Дата начала отпуска не может быть позже даты окончания.");
    }

    @DisplayName("❌ Ошибка, если количество дней не совпадает с датами")
    @Test
    void calculateDays_DaysMismatch_ShouldThrowException() {
        LocalDate startDate = LocalDate.of(2025, 6, 1);
        LocalDate endDate = LocalDate.of(2025, 6, 15);
        int incorrectVacationDays = 10;
        Region region = Region.RU;

        Exception exception = assertThrows(VacationValidationException.class, () ->
                daysCalculationService.calculateDays(startDate, endDate, incorrectVacationDays, region)
        );

        assertThat(exception.getMessage()).contains("Указанное количество дней (10) не совпадает с количеством дней по датам (14).");
    }
}

package org.example.vacationpay.calculator.service.days.businessday;

import org.example.vacationpay.calculator.dto.enums.Region;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BusinessDayCalculationServiceImplTest {

    @Autowired
    private BusinessDayCalculationServiceImpl businessDayCalculationService;

    @DisplayName("✅ Корректный расчет рабочих дней без праздников")
    @Test
    void calculate_BusinessDaysWithoutHolidays_ShouldReturnCorrectCount() {
        LocalDate startDate = LocalDate.of(2025, 6, 3);
        LocalDate endDate = LocalDate.of(2025, 6, 10);
        Region region = Region.RU;

        int businessDays = businessDayCalculationService.calculate(startDate, endDate, region);

        assertThat(businessDays).isEqualTo(6);
    }

    @DisplayName("✅ Корректный расчет рабочих дней с учетом праздников, region = RU")
    @Test
    void calculate_BusinessDaysWithHolidays_ShouldExcludeHolidaysRU() {
        LocalDate startDate = LocalDate.of(2025, 5, 1);
        LocalDate endDate = LocalDate.of(2025, 5, 10);
        Region region = Region.RU;

        int businessDays = businessDayCalculationService.calculate(startDate, endDate, region);

        assertThat(businessDays).isEqualTo(5);
    }

    @DisplayName("✅ Корректный расчет рабочих дней с учетом праздников, region = EU")
    @Test
    void calculate_BusinessDaysWithHolidays_ShouldExcludeHolidaysEU() {
        LocalDate startDate = LocalDate.of(2025, 12, 25);
        LocalDate endDate = LocalDate.of(2025, 12, 30);
        Region region = Region.EU;

        int businessDays = businessDayCalculationService.calculate(startDate, endDate, region);

        assertThat(businessDays).isEqualTo(2);
    }

    @DisplayName("✅ Корректный расчет рабочих дней с учетом праздников, region = EU")
    @Test
    void calculate_BusinessDaysWithHolidays_ShouldExcludeHolidaysUSA() {
        LocalDate startDate = LocalDate.of(2025, 12, 25);
        LocalDate endDate = LocalDate.of(2025, 12, 30);
        Region region = Region.USA;

        int businessDays = businessDayCalculationService.calculate(startDate, endDate, region);

        assertThat(businessDays).isEqualTo(3);
    }

    @DisplayName("✅ Корректный расчет рабочих дней, если все дни — выходные и праздники")
    @Test
    void calculate_OnlyHolidaysAndWeekends_ShouldReturnZero() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 5);
        Region region = Region.RU;

        int businessDays = businessDayCalculationService.calculate(startDate, endDate, region);

        assertThat(businessDays).isEqualTo(0);
    }
}

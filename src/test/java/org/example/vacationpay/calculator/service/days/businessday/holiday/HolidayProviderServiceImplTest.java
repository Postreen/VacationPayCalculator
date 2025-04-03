package org.example.vacationpay.calculator.service.days.businessday.holiday;

import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.service.days.businessday.holiday.provider.EuropeHolidayProvider;
import org.example.vacationpay.calculator.service.days.businessday.holiday.provider.UsaHolidayProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class HolidayProviderServiceImplTest {

    @Autowired
    private HolidayProviderService holidayProviderService;

    @Test
    @DisplayName("✅ Тест на получение праздников для США")
    void getHolidays_ForUsa_ShouldReturnCorrectHolidays() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        Region region = Region.USA;

        List<LocalDate> holidays = holidayProviderService.getHolidays(region, startDate);

        assertThat(holidays).isNotEmpty();
        assertThat(holidays).contains(LocalDate.of(2025, 1, 1));
        assertThat(holidays).contains(LocalDate.of(2025, 7, 4));
    }

    @Test
    @DisplayName("✅ Тест на получение праздников для Европы")
    void getHolidays_ForEurope_ShouldReturnCorrectHolidays() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        Region region = Region.EU;

        List<LocalDate> holidays = holidayProviderService.getHolidays(region, startDate);

        assertThat(holidays).isNotEmpty();
        assertThat(holidays).contains(LocalDate.of(2025, 1, 1));
        assertThat(holidays).contains(LocalDate.of(2025, 12, 25));
    }

    @Test
    @DisplayName("✅ Тест на получение праздников для России")
    void getHolidays_ForRussia_ShouldReturnCorrectHolidays() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        Region region = Region.RU;

        List<LocalDate> holidays = holidayProviderService.getHolidays(region, startDate);

        assertThat(holidays).isNotEmpty();
        assertThat(holidays).contains(LocalDate.of(2025, 1, 1));
        assertThat(holidays).contains(LocalDate.of(2025, 6, 12));
    }
}

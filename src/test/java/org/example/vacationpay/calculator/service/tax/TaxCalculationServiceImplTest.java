package org.example.vacationpay.calculator.service.tax;

import org.example.vacationpay.calculator.dto.enums.Region;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TaxCalculationServiceImplTest {

    @Autowired
    private TaxCalculationService taxCalculationService;

    @DisplayName("✅ Корректный расчет налога для России")
    @Test
    void calculateNdfl_ForRussia_ShouldReturnCorrectTax() {
        BigDecimal salary = BigDecimal.valueOf(60000);
        Region region = Region.RU;
        BigDecimal expectedTax = BigDecimal.valueOf(0.13);

        BigDecimal tax = taxCalculationService.calculateNdfl(region, salary);

        assertThat(tax).isEqualByComparingTo(expectedTax);
    }

    @DisplayName("✅ Корректный расчет налога для Европы")
    @Test
    void calculateNdfl_ForEurope_ShouldReturnCorrectTax() {
        BigDecimal salary = BigDecimal.valueOf(7000);
        Region region = Region.EU;
        BigDecimal expectedTax = BigDecimal.valueOf(0.25);

        BigDecimal tax = taxCalculationService.calculateNdfl(region, salary);

        assertThat(tax).isEqualByComparingTo(expectedTax);
    }

    @DisplayName("✅ Корректный расчет налога для США")
    @Test
    void calculateNdfl_ForUSA_ShouldReturnCorrectTax() {
        BigDecimal salary = BigDecimal.valueOf(12000);
        Region region = Region.USA;
        BigDecimal expectedTax = BigDecimal.valueOf(0.30);

        BigDecimal tax = taxCalculationService.calculateNdfl(region, salary);

        assertThat(tax).isEqualByComparingTo(expectedTax);
    }

    @DisplayName("❌ тест: регион не найден")
    @Test
    void calculateNdfl_ForUnknownRegion_ShouldThrowException() {
        BigDecimal salary = BigDecimal.valueOf(50000);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                taxCalculationService.calculateNdfl(null, salary)
        );

        assertThat(exception.getMessage()).contains("Не найден налоговый провайдер для региона");
    }
}

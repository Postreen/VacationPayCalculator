package org.example.vacationpay.calculator.service.tax.provider;

import org.example.vacationpay.calculator.dto.enums.Region;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EuropeTaxProvider implements RegionTaxProvider{

    @Override
    public boolean matchesRegion(Region region) {
        return region == Region.EU;
    }

    @Override
    public BigDecimal getTaxRate(BigDecimal monthlySalary) {
        if (monthlySalary.compareTo(BigDecimal.valueOf(5000)) > 0) {
            return BigDecimal.valueOf(0.25); // 25% для высоких доходов
        }
        return BigDecimal.valueOf(0.20); // 20% стандартный налог
    }
}

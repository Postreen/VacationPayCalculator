package org.example.vacationpay.calculator.service.tax.provider;

import org.example.vacationpay.calculator.config.TaxRateConfig;
import org.example.vacationpay.calculator.dto.enums.Region;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RussiaTaxProvider implements  RegionTaxProvider{

    @Override
    public boolean matchesRegion(Region region) {
        return region == Region.RU;
    }

    @Override
    public BigDecimal getTaxRate(BigDecimal monthlySalary) {
        return TaxRateConfig.getTaxRate(Region.RU, monthlySalary);
    }
}

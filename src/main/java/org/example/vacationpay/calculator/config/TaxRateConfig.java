package org.example.vacationpay.calculator.config;

import org.example.vacationpay.calculator.dto.enums.Region;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.*;

@Configuration
public class TaxRateConfig {

    public static final Map<Region, NavigableMap<BigDecimal, BigDecimal>> TAX_RATES = new HashMap<>();

    static {
        TAX_RATES.put(Region.RU, new TreeMap<>());
        TAX_RATES.put(Region.USA, new TreeMap<>());
        TAX_RATES.put(Region.EU, new TreeMap<>());

        // Россия
        TAX_RATES.get(Region.RU).put(BigDecimal.ZERO, BigDecimal.valueOf(0.13));
        TAX_RATES.get(Region.RU).put(BigDecimal.valueOf(200_000), BigDecimal.valueOf(0.15));
        TAX_RATES.get(Region.RU).put(BigDecimal.valueOf(416_667), BigDecimal.valueOf(0.18));
        TAX_RATES.get(Region.RU).put(BigDecimal.valueOf(1_670_000), BigDecimal.valueOf(0.20));
        TAX_RATES.get(Region.RU).put(BigDecimal.valueOf(4_170_000), BigDecimal.valueOf(0.22));

        // США
        TAX_RATES.get(Region.USA).put(BigDecimal.ZERO, BigDecimal.valueOf(0.22));
        TAX_RATES.get(Region.USA).put(BigDecimal.valueOf(10_000), BigDecimal.valueOf(0.30));

        // Европа
        TAX_RATES.get(Region.EU).put(BigDecimal.ZERO, BigDecimal.valueOf(0.20));
        TAX_RATES.get(Region.EU).put(BigDecimal.valueOf(5000), BigDecimal.valueOf(0.25));
    }

    public static BigDecimal getTaxRate(Region region, BigDecimal monthlySalary) {
        return Optional.ofNullable(TAX_RATES.get(region))
                .map(rates -> rates.floorEntry(monthlySalary).getValue())
                .orElseThrow(() -> new IllegalArgumentException("Не найдены налоговые ставки для региона: " + region));
    }
}

package org.example.vacationpay.calculator.config;

import java.math.BigDecimal;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class RussiaTaxRateConfig {

    public static final NavigableMap<BigDecimal, BigDecimal> TAX_RATES = new TreeMap<>();

    static {
        TAX_RATES.put(BigDecimal.ZERO, BigDecimal.valueOf(0.13));       // До 200 тыс. руб. — 13%
        TAX_RATES.put(BigDecimal.valueOf(200_000), BigDecimal.valueOf(0.15)); // 200 тыс. - 416,7 тыс. руб. — 15%
        TAX_RATES.put(BigDecimal.valueOf(416_667), BigDecimal.valueOf(0.18)); // 416,7 тыс. - 1,67 млн руб. — 18%
        TAX_RATES.put(BigDecimal.valueOf(1_670_000), BigDecimal.valueOf(0.20)); // 1,67 млн - 4,17 млн руб. — 20%
        TAX_RATES.put(BigDecimal.valueOf(4_170_000), BigDecimal.valueOf(0.22)); // Свыше 4,17 млн руб. — 22%
    }
}

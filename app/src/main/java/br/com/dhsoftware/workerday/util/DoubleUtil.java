package br.com.dhsoftware.workerday.util;

import java.math.BigDecimal;

public class DoubleUtil {

    public double doubleWithTwoDecimal(Double number) {
        return truncateDecimal(number).doubleValue();
    }

    private BigDecimal truncateDecimal(double x) {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(2, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(2, BigDecimal.ROUND_CEILING);
        }
    }
}

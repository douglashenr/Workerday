package br.com.dhsoftware.workerday.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Money {

    public BigDecimal moneyToDouble(String amount, Locale locale) throws ParseException {
        NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]", ""));
    }

    public String doubleToStringMoney(String money) {

        if (money.charAt(money.length() - 2) == '.')
            money = money + "0";

        if (money == null) {
            return "";
        } else {
            money = money.replaceAll("[(a-z)|(A-Z)|($,. )]", "");
        }

        double parsed = Double.parseDouble(money);
        //System.out.println(parsed + " Esse é o parsed");
        String formatted = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format((parsed / 100));
        formatted = formatted.replaceAll("[^(0-9)(.,)]", "");

        return formatted;
    }


    public double doubleWithTwiDecimal(Double number) {
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

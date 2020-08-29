package br.com.dhsoftware.workerday;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public abstract class Money {

    public double moneyToDouble(String amount) throws Exception {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.FRANCE);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return format.parse(amount.replaceAll("[^\\d.,]", "")).doubleValue();
    }

    public String doubleToStringMoney(double doubleMoney) {
        String money = String.valueOf(doubleMoney);
        if (money.charAt(money.length() - 2) == '.')
            money = money + "0";

        if (money == null) {
            return "";
        } else {
            money = money.replaceAll("[(a-z)|(A-Z)|($,. )]", "");
        }

        double parsed = Double.parseDouble(money);
        String formatted = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format((parsed / 100));
        formatted = formatted.replaceAll("[^(0-9)(.,)]", "");

        return formatted;
    }


}

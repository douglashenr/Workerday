package br.com.dhsoftware.workerday.util;

import java.text.ParseException;
import java.util.Locale;

public class SalaryUtil {

    private Money money;

    public SalaryUtil() {
        money = new Money();
    }

    public Double calculateSalaryPerHour(String salary, int hourForWeek){
        double moneyPerHour = 0.0;
        try {
            moneyPerHour = money.dinheiroParaDouble(salary, Locale.FRANCE).doubleValue() / (hourForWeek*5);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return money.doubleComDoisDecimais(moneyPerHour);
    }

}

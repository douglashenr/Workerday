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

    public void calculateINSS(String value){
        try {
            double salary = money.dinheiroParaDouble(value, Locale.FRANCE).doubleValue();
            if(salary <= 1045){

            }else if(salary > 1045 && salary <= 2080.60){

            }else if(salary > 2080.60 && salary <= 3134.40){

            }else if(salary > 3134.40 && salary <=6101.06){

            }else{

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}

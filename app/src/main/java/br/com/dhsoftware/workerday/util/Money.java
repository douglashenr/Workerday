package br.com.dhsoftware.workerday.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Money {

    public BigDecimal dinheiroParaDouble(String amount, Locale locale) throws ParseException {
        NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]",""));
    }


    public double doubleComDoisDecimais(Double numero){
        System.out.println("NumeroRecebido: " + numero);
        /*numero+= 0.0000000002;
        DecimalFormat formato = new DecimalFormat();

        if(numero < 10){
            formato = new DecimalFormat("#,##");
        }

        if(numero >=10.0 && numero <100.0){
            formato = new DecimalFormat("##,##");
        }
        if(numero >= 100 && numero < 1000) {
            formato = new DecimalFormat("###,##");
            System.out.println("Passou no formado correto!");
        }
        if(numero >= 1000)
            formato = new DecimalFormat("###,##");



        return Double.valueOf(formato.format(numero));*/

        //return Math.floor(numero * 100) / 100;
        return truncateDecimal(numero, 2).doubleValue();
    }

    private BigDecimal truncateDecimal(double x,int numberofDecimals)
    {
        if ( x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }
}

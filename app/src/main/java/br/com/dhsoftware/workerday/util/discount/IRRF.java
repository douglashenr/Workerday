package br.com.dhsoftware.workerday.util.discount;

import br.com.dhsoftware.workerday.Discount;
import br.com.dhsoftware.workerday.util.DoubleUtil;

public class IRRF extends Discount {

    public IRRF(Discount otherDiscount){
        super(otherDiscount);
    }

    @Override
    public double calculate(double value) {
        DoubleUtil doubleUtil = new DoubleUtil();
        System.out.println("Valor Antes: " + value);
        value = value - calculateOtherDiscount(value);
        System.out.println("Valor Depois: " + value);
        if (value <= 1903.98) {
            return 0.00;
        } else if (value > 1903.98 && value <= 2826.65) {
            return doubleUtil.doubleWithTwoDecimal((value * 0.075) - 142.80);
        } else if (value > 2826.65 && value <= 3751.05) {
            return doubleUtil.doubleWithTwoDecimal((value * 0.15) - 354.80);
        } else if (value > 3751.05 && value <= 4664.68) {
            return doubleUtil.doubleWithTwoDecimal((value * 0.225) - 636.13);
        } else {
            return doubleUtil.doubleWithTwoDecimal((value * 0.275) - 869.36);
        }
    }
}

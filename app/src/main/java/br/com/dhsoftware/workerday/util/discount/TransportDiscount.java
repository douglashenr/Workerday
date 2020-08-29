package br.com.dhsoftware.workerday.util.discount;

import br.com.dhsoftware.workerday.Discount;
import br.com.dhsoftware.workerday.util.DoubleUtil;

public class TransportDiscount extends Discount {

    @Override
    public double calculate(double value) {
        return new DoubleUtil().doubleWithTwoDecimal(value * 0.06);
    }
}

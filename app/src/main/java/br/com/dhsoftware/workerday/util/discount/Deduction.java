package br.com.dhsoftware.workerday.util.discount;

import br.com.dhsoftware.workerday.Discount;
import br.com.dhsoftware.workerday.util.DoubleUtil;

public class Deduction extends Discount {

    public Deduction(Discount otherDiscount) {
        super(otherDiscount);
    }

    public Deduction() {
    }

    @Override
    public double calculate(double valor) {
        return new DoubleUtil().doubleWithTwoDecimal(valor);
    }
}

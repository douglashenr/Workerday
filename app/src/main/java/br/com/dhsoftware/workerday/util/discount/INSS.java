package br.com.dhsoftware.workerday.util.discount;

import br.com.dhsoftware.workerday.Discount;
import br.com.dhsoftware.workerday.util.DoubleUtil;

public class INSS extends Discount {


    public INSS(Discount otherDiscount) {
        super(otherDiscount);
    }

    public INSS() {
    }

    @Override
    public double calculate(double value) {
        DoubleUtil doubleUtil = new DoubleUtil();
        double salary = value;
        if (salary <= 1045) {
            return doubleUtil.doubleWithTwoDecimal(salary * 0.075);
        } else if (salary > 1045 && salary <= 2080.60) {
            return doubleUtil.doubleWithTwoDecimal(((salary - 1045) * 0.09) + 78.37);
        } else if (salary > 2080.60 && salary <= 3134.40) {
            return doubleUtil.doubleWithTwoDecimal(((salary - 2080.60) * 0.12) + 78.37 + 94.01);
        } else if (salary > 3134.40 && salary <= 6101.06) {
            return doubleUtil.doubleWithTwoDecimal(((salary - 3134.40) * 0.14) + 78.37 + 94.01 + 125.37);
        } else {
            return 713.09;
        }
    }
}

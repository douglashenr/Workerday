package br.com.dhsoftware.workerday.util;

import br.com.dhsoftware.workerday.Discount;
import br.com.dhsoftware.workerday.Money;
import br.com.dhsoftware.workerday.model.User;
import br.com.dhsoftware.workerday.util.discount.INSS;
import br.com.dhsoftware.workerday.util.discount.IRRF;

public class SalaryUtil extends Money {

    private User user;
    private DoubleUtil doubleUtil;

    public SalaryUtil(final User user) {
        this.user = user;
        setNewDoubleUtil();
    }

    public SalaryUtil() {
    setNewDoubleUtil();
    }

    public void setNewDoubleUtil() {
        this.doubleUtil = new DoubleUtil();
    }


    public double calculateDiscount(double value, Discount discount){
        return discount.calculate(value);
    }

    public String calculateDiscountToString(double value, Discount discount){
        return discount.doubleToStringMoney(calculateDiscount(value, discount));
    }

//    public double calculateTransportation(){
//        if(user == null || user.getGrossSalary().getGrossSalary() == 0.0)
//            return 0.0;
//
//        return money.doubleWithTwiDecimal(user.getGrossSalary() * 0.06);
//    }


    public Double calculateNetSalary() {
        return doubleUtil.doubleWithTwoDecimal(user.getGrossSalary().getGrossSalary() - (calculateDiscount(user.getGrossSalary().getGrossSalary(), new IRRF(new INSS()))+ user.getDeduction() + calculateDiscount(user.getGrossSalary().getGrossSalary(), new INSS())));
    }



}

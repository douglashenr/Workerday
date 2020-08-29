package br.com.dhsoftware.workerday.model;


import br.com.dhsoftware.workerday.Money;
import br.com.dhsoftware.workerday.util.DateUtil;
import br.com.dhsoftware.workerday.util.DoubleUtil;

public class GrossSalary extends Money {
    private double grossSalary;
    private double salaryPerHour;
    private int timeForWeek;
    private DoubleUtil doubleUtil = new DoubleUtil();

    public GrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
        this.salaryPerHour = 0.0;
        this.timeForWeek = 0;
    }

    GrossSalary(String grossSalary) {
        try {
            this.grossSalary = new DoubleUtil().doubleWithTwoDecimal(moneyToDouble(grossSalary));
        } catch (Exception e) {
            e.printStackTrace();
            this.grossSalary = 0;
        }
        this.salaryPerHour = 0.0;
        this.timeForWeek = 0;
    }

    public GrossSalary(double grossSalary, int timeForWeek){
        this.grossSalary = grossSalary;
        this.salaryPerHour = 0.0;
        setTimeForWeek(timeForWeek);

    }



    private void calculateSalaryPerHour() throws Exception{
        if(timeForWeek <= 0)
            throw new IllegalArgumentException("Erro para calcular salario por hora: timeForWeek <=0");

        this.salaryPerHour = doubleUtil.doubleWithTwoDecimal(this.grossSalary / (this.timeForWeek * 5));
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setTimeForWeek(int timeForWeek) {
        this.timeForWeek = timeForWeek;
        try {
            calculateSalaryPerHour();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public double getSalaryPerDay(Registry registry, String extraTime) {
        if (registry.getRequiredTimeToWorkString() == null || registry.getRequiredTimeToWorkString().equals(""))
            return 0.00;


        double salaryDay = getSalaryPerHour() * DateUtil.getInstanceDateUtil().getHourInt(registry.getRequiredTimeToWorkString());
        salaryDay += (getSalaryPerHour() / 60) * DateUtil.getInstanceDateUtil().getMinuteInt(registry.getRequiredTimeToWorkString());
        if (extraTime.equals("00:00"))
            return salaryDay;

        double extraSalaryDouble;


        if (extraTime.charAt(0) == '-') {
            extraTime = extraTime.substring(1);
            extraSalaryDouble = getSalaryPerHour() * DateUtil.getInstanceDateUtil().getHourInt(extraTime);
            extraSalaryDouble += (getSalaryPerHour() / 60) * DateUtil.getInstanceDateUtil().getMinuteInt(extraTime);
            return doubleUtil.doubleWithTwoDecimal(salaryDay - extraSalaryDouble);
        } else {
            extraSalaryDouble = (getSalaryPerHour() * ((registry.getPercent() / 100f) + 1.0)) * DateUtil.getInstanceDateUtil().getHourInt(extraTime);
            extraSalaryDouble += ((getSalaryPerHour() * ((registry.getPercent() / 100f) + 1)) / 60) * DateUtil.getInstanceDateUtil().getMinuteInt(extraTime);
                        return doubleUtil.doubleWithTwoDecimal(salaryDay + extraSalaryDouble);
        }
    }

    @Override
    public String toString() {
        return doubleToStringMoney(grossSalary);
    }
}

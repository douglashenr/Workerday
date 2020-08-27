package br.com.dhsoftware.workerday;

public abstract class Discount extends Money{

    private final Discount otherDiscount;

    public Discount(Discount otherDiscount) {
        this.otherDiscount = otherDiscount;
    }

    public Discount() {
        this.otherDiscount = null;
    }

    public abstract double calculate(double value);

    protected double calculateOtherDiscount(double discount){
        if(this.otherDiscount != null) return otherDiscount.calculate(discount);

        return 0;
    }

}

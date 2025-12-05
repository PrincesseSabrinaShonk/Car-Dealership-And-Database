package com.pluralsight.models;

public class LeaseContract {
    private String vin;
    private double endingValue;
    private double leaseFee;


    public LeaseContract(String vin, double endingValue, double leaseFee) {
        this.vin = vin;
        this.endingValue = endingValue;
        this.leaseFee = leaseFee;
    }

    public String getVin() {
        return vin;
    }

    public double getEndingValue() {
        return endingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }
}
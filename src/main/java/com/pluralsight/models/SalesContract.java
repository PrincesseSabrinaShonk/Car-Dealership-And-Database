package com.pluralsight.models;

public class SalesContract {
    private String vin;
    private double salesPrice;
    private double recordingFee;
    private double processingFee;

    public SalesContract(String vin, double salesPrice, double recordingFee, double processingFee) {
        this.vin = vin;
        this.salesPrice = salesPrice;
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
    }

    public String getVin() {
        return vin;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public double getRecordingFee() {
        return recordingFee;
    }
    public double getProcessingFee() {
        return processingFee;
    }
    public double getTotalPrice() {
        return salesPrice + recordingFee + processingFee;
    }
}
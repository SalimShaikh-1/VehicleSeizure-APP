package org.sk.jih.modal;

public class Vehicle {

//    String id;
    String vehicleNumber;
//    String state;



    public Vehicle(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Vehicle(){}

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}

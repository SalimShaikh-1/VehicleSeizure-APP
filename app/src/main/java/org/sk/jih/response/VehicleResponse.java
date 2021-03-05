package org.sk.jih.response;

import org.sk.jih.modal.Vehicle;

import java.util.List;

public class VehicleResponse {
    private boolean status;
    private String message;
    private List<Vehicle> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Vehicle> getData() {
        return data;
    }

    public void setData(List<Vehicle> data) {
        this.data = data;
    }
}

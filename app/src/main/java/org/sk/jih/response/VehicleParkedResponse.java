package org.sk.jih.response;

import org.sk.jih.modal.VehicleParked;

public class VehicleParkedResponse {

    private boolean status;
    private String message;
    private VehicleParked data;

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

    public VehicleParked getData() {
        return data;
    }

    public void setData(VehicleParked data) {
        this.data = data;
    }
}

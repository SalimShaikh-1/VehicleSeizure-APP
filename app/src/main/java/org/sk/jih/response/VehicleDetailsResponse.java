package org.sk.jih.response;

import org.sk.jih.modal.Vehicle;
import org.sk.jih.modal.VehicleDetails;

import java.util.List;

public class VehicleDetailsResponse {

    private boolean status;
    private String message;
    private VehicleDetails data;

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

    public VehicleDetails getData() {
        return data;
    }

    public void setData(VehicleDetails data) {
        this.data = data;
    }
}

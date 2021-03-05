package org.sk.jih.response;

import org.sk.jih.modal.Location;
import org.sk.jih.modal.User;

public class LocationResponse {
    private boolean status;
    private String message;
    public Location location;

    public LocationResponse(boolean status, String message, Location location) {
        this.status = status;
        this.message = message;
        this.location = location;
    }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

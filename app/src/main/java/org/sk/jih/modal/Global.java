package org.sk.jih.modal;

import java.util.List;

public class Global {

    public static boolean status = false;
    public static List<Vehicle> vehicles;
    public static User user;

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        Global.status = status;
    }

    public static List<Vehicle> getVehicles() {
        return vehicles;
    }

    public static void setVehicles(List<Vehicle> vehicles) {
        Global.vehicles = vehicles;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Global.user = user;
    }
}

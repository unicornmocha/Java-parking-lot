package org.parking_lot.core;
import java.time.LocalTime;
import java.util.*;

public class Vehicle {
    private final String vehicleNumber;
    private final VehicleClass vehicleClass;

    Vehicle(String vehicleNumber, VehicleClass vehicleClass) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleClass = vehicleClass;
    }

    public String readVehicleNumber() { return this.vehicleNumber; }
    public VehicleClass readVehicleClass() { return this.vehicleClass; }
}


package org.parking_lot.core;

import java.util.*;

public enum SpotCategory {
    RESERVED(new ArrayList<VehicleClass>(List.of(VehicleClass.MEDICAL)), 0),
    REGULAR(new ArrayList<VehicleClass>(List.of(VehicleClass.MEDICAL, VehicleClass.CAR, VehicleClass.MOTORBIKE)), 10),
    COMPACT(new ArrayList<VehicleClass>(List.of(VehicleClass.MOTORBIKE)), 5),
    LARGE(new ArrayList<VehicleClass>(List.of(VehicleClass.MEDICAL, VehicleClass.CAR, VehicleClass.MOTORBIKE, VehicleClass.TRUCK)), 20);

    private EnumSet<VehicleClass> allowedVehicles = EnumSet.noneOf(VehicleClass.class);
    private double rate;

    SpotCategory(List<VehicleClass> allowedVehicles, double rate) {
        if (allowedVehicles == null) {
            throw new IllegalArgumentException("Null values not allowed");
        }
        this.rate = rate;
        this.allowedVehicles.addAll(allowedVehicles);
    }

    public EnumSet<VehicleClass> getAllowedVehicles() {
        return this.allowedVehicles;
    }

    public double getRate() {
        return this.rate;
    }
}

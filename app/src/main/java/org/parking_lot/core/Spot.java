package org.parking_lot.core;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;


public class Spot {
    private boolean isOccupied = false;
//    final int distanceFromEntrance;
    private final SpotCategory spotCategory;
    private final String label;
    private LocalTime checkinTime;
    private String vehicleNumber = "";

    Spot(int distanceFromEntrance, SpotCategory spotCategory, String label) {
//        this.distanceFromEntrance = distanceFromEntrance;
        this.spotCategory = spotCategory;
        this.label = label;
    }

    public void checkIn(Vehicle vehicle) {
//        check is VehicleClass matches spot category
        if (vehicle == null || vehicleNumber == null)  {
            throw new IllegalArgumentException("Null values not allowed");
        }
        if (this.isOccupied) {
            throw new IllegalStateException("Spot already occupied");
        }
        if (!this.spotCategory.getAllowedVehicles().contains(vehicle.readVehicleClass())) {
            throw new IllegalStateException("This spot cannot be occupied by this vehicle class: " +vehicle.readVehicleClass());
        }
        this.isOccupied = true;
        this.vehicleNumber = vehicle.readVehicleNumber();
        this.checkinTime =  LocalTime.now();
    }

    public boolean readVacancy() { return this.isOccupied; }

    public String readVehicleNum() { return this.vehicleNumber; }

    public String readLabel() { return this.label; }

    public SpotCategory readCategory() { return this.spotCategory; }

    public LocalTime readCheckInTime() {
        if (!this.isOccupied) {
            throw new IllegalStateException("Spot not occupied");
        }
        return this.checkinTime;
    }

    public double getBill() {
        if (!this.isOccupied) {
            throw new IllegalStateException("Spot not occupied");
        }
        return this.spotCategory.getRate() * Duration.between(this.checkinTime, LocalTime.now()).toHours();
    }

    public void checkOut() {
//        include check to insure its occupied
        this.isOccupied = false;
        this.vehicleNumber = "";
        this.checkinTime = null;
    }
}

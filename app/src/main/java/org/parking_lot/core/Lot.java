package org.parking_lot.core;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Lot {
    private final List<Floor> floorList = new ArrayList<>();
    private Map<String, AbstractMap.SimpleEntry<Floor, Spot>> spotMap = new ConcurrentHashMap<>();
    private final Set<VehicleClass> allowedVehicleTypes = EnumSet.noneOf(VehicleClass.class);

    Lot (List<Floor> floorList, Set<VehicleClass> allowedVehicleTypes) {
        this.floorList.addAll(floorList);
        this.allowedVehicleTypes.addAll(allowedVehicleTypes);
    }

    public String attemptVehicleCheckIn(Vehicle vehicle) {
        if(!allowedVehicleTypes.contains(vehicle.readVehicleClass())) { return "This vehicle type is not allowed"; }
        Spot spot;
        for(Floor floor : this.floorList) {
            try {
                spot = floor.searchVacancy(vehicle);
            } catch(Exception e) {
                continue;
            }
            if(spot != null) {
                AbstractMap.SimpleEntry<Floor, Spot> floorSpot = new AbstractMap.SimpleEntry<>(floor, spot);
                spotMap.put(vehicle.readVehicleNumber(), floorSpot);
                return "Floor: " + floor.getFloorNum() + "; Spot: " + spot.readLabel();
            }
        }
        return "No spots available for this vehicle";
    }

    public String attemptVehicleCheckOut(Vehicle vehicle) {

    }

    public String getBill(String vehicleNum) {
        AbstractMap.SimpleEntry<Floor, Spot> floorSpot = spotMap.get(vehicleNum);
        double bill = 0.0;
        if(floorSpot != null) { bill = floorSpot.getKey().getBill(floorSpot.getValue()); }
        if(bill == 0.0) {
            return "Vehicle has not been checked-in yet";
        }
        return (String.valueOf(bill));
    }
}
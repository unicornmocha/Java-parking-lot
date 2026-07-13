package org.parking_lot.core;
import java.util.HashSet;
import java.util.List;

public class Floor {
    private final HashSet<Spot> reservedSpots = new HashSet<>();
    private final HashSet<Spot> regularSpots = new HashSet<>();
    private final HashSet<Spot> compactSpots = new HashSet<>();
    private final HashSet<Spot> largeSpots = new HashSet<>();
    private final int floorNum;
    private final HashSet<Spot> spotSet = new HashSet<>();

    Floor(int floorNum, HashSet<Spot> reservedSpots, HashSet<Spot> regularSpots, HashSet<Spot> compactSpots, HashSet<Spot> largeSpots) {
        this.floorNum = floorNum;
        this.reservedSpots.addAll(reservedSpots);
        this.spotSet.addAll(reservedSpots);
        this.regularSpots.addAll(regularSpots);
        this.spotSet.addAll(regularSpots);
        this.compactSpots.addAll(compactSpots);
        this.spotSet.addAll(compactSpots);
        this.largeSpots.addAll(largeSpots);
        this.spotSet.addAll(largeSpots);

    }

//    The Floor adds/creates the spots? The ParkingLot creates floors
//    public void addSpot(Spot spot) {
//        switch (spot.readCategory()) {
//            case RESERVED -> reservedSpots.add(spot);
//            case REGULAR -> regularSpots.add(spot);
//            case COMPACT -> compactSpots.add(spot);
//            case LARGE -> largeSpots.add(spot);
//        }
//    }
//    searchVacancy (if vacancy found, begin to check in vehicle)

    public Spot checkIn(Vehicle vehicle, HashSet<Spot> spotHashSet) {
//        check for empty-return null, remove the spot from set, checkin vehicle in that spot, return the spot
        if (spotHashSet.isEmpty()) {
           return null;
        }
        Spot spot = spotHashSet.iterator().next();
        try {
            spot.checkIn(vehicle);
            spotHashSet.remove(spot);
        } catch(Exception e) {
            return null;
        }
        return spot;
    }

    public Spot searchVacancy(Vehicle vehicle) {
//        if vehicle is car, how do we search through regular spots and then search through large spots?
        List<SpotCategory> spotCategories = List.of(SpotCategory.values());
        Spot vacantSpot = null;
        for (SpotCategory spotCategory : spotCategories) {
           if (spotCategory.getAllowedVehicles().contains(vehicle.readVehicleClass())) {

               vacantSpot = switch (spotCategory) {
                   case SpotCategory.RESERVED -> checkIn(vehicle, reservedSpots);
                   case SpotCategory.REGULAR -> checkIn(vehicle, regularSpots);
                   case SpotCategory.COMPACT -> checkIn(vehicle, compactSpots);
                   case SpotCategory.LARGE -> checkIn(vehicle, largeSpots);
               };
               if (vacantSpot == null) {
                   throw new IllegalStateException("No vacant spots available");
               };
               break;
            }
        }
        return vacantSpot;
    }
    public void checkOut(Vehicle vehicle, Spot spot) {
        try {
            spot.checkOut(vehicle.readVehicleNumber());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

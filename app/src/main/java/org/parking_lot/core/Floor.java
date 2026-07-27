package org.parking_lot.core;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class Floor {
    private final PriorityBlockingQueue<Spot> reservedSpots = new PriorityBlockingQueue<>();
    private final PriorityBlockingQueue<Spot> regularSpots = new PriorityBlockingQueue<>();
    private final PriorityBlockingQueue<Spot> compactSpots = new PriorityBlockingQueue<>();
    private final PriorityBlockingQueue<Spot> largeSpots = new PriorityBlockingQueue<>();
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

    public Spot checkIn(Vehicle vehicle, PriorityBlockingQueue<Spot> spotPriorityBlockingQueue) {
//        check for empty-return null, remove the spot from set, checkin vehicle in that spot, return the spot
        if (spotPriorityBlockingQueue.isEmpty()) {
           return null;
        }
        Spot spot = spotPriorityBlockingQueue.poll();
        try {
            spot.checkIn(vehicle);
        } catch(Exception e) {
            return null;
        }
        return spot;
    }

    public Spot searchVacancy(Vehicle vehicle) {
        List<SpotCategory> spotCategories = List.of(SpotCategory.values());
        Spot spot = null;
        for (SpotCategory spotCategory : spotCategories) {
           if (spotCategory.getAllowedVehicles().contains(vehicle.readVehicleClass())) {

               spot = switch (spotCategory) {
                   case SpotCategory.RESERVED -> checkIn(vehicle, reservedSpots);
                   case SpotCategory.REGULAR -> checkIn(vehicle, regularSpots);
                   case SpotCategory.COMPACT -> checkIn(vehicle, compactSpots);
                   case SpotCategory.LARGE -> checkIn(vehicle, largeSpots);
               };
               if (spot == null) {
                   throw new IllegalStateException("No vacant spots available");
               };
               break;
            }
        }
        return spot;
    }
    public void checkOut(Vehicle vehicle, Spot spot) {
        try {
            spot.checkOut(vehicle.readVehicleNumber());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public double getBill(Spot spot) {
        return spot.getBill();
    }
    public int getFloorNum() { return this.floorNum; }
}

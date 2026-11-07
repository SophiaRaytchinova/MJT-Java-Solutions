package bg.sofia.uni.fmi.mjt.parking.zone;

import bg.sofia.uni.fmi.mjt.parking.location.Location;

public interface ParkingZone {
    String getName();
    String getSmsNumber();
    boolean contains(Location location);
}
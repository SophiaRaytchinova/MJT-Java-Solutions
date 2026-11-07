package bg.sofia.uni.fmi.mjt.parking.sanction;

import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

public interface Sanction {
    Vehicle getVehicle();
    ParkingZone getZone();
    int getAmount();
}
package bg.sofia.uni.fmi.mjt.parking.sanction;

import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

public class TowAway extends AbstractSanction {
    public TowAway(Vehicle vehicle, ParkingZone zone) {
        super(vehicle, zone, 30);
    }
}
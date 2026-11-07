package bg.sofia.uni.fmi.mjt.parking.sanction;

import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

public class WheelClamp extends AbstractSanction {
    public WheelClamp(Vehicle vehicle, ParkingZone zone) {
        super(vehicle, zone, 15);
    }
}
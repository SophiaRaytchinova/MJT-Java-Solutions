package bg.sofia.uni.fmi.mjt.parking;

import bg.sofia.uni.fmi.mjt.parking.location.Location;
import bg.sofia.uni.fmi.mjt.parking.sanction.Sanction;
import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;

import java.time.LocalDateTime;

public interface ParkingInspectorAPI {
    Sanction inspect(Vehicle vehicle, Location location, LocalDateTime moment);
}
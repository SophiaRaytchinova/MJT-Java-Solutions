package bg.sofia.uni.fmi.mjt.parking.sanction;

import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

import java.util.Objects;

public abstract class AbstractSanction implements Sanction {
    private final Vehicle vehicle;
    private final ParkingZone zone;
    private final int baseAmount;

    protected AbstractSanction(Vehicle vehicle, ParkingZone zone, int baseAmount) {
        if (vehicle == null || zone == null) {
            throw new IllegalArgumentException("Vehicle and zone cannot be null");
        }
        this.vehicle = vehicle;
        this.zone = zone;
        this.baseAmount = baseAmount;
    }

    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public ParkingZone getZone() {
        return zone;
    }

    @Override
    public int getAmount() {
        return baseAmount + vehicle.getSurcharge();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractSanction that = (AbstractSanction) o;
        return baseAmount == that.baseAmount &&
                Objects.equals(vehicle, that.vehicle) &&
                Objects.equals(zone, that.zone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, zone, baseAmount);
    }
}
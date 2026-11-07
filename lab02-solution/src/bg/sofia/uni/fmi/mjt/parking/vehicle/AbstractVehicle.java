package bg.sofia.uni.fmi.mjt.parking.vehicle;

import java.util.Objects;

public abstract class AbstractVehicle implements Vehicle {
    private final LicensePlate licensePlate;
    private final int surcharge;

    protected AbstractVehicle(LicensePlate licensePlate, int surcharge) {
        if (licensePlate == null) {
            throw new IllegalArgumentException("License plate cannot be null");
        }
        this.licensePlate = licensePlate;
        this.surcharge = surcharge;
    }

    @Override
    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    @Override
    public int getSurcharge() {
        return surcharge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractVehicle that = (AbstractVehicle) o;
        return surcharge == that.surcharge && Objects.equals(licensePlate, that.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate, surcharge);
    }

    @Override
    public String toString() {
        return "Vehicle{licensePlate=" + licensePlate + ", surcharge=" + surcharge + "}";
    }
}
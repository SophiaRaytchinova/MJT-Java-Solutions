package bg.sofia.uni.fmi.mjt.parking.zone;

import bg.sofia.uni.fmi.mjt.parking.location.Location;
import java.util.Objects;

public abstract class AbstractZone implements ParkingZone {
    private final String name;
    private final String smsNumber;
    protected final Location center;

    protected AbstractZone(String name, String smsNumber, Location center) {
        if (name == null || smsNumber == null || center == null) {
            throw new IllegalArgumentException("Zone parameters cannot be null");
        }
        this.name = name;
        this.smsNumber = smsNumber;
        this.center = center;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSmsNumber() {
        return smsNumber;
    }

    protected double calculateDistance(Location location) {
        if (location == null) {
            return Double.MAX_VALUE;
        }
        double dx = location.getX() - center.getX();
        double dy = location.getY() - center.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public abstract boolean contains(Location location);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractZone that = (AbstractZone) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(smsNumber, that.smsNumber) &&
                Objects.equals(center, that.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, smsNumber, center);
    }

    @Override
    public String toString() {
        return "AbstractZone{name='" + name + "', smsNumber='" + smsNumber + "', center=" + center + "}";
    }
}
package bg.sofia.uni.fmi.mjt.parking.zone;

import bg.sofia.uni.fmi.mjt.parking.location.Location;
import java.util.Objects;

public class BlueZone extends AbstractZone {
    private final double radius;

    public BlueZone(String name, Location center, double radius) {
        super(name, "1302", center);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean contains(Location location) {
        if (location == null) {
            return false;
        }
        double distance = calculateDistance(location);
        return distance <= radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BlueZone blueZone = (BlueZone) o;
        return Double.compare(blueZone.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), radius);
    }

    @Override
    public String toString() {
        return "BlueZone{" +
                "name='" + getName() + '\'' +
                ", smsNumber='" + getSmsNumber() + '\'' +
                ", center=" + center +
                ", radius=" + radius +
                '}';
    }
}
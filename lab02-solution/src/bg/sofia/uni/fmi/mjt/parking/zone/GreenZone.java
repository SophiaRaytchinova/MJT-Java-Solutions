package bg.sofia.uni.fmi.mjt.parking.zone;

import bg.sofia.uni.fmi.mjt.parking.location.Location;
import java.util.Objects;

public class GreenZone extends AbstractZone {
    private final double innerRadius;
    private final double outerRadius;

    public GreenZone(String name, Location center, double innerRadius, double outerRadius) {
        super(name, "1303", center);
        if (innerRadius < 0 || outerRadius <= innerRadius) {
            throw new IllegalArgumentException("Invalid radius values for GreenZone");
        }
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    public double getInnerRadius() {
        return innerRadius;
    }

    public double getOuterRadius() {
        return outerRadius;
    }

    @Override
    public boolean contains(Location location) {
        if (location == null) {
            return false;
        }
        double distance = calculateDistance(location);
        return distance > innerRadius && distance <= outerRadius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GreenZone greenZone = (GreenZone) o;
        return Double.compare(greenZone.innerRadius, innerRadius) == 0 &&
                Double.compare(greenZone.outerRadius, outerRadius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), innerRadius, outerRadius);
    }

    @Override
    public String toString() {
        return "GreenZone{" +
                "name='" + getName() + '\'' +
                ", smsNumber='" + getSmsNumber() + '\'' +
                ", center=" + center +
                ", innerRadius=" + innerRadius +
                ", outerRadius=" + outerRadius +
                '}';
    }
}
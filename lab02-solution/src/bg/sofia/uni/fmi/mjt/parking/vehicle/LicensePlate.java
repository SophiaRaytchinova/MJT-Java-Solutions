package bg.sofia.uni.fmi.mjt.parking.vehicle;

import java.util.Objects;
import java.util.regex.Pattern;

public class LicensePlate {
    private final String plate;

    private LicensePlate(String plate) {
        this.plate = plate;
    }

    public static LicensePlate parse(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }

        String cleaned = text.replace(" ", "").replace("-", "").toUpperCase();

        cleaned = cleaned.replace('А', 'A')
                .replace('В', 'B')
                .replace('Е', 'E')
                .replace('К', 'K')
                .replace('М', 'M')
                .replace('Н', 'H')
                .replace('О', 'O')
                .replace('Р', 'P')
                .replace('С', 'C')
                .replace('Т', 'T')
                .replace('У', 'Y')
                .replace('Х', 'X');

        if (!Pattern.matches("^[A-Z]{1,2}[0-9]{4}[A-Z]{1,2}$", cleaned)) {
            return null;
        }

        return new LicensePlate(cleaned);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LicensePlate that)) return false;
        return plate.equals(that.plate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plate);
    }

    @Override
    public String toString() {
        return plate;
    }
}
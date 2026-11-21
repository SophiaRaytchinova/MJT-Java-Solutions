package bg.sofia.uni.fmi.mjt.laundry.garment;

public abstract class Garment {

    private final String brand;
    private final double weightKg;
    private boolean clean;

    public Garment(String brand, double weightKg) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand must not be null or blank");
        }
        if (!(weightKg > 0)) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.brand = brand;
        this.weightKg = weightKg;
        this.clean = false;
    }

    public String getBrand() {
        return brand;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public boolean isClean() {
        return clean;
    }

    public void markClean() {
        clean = true;
    }

}

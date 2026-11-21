package bg.sofia.uni.fmi.mjt.laundry.program;

import bg.sofia.uni.fmi.mjt.laundry.garment.Wool;

public class WoolProgram implements WashProgram<Wool> {

    private static final int TEMPERATURE = 40;

    @Override
    public int getTemperature() {
        return TEMPERATURE;
    }

    @Override
    public void wash(Wool garment) {
        if (garment == null) {
            throw new IllegalArgumentException("Garment must not be null");
        }
        garment.markClean();
    }

}

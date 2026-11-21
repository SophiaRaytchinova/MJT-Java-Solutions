package bg.sofia.uni.fmi.mjt.laundry.program;

import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;

public class GentleProgram implements WashProgram<Garment> {

    private static final int TEMPERATURE = 30;

    @Override
    public int getTemperature() {
        return TEMPERATURE;
    }

    @Override
    public void wash(Garment garment) {
        if (garment == null) {
            throw new IllegalArgumentException("Garment must not be null");
        }
        garment.markClean();
    }

}

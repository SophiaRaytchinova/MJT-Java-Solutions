package bg.sofia.uni.fmi.mjt.laundry.program;

import bg.sofia.uni.fmi.mjt.laundry.garment.Cotton;

public class CottonProgram implements WashProgram<Cotton> {

    private static final int TEMPERATURE = 90;

    @Override
    public int getTemperature() {
        return TEMPERATURE;
    }

    @Override
    public void wash(Cotton garment) {
        if (garment == null) {
            throw new IllegalArgumentException("Garment must not be null");
        }
        garment.markClean();
    }

}

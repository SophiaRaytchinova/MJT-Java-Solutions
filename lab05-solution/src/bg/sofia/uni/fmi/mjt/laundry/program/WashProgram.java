package bg.sofia.uni.fmi.mjt.laundry.program;

import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;

public interface WashProgram<G extends Garment> {

    int getTemperature();

    void wash(G garment);

}

package bg.sofia.uni.fmi.mjt.laundry;

import bg.sofia.uni.fmi.mjt.laundry.basket.LaundryBasket;
import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;
import bg.sofia.uni.fmi.mjt.laundry.program.WashProgram;

public record WashJob<G extends Garment>(LaundryBasket<G> basket, WashProgram<? super G> program) {

    public WashJob {
        if (basket == null) {
            throw new IllegalArgumentException("Basket must not be null");
        }
        if (program == null) {
            throw new IllegalArgumentException("Program must not be null");
        }
    }

    public void wash() {
        for (G garment : basket) {
            program.wash(garment);
        }
    }

}

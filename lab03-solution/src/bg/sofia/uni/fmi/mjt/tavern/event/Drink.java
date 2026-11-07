package bg.sofia.uni.fmi.mjt.tavern.event;

public record Drink(String guest, int rakias) implements Event {

    public Drink {
        Event.requireName(guest);
        if (rakias <= 0) {
            throw new IllegalArgumentException("Rakias must be positive");
        }
    }
}
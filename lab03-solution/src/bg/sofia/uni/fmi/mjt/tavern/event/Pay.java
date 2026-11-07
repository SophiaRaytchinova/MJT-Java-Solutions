package bg.sofia.uni.fmi.mjt.tavern.event;

public record Pay(String guest, int amount) implements Event {

    public Pay {
        Event.requireName(guest);
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
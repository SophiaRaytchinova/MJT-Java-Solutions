package bg.sofia.uni.fmi.mjt.tavern.event;

public record Leave(String guest) implements Event {

    public Leave {
        Event.requireName(guest);
    }
}
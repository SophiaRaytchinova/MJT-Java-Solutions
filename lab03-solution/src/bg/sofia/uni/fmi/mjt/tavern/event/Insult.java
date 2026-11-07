package bg.sofia.uni.fmi.mjt.tavern.event;

public record Insult(String from, String to) implements Event {

    public Insult {
        Event.requireName(from);
        Event.requireName(to);
        if (from.equals(to)) {
            throw new IllegalArgumentException("A guest cannot insult himself");
        }
    }
}
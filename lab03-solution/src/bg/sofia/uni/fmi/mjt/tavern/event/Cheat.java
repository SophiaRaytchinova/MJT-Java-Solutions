package bg.sofia.uni.fmi.mjt.tavern.event;

public record Cheat(String cheater, String victim) implements Event {

    public Cheat {
        Event.requireName(cheater);
        Event.requireName(victim);
        if (cheater.equals(victim)) {
            throw new IllegalArgumentException("A guest cannot cheat himself");
        }
    }
}
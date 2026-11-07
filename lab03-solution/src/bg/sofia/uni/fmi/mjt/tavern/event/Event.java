package bg.sofia.uni.fmi.mjt.tavern.event;

public sealed interface Event permits Drink, Insult, Cheat, Pay, Leave {

    static void requireName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
    }
}
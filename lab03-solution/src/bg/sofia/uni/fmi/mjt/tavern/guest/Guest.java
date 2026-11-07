package bg.sofia.uni.fmi.mjt.tavern.guest;

public abstract class Guest {

    private final String name;
    private final int drunkThreshold;

    public Guest(String name) {
        this(name, Integer.MAX_VALUE);
    }

    protected Guest(String name, int drunkThreshold) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Guest name must not be null or blank");
        }
        this.name = name;
        this.drunkThreshold = drunkThreshold;
    }

    public String getName() {
        return name;
    }

    public boolean isDrunk(int rakiasDrunk) {
        return rakiasDrunk >= drunkThreshold;
    }

    public Mood reactToInsult(Mood current) {
        return current;
    }

    public Mood reactToCheating(Mood current) {
        return current;
    }

    public Mood intervene(Mood affectedMood) {
        return affectedMood;
    }
}
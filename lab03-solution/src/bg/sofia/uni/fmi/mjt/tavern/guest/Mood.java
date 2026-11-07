package bg.sofia.uni.fmi.mjt.tavern.guest;

public enum Mood {
    CALM,
    TENSE,
    ANGRY,
    BRAWLING;

    public Mood escalate() {
        Mood[] values = values();
        return values[Math.min(ordinal() + 1, values.length - 1)];
    }

    public Mood calmDown() {
        Mood[] values = values();
        return values[Math.max(ordinal() - 1, 0)];
    }
}
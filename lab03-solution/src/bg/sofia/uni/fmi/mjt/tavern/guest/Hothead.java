package bg.sofia.uni.fmi.mjt.tavern.guest;

public class Hothead extends Guest {

    private static final int THRESHOLD = 4;

    public Hothead(String name) {
        super(name, THRESHOLD);
    }

    @Override
    public Mood reactToInsult(Mood current) {
        return Mood.BRAWLING;
    }

    @Override
    public Mood reactToCheating(Mood current) {
        return current.escalate().escalate();
    }

    @Override
    public Mood intervene(Mood affectedMood) {
        return affectedMood.escalate();
    }
}
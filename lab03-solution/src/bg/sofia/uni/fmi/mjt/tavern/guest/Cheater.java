package bg.sofia.uni.fmi.mjt.tavern.guest;

public class Cheater extends Guest {

    private static final int THRESHOLD = 3;

    public Cheater(String name) {
        super(name, THRESHOLD);
    }

    @Override
    public Mood reactToInsult(Mood current) {
        return current.escalate();
    }

    @Override
    public Mood reactToCheating(Mood current) {
        return current.calmDown();
    }
}
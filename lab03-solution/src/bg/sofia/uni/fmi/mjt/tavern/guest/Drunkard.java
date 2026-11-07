package bg.sofia.uni.fmi.mjt.tavern.guest;

public class Drunkard extends Guest {

    private static final int THRESHOLD = 2;

    public Drunkard(String name) {
        super(name, THRESHOLD);
    }

    @Override
    public Mood reactToInsult(Mood current) {
        return current.escalate();
    }

    @Override
    public Mood reactToCheating(Mood current) {
        return current;
    }
}
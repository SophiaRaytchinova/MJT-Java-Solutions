package bg.sofia.uni.fmi.mjt.tavern.guest;

public class Peacemaker extends Guest {

    private static final int THRESHOLD = 5;

    public Peacemaker(String name) {
        super(name, THRESHOLD);
    }

    @Override
    public Mood reactToInsult(Mood current) {
        return current;
    }

    @Override
    public Mood reactToCheating(Mood current) {
        return current;
    }

    @Override
    public Mood intervene(Mood affectedMood) {
        return affectedMood.calmDown();
    }
}
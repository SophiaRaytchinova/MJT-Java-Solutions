package bg.sofia.uni.fmi.mjt.tavern;

import bg.sofia.uni.fmi.mjt.tavern.guest.Guest;
import bg.sofia.uni.fmi.mjt.tavern.guest.Mood;

class Visit {

    private final Guest guest;
    private Mood mood = Mood.CALM;
    private int rakiasDrunk;
    private int tab;

    Visit(Guest guest) {
        this.guest = guest;
    }

    Guest guest() {
        return guest;
    }

    String name() {
        return guest.getName();
    }

    Mood mood() {
        return mood;
    }

    void setMood(Mood mood) {
        this.mood = mood;
    }

    int tab() {
        return tab;
    }

    boolean isDrunk() {
        return guest.isDrunk(rakiasDrunk);
    }

    void drink(int rakias, int rakiaPrice) {
        rakiasDrunk += rakias;
        tab += rakias * rakiaPrice;
    }

    void pay(int amount) {
        tab = Math.max(0, tab - amount);
    }
}
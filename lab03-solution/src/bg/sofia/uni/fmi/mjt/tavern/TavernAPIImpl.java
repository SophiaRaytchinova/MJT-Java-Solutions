package bg.sofia.uni.fmi.mjt.tavern;

import bg.sofia.uni.fmi.mjt.tavern.event.Cheat;
import bg.sofia.uni.fmi.mjt.tavern.event.Drink;
import bg.sofia.uni.fmi.mjt.tavern.event.Event;
import bg.sofia.uni.fmi.mjt.tavern.event.Insult;
import bg.sofia.uni.fmi.mjt.tavern.event.Leave;
import bg.sofia.uni.fmi.mjt.tavern.event.Pay;
import bg.sofia.uni.fmi.mjt.tavern.exception.BannedGuestException;
import bg.sofia.uni.fmi.mjt.tavern.exception.BrawlException;
import bg.sofia.uni.fmi.mjt.tavern.exception.CheatingException;
import bg.sofia.uni.fmi.mjt.tavern.exception.GuestAlreadyInsideException;
import bg.sofia.uni.fmi.mjt.tavern.exception.InsultException;
import bg.sofia.uni.fmi.mjt.tavern.exception.NoSuchGuestException;
import bg.sofia.uni.fmi.mjt.tavern.exception.ProvocationException;
import bg.sofia.uni.fmi.mjt.tavern.exception.UnpaidTabException;
import bg.sofia.uni.fmi.mjt.tavern.guest.Guest;
import bg.sofia.uni.fmi.mjt.tavern.guest.Mood;

public class TavernAPIImpl implements TavernAPI {

    private static final int INITIAL_CAPACITY = 8;

    private final int rakiaPrice;

    private Visit[] inside = new Visit[INITIAL_CAPACITY];
    private int insideCount;

    private String[] banned = new String[INITIAL_CAPACITY];
    private int bannedCount;

    public TavernAPIImpl(int rakiaPrice) {
        if (rakiaPrice <= 0) {
            throw new IllegalArgumentException("The rakia price must be positive");
        }
        this.rakiaPrice = rakiaPrice;
    }

    @Override
    public void admit(Guest guest) {
        if (guest == null) {
            throw new IllegalArgumentException("The guest must not be null");
        }
        String name = guest.getName();
        if (isBanned(name)) {
            throw new BannedGuestException("Guest " + name + " is banned");
        }
        if (indexOf(name) >= 0) {
            throw new GuestAlreadyInsideException("Guest " + name + " is already inside");
        }
        if (insideCount == inside.length) {
            Visit[] bigger = new Visit[inside.length * 2];
            System.arraycopy(inside, 0, bigger, 0, insideCount);
            inside = bigger;
        }
        inside[insideCount++] = new Visit(guest);
    }

    @Override
    public void handle(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("The event must not be null");
        }
        switch (event) {
            case Drink d -> find(d.guest()).drink(d.rakias(), rakiaPrice);
            case Pay p -> find(p.guest()).pay(p.amount());
            case Leave l -> handleLeave(l);
            case Insult i -> {
                Visit from = find(i.from());
                Visit to = find(i.to());
                quarrel(from, to, true);
            }
            case Cheat c -> {
                Visit cheater = find(c.cheater());
                Visit victim = find(c.victim());
                quarrel(cheater, victim, false);
            }
        }
    }

    @Override
    public Mood moodOf(String name) {
        requireName(name);
        return find(name).mood();
    }

    @Override
    public int tabOf(String name) {
        requireName(name);
        return find(name).tab();
    }

    @Override
    public String[] guestsInside() {
        String[] names = new String[insideCount];
        for (int i = 0; i < insideCount; i++) {
            names[i] = inside[i].name();
        }
        return names;
    }

    @Override
    public String[] bannedGuests() {
        String[] copy = new String[bannedCount];
        System.arraycopy(banned, 0, copy, 0, bannedCount);
        return copy;
    }

    private void handleLeave(Leave leave) {
        Visit visit = find(leave.guest());
        remove(visit.name());
        if (visit.tab() > 0) {
            ban(visit.name());
            throw new UnpaidTabException(visit.name() + " left without paying " + visit.tab());
        }
    }

    private void quarrel(Visit offender, Visit affected, boolean insult) {
        Mood before = affected.mood();
        Mood after = insult
                ? affected.guest().reactToInsult(before)
                : affected.guest().reactToCheating(before);

        if (affected.isDrunk() && after.ordinal() > before.ordinal()) {
            after = after.escalate();
        }

        for (int i = 0; i < insideCount; i++) {
            Visit other = inside[i];
            if (other == offender || other == affected) {
                continue;
            }
            after = other.guest().intervene(after);
        }

        affected.setMood(after);

        if (after == Mood.BRAWLING) {
            String offenderName = offender.name();
            String affectedName = affected.name();
            remove(offenderName);
            remove(affectedName);
            ban(offenderName);
            ban(affectedName);

            ProvocationException cause = insult
                    ? new InsultException(offenderName + " insulted " + affectedName)
                    : new CheatingException(offenderName + " cheated " + affectedName);
            throw new BrawlException("Brawl between " + offenderName + " and " + affectedName, cause);
        }
    }

    private static void requireName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name must not be null or blank");
        }
    }

    private int indexOf(String name) {
        for (int i = 0; i < insideCount; i++) {
            if (inside[i].name().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private Visit find(String name) {
        int index = indexOf(name);
        if (index < 0) {
            throw new NoSuchGuestException("No guest named " + name + " inside the tavern");
        }
        return inside[index];
    }

    private void remove(String name) {
        int index = indexOf(name);
        if (index < 0) {
            return;
        }
        for (int i = index; i < insideCount - 1; i++) {
            inside[i] = inside[i + 1];
        }
        inside[--insideCount] = null;
    }

    private boolean isBanned(String name) {
        for (int i = 0; i < bannedCount; i++) {
            if (banned[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void ban(String name) {
        if (isBanned(name)) {
            return;
        }
        if (bannedCount == banned.length) {
            String[] bigger = new String[banned.length * 2];
            System.arraycopy(banned, 0, bigger, 0, bannedCount);
            banned = bigger;
        }
        banned[bannedCount++] = name;
    }
}
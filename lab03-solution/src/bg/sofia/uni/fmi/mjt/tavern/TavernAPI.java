package bg.sofia.uni.fmi.mjt.tavern;

import bg.sofia.uni.fmi.mjt.tavern.event.Event;
import bg.sofia.uni.fmi.mjt.tavern.exception.BannedGuestException;
import bg.sofia.uni.fmi.mjt.tavern.exception.BrawlException;
import bg.sofia.uni.fmi.mjt.tavern.exception.GuestAlreadyInsideException;
import bg.sofia.uni.fmi.mjt.tavern.exception.NoSuchGuestException;
import bg.sofia.uni.fmi.mjt.tavern.exception.UnpaidTabException;
import bg.sofia.uni.fmi.mjt.tavern.guest.Guest;
import bg.sofia.uni.fmi.mjt.tavern.guest.Mood;

public interface TavernAPI {

    void admit(Guest guest);

    void handle(Event event);

    Mood moodOf(String name);

    int tabOf(String name);

    String[] guestsInside();

    String[] bannedGuests();

}
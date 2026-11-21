package bg.sofia.uni.fmi.mjt.laundry;

import bg.sofia.uni.fmi.mjt.laundry.basket.LaundryBasket;
import bg.sofia.uni.fmi.mjt.laundry.exception.AlreadyQueuedException;
import bg.sofia.uni.fmi.mjt.laundry.exception.EmptyQueueException;
import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;
import bg.sofia.uni.fmi.mjt.laundry.program.WashProgram;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class LaundryRoom implements LaundryRoomAPI {

    private final Queue<WashJob<?>> queue = new LinkedList<>();
    private final Map<String, Integer> tokensSpent = new HashMap<>();

    public LaundryRoom() {
    }

    @Override
    public <G extends Garment> WashJob<G> scheduleWash(LaundryBasket<G> basket, WashProgram<? super G> program) {
        if (basket == null) {
            throw new IllegalArgumentException("Basket must not be null");
        }
        if (program == null) {
            throw new IllegalArgumentException("Program must not be null");
        }
        if (basket.size() == 0) {
            throw new IllegalArgumentException("Basket must not be empty");
        }
        if (isQueued(basket)) {
            throw new AlreadyQueuedException("The basket is already waiting in the queue");
        }

        WashJob<G> job = new WashJob<>(basket, program);
        queue.add(job);
        return job;
    }

    @Override
    public boolean cancelWash(LaundryBasket<?> basket) {
        if (basket == null) {
            throw new IllegalArgumentException("Basket must not be null");
        }
        Iterator<WashJob<?>> it = queue.iterator();
        while (it.hasNext()) {
            if (it.next().basket() == basket) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public WashJob<?> runNext() {
        if (queue.isEmpty()) {
            throw new EmptyQueueException("The queue is empty");
        }
        WashJob<?> next = queue.poll();
        next.wash();
        String owner = next.basket().getOwner();
        tokensSpent.put(owner, tokensSpent.getOrDefault(owner, 0) + 1);
        return next;
    }

    @Override
    public List<WashJob<?>> getScheduledWashes() {
        return List.copyOf(queue);
    }

    @Override
    public int getTokensSpent(String owner) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner must not be null or blank");
        }
        return tokensSpent.getOrDefault(owner, 0);
    }

    private boolean isQueued(LaundryBasket<?> basket) {
        for (WashJob<?> job : queue) {
            if (job.basket() == basket) {
                return true;
            }
        }
        return false;
    }

}

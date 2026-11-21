package bg.sofia.uni.fmi.mjt.laundry.basket;

import bg.sofia.uni.fmi.mjt.laundry.exception.BasketOverloadedException;
import bg.sofia.uni.fmi.mjt.laundry.exception.EmptyBasketException;
import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class LaundryBasket<G extends Garment> implements Iterable<G> {

    private static final double EPSILON = 1e-9;

    private final String owner;
    private final double capacityKg;
    private final Deque<G> garments = new ArrayDeque<>();

    public LaundryBasket(String owner, double capacityKg) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner must not be null or blank");
        }
        if (!(capacityKg > 0)) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.owner = owner;
        this.capacityKg = capacityKg;
    }

    public String getOwner() {
        return owner;
    }

    public double getCapacityKg() {
        return capacityKg;
    }

    public double getWeightKg() {
        double total = 0;
        for (G garment : garments) {
            total += garment.getWeightKg();
        }
        return total;
    }

    public int size() {
        return garments.size();
    }

    public void add(G garment) {
        if (garment == null) {
            throw new IllegalArgumentException("Garment must not be null");
        }
        ensureFits(garment.getWeightKg());
        garments.push(garment);
    }

    public void addAll(Collection<? extends G> garments) {
        if (garments == null) {
            throw new IllegalArgumentException("Garments must not be null");
        }
        double total = 0;
        for (G garment : garments) {
            if (garment == null) {
                throw new IllegalArgumentException("Garments must not contain null");
            }
            total += garment.getWeightKg();
        }
        ensureFits(total);
        for (G garment : garments) {
            this.garments.push(garment);
        }
    }

    public int moveAllTo(LaundryBasket<? super G> other) {
        if (other == null || other == this) {
            throw new IllegalArgumentException("Other must be a different, non-null basket");
        }
        other.ensureFits(getWeightKg());

        int moved = garments.size();
        Iterator<G> fromBottom = garments.descendingIterator();
        while (fromBottom.hasNext()) {
            other.garments.push(fromBottom.next());
        }
        garments.clear();
        return moved;
    }

    public G takeTop() {
        if (garments.isEmpty()) {
            throw new EmptyBasketException("The basket is empty");
        }
        return garments.pop();
    }

    @Override
    public Iterator<G> iterator() {
        final Iterator<G> it = garments.iterator();
        return new Iterator<G>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public G next() {
                return it.next();
            }
        };
    }

    private void ensureFits(double additionalKg) {
        if (getWeightKg() + additionalKg > capacityKg + EPSILON) {
            throw new BasketOverloadedException("The garments do not fit in the basket");
        }
    }

}

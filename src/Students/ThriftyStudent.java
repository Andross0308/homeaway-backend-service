package Students;

import Service.Service;

public interface ThriftyStudent extends Student{

    /**
     * Verifies if the given eating is cheaper than the one stored and,
     * if so, changes the cheapest to the eating
     * @param eating: New location of the student
     * @return true if the eating is more expensive than the cheapestEating, false otherwise
     */
    boolean checkCheapestEating(Service eating);
}

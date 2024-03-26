package edu.northeastern.cs5520_lab6.stickers;

/**
 * Represents the cost associated with the use of a particular sticker within the application.
 * This class includes the sticker's identifier, the count of how many times the sticker has
 * been used, and the cost per use. It provides functionality to calculate the total cost
 * based on the usage count and cost per use.
 *
 * The `Cost` class is crucial for managing and displaying the financial aspects related to
 * sticker usage, allowing for a straightforward calculation of expenses incurred from sticker
 * usage within the app.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class Cost {
    private int stickerId;
    private int count;
    private double costPerUse;

    /**
     * Constructs a Cost instance with specified sticker usage details.
     *
     * @param stickerId The unique identifier of the sticker.
     * @param count The number of times the sticker has been used.
     * @param costPerUse The cost incurred each time the sticker is used.
     */
    public Cost(int stickerId, int count, double costPerUse) {
        this.stickerId = stickerId;
        this.count = count;
        this.costPerUse = costPerUse;
    }

    // Getters for the class properties

    /**
     * Returns the sticker's unique identifier.
     * @return The sticker ID as an integer.
     */
    public int getStickerId() {
        return stickerId;
    }

    /**
     * Returns the count of how many times the sticker has been used.
     * @return The usage count as an integer.
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns the cost per use of the sticker.
     * @return The cost per use as a double.
     */
    public double getCostPerUse() {
        return costPerUse;
    }

    /**
     * Calculates and returns the total cost incurred from using the sticker.
     * @return The total cost as a double, derived from the usage count and cost per use.
     */
    public double getTotalCost() {
        return count * costPerUse;
    }

    // Optionally, we can add setters if needed
}
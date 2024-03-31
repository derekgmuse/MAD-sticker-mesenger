package edu.northeastern.cs5520_lab6.stickers;

/**
 * Represents the cost associated with the use of a particular sticker within the application.
 * This class includes the sticker's identifier, the count of how many times the sticker has
 * been used, and the cost per use. It provides functionality to calculate the total cost
 * based on the usage count and cost per use.
 *
 * The {@code Cost} class is crucial for managing and displaying the financial aspects related to
 * sticker usage, allowing for a straightforward calculation of expenses incurred from sticker
 * usage within the app.
 *
 * @version 1.1
 * @author Tony Wilson
 */
public class Cost {
    private String stickerId;  // The unique identifier of the sticker
    private int count;  // The number of times the sticker has been used
    private double costPerUse;  // The cost incurred each time the sticker is used

    /**
     * Default constructor for creating an empty Cost instance. This is used primarily
     * for data retrieval from databases that require a no-argument constructor.
     */
    public Cost() { }

    /**
     * Constructs a Cost instance with specified sticker usage details.
     *
     * @param stickerId The unique identifier of the sticker.
     * @param count The number of times the sticker has been used.
     * @param costPerUse The cost incurred each time the sticker is used.
     */
    public Cost(String stickerId, int count, double costPerUse) {
        this.stickerId = stickerId;
        this.count = count;
        this.costPerUse = costPerUse;
    }

    /**
     * Constructs a Cost instance based on a provided Sticker object.
     * Retrieves the sticker's ID and count, and calculates the cost per use based on sticker ID.
     *
     * @param sticker The sticker object from which to derive cost details.
     */
    public Cost(Sticker sticker) {
        this.stickerId = sticker.getId();
        this.count = sticker.getCount();
        this.costPerUse = StickerEnum.getCostPerSticker(stickerId);
    }
    // Getters for the class properties

    /**
     * Returns the sticker's unique identifier.
     *
     * @return The sticker ID as a String.
     */
    public String getStickerId() {
        return stickerId;
    }

    /**
     * Returns the count of how many times the sticker has been used.
     *
     * @return The usage count as an integer.
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns the cost per use of the sticker.
     *
     * @return The cost per use as a double.
     */
    public double getCostPerUse() {
        return costPerUse;
    }

    /**
     * Calculates and returns the total cost incurred from using the sticker.
     *
     * @return The total cost as a double, derived from the usage count and cost per use.
     */
    public double getTotalCost() {
        return count * costPerUse;
    }

    // Optionally, we can add setters if needed
}
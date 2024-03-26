package edu.northeastern.cs5520_lab6.stickers;

/**
 * Represents a sticker entity with a unique identifier and a count indicating how many times
 * the sticker has been used within the application. This class is essential for tracking
 * sticker usage and managing sticker-related data.
 *
 * The `Sticker` class facilitates the organization and manipulation of sticker information,
 * making it easier to handle sticker selection, display, and usage analysis.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class Sticker {
    private int id;
    private int count;

    /**
     * Constructs a Sticker instance with specified identifier and usage count.
     *
     * @param id The unique identifier for the sticker.
     * @param count The number of times the sticker has been used.
     */
    public Sticker(int id, int count) {
        this.id = id;
        this.count = count;
    }

    /**
     * Returns the unique identifier of the sticker.
     *
     * @return The sticker's ID as an integer.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns how many times the sticker has been used.
     *
     * @return The usage count of the sticker as an integer.
     */
    public int getCount() {
        return count;
    }

    // Optionally, setters can be added if needed
}
package edu.northeastern.cs5520_lab6.stickers;

/**
 * Represents a sticker entity with a unique identifier and a count indicating how many times
 * the sticker has been used within the application. This class is essential for tracking
 * sticker usage and managing sticker-related data.
 *
 * The {@code Sticker} class facilitates the organization and manipulation of sticker information,
 * making it easier to handle sticker selection, display, and usage analysis. It includes methods
 * for retrieving sticker details and manipulating the usage count.
 *
 * @version 1.1
 * @author Tony Wilson
 */
public class Sticker {
    private String id; // Unique identifier for the sticker
    private int count; // Number of times the sticker has been used

    /**
     * Default constructor for creating an empty Sticker instance. This is used primarily
     * for data retrieval from databases that require a no-argument constructor.
     */
    public Sticker() { }


    /**
     * Constructs a {@code Sticker} instance with a specified identifier and usage count.
     * This constructor allows for the creation of a sticker with predefined attributes.
     *
     * @param id The unique identifier for the sticker.
     * @param count The number of times the sticker has been used.
     */
    public Sticker(String id, int count) {
        this.id = id;
        this.count = count;
    }

    /**
     * Returns the unique identifier of the sticker.
     *
     * @return The sticker's ID as an integer.
     */
    public String getId() {
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

    /**
     * Increments the usage count of the sticker by one.
     * This method is useful for tracking sticker usage throughout the application.
     */
    public void incrementCount() {
        this.count++;
    }

    /**
     * Determines whether the specified object is equal to this {@code Sticker} instance.
     * Two {@code Sticker} objects are considered equal if they have the same ID.
     *
     * @param o The object to compare with this {@code Sticker} instance.
     * @return {@code true} if the specified object is equal to this sticker; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sticker)) return false;
        Sticker sticker = (Sticker) o;
        return id != null && id.equals(sticker.id);
    }
}
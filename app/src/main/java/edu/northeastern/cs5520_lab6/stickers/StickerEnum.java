package edu.northeastern.cs5520_lab6.stickers;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_lab6.R;

/**
 * Defines an enumeration for stickers available in the application. Each enum constant
 * represents a unique sticker, identified by a string ID, associated with a drawable resource,
 * and having a specific cost associated with its use.
 *
 * @version 1.0
 * @author Tony Wilson
 */
public enum StickerEnum {
    STICKER_1("1", R.drawable.sticker1, 0.99),
    STICKER_2("2", R.drawable.sticker2, 0.99),
    UNKNOWN("-1", R.drawable.default_sticker, 0.00); // Default sticker for error cases

    // Add more stickers as needed

    private final String id;
    private final int resourceId;
    private final double cost;

    /**
     * Constructs a StickerEnum constant with the specified identifier, drawable resource, and cost.
     *
     * @param id The unique identifier for the sticker.
     * @param resourceId The drawable resource associated with the sticker.
     * @param cost The cost associated with using the sticker.
     */
    StickerEnum(String id, int resourceId, double cost) {
        this.id = id;
        this.resourceId = resourceId;
        this.cost = cost;
    }

    /**
     * Returns the unique identifier of the sticker.
     *
     * @return The sticker's identifier as a String.
     */
    public String getId() { return id; }

    /**
     * Returns the drawable resource ID of the sticker.
     *
     * @return The resource ID of the sticker's drawable.
     */
    public int getResourceId() { return resourceId; }

    /**
     * Returns the cost associated with the sticker.
     *
     * @return The sticker's cost as a double.
     */
    public double getCost() { return cost; }

    /**
     * Retrieves the drawable resource ID for a sticker by its identifier.
     *
     * @param id The unique identifier for the sticker.
     * @return The drawable resource ID of the sticker if found, otherwise returns the resource ID of the UNKNOWN sticker.
     */
    public static int getResourceIdById(String id) {
        int idInt;
        try {
            idInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return UNKNOWN.getResourceId(); // Default or error case for invalid integer strings
        }

        for (StickerEnum sticker : values()) {
            if (sticker.getId().equals(id)) {
                return sticker.getResourceId();
            }
        }
        return UNKNOWN.getResourceId(); // Default or error case
    }

    /**
     * Returns a list of all stickers defined in the enumeration, excluding the UNKNOWN sticker.
     *
     * @return A List of Sticker objects representing all known stickers.
     */
    public static List<Sticker> getAllStickers() {
        List<Sticker> stickers = new ArrayList<>();
        for (StickerEnum stickerEnum : values()) {
            if (!stickerEnum.equals(UNKNOWN)) { // Skip UNKNOWN
                stickers.add(new Sticker(stickerEnum.getId(), 1));
            }
        }
        return stickers;
    }

    /**
     * Retrieves the cost of a sticker by its identifier.
     *
     * @param id The unique identifier for the sticker.
     * @return The cost of the sticker if found, otherwise returns the cost of the UNKNOWN sticker.
     */
    public static double getCostPerSticker(String id) {
        for (StickerEnum sticker : values()) {
            if (sticker.getId().equals(id)) {
                return sticker.getCost();
            }
        }
        return UNKNOWN.getCost(); // Return cost of UNKNOWN sticker if not found
    }
}
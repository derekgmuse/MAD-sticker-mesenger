package edu.northeastern.cs5520_lab6.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Adapter for managing the fragments in a ViewPager, allowing navigation between the Chats,
 * Stickers, and Costs sections of the app. This adapter dynamically creates and displays
 * fragments based on user interaction with the tab layout.
 *
 * Implements {@link FragmentPagerAdapter} for efficient memory management of pages; pages
 * are retained in memory but their view hierarchies might be destroyed if not visible. This
 * behavior is suitable for a small number of static pages.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    /**
     * Constructs the ViewPagerAdapter with the specified FragmentManager.
     *
     * @param fm FragmentManager that will manage the fragments.
     */
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    /**
     * Returns the Fragment associated with a specified position. This method determines
     * which Fragment should be displayed based on the user's current position in the ViewPager.
     *
     * @param position Position of the item to be displayed.
     * @return The Fragment corresponding to the position, or null if the position is not valid.
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ChatsFragment(); // Tab 1: Chats
            case 1:
                return new StickersFragment(); // Tab 2: Stickers
            case 2:
                return new CostsFragment(); // Tab 3: Costs
            default:
                return null; // This should never happen
        }
    }

    /**
     * Gets the count of pages the ViewPager will display.
     *
     * @return The total number of pages.
     */
    @Override
    public int getCount() {
        // Fixed count for three tabs.
        return 3;
    }

    /**
     * Retrieves the page title for the tabs in the ViewPager based on the position.
     *
     * @param position Position of the tab whose title is requested.
     * @return The title of the tab at the given position.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "CHATS";
            case 1:
                return "STICKERS";
            case 2:
                return "COSTS";
            default:
                return null;
        }
    }
}
package edu.northeastern.cs5520_lab6.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.contacts.ContactsActivity;
import edu.northeastern.cs5520_lab6.api.MessageListenerService;

/**
 * Main activity of the application, hosting the primary user interface components. This activity
 * sets up a {@link Toolbar} as the app's ActionBar, a {@link ViewPager} for navigating between
 * different sections (fragments) of the app, and a {@link FloatingActionButton} (FAB) for user
 * actions like creating a new chat or adding contacts.
 *
 * The {@link ViewPager} is used in conjunction with a {@link TabLayout} to facilitate tabbed
 * navigation between the app's main features: chats, stickers, and cost analysis. Each tab is
 * associated with a specific fragment that manages its respective content.
 *
 * This activity also inflates a menu for the toolbar, providing additional actions to the user
 * through the ActionBar.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    /**
     * Initializes the activity, sets up the user interface components, and configures navigation.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Intent serviceIntent = new Intent(this, MessageListenerService.class);
        startForegroundService(serviceIntent);
        */

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        fab = findViewById(R.id.fab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Set the Toolbar as the ActionBar

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        fab.setOnClickListener(view -> {
            // Open contact selection activity
            Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(intent);
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not needed for this use case
            }

            @Override
            public void onPageSelected(int position) {
                // Check if the selected page is your target tab
                if (position == 1) { // Assuming position 1 is your StickersFragment
                    refreshStickersFragment();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Not needed for this use case
            }
        });
    }

    /**
     * Sets up the ViewPager with an adapter for navigating between fragments.
     *
     * @param viewPager The ViewPager that will allow swipe navigation between fragments.
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void refreshStickersFragment() {
        // Find the StickersFragment instance and call a method to refresh its content
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + 1);
        if (page != null) {
            ((StickersFragment) page).refreshContent(); // Ensure you have this method in your StickersFragment
        }
    }

    /**
     * Inflates the menu for the toolbar to add actions to the ActionBar.
     *
     * @param menu The options menu in which the items are placed.
     * @return true for the menu to be displayed; if false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
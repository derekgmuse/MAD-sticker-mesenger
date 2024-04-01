package edu.northeastern.cs5520_lab6.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.contacts.ContactsActivity;

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
        Need to first check if the user has permitted notifications
        Android versions including and after Tiramisu need user permission
        If the user has not granted permission, a request permissions
        pop up asks whether or not the user would like to receive permissions
        and modifies accordingly
        source - https://www.youtube.com/watch?v=vyt20Gg2Ckg
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.POST_NOTIFICATIONS)!=
                    PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        // Setup UI components
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

        /**
         * Monitors page changes within the ViewPager. Specifically, it checks if the currently selected
         * page is the Stickers tab. If so, it triggers a refresh of the StickersFragment, ensuring that
         * the latest stickers data is displayed. This method is part of the ViewPager's onPageChangeListener,
         * which responds to new pages being selected by the user.
         *
         * @param position The index position of the currently selected page in the ViewPager. This index is
         * used to determine if the StickersFragment is the current page.
         */

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not needed for this use case
            }

            @Override
            public void onPageSelected(int position) {
                // Check if the selected page is target tab
                if (position == 1) { // Position 1 is the StickersFragment
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

    /**
     * Refreshes the content of the StickersFragment if it is currently instantiated and visible to the user.
     * This method locates the StickersFragment using a FragmentManager and a unique tag that identifies the
     * fragment within the ViewPager. If the fragment is found, it calls the fragment's refreshContent() method
     * to update its UI with the latest data. This is particularly useful for ensuring that the sticker collection
     * displayed to the user is up-to-date following any changes to the dataset or when the fragment becomes visible
     * after being selected in the ViewPager.
     */
    private void refreshStickersFragment() {
        // Find the StickersFragment instance and call a method to refresh its content
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + 1);
        if (page != null) {
            ((StickersFragment) page).refreshContent();
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
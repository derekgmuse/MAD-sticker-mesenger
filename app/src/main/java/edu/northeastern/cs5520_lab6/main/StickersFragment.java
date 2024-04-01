package edu.northeastern.cs5520_lab6.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.api.FirebaseApi;
import edu.northeastern.cs5520_lab6.contacts.GenericAdapterNotifier;
import edu.northeastern.cs5520_lab6.stickers.Sticker;

/**
 * Displays a list of stickers in a RecyclerView within the app. Each item in the list represents
 * a sticker, showing the image of the sticker and the count of its usage. This fragment is part of
 * the app's main interface, allowing users to view and interact with their collection of stickers.
 *
 * The fragment uses a LinearLayoutManager for the RecyclerView, which lays out the sticker items
 * in a linear fashion. The StickersAdapter is responsible for binding the data from the stickerList
 * to the views defined in the RecyclerView's layout.
 *
 * Additional functionality for updating the list of stickers dynamically as new stickers are added
 * or existing stickers are used should be implemented to keep the display current.
 *
 * On selection of this page the data will be refreshed to maintain the most up to date data from
 * Firebase.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class StickersFragment extends Fragment {

    private RecyclerView recyclerView;
    private StickersAdapter adapter;
    private List<Sticker> stickerList;

    /**
     * Required empty public constructor for initializing the fragment.
     */
    public StickersFragment() {
        // Required empty public constructor
    }

    /**
     * Inflates the fragment's layout, sets up the RecyclerView, and initializes the sticker
     * data list. The RecyclerView is configured with a LinearLayoutManager and an instance
     * of StickersAdapter to manage and display the list of Sticker objects.
     *
     * @param inflater LayoutInflater object to inflate views in the fragment.
     * @param container Parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from
     *                           a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stickers, container, false);

        recyclerView = view.findViewById(R.id.stickersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Populate this list with our sticker data
        stickerList = new ArrayList<>();

        adapter = new StickersAdapter(stickerList);
        recyclerView.setAdapter(adapter);

        FirebaseApi.loadUserStickers(new FirebaseApi.StickerDataCallback() {
            @Override
            public void onStickersLoaded(List<Sticker> updatedStickers) {
                stickerList.clear();
                stickerList.addAll(updatedStickers);
                adapter.notifyDataSetChanged(); // Now the data is fully synchronized
            }
        });

        return view;
    }

    /**
     * Initiates the process to reload the user's sticker collection from the database and refreshes the fragment's
     * UI to reflect any changes. This method makes a call to the Firebase API to fetch the most current set of stickers
     * associated with the user. Upon successful retrieval of the updated sticker data, it clears the existing list of
     * stickers and repopulates it with the newly fetched data. Finally, it notifies the adapter of the changes to refresh
     * the display, ensuring the UI shows the latest information.
     *
     * This is essential for keeping the user's sticker collection up-to-date, particularly after new stickers have been
     * added or existing ones have been used, and it enhances the user experience by providing immediate feedback on any
     * changes.
     */
    public void refreshContent() {
        // Code to reload the stickers and update the UI
        FirebaseApi.loadUserStickers(new FirebaseApi.StickerDataCallback() {
            @Override
            public void onStickersLoaded(List<Sticker> updatedStickers) {
                stickerList.clear();
                stickerList.addAll(updatedStickers);
                adapter.notifyDataSetChanged(); // Now the data is fully synchronized
            }
        });
    }
}
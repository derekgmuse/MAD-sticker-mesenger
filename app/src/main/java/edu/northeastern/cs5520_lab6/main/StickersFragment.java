package edu.northeastern.cs5520_lab6.main;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;

import edu.northeastern.cs5520_lab6.R;
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

        // TODO: Populate this list with our sticker data
        stickerList = new ArrayList<>();
        adapter = new StickersAdapter(stickerList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // TODO: Add methods to update stickerList and refresh the adapter as needed
}
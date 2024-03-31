package edu.northeastern.cs5520_lab6.main;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.api.FirebaseApi;
import edu.northeastern.cs5520_lab6.contacts.GenericAdapterNotifier;
import edu.northeastern.cs5520_lab6.stickers.Cost;
import edu.northeastern.cs5520_lab6.stickers.Sticker;

/**
 * Displays a list of individual costs associated with stickers in a RecyclerView and shows the
 * total cost across all stickers at the top. Utilizes a {@link CostsAdapter} to manage and present
 * the sticker costs data.
 *
 * This fragment is intended for use in scenarios where an itemized breakdown of costs is necessary,
 * providing the user with clear visibility into specific expenses. The total cost is dynamically
 * calculated based on the individual costs displayed in the list.
 *
 * @author Tony Wilson
 * @version 2.0
 */
public class CostsFragment extends Fragment {

    private RecyclerView recyclerView;
    private CostsAdapter adapter;
    private List<Cost> costList;
    private List<Sticker> stickerList;
    private TextView totalCostTextView;

    /**
     * Required empty public constructor for fragment initialization.
     */
    public CostsFragment() {
        // Required empty public constructor
    }

    /**
     * Inflates the fragment layout, initializes the RecyclerView with a LinearLayoutManager and
     * a CostsAdapter, and sets up the TextView for displaying the total cost.
     *
     * @param inflater           LayoutInflater object to inflate views in the fragment.
     * @param container          Parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here.
     * @return The View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_costs, container, false);

        totalCostTextView = view.findViewById(R.id.totalCostTextView);
        recyclerView = view.findViewById(R.id.costsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize lists
        stickerList = new ArrayList<>();
        costList = new ArrayList<>();

        // Initialize adapter
        adapter = new CostsAdapter(costList);
        recyclerView.setAdapter(adapter);

        // Populate this list with our sticker data
        FirebaseApi.loadUserStickers(new FirebaseApi.StickerDataCallback() {
            @Override
            public void onStickersLoaded(List<Sticker> updatedStickers) {
                stickerList.clear();
                stickerList.addAll(updatedStickers);
                buildCost();
                updateTotalCost();
                if(adapter != null) {
                    adapter.notifyDataSetChanged(); // Now the data is fully synchronized
                }
            }
        });

        return view;
    }

    /**
     * Iterates over the list of stickers and creates a corresponding {@link Cost} object for each
     * one. This method is used to generate a list of costs associated with the use of stickers,
     * based on their usage count and predefined cost per use. After building the cost list,
     * it notifies the adapter to refresh the display, ensuring the UI reflects the most current
     * sticker cost data.
     */
    private void buildCost() {
        for(Sticker sticker : stickerList) {
            Cost cost = new Cost(sticker);
            costList.add(cost);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Calculates the total cost from the list of costs and updates the TextView to display
     * this total. Should be called whenever the costs data changes to ensure the displayed
     * total remains accurate.
     */
    private void updateTotalCost() {
        double totalCost = 0.0;
        for(Cost cost : costList) {
            totalCost += cost.getTotalCost();
        }
        // This method should be called whenever the costs data changes.
        totalCostTextView.setText(String.format("Total Cost: $%.2f", totalCost));
    }
}
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
import edu.northeastern.cs5520_lab6.stickers.Cost;

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
 * @version 1.0
 */
public class CostsFragment extends Fragment {

    private RecyclerView recyclerView;
    private CostsAdapter adapter;
    private List<Cost> costList;
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

        // TODO: Populate this list with our cost data
        costList = new ArrayList<>();
        adapter = new CostsAdapter(costList);
        recyclerView.setAdapter(adapter);

        // TODO: Calculate total cost and display it
        // TODO: Initialize RecyclerView adapter for costs and set it to the costsRecyclerView

        return view;
    }

    /**
     * Calculates the total cost from the list of costs and updates the TextView to display
     * this total. Should be called whenever the costs data changes to ensure the displayed
     * total remains accurate.
     */
    private void updateTotalCost() {
        // Placeholder for actual calculation
        double totalCost = 0.0;
        // This method should be called whenever the costs data changes.
        totalCostTextView.setText(String.format("Total Cost: $%.2f", totalCost));
    }
}
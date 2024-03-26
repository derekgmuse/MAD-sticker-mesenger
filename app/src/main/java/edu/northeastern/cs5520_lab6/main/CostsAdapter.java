package edu.northeastern.cs5520_lab6.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.stickers.Cost;

/**
 * Manages the display of sticker costs in a list format within a RecyclerView. Utilizes a
 * {@link CostsAdapter} to present the data related to each cost, including the sticker image,
 * the count of how many times each sticker was used, and the total cost associated with each sticker.
 *
 * This adapter is intended to be used in a context where showing a detailed breakdown of costs
 * to the user is necessary, providing both a visual and a numerical representation of expenses.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class CostsAdapter extends RecyclerView.Adapter<CostsAdapter.CostViewHolder> {
    private List<Cost> costList;

    /**
     * Constructs a CostsAdapter with a specified list of costs.
     *
     * @param costList The list of Cost objects to be displayed by the adapter.
     */
    public CostsAdapter(List<Cost> costList) {
        this.costList = costList;
    }

    /**
     * Inflates the layout for individual cost items and returns a new ViewHolder.
     *
     * @param parent The parent ViewGroup into which the new view will be added.
     * @param viewType The view type of the new View.
     * @return A new instance of CostViewHolder.
     */
    @NonNull
    @Override
    public CostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cost, parent, false);
        return new CostViewHolder(view);
    }

    /**
     * Binds data to the ViewHolder, populating the cost item views with appropriate information.
     *
     * @param holder The ViewHolder which should be updated to represent the contents
     *               of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CostViewHolder holder, int position) {
        Cost cost = costList.get(position);
        holder.countTextView.setText(String.valueOf(cost.getCount()));
        holder.totalCostTextView.setText(String.format("$%.2f", cost.getTotalCost()));
        // Load sticker image using stickerId
    }

    /**
     * Returns the total number of items in the dataset held by the adapter.
     *
     * @return The size of the costList.
     */
    @Override
    public int getItemCount() {
        return costList.size();
    }

    /**
     * ViewHolder class for cost items. Provides a reference to each view in the individual cost item.
     */
    public class CostViewHolder extends RecyclerView.ViewHolder {
        public ImageView stickerImageView;
        public TextView countTextView;
        public TextView totalCostTextView;

        /**
         * Initializes a new instance of the ViewHolder for the cost items.
         *
         * @param itemView The root view of the item layout.
         */
        public CostViewHolder(View itemView) {
            super(itemView);
            stickerImageView = itemView.findViewById(R.id.stickerImageView);
            countTextView = itemView.findViewById(R.id.countTextView);
            totalCostTextView = itemView.findViewById(R.id.totalCostTextView);
        }
    }
}
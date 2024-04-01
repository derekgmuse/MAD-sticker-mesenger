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
import edu.northeastern.cs5520_lab6.stickers.Sticker;
import edu.northeastern.cs5520_lab6.stickers.StickerEnum;

/**
 * Adapter for displaying a list of stickers in a RecyclerView. Each sticker item shows an image
 * of the sticker and a count of how many times it has been used. This adapter supports dynamic
 * updates to the sticker list, allowing the display to refresh as new stickers are added or counts
 * are updated.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public class StickersAdapter extends RecyclerView.Adapter<StickersAdapter.StickerViewHolder> {
    private List<Sticker> stickerList;

    /**
     * Constructs the StickersAdapter with a specified list of Sticker objects.
     *
     * @param stickerList The initial list of stickers to display.
     */
    public StickersAdapter(List<Sticker> stickerList) { this.stickerList = stickerList; }

    /**
     * Creates new views (invoked by the layout manager) for each item in the stickerList.
     *
     * @param parent The parent ViewGroup into which the new view will be added.
     * @param viewType The view type of the new View, if different view types are needed.
     * @return A new StickerViewHolder that holds the View for each sticker item.
     */
    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticker, parent, false);
        return new StickerViewHolder(view);
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager) for each sticker item.
     *
     * @param holder The StickerViewHolder that should be updated to represent the contents
     *               of the sticker item at the given position in the data set.
     * @param position The position of the sticker item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        Sticker sticker = stickerList.get(position);
        holder.bind(sticker);
        // Load sticker image using stickerId
    }

    /**
     * Returns the total number of stickers in the list (invoked by the layout manager).
     *
     * @return The size of the stickerList.
     */
    @Override
    public int getItemCount() {
        return stickerList.size();
    }

    /**
     * Provides a reference to the views for each sticker item. Each item consists of a sticker image
     * and a text view displaying the count of how many times the sticker has been used.
     */
    public class StickerViewHolder extends RecyclerView.ViewHolder {
        public ImageView stickerImageView;
        public TextView countTextView;

        /**
         * Initializes a new instance of the ViewHolder for the sticker items.
         *
         * @param itemView The root view of the item layout.
         */
        public StickerViewHolder(View itemView) {
            super(itemView);
            stickerImageView = itemView.findViewById(R.id.stickerImageView);
            countTextView = itemView.findViewById(R.id.countTextView);
        }

        /**
         * Binds a sticker object to the ViewHolder, updating the UI components to display the sticker's
         * usage count and image. If the sticker's image resource ID can be found based on its ID, that
         * image is displayed. Otherwise, a default image is used to indicate an unknown sticker.
         *
         * @param sticker The sticker object containing the information to be displayed.
         */
        void bind(Sticker sticker) {
            countTextView.setText(String.valueOf(sticker.getCount()));
            int stickerResId = StickerEnum.getResourceIdById(sticker.getId());
            if (stickerResId != -1) {
                stickerImageView.setImageResource(stickerResId);
            } else {
                // Handle unknown sticker
                stickerImageView.setImageResource(R.drawable.default_sticker);
            }
        }
    }
}
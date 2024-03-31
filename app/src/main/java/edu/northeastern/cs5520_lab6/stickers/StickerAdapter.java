package edu.northeastern.cs5520_lab6.stickers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

import edu.northeastern.cs5520_lab6.R;

/**
 * An adapter for displaying stickers in a RecyclerView. Each item in the RecyclerView is represented
 * by a Sticker object, displaying the image associated with the sticker. This adapter is designed
 * to handle user interactions with stickers, triggering a specified action when a sticker is clicked.
 *
 * @version 1.0
 * @author Tony Wilson
 */
public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.StickerViewHolder> {

    private List<Sticker> stickers;
    private Consumer<Sticker> onStickerClickedListener;

    /**
     * Constructor for StickerAdapter. Initializes the adapter with a list of stickers and a click listener.
     *
     * @param stickers A list of {@link Sticker} objects to be displayed in the RecyclerView.
     * @param onStickerClickedListener A {@link Consumer} functional interface that handles sticker click events.
     */
    public StickerAdapter(List<Sticker> stickers, Consumer<Sticker> onStickerClickedListener) {
        this.stickers = stickers;
        this.onStickerClickedListener = onStickerClickedListener;
    }

    /**
     * Called when RecyclerView needs a new {@link StickerViewHolder} of the given type to represent an item.
     * This is where the layout is inflated and a new ViewHolder is returned.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View. This allows multiple types of views in the same RecyclerView.
     * @return A new instance of {@link StickerViewHolder} that holds the View for the sticker.
     */
    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon_sticker, parent, false);
        return new StickerViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method updates the contents
     * of the {@link StickerViewHolder#itemView} to reflect the sticker at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the sticker
     *               at the given position in the data set.
     * @param position The position of the sticker within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        Sticker sticker = stickers.get(position);
        // Bind sticker data to the view
        holder.bind(sticker);
        holder.itemView.setOnClickListener(v -> onStickerClickedListener.accept(sticker));
    }

    /**
     * Returns the total number of stickers available in the adapter.
     * This count reflects the number of stickers that will be displayed in the RecyclerView.
     *
     * @return The size of the stickers list.
     */
    @Override
    public int getItemCount() {
        return stickers.size();
    }

    /**
     * ViewHolder class for stickers. Holds a reference to an ImageView that displays the sticker.
     */
    static class StickerViewHolder extends RecyclerView.ViewHolder {
        // Your ViewHolder should contain a reference to the ImageView
        ImageView stickerImageView;

        /**
         * Constructor for the ViewHolder, where the ImageView that displays the sticker
         * is initialized.
         *
         * @param itemView The root view of the RecyclerView item.
         */
        StickerViewHolder(View itemView) {
            super(itemView);
            stickerImageView = itemView.findViewById(R.id.stickerImageView);
        }

        /**
         * Binds the provided Sticker object to this ViewHolder, setting the ImageView
         * to display the sticker's image based on its ID. If the sticker ID does not match
         * any known sticker, a default image is used as a fallback.
         *
         * @param sticker The Sticker object to bind to this ViewHolder.
         */
        void bind(Sticker sticker) {
            // Use the enum to map sticker ID to drawable resource
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

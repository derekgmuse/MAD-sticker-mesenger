package edu.northeastern.cs5520_lab6.main;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.api.FirebaseApi;
import edu.northeastern.cs5520_lab6.messages.Chat;
import edu.northeastern.cs5520_lab6.messages.MessageActivity;

/**
 * Fragment for displaying a list of chat conversations in a RecyclerView.
 * It manages chat display and interaction, allowing users to see recent conversations
 * and navigate to individual chat messages. Initializes with an empty list that gets populated
 * through Firebase database interactions.
 *
 * Utilizes {@link ChatsAdapter} for RecyclerView management and {@link FirebaseApi} for data loading and real-time updates.
 *
 * @version 2.0
 * @author Tony Wilson
 */
public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatsAdapter adapter;
    private List<Chat> chatList;
    public boolean onStartUp = false;
    private int count = 0;

    /**
     * Required empty public constructor for fragment initialization.
     */
    public ChatsFragment() {
        // Required empty public constructor
    }

    /**
     * This method inflates the layout for the ChatsFragment and initializes the RecyclerView component
     * that displays the list of chat conversations. It sets up the adapter for the RecyclerView and
     * triggers the loading of chat data from Firebase. Additionally, it subscribes to updates for real-time
     * notifications on new messages. This method ensures that the user interface is dynamic and responsive
     * to new chat data as it becomes available.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, the fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null if the fragment is not providing a UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        // Initialize components of activity
        chatList = new ArrayList<>();
        initializeRecyclerView(view);

        FirebaseApi.loadChatData(chatList, adapter);

        FirebaseApi.attachChatListener(new FirebaseApi.DataLoadListener() {
            @Override
            public void onDataLoaded(Chat chat) {
                // Actions to take after data is loaded, e.g., hide a loading spinner
                if(isAdded()) {
                    NotificationManager notificationManager = createNotificationChannel();
                    Notification notification = buildNotificationForChat(chat); // Implement this method based on your needs
                    // Ensure each notification has a unique ID
                    notifyNow(notificationManager, notification);
                }
            }
        });

        return view;
    }

    /**
     * Determines whether to display a notification immediately based on specific criteria, specifically
     * avoiding notification dispatch during initial data load. This method ensures that users receive
     * notifications only for new messages that arrive after the app has completed its initial setup process,
     * thereby enhancing the user experience by preventing redundant alerts.
     *
     * @param notificationManager An instance of NotificationManager responsible for issuing notifications.
     * @param notification The constructed Notification object ready to be shown to the user.
     */
    void notifyNow(NotificationManager notificationManager, Notification notification) {
        Log.d("TAG", "notifyNow: count me ");
        if(onStartUp && count>0) {
            notificationManager.notify(123456, notification);
        } else {
            onStartUp = true;
            count++;
        }
    }

    /**
     * Configures the RecyclerView used for displaying the list of chat conversations. This includes
     * setting a LinearLayoutManager to handle the layout of individual chat items and attaching
     * the ChatsAdapter to manage the binding of chat data to the ViewHolders. Additionally, an
     * item click listener is set up to handle navigation to detailed chat views when a user selects
     * a chat from the list.
     *
     * @param view The root View of the fragment, containing the RecyclerView to be initialized.
     */
    private void initializeRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.chatsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatsAdapter(chatList, new ChatsAdapter.ChatItemClickListener() {
            @Override
            public void onChatClick(String chatId) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("chatId", chatId);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * Creates a notification channel to be used for chat message notifications. This setup is
     * required for Android Oreo (API level 26) and above to ensure notifications are delivered
     * appropriately. The method configures the channel with basic settings including its name,
     * importance level, and a brief description.
     *
     * @return The NotificationManager instance that has been configured with the new notification channel.
     */
    private NotificationManager createNotificationChannel() {
        // Setup channel fields
        CharSequence name = "Message Notifications";
        String description = "Notification channel for chat messages";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        // create new notification channel
        NotificationChannel channel = new NotificationChannel("123456", name, importance);
        channel.setDescription(description);

        // Create new Notification Manager
        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

        // Return for notify method
        return notificationManager;
    }

    /**
     * Constructs a detailed notification for a new message in a specific chat. The notification
     * includes a title derived from the chat's name, a prompt to view the message, and an action
     * that opens the chat in the app when the notification is tapped. This method is crucial for
     * alerting users about new messages in a manner that is accessible and actionable directly
     * from the notification itself.
     *
     * @param chat The Chat object representing the conversation for which the notification is built.
     * @return A fully configured Notification object, ready to be issued by the NotificationManager.
     */
    private Notification buildNotificationForChat(Chat chat) {
        // Setup intent when user clicks on notification
        Intent notificationIntent = new Intent(requireContext(), MessageActivity.class);
        notificationIntent.putExtra("chatId", chat.getId()); // Pass chat ID to handle in MainActivity
        PendingIntent pendingIntent = PendingIntent.getActivity(requireContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        String notificationTitle = "New message from " + chat.getName(); // Customize title with chat name
        String notificationContent = "Tap to view"; // Customize content as needed

        // Ensure a notification channel is set up with this id
        String channelId = "123456"; // The ID of the notification channel

        // build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), channelId)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setSmallIcon(R.drawable.ic_sticker_icon)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // Remove notification once clicked

        return builder.build();
    }
}
package edu.northeastern.cs5520_lab6.api;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import edu.northeastern.cs5520_lab6.R;
import edu.northeastern.cs5520_lab6.main.MainActivity;
import edu.northeastern.cs5520_lab6.messages.Message;

public class MessageListenerService extends Service {

    private DatabaseReference messagesRef;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        // Initialize your Firebase reference here
        String chatId = "some_chat_id"; // This should be dynamically determined based on your app's logic
        messagesRef = FirebaseDatabase.getInstance().getReference("messages").child(chatId);

        attachMessageEventListener();
    }

    private void createNotificationChannel() {
        CharSequence name = "Message Notifications";
        String description = "Includes all the chat messages notifications";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("CHAT_CHANNEL_ID", name, importance);
        channel.setDescription(description);
        // Register the channel with the system
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void attachMessageEventListener() {
        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Message newMessage = dataSnapshot.getValue(Message.class);
                // A new message was added, process it, e.g., show a notification
                if (newMessage != null) {
                    // Build and show the notification
                    Notification notification = buildForegroundNotification(newMessage.getText()); // Adjust to include sticker logic if necessary
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(new Random().nextInt(), notification); // Use random ID for each notification or manage IDs yourself
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Assuming you are passing the message content via the intent
        String message = intent.getStringExtra("message");
        Notification notification = buildForegroundNotification(message);
        startForeground(1, notification);

        // Your service is starting. Return START_STICKY if you want the service to be restarted if it gets terminated.
        return START_STICKY;
    }

    private Notification buildForegroundNotification(String message) {
        Intent notificationIntent = new Intent(this, MainActivity.class); // Intent to open your app's main activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, "CHAT_CHANNEL_ID")
                .setContentTitle("New Message")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_sticker_icon) // Ensure you have a drawable resource for the small icon
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service. If you don't need to communicate with the service, return null.
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (messagesRef != null) {
            // Don't forget to remove the event listener when the service is destroyed
            messagesRef.removeEventListener((ChildEventListener) this);
        }
    }
}


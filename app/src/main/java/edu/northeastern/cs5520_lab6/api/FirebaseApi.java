package edu.northeastern.cs5520_lab6.api;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.northeastern.cs5520_lab6.LogInActivity;
import edu.northeastern.cs5520_lab6.contacts.Contacts;
import edu.northeastern.cs5520_lab6.contacts.GenericAdapterNotifier;
import edu.northeastern.cs5520_lab6.contacts.NewContactActivity;
import edu.northeastern.cs5520_lab6.contacts.User;
import edu.northeastern.cs5520_lab6.main.ChatsAdapter;
import edu.northeastern.cs5520_lab6.messages.Chat;
import edu.northeastern.cs5520_lab6.messages.Message;
import edu.northeastern.cs5520_lab6.messages.MessageActivity;
import edu.northeastern.cs5520_lab6.stickers.Sticker;

/**
 * Manages interactions with the Firebase Realtime Database for user, chat, message, and sticker data.
 * This utility class encapsulates the logic for adding and retrieving users, managing user contacts,
 * conducting user searches, handling chat sessions, and sending/retrieving messages. It has been
 * extended to support sticker functionality, allowing users to send stickers as messages and track
 * sticker usage within chats.
 *
 * Additionally, this class implements methods to adjust chat names dynamically based on the current
 * user's perspective, enhancing the user experience by displaying only the relevant participant names
 * in chat overviews.
 *
 * @version 2.0
 * @author Tony Wilson
 */
public class FirebaseApi {
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public FirebaseApi() {
        //this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Adds a new user to the Firebase Realtime Database under the "users" node. This method
     * should be called when a new user signs up.
     *
     * @param context The context from which this method is called, used for displaying Toast messages.
     * @param user    The user object to be added to the database.
     */
    public static void addUserToDatabase(Context context, User user) {
        DatabaseReference usersRef = databaseReference.child("users").child(user.getUserId());
        usersRef.setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Handle success, such as navigating to the main app UI or showing a success message
                Toast.makeText(context, "Congratulations! you have successfully signed up!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LogInActivity.class);
                context.startActivity(intent);
            } else {
                // Handle failure, such as showing an error message to the user
            }
        });
    }

    /**
     * Adds a new contact to the current user's list of contacts in the Firebase database. If the
     * contact is successfully added, a callback method is invoked.
     *
     * @param context          The context from which this method is called.
     * @param currentUserId    The ID of the current user.
     * @param newContactUserId The ID of the new contact to be added.
     * @param callback         Callback interface for post-operation actions.
     */
    public static void addContactToUser(Context context, String currentUserId, String newContactUserId, ContactAddedCallback callback) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    Contacts contacts = user.getContacts();
                    if (contacts == null) {
                        contacts = new Contacts(new ArrayList<>());
                    }
                    // Add the new contact's userID if not already present
                    if (!contacts.getUserIDs().contains(newContactUserId)) {
                        contacts.addUserID(newContactUserId);
                        user.setContacts(contacts);
                        // Write the updated User object back to Firebase
                        userRef.setValue(user).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Congratulations! Contact added successfully!", Toast.LENGTH_SHORT).show();
                                if (callback != null) callback.onContactAdded();
                            } else {
                                Toast.makeText(context, "Unfortunately, contact not added!", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseApi", "Failed to read contacts", databaseError.toException());
            }
        });
    }

    /**
     * Searches for users in the Firebase database by username. Results are returned via a callback.
     *
     * @param username The username to search for.
     * @param callback Callback interface to handle the search results.
     */
    public static void searchUsersByUsername(String username, NewContactActivity.UserSearchCallback callback) {
        DatabaseReference usersRef = databaseReference.child("users");
        usersRef.orderByChild("username").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<User> userList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if (user != null) {
                                userList.add(user);
                            }
                        }
                        callback.onSearchResults(userList); // Notify callback with the result
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors
                    }
                });
    }

    /**
     * Loads chat data for the current user and notifies the provided adapter of any changes. This
     * method should be used to populate the UI with chat information.
     *
     * @param chatList The list to store the chat data.
     * @param adapter  The adapter to be notified of data changes.
     */
    public static void loadChatData(List<Chat> chatList, ChatsAdapter adapter) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference chatsRef = database.getReference("chats");
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        chatsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatList.clear(); // Clear existing data first
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat != null && chat.getUserIds().contains(currentUserId)) {
                        chat.setId(snapshot.getKey());
                        chatList.add(chat);
                    }
                }
                Log.d("loadChatData", "Number of chats loaded: " + chatList.size());
                // Notify the adapter of data changes
                adapter.notifyDataSetChanged();
                // After loading chats, adjust chat names
                adjustChatNamesForCurrentUser(chatList, adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
                Log.w("loadChatData", "loadChat:onCancelled", databaseError.toException());
            }
        });
    }

    /**
     * Adjusts the names of chats in the provided list to exclude the current user's name,
     * ensuring that chat names reflect the other participants more accurately. This method
     * queries the database for user information and updates each chat's name accordingly.
     *
     * It is designed to enhance the user interface by displaying chat names that are more
     * meaningful to the current user, omitting their own name from the list of chat participants.
     * The method handles the asynchronous nature of database operations and updates the chat
     * list adapter once all chat names have been adjusted.
     *
     * Note: This method may invoke {@link ChatsAdapter#notifyDataSetChanged()} multiple times,
     * which could impact performance. Consider optimizing if necessary.
     *
     * @param chatList The list of {@link Chat} objects to be adjusted.
     * @param adapter  The adapter used to display the chat list. This adapter is notified to
     *                 refresh the view once chat names have been adjusted.
     */
    private static void adjustChatNamesForCurrentUser(List<Chat> chatList, ChatsAdapter adapter) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");

        // Fetch the current user's name
        userRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                if (currentUser != null) {
                    // Adjust each chat's name
                    for (Chat chat : chatList) {
                        List<String> otherUserIds = new ArrayList<>(chat.getUserIds());
                        otherUserIds.remove(currentUserId); // Remove current user's ID
                        StringBuilder otherUserNames = new StringBuilder();

                        for (String userId : otherUserIds) {
                            // Fetch each user's name
                            userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot userSnapshot) {
                                    User user = userSnapshot.getValue(User.class);
                                    if (user != null) {
                                        if (otherUserNames.length() > 0) otherUserNames.append(", ");
                                        otherUserNames.append(user.getName());
                                        // Update chat name if last user's name fetched
                                        if (otherUserNames.toString().split(", ").length == otherUserIds.size()) {
                                            chat.setName(otherUserNames.toString());
                                            adapter.notifyDataSetChanged(); // This might be called multiple times, we should consider optimization
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {}
                            });
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


    /**
     * Loads contact data for the current user and notifies the provided adapter of any changes. This
     * method should be used to populate the UI with contact information.
     *
     * @param contacts The list to store the contact data.
     * @param adapter  The adapter to be notified of data changes.
     */
    public static void loadContactData(List<User> contacts, GenericAdapterNotifier adapter) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users");

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Correct path based on our structure would be "users/{userId}/contacts/userIDs"
        DatabaseReference contactsRef = userRef.child(currentUserId).child("contacts").child("userIDs");

        // Clear the contacts list before loading new data
        contacts.clear();

        contactsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Assuming userIDs is a list of strings
                List<String> userIDs = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userId = snapshot.getValue(String.class);
                    if (userId != null) {
                        userIDs.add(userId);
                    }
                }
                // Now, for each userId, fetch the user details
                for (String userId : userIDs) {
                    userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            User user = userSnapshot.getValue(User.class);
                            if (user != null) {
                                contacts.add(user); // Rebuild contacts with detailed info
                                adapter.notifyAdapterDataSetChanged(); // Update RecyclerView
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle possible errors
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    /**
     * Loads messages for a specific chat and notifies the provided adapter of any changes. This
     * method should be used to populate the UI with messages from a chat.
     *
     * @param chatId   The ID of the chat for which messages are loaded.
     * @param messages The list to store the message data.
     * @param adapter  The adapter to be notified of data changes.
     */
    public static void loadMessagesForChat(String chatId, List<Message> messages, GenericAdapterNotifier adapter) {
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages").child(chatId);

        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear(); // Clear existing messages before loading new ones
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (message != null) {
                        messages.add(message);
                    }
                }
                adapter.notifyAdapterDataSetChanged(); // Notify the adapter of data changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    /**
     * Loads the current user's stickers from the database, updating a local list. This method
     * facilitates the display and management of the user's sticker collection within the app,
     * ensuring the UI reflects the most current data.
     *
     * @param stickersListToUpdate The list to be populated with the user's stickers.
     * @param callback             Callback to notify when the list has been updated.
     */
    public static void loadUserStickers(StickerDataCallback callback) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userStickersRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserId).child("stickers");

        userStickersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Sticker> stickers = new ArrayList<>();
                for (DataSnapshot stickerSnapshot : dataSnapshot.getChildren()) {
                    Sticker sticker = stickerSnapshot.getValue(Sticker.class);
                    if (sticker != null) {
                        stickers.add(sticker);
                    }
                }
                if (callback != null) {
                    callback.onStickersLoaded(stickers);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FirebaseApi", "loadUserStickers:onCancelled", databaseError.toException());
            }
        });
    }

    /**
     * Finds an existing chat with the given participants or creates a new one if it doesn't exist.
     * Navigates to the MessageActivity with the chat ID once the operation is complete.
     *
     * @param context        The context from which this method is called.
     * @param participantIds A list of participant IDs for the chat.
     * @param initialMessage The initial message to be sent in the chat.
     */
    public static void findOrCreateChatWithUsers(Context context, List<String> participantIds, String initialMessage) {
        DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("chats");
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (!participantIds.contains(currentUserId)) {
            participantIds.add(currentUserId);
        }
        // Sort to maintain consistency in ID generation
        Collections.sort(participantIds);

        // Generate a unique identifier based on sorted participant IDs
        String chatId = TextUtils.join("_", participantIds);

        chatsRef.child(chatId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // Chat doesn't exist, create a new chat
                    final String[] chatName = {""};
                    Map<String, Boolean> userIdsMap = new HashMap<>();
                    for (String userId : participantIds) {
                        userIdsMap.put(userId, true);
                        // Do not Skip the current user when setting the chat name
                        if(!userId.equals(currentUserId)) {
                            usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user = snapshot.getValue(User.class);
                                    if (user != null) {
                                        chatName[0] = chatName[0].isEmpty() ? user.getName() : chatName[0] + ", " + user.getName();
                                        // If this is the last user to fetch, create the chat
                                        if (chatName[0].split(", ").length == (participantIds.size() - 1)) {
                                            Chat newChat = new Chat(chatId, new ArrayList<>(userIdsMap.keySet()), chatName[0], initialMessage, getCurrentTimestamp(), "");
                                            chatsRef.child(chatId).setValue(newChat).addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    // Navigate to MessageActivity with the new or found chatId
                                                    Intent intent = new Intent(context, MessageActivity.class);
                                                    intent.putExtra("chatId", chatId);
                                                    context.startActivity(intent);
                                                }
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Handle possible errors
                                }
                            });
                        }
                    }
                } else {
                    // Chat exists, navigate to MessageActivity with chatId
                    Intent intent = new Intent(context, MessageActivity.class);
                    intent.putExtra("chatId", chatId);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    /**
     * Generates a string representation of the current time using the "HH:mm" format (24-hour format).
     * This method is useful for creating timestamps for chat messages or other time-sensitive data
     * in the application.
     *
     * @return A string representing the current time in the "HH:mm" format.
     */
    private static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMM HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    /**
     * Sends a message or sticker within a specified chat. If the content type is a text message, it
     * is sent directly. For stickers, this method handles the addition or update of the sticker count
     * in the user's sticker collection before sending. It ensures that the chat's last message and
     * timestamp are updated accordingly. In the case of stickers, a unique format is applied to the
     * last message for easy identification.
     *
     * @param chatId       The ID of the chat where the message is to be sent.
     * @param messageText  The content of the message, or the sticker ID if a sticker is being sent.
     * @param stickerCount The usage count of the sticker being sent, relevant only for sticker messages.
     * @param type         Indicates whether the message is a "text" or "sticker".
     * @param callback     Callback to handle the result of the send operation.
     */
    public static void sendMessage(String chatId, String messageText, int stickerCount, String type, MessageSendCallback callback) {
        DatabaseReference messagesRef = databaseReference.child("messages").child(chatId);
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Ensure proper authentication handling

        DatabaseReference chatsRef = databaseReference.child("chats").child(chatId);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserId);

        String messageId = messagesRef.push().getKey();
        if (messageId != null) {
            Message newMessage;
            // handle texts and stickers differently
            if(type.equals("text")) {
                newMessage = new Message(chatId, messageId, messageText, currentUserId, getCurrentTimestamp(), type, "-1");

                messagesRef.child(messageId).setValue(newMessage).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update the chat with the new last message and timestamp
                        Map<String, Object> chatUpdates = new HashMap<>();
                        chatUpdates.put("lastMessage", messageText);
                        chatUpdates.put("timestamp", getCurrentTimestamp());

                        // Perform the update on the chat reference
                        chatsRef.updateChildren(chatUpdates).addOnCompleteListener(updateTask -> {
                            if (updateTask.isSuccessful()) {
                                // Notify caller of success
                                if (callback != null) callback.onSuccess(newMessage);
                            } else {
                                // Handle update failure
                                if (callback != null) callback.onFailure();
                            }
                        });
                    } else {
                        // Notify caller of message send failure
                        if (callback != null) callback.onFailure();
                    }
                });
            } else {
                newMessage = new Message(chatId, messageId, messageText, currentUserId, getCurrentTimestamp(), type, messageText);

                // Perform a transaction to update the user's stickers
                userRef.runTransaction(new Transaction.Handler() {
                   @NonNull
                   @Override
                   public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                       User user = mutableData.getValue(User.class);
                       if (user == null) {
                           return Transaction.success(mutableData); // User does not exist, abort the transaction
                       }

                       // addSticker modifies the user's stickers list correctly
                       Sticker stickerToAdd = new Sticker(messageText, stickerCount);
                       user.addSticker(stickerToAdd); // This method should handle the logic of updating counts

                       // Set the updated user object back into the transaction
                       mutableData.setValue(user);
                       return Transaction.success(mutableData);
                   }
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot dataSnapshot) {
                        // Transaction completed
                        if (committed) {
                            // Sticker count updated, now send the sticker message
                            messagesRef.child(messageId).setValue(newMessage).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Sticker count updated, now send the sticker message with a special format
                                    String stickerMessageFormat = "%sticker%:" + messageText; // Assuming messageText is the sticker ID

                                    // Update the chat with the new last message and timestamp
                                    Map<String, Object> chatUpdates = new HashMap<>();
                                    chatUpdates.put("lastMessage", stickerMessageFormat);
                                    chatUpdates.put("timestamp", getCurrentTimestamp());

                                    // Perform the update on the chat reference
                                    chatsRef.updateChildren(chatUpdates).addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            // Notify caller of success
                                            if (callback != null) callback.onSuccess(newMessage);
                                        } else {
                                            // Handle update failure
                                            if (callback != null) callback.onFailure();
                                        }
                                    });
                                } else {
                                    // Notify caller of message send failure
                                    if (callback != null) callback.onFailure();
                                }
                            });
                        } else {
                            // Transaction failed to commit
                            if (callback != null) callback.onFailure();
                        }
                    }
                });
            }
        } else {
            // Handle the case where messageId could not be generated
            if (callback != null) callback.onFailure();
        }
    }

    /**
     * Callback interface for adding a new contact.
     */
    public interface ContactAddedCallback {
        void onContactAdded();
    }

    /**
     * Callback interface for sending a message.
     */
    public interface MessageSendCallback {
        void onSuccess(Message message);
        void onFailure();
    }

    public interface StickerDataCallback {
        void onStickersLoaded(List<Sticker> stickers);
    }
}
package edu.northeastern.cs5520_lab6.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.cs5520_lab6.LogInActivity;
import edu.northeastern.cs5520_lab6.SignUpActivity;
import edu.northeastern.cs5520_lab6.contacts.Contacts;
import edu.northeastern.cs5520_lab6.contacts.GenericAdapterNotifier;
import edu.northeastern.cs5520_lab6.contacts.NewContactActivity;
import edu.northeastern.cs5520_lab6.contacts.User;
import edu.northeastern.cs5520_lab6.main.ChatsAdapter;
import edu.northeastern.cs5520_lab6.messages.Chat;
import edu.northeastern.cs5520_lab6.messages.Message;
import edu.northeastern.cs5520_lab6.messages.MessageActivity;

public class FirebaseApi {
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public FirebaseApi() {
        //this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

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

    public static void loadChatData(List<Chat> chatList, ChatsAdapter adapter) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference chatsRef = database.getReference("chats");

        // Assume currentUserID is obtained from Firebase Authentication
        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        chatsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatList.clear(); // Clear existing data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat != null && chat.getUserIds().contains(currentUserID)) {
                        chat.setId(snapshot.getKey());
                        chatList.add(chat);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify the adapter of data changes
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

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

    public static void navigateToMessageActivityWithChatId(Context context, String selectedContactId) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("chats");

        // Generate a provisional chatId. Note: This simplistic approach may need refinement.
        String chatId = (currentUserId.compareTo(selectedContactId) > 0) ? currentUserId + "_" + selectedContactId : selectedContactId + "_" + currentUserId;

        chatsRef.child(chatId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // Fetch the selected contact's name to use in the new chat
                    usersRef.child(selectedContactId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User otherUser = snapshot.getValue(User.class);
                            if (otherUser != null) {
                                // Use other user's name as the chat name
                                List<String> userIds = Arrays.asList(currentUserId, selectedContactId);
                                Chat newChat = new Chat(chatId, userIds, otherUser.getName(), "", "", "");
                                chatsRef.child(chatId).setValue(newChat).addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        // Proceed to MessageActivity after the chat is successfully created
                                        Intent intent = new Intent(context, MessageActivity.class);
                                        intent.putExtra("chatId", chatId);
                                        context.startActivity(intent);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle errors, e.g., log or show error message
                        }
                    });
                } else {
                    // Chat exists, proceed to MessageActivity
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


    public static void navigateToMessageActivityWithChatId(Context context, List<String> selectedUserIds) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference chatsRef = database.getReference("chats");

        // Include the current user's ID in the list of participant IDs
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (!selectedUserIds.contains(currentUserId)) {
            selectedUserIds.add(currentUserId);
        }

        // Query chats where the selectedUserIds are exactly the participants
        // This is conceptual; the exact implementation will depend on your data structure
        // and may require custom indexing or more complex queries
        chatsRef.orderByChild("userIds").equalTo(selectedUserIds.toString()) // This exact match is conceptual
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String chatId = null;
                        if (dataSnapshot.exists()) {
                            // Chat exists, extract the chatId
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                chatId = childSnapshot.getKey();
                                break;
                            }
                        } else {
                            // No chat exists, create a new chat
                            chatId = chatsRef.push().getKey();
                            Chat newChat = new Chat(chatId, selectedUserIds, "", "", "", null);
                            chatsRef.child(chatId).setValue(newChat);
                        }

                        // Navigate to MessageActivity with chatId
                        Intent intent = new Intent(context, MessageActivity.class);
                        intent.putExtra("chatId", chatId);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors
                    }
                });
    }

    public static void sendMessage(String chatId, String messageText, long timestamp, MessageSendCallback callback) {
        DatabaseReference messagesRef = databaseReference.child("messages").child(chatId);
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Ensure proper authentication handling

        DatabaseReference chatsRef = databaseReference.child("chats").child(chatId);

        String messageId = messagesRef.push().getKey();
        if (messageId != null) {
            Message newMessage = new Message(chatId, messageId, messageText, currentUserId, timestamp);
            messagesRef.child(messageId).setValue(newMessage).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Update the chat with the new last message and timestamp
                    Map<String, Object> chatUpdates = new HashMap<>();
                    chatUpdates.put("lastMessage", messageText);
                    chatUpdates.put("timestamp", Long.toString(timestamp));

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
            // Handle the case where messageId could not be generated
            if (callback != null) callback.onFailure();
        }
    }

    public interface ContactAddedCallback {
        void onContactAdded();
    }

    public interface MessageSendCallback {
        void onSuccess(Message message);
        void onFailure();
    }

}

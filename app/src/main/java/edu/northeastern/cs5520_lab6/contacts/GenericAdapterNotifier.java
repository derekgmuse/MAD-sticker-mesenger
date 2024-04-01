package edu.northeastern.cs5520_lab6.contacts;

/**
 * Interface to provide a generic callback for notifying data set changes in adapters.
 * This interface abstracts the notification mechanism, allowing for different types of adapters
 * (such as RecyclerView.Adapter or any custom adapter) to implement a unified way of signaling
 * data changes. Implementing this interface in adapters enables them to inform their corresponding
 * UI components that the underlying data has been updated, prompting a UI refresh.
 *
 * This approach facilitates a loosely coupled design where the adapter does not need to know
 * about the specific implementation details of the UI components it is associated with, thereby
 * enhancing modularity and reusability of the adapter code.
 *
 * @author Tony Wilson
 * @version 1.0
 */
public interface GenericAdapterNotifier {
    /**
     * Notifies that the data set has been changed and any View reflecting the data set should refresh itself.
     */
    void notifyAdapterDataSetChanged();
}
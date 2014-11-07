package fr.insalyon.optimod.controllers.listeners.intents;

/**
 * Used to show error dialogs
 */
public interface ShowErrorIntentListener {
    /**
     * Shows an error
     *
     * @param title The error's title
     * @param content The error's content
     */
    public void onErrorIntent(String title, String content);
}

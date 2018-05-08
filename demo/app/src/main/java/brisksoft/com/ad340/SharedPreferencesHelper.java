package brisksoft.com.ad340;

import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesHelper {

    // Keys for saving values in SharedPreferences.
    static final String KEY_ENTRY = "text_entry";

    // The injected SharedPreferences implementation to use for persistence.
    private final SharedPreferences mSharedPreferences;

    /**
     * Constructor with dependency injection.
     *
     * @param sharedPreferences The {@link SharedPreferences} that will be used in this DAO.
     */
    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    /**
     * Saves the given string to {@link SharedPreferences}.
     *
     * @param message contains string entry to save to {@link SharedPreferences}.
     * @return {@code true} if writing to {@link SharedPreferences} succeeded. {@code false}
     *         otherwise.
     */
    public boolean saveEntry(String message){
        // Start a SharedPreferences transaction.
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_ENTRY, message);

        // Commit changes to SharedPreferences & return success/failure result
        return editor.commit();
    }

    public String getEntry() {
        return mSharedPreferences.getString(KEY_ENTRY, "");
    }

}

package brisksoft.com.myapplication;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;

import brisksoft.com.ad340.SharedPreferencesHelper;

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesUnitTest {

    @Mock
    SharedPreferences mMockSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor ;


    private SharedPreferencesHelper mMockSharedPreferencesHelper;

    private String text_entry = "blahckish";

    @Before
    public void initMocks() {

        // Create a mocked SharedPreferences.
        mMockSharedPreferencesHelper = createMockSharedPreference();

    }

    @Test
    public void sharedPreferences_SaveAndReadEntry() {

        // Save the personal information to SharedPreferences
        boolean success = mMockSharedPreferencesHelper.saveEntry(text_entry);

        assertThat("SharedPreferenceEntry.save... returns true",
                success, is(true));

        assertEquals(text_entry, mMockSharedPreferencesHelper.getEntry());

    }

    /**
     * Creates a mocked SharedPreferences object for successful read/write
     */
    private SharedPreferencesHelper createMockSharedPreference() {

        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written
        // correctly.
        when(mMockSharedPreferences.getString(eq("text_entry"), anyString()))
                .thenReturn(text_entry);

        // Mocking a successful commit.
        when(mMockEditor.commit()).thenReturn(true);

        // Return the MockEditor when requesting it.
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);

        return new SharedPreferencesHelper(mMockSharedPreferences);
    }
}
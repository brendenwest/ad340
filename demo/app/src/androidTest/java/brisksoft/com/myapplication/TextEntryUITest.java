package brisksoft.com.myapplication;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import brisksoft.com.ad340.MainActivity;
import brisksoft.com.ad340.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class TextEntryUITest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void uiElementsExist() {
        onView(withId(R.id.editText))
                .check(matches(isDisplayed()));
        onView(withId(R.id.button))
                .check(matches(isDisplayed()));
    }

    @Test
    public void validTextEntry() {
        // Type text and then press the button.
        onView(withId(R.id.editText))
                .perform(typeText("choad"), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());

        // Check that intent was fired
//        onView(withId(R.id.textToBeChanged))
//                .check(matches(withText(mStringToBetyped)));
    }
}

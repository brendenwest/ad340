package brisksoft.com.myapplication;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import brisksoft.com.ad340.MainActivity;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
/**
 * Unit tests for the {@link MainActivity} .
 */
public class ValidEntryUnitTest {
    private MainActivity mActivity = new MainActivity();

    @Test
    public void validInput_ReturnsTrue() {
        assertThat(mActivity.inputIsValid("chowder"), is(true));
    }

    @Test
    public void emptyInput_ReturnsFalse() {
        assertThat(mActivity.inputIsValid(""), is(false));
    }

    @Test
    public void numericInput_ReturnsFalse() {
        assertThat(mActivity.inputIsValid("12345"), is(false));
    }
}
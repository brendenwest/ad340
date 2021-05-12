Android Testing
====

Reading
----
- https://developer.android.com/training/testing/fundamentals.html
- https://developer.android.com/training/testing/unit-testing/index.html
- https://developer.android.com/training/testing/junit-runner.html
- https://developer.android.com/training/testing/ui-testing/espresso-testing.html
- https://developer.android.com/training/testing/espresso/basics.html

Watch
----
- `Test-Driven Development on Android with the Android Testing Support Library (Google I/O &#39;17) <https://www.youtube.com/watch?v=pK7W5npkhho&start=111>`_

Practice
----
- https://codelabs.developers.google.com/codelabs/android-testing/index.html
- https://github.com/googlesamples/android-testing/tree/master/unit/BasicSample

Learning Outcomes
----
- Android testing best practices
- Unit tests w JUnit
- Integration testing w/ Android framework using Robolectric
- Integration testing w/ Android system using `Mockito <https://github.com/mockito/mockito>`_
- User interface testing w Espresso
- Code coverage

Key Concepts
----
- application testing can include unit, integration & UI tests
- tests that run on a local machine are stored in the `test` folder
- tests that run on a physical or virtual Android device are stored in the `androidTest` folder
- `mock` objects simulate the behavior of objects or services that are hard to interact with in a test environment
- tests may need special configuration for access to Android API features when run locally

Unit Tests
----
- verify a small `unit` of application functionality one class at a time
- written in JUnit
- usually written to run locally in development environment
- `instrumented unit tests` validate actual behavior on a physical or virtual Android device
- may use 'mocks' to simulate services that aren't external to the application
- Robolectric is useful to mock complex interaction with Android framework dependencies
- Mockito is useful for mocking your own objects or those with minimal dependency on the Android framework.

Your unit tests should verify application functionality instead of duplicating it. How you reference that code will depend on how your application is structured.

**Example 1** - Testing a standalone Java class
::

    package com.example.android.testing.unittesting.BasicSample;
    import org.junit.Test;
    import static org.junit.Assert.assertTrue;

    /**
     * Unit test for the EmailValidator class contained in our package
     *
     */
    public class EmailValidatorTest {

        @Test
        public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
            // isValidEmail() method is in EmailValidator class
            assertTrue(EmailValidator.isValidEmail("name@email.com"));
        }
    }

**Example 2** - Testing methods of an activity
::

    package com.example.myapplication;
    import org.junit.Test;
    import static org.junit.Assert.*;
    import com.example.myapplication.MainActivity;

    /**
     * Unit tests for methods in the MainActivity .
     */
    public class EmailValidatorTest {

        // mActivity can be instantiated once, or before each unit test
        private MainActivity mActivity = new MainActivity();

        @Test
        public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
            // isValidEmail() method is a public method in MainActivity class
            assertTrue(mActivity.isValidEmail("name@email.com"));
        }
    }

Test Coverage
----
Code coverage measures how much application code is covered by unit tests and helps identify areas that need more testing.

Android Studio has a built-in feature to run tests with code coverage.

Simply navigate to the **src/test/java** folder and right click. Then select *Run 'Tests in ‘java’’* with Coverage
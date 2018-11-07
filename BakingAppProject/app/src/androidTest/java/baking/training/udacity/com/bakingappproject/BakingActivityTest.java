package baking.training.udacity.com.bakingappproject;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import baking.training.udacity.com.bakingappproject.main.MainBakingActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
public class BakingActivityTest {

    private static final String ERROR_MESSAGE = "An error has occurred. Please try again!";

    @Rule
    public ActivityTestRule<MainBakingActivity> mActivityRule =
            new ActivityTestRule<>(MainBakingActivity.class);


    @Test
    public void testIfViewsAreShown() {

        onView(withId(R.id.recyclerview_recipes))
                .check(matches(isDisplayed()));

        onView(withId(R.id.tv_error_message_display))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.tv_error_message_display))
                .check(matches(withText(ERROR_MESSAGE)));

        onView(withId(R.id.pb_loading_indicator))
                .check(matches(not(isDisplayed())));

    }

}

package io.cayeta.webake;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class RecipesListActivityBasicTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule
            = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void clickRecipeCard_OpensRecipeDetailsActivity() {
        onView(withId(R.id.rv_recipes)).check(new RecyclerViewItemCountAssertion(4));

        onView((withId(R.id.rv_recipes)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_ingredients)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_instructions)).check(matches(isDisplayed()));
    }

    /**
     * This was the top answer for the following question:
     * https://stackoverflow.com/questions/36399787/how-to-count-recyclerview-items-with-espresso
     */
    class RecyclerViewItemCountAssertion implements ViewAssertion {

        private final int mExpectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            mExpectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(mExpectedCount));
        }

    }

}
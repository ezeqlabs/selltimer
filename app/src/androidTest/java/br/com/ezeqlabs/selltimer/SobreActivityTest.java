package br.com.ezeqlabs.selltimer;

import android.content.pm.PackageManager;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SobreActivityTest {
    @Rule
    public ActivityTestRule<SobreActivity> activityTestRule = new ActivityTestRule(SobreActivity.class);

    @Test
    public void versaoAppMostrada() throws PackageManager.NameNotFoundException {
        onView(withId(R.id.txtVersao)).check(matches(isDisplayed()));
    }

    @Test
    public void mostraBotaoFeedback(){
        onView(withId(R.id.btnFeedback)).check(matches(isDisplayed()));
    }

    @Test
    public void mostraDialogFeedback(){
        onView(withId(R.id.btnFeedback)).perform(click());
        onView(withId(R.id.titulo_feedback)).check(matches(isDisplayed()));
        onView(withId(R.id.tipo_feedback)).check(matches(isDisplayed()));
        onView(withId(R.id.descricao_feedback)).check(matches(isDisplayed()));
    }
}

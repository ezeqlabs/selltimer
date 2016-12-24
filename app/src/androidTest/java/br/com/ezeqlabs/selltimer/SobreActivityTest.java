package br.com.ezeqlabs.selltimer;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.ezeqlabs.selltimer.utils.Constantes;
import br.com.ezeqlabs.selltimer.utils.PreparaTela;
import br.com.ezeqlabs.selltimer.utils.RotacionaTela;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SobreActivityTest {
    private Context context;
    private String texto;

    @Rule
    public ActivityTestRule<SobreActivity> activityTestRule = new ActivityTestRule(SobreActivity.class);

    @Before
    public void preparaContext(){
        context = this.activityTestRule.getActivity().getApplicationContext();
    }

    @Test
    public void versaoAppMostrada() throws PackageManager.NameNotFoundException {
        texto = Constantes.getVersao(context);

        onView(withId(R.id.txtVersao)).check(matches(isDisplayed()));
        onView(withId(R.id.txtVersao)).check(matches(withText(texto)));
    }

    @Test
    public void mostraBotaoFeedback(){
        texto = context.getString(R.string.feedback_sobre_app);

        onView(withId(R.id.btnFeedback)).check(matches(isDisplayed()));
        onView(withId(R.id.btnFeedback)).check(matches(isClickable()));
        onView(withId(R.id.btnFeedback)).check(matches(withText(texto)));
    }

    @Test
    public void mostraDialogFeedback(){
        onView(withId(R.id.btnFeedback)).perform(click());
        onView(withId(R.id.titulo_feedback)).check(matches(isDisplayed()));
        onView(withId(R.id.tipo_feedback)).check(matches(isDisplayed()));
        onView(withId(R.id.descricao_feedback)).check(matches(isDisplayed()));
    }

    @Test
    @RotacionaTela
    public void versaoAppMostradaLand() throws PackageManager.NameNotFoundException {
        PreparaTela.rotaciona(context, activityTestRule);
        texto = Constantes.getVersao(context);

        onView(withId(R.id.txtVersao)).check(matches(isDisplayed()));
        onView(withId(R.id.txtVersao)).check(matches(withText(texto)));
    }

    @Test
    @RotacionaTela
    public void mostraBotaoFeedbackLand(){
        PreparaTela.rotaciona(context, activityTestRule);
        texto = context.getString(R.string.feedback_sobre_app);

        onView(withId(R.id.btnFeedback)).check(matches(isDisplayed()));
        onView(withId(R.id.btnFeedback)).check(matches(isClickable()));
        onView(withId(R.id.btnFeedback)).check(matches(withText(texto)));
    }

    @Test
    @RotacionaTela
    public void mostraDialogFeedbackLand(){
        PreparaTela.rotaciona(context, activityTestRule);

        onView(withId(R.id.btnFeedback)).perform(click());
        onView(withId(R.id.titulo_feedback)).check(matches(isDisplayed()));
        onView(withId(R.id.tipo_feedback)).check(matches(isDisplayed()));
        onView(withId(R.id.descricao_feedback)).check(matches(isDisplayed()));
    }
}

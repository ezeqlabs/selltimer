package br.com.ezeqlabs.selltimer;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import br.com.ezeqlabs.selltimer.helpers.ContatoHelper;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.utils.Constantes;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

public class CadastroContatoActivityTest {
    private Cliente cliente;
    private Context context;
    private ContatoHelper helper;

    @Rule
    public ActivityTestRule<CadastroContatoActivity> activityTestRule = new ActivityTestRule(CadastroContatoActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, CadastroContatoActivity.class);

            cliente = new Cliente();
            cliente.setNome("Cliente Teste");
            cliente.setId(Long.valueOf(123));

            result.putExtra(Constantes.CLIENTE_INTENT, cliente);
            return result;
        }
    };

    @Before
    public void preparaContext(){
        context = this.activityTestRule.getActivity().getApplicationContext();
        helper = new ContatoHelper(this.activityTestRule.getActivity());
    }

    @Test
    public void mostraCamposPrincipais(){
        onView(withId(R.id.data_contato)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.interesse_contato)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.descricao_contato)).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void insereContatoCompleto(){
        String txtInteresse = "Interessado";
        String txtDescricao = "teste blablba bla";
        String txtDetalheInteresse = dataHoje() + " - " + txtInteresse;

        onView(withId(R.id.data_contato)).perform(scrollTo(), click());
        onView(withText("Concluído")).perform(click());

        onView(withId(R.id.interesse_contato)).perform(scrollTo(), click());
        onView(allOf(withText(txtInteresse))).perform(click());

        onView(withId(R.id.descricao_contato)).perform(scrollTo(), typeText(txtDescricao));
        onView(withId(R.id.action_salvar)).perform(click());

        onView(withText(cliente.getNome())).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withText(txtDetalheInteresse)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withText(txtDescricao)).perform(scrollTo()).check(matches(isDisplayed()));
    }

    /*************
     * VALIDAÇÕES *
     **************/

    // CAMPO DATA
    @Test
    public void mostraErroDataVazia(){
        String erroDataVazia = context.getString(R.string.erro_data);

        onView(withId(R.id.action_salvar)).perform(click());
        onView(withId(R.id.data_contato)).perform(scrollTo()).check(matches(hasErrorText(erroDataVazia)));
    }

    @Test
    @UiThreadTest
    public void dataVaziaInvalida(){
        assertFalse(helper.dataValida(""));
    }

    @Test
    @UiThreadTest
    public void dataComTamanhoInvalido(){
        assertFalse(helper.dataValida("123"));
        assertFalse(helper.dataValida("1234567890123"));
    }

    @Test
    @UiThreadTest
    public void dataComCaracteresInvalidos(){
        assertFalse(helper.dataValida("testeteste"));
    }

    @Test
    @UiThreadTest
    public void dataComFormatoInvalido(){
        assertFalse(helper.dataValida("123/567/90"));
        assertFalse(helper.dataValida("3/6/700890"));
    }

    @Test
    @UiThreadTest
    public void dataSelecionadaInvalida(){
        assertFalse(helper.dataValida("07/07/3017"));
    }

    // CAMPO INTERESSE
    @Test
    public void mostraErroInteresseVazio(){
        String erroInteresseVazio = context.getString(R.string.erro_interesse);

        onView(withId(R.id.action_salvar)).perform(click());
        onView(withText(erroInteresseVazio)).perform(scrollTo()).check(matches(isDisplayed()));
    }

    private String dataHoje(){
        Calendar c = Calendar.getInstance();

        String hoje = dia(c) + "/";
        hoje = hoje + mes(c) + "/";
        hoje = hoje + c.get(Calendar.YEAR);

        return hoje;
    }

    private String dia(Calendar c){
        int dia = c.get(Calendar.DAY_OF_MONTH);
        if( dia < 10 ){
            return "0" + dia;
        }else{
            return "" + dia;
        }
    }

    private String mes(Calendar c){
        int mes = c.get(Calendar.MONTH);
        if( mes < 10 ){
            return "0" + (mes+1);
        }else{
            return "" + (mes+1);
        }
    }
}

package br.com.ezeqlabs.selltimer;

import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CadastroClientesActivityTest {
    private Context context;

    @Rule
    public ActivityTestRule<CadastroClientesActivity> activityTestRule = new ActivityTestRule(CadastroClientesActivity.class);

    @Before
    public void preparaContext(){
        context = this.activityTestRule.getActivity().getApplicationContext();
    }

    @Test
    public void mostraCamposPrincipais(){
        onView(withId(R.id.nome_cliente)).check(matches(isDisplayed()));
        onView(withId(R.id.endereco_cliente)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.telefone_cliente)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.email_cliente)).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void mostraBotoesDeAdicionarCampos(){
        onView(withText(R.string.novo_endereco)).check(matches(isDisplayed()));
        onView(withText(R.string.novo_endereco)).check(matches(isClickable()));
        onView(withText(R.string.novo_telefone)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withText(R.string.novo_telefone)).perform(scrollTo()).check(matches(isClickable()));
        onView(withText(R.string.novo_email)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withText(R.string.novo_email)).perform(scrollTo()).check(matches(isClickable()));
    }

    /*************
    * VALIDAÇÕES *
    **************/

    // CAMPO NOME
    @Test
    public void insereNomeValido(){
        onView(withId(R.id.nome_cliente)).perform(typeText("abc"));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.nome_cliente_detalhe)).check(matches(isDisplayed()));
        onView(withId(R.id.nome_cliente_detalhe)).check(matches(withText("abc")));
    }

    @Test
    public void mostraErroNomeVazio(){
        String erroNomeVazio = context.getString(R.string.erro_nome);
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.action_salvar)).perform(click());
        onView(withId(R.id.nome_cliente)).perform(scrollTo()).check(matches(hasErrorText(erroNomeVazio)));
    }

    @Test
    public void mostraErroNomeInvalido(){
        String erroNomeVazio = context.getString(R.string.erro_nome_invalido);

        onView(withId(R.id.nome_cliente)).perform(typeText("ab"));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.nome_cliente)).check(matches(hasErrorText(erroNomeVazio)));
    }

    // CAMPO ENDEREÇO
    @Test
    public void insereEnderecoValido(){
        String texto = "Rua teste, 123456, 4 andar";
        onView(withId(R.id.nome_cliente)).perform(typeText("abc"));
        onView(withId(R.id.endereco_cliente)).perform(typeText(texto));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.endereco_detalhe_cliente)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.endereco_detalhe_cliente)).perform(scrollTo()).check(matches(withText(texto)));
    }

    @Test
    public void insereMultiplosEnderecos(){
        String texto = "Rua teste, 123456, 4 andar";
        String segundoTexto = "Rua teste, 12799, 2 andar";

        onView(withId(R.id.nome_cliente)).perform(typeText("abc"));
        onView(withId(R.id.endereco_cliente)).perform(typeText(texto));
        onView(withText(R.string.novo_endereco)).perform(scrollTo()).perform(click());
        onView(allOf(withId(R.id.generated_endereco))).perform(scrollTo()).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.generated_endereco))).perform(scrollTo()).perform(typeText(segundoTexto));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.container_enderecos_detalhe)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.endereco_detalhe_cliente), withText(texto))).perform(scrollTo()).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.endereco_detalhe_cliente), withText(segundoTexto))).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void mostraErroEnderecoInvalido(){
        String texto = "Rua";

        String erroEnderecoInvalido = context.getString(R.string.erro_endereco_invalido);
        onView(withId(R.id.endereco_cliente)).perform(typeText(texto));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.endereco_cliente)).check(matches(hasErrorText(erroEnderecoInvalido)));
    }

    // CAMPO TELEFONE
    @Test
    public void insereTelefoneValido(){
        onView(withId(R.id.nome_cliente)).perform(typeText("abc"));
        onView(withId(R.id.telefone_cliente)).perform(scrollTo()).perform(typeText("1111223344"));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.telefone_detalhe_cliente)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.telefone_detalhe_cliente)).perform(scrollTo()).check(matches(withText("(11) 1122-3344")));
    }

    @Test
    public void insereTelefoneValidoComNoveDigitos(){
        onView(withId(R.id.nome_cliente)).perform(typeText("abc"));
        onView(withId(R.id.telefone_cliente)).perform(scrollTo()).perform(typeText("11911223344"));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.telefone_detalhe_cliente)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.telefone_detalhe_cliente)).perform(scrollTo()).check(matches(withText("(11) 91122-3344")));
    }

    @Test
    public void insereMultiplosTelefones(){
        String texto = "1111223344";
        String textoDetalhe = "(11) 1122-3344";
        String segundoTexto = "9955667788";
        String segundoTextoDetalhe = "(99) 5566-7788";

        onView(withId(R.id.nome_cliente)).perform(typeText("abc"));
        onView(withId(R.id.telefone_cliente)).perform(typeText(texto));
        onView(withText(R.string.novo_telefone)).perform(scrollTo()).perform(click());
        onView(allOf(withId(R.id.generated_telefone))).perform(scrollTo()).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.generated_telefone))).perform(scrollTo()).perform(typeText(segundoTexto));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.container_telefones_detalhe)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.telefone_detalhe_cliente), withText(textoDetalhe))).perform(scrollTo()).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.telefone_detalhe_cliente), withText(segundoTextoDetalhe))).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void mostraErroTelefoneInvalido(){
        String texto = "11";

        String erroTelefoneInvalido = context.getString(R.string.erro_telefone_invalido);
        onView(withId(R.id.telefone_cliente)).perform(typeText(texto));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.telefone_cliente)).check(matches(hasErrorText(erroTelefoneInvalido)));
    }

    // CAMPO EMAIL
    @Test
    public void insereEmailValido(){
        String texto = "teste@teste.com.br";

        onView(withId(R.id.nome_cliente)).perform(typeText("abc"));
        onView(withId(R.id.email_cliente)).perform(scrollTo()).perform(typeText(texto));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.email_detalhe_cliente)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.email_detalhe_cliente)).perform(scrollTo()).check(matches(withText(texto)));
    }

    @Test
    public void insereMultiplosEmails(){
        String texto = "teste@teste.com";
        String segundoTexto = "segundoteste@teste.com";

        onView(withId(R.id.nome_cliente)).perform(typeText("abc"));
        onView(withId(R.id.email_cliente)).perform(scrollTo()).perform(typeText(texto));
        onView(withText(R.string.novo_email)).perform(scrollTo()).perform(click());
        onView(allOf(withId(R.id.generated_email))).perform(scrollTo()).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.generated_email))).perform(scrollTo()).perform(typeText(segundoTexto));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.container_emails_detalhe)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.email_detalhe_cliente), withText(texto))).perform(scrollTo()).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.email_detalhe_cliente), withText(segundoTexto))).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void mostraErroEmailInvalido(){
        String texto = "teste";

        String erroEmailInvalido = context.getString(R.string.erro_email_invalido);
        onView(withId(R.id.email_cliente)).perform(typeText(texto));
        onView(withId(R.id.action_salvar)).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.email_cliente)).check(matches(hasErrorText(erroEmailInvalido)));
    }
}

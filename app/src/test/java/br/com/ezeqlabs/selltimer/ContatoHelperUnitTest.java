package br.com.ezeqlabs.selltimer;

import org.junit.Before;
import org.junit.Test;

import br.com.ezeqlabs.selltimer.helpers.ContatoHelper;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

public class ContatoHelperUnitTest {
    private ContatoHelper helper;

    @Before
    public void preparaHelper(){
        helper = mock(ContatoHelper.class);
    }

    @Test
    public void dataVaziaInvalida(){
        assertFalse(helper.dataValida(""));
    }

    @Test
    public void dataComTamanhoInvalido(){
        assertFalse(helper.dataValida("123"));
        assertFalse(helper.dataValida("1234567890123"));
    }

    @Test
    public void dataComCaracteresInvalidos(){
        assertFalse(helper.dataValida("teste"));
    }

    @Test
    public void dataComFormatoInvalido(){
        assertFalse(helper.dataValida("123/456/7890"));
        assertFalse(helper.dataValida("3/6/7890"));
    }

    @Test
    public void dataSelecionadaInvalida(){
        assertFalse(helper.dataValida("07/07/3017"));
    }

}

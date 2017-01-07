package br.com.ezeqlabs.selltimer.utils;

import java.util.Calendar;

public class Datas {
    public static String dataAtual(){
        String atual = "" + Calendar.getInstance().get(Calendar.YEAR);

        atual += "-";

        if( (Calendar.getInstance().get(Calendar.MONTH)+1) > 9 ){
            atual += (Calendar.getInstance().get(Calendar.MONTH)+1);
        }else{
            atual += "0"+(Calendar.getInstance().get(Calendar.MONTH)+1);
        }

        atual += "-";

        if( Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 9 ){
            atual += Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }else{
            atual += "0"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }

        return atual;
    }
}

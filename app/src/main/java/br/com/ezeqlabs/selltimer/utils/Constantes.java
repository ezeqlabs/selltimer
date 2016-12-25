package br.com.ezeqlabs.selltimer.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Constantes {
    public static final String CLIENTE_INTENT = "cliente";
    public static final String CONTATO_INTENT = "contato";

    public static String getVersao(Context contexto) throws PackageManager.NameNotFoundException {
        PackageInfo info = contexto.getPackageManager().getPackageInfo(contexto.getPackageName(), 0);
        return info.versionName;
    }
}

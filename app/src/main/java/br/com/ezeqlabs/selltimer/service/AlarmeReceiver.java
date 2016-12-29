package br.com.ezeqlabs.selltimer.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;

public class AlarmeReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        preparaAlarme(context);
    }

    public static void preparaAlarme(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificacaoService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        Calendar alarmeOitoDaManha = Calendar.getInstance();
        Calendar alarmeUmaDaTarde = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmeOitoDaManha.set(Calendar.HOUR_OF_DAY, 8);
        alarmeOitoDaManha.set(Calendar.MINUTE, 0);
        alarmeOitoDaManha.set(Calendar.SECOND, 0);

        if (now.after(alarmeOitoDaManha)) {
            alarmeOitoDaManha.add(Calendar.DATE, 1);
        }

        alarmeUmaDaTarde.set(Calendar.HOUR_OF_DAY, 13);
        alarmeUmaDaTarde.set(Calendar.MINUTE, 0);
        alarmeUmaDaTarde.set(Calendar.SECOND, 0);

        if (now.after(alarmeUmaDaTarde)) {
            alarmeUmaDaTarde.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmeOitoDaManha.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmeUmaDaTarde.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }
}

package br.com.ezeqlabs.selltimer.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmeReceiver extends BroadcastReceiver{
    static AlarmManager alarmManager;
    static Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        preparaAlarme(context);
    }

    public static void preparaAlarme(Context c){
        context = c;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        setAlarmeManha();
        setAlarmeTarde();
    }

    private static void setAlarmeManha(){
        PendingIntent pendingIntentManha = preparaPendingIntent(0);

        Calendar alarmeOitoDaManha = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmeOitoDaManha.set(Calendar.HOUR_OF_DAY, 8);
        alarmeOitoDaManha.set(Calendar.MINUTE, 0);
        alarmeOitoDaManha.set(Calendar.SECOND, 0);

        if (now.after(alarmeOitoDaManha)) {
            alarmeOitoDaManha.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                alarmeOitoDaManha.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntentManha
        );
    }

    private static void setAlarmeTarde(){
        PendingIntent pendingIntentTarde = preparaPendingIntent(1);

        Calendar alarmeUmaDaTarde = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmeUmaDaTarde.set(Calendar.HOUR_OF_DAY, 13);
        alarmeUmaDaTarde.set(Calendar.MINUTE, 30);
        alarmeUmaDaTarde.set(Calendar.SECOND, 0);

        if (now.after(alarmeUmaDaTarde)) {
            alarmeUmaDaTarde.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                alarmeUmaDaTarde.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntentTarde
        );
    }

    private static PendingIntent preparaPendingIntent(int id){
        Intent intent = new Intent(context, NotificacaoService.class);
        return PendingIntent.getService(context, id, intent, 0);
    }
}

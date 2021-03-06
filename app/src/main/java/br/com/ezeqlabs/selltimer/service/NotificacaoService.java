package br.com.ezeqlabs.selltimer.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import br.com.ezeqlabs.selltimer.MainActivity;
import br.com.ezeqlabs.selltimer.R;
import br.com.ezeqlabs.selltimer.database.DatabaseHelper;

public class NotificacaoService extends IntentService {
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private Context context;
    private static int NOTIFICATION_ID = 1;
    Notification notification;

    public NotificacaoService() {
        super("NotificacaoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        context = this.getApplicationContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        if( databaseHelper.temClienteHoje() ){
            preparaVariaveis();
            preparaNotificacao();
            enviaNotificacao();
        }

    }

    private void preparaVariaveis(){
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void preparaNotificacao(){
        Resources res = this.getResources();
        notification = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_money)
                .setAutoCancel(true)
                .setPriority(8)
                .setContentTitle(res.getString(R.string.titulo_notificacao))
                .setContentText(res.getString(R.string.texto_notificacao))
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
    }

    private void enviaNotificacao(){
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}

package com.example.internetpic.messageReceive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.internetpic.R;
import com.example.internetpic.listview.ListViewMusicActivity;

import static android.content.Context.NOTIFICATION_SERVICE;


public class MusicMessagereceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String music_name = intent.getStringExtra("music_name");
        Toast.makeText(context, music_name, Toast.LENGTH_SHORT).show();

        Notification.Builder notification = new Notification.Builder(context);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.drawable.fk);
        notification.setContentTitle(music_name);
        notification.setContentText("点击停止播放");
        notification.setWhen(System.currentTimeMillis());
        notification.setDefaults(Notification.DEFAULT_VIBRATE);//设置通知栏通知

        Intent intent1 = new Intent(context,ListViewMusicActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent1,0);
        notification.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification.build());
    }
}

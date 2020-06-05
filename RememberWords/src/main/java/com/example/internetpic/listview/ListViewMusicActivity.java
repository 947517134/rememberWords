package com.example.internetpic.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.internetpic.R;
import com.example.internetpic.activity.MainActivity;
import com.example.internetpic.messageReceive.MusicMessagereceiver;
import com.example.internetpic.service.MusicService;

public class ListViewMusicActivity extends AppCompatActivity {

    private ListView listView_music;
    private String[] music_name = {"双笙 - 棠梨煎雪（Cover 银临）","松紧先生（李宗锦） - 你走","凯瑟喵 - idnight","尚东峰 - 也想，你永远不懂这首歌","Akie秋绘 - Pray（翻自 SumaiL）"};
    private int music_id;
    private MusicService musicService;
    private Button stopMusicBtn,MusicBtn;
    boolean isplay = false;

    MusicMessagereceiver musicMessagereceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_music);

        listView_music = findViewById(R.id.listView_music);
        stopMusicBtn = findViewById(R.id.stopMusicBtn);
        MusicBtn = findViewById(R.id.MusicBtn);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.layout_music_item,music_name);
        listView_music.setAdapter(arrayAdapter);//list显示

        musicMessagereceiver = new MusicMessagereceiver();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("MusicMsgReceiver");
        registerReceiver(musicMessagereceiver,intentFilter);//注册广播接收器


        listView_music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                musicService.MusicPlay(position);
                music_id = position;
            }
        });

        final Intent intent = new Intent(ListViewMusicActivity.this,MusicService.class);
        bindService(intent,con,BIND_AUTO_CREATE);

        MusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
                if (!isplay){
                    isplay = true;
                    MusicBtn.setText("暂停");

                    Intent intentReceiver = new Intent("MusicMsgReceiver");
                    intentReceiver.putExtra("music_name",music_name[music_id]);
                    sendBroadcast(intentReceiver);


//                    Notification.Builder notification = new Notification.Builder(ListViewMusicActivity.this);
//                    notification.setAutoCancel(true);
//                    notification.setSmallIcon(R.drawable.fk);
//                    notification.setContentTitle(music_name[music_id]);
//                    notification.setContentText("点击停止播放");
//                    notification.setWhen(System.currentTimeMillis());
//                    notification.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);
//
//                    Intent intent1 = new Intent(ListViewMusicActivity.this,ListViewMusicActivity.class);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(ListViewMusicActivity.this,0,intent1,0);
//                    notification.setContentIntent(pendingIntent);
//
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    notificationManager.notify(1,notification.build());


                }else {
                    isplay = false;
                    MusicBtn.setText("开始播放");
                }
            }
        });

        stopMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isplay = musicService.MusicStop();
                MusicBtn.setText("开始播放");
            }
        });
    }



    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}

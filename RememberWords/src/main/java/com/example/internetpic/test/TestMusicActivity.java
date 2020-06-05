package com.example.internetpic.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internetpic.R;
import com.example.internetpic.messageReceive.MusicMessagereceiver;
import com.example.internetpic.pojo.Song;
import com.example.internetpic.service.TestMusicService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TestMusicActivity extends AppCompatActivity {


    MusicMessagereceiver musicMessagereceiver;

    private TestMusicService testMusicService;

    private List<Song> songs;
    private ContentResolver mContentResolver;
    private String[] permissions = {Manifest.permission.INTERNET,Manifest.permission.READ_EXTERNAL_STORAGE};//需要的权限
    private static int Permission_Request_Code = 1;
    private int currentPosition = -1;

    private TextView currentPlayName;
    private TextView currentProgress;       //当前进度
    private TextView totalProgress;         //总进度
    private SeekBar seekBar;
    private Button play_button;
    private Button previous_button;
    private Button next_button;
    private Button stop_button;
    private Timer timer;
    private RecyclerView playList;

    private ServiceConnection con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_music_list);
        initPermission(permissions);//动态获取权限
        mContentResolver = getContentResolver();
        playList = findViewById(R.id.playlist);         //拿到view布局
        playList.setLayoutManager(new LinearLayoutManager(this));       //RecycleView必须设置layoutmanager
        findViewById(R.id.music_play_tool).setVisibility(View.VISIBLE);         //通过设置setorancental可以把布局改成横向，Recycle专属技能，牛批
        songs = getSongs();

        final Intent intent = new Intent(this,TestMusicService.class);      //用于连接服务的intent
        startService(intent);
        con = new ServiceConnection() {         //设置与后台service进行通讯
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                testMusicService = ((TestMusicService.MyBinder) service).getService();      //通过强制转换为MyBinder调用getservice，从而获取service信息
                testMusicService.mContext=TestMusicActivity.this;
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        bindService(intent,con,BIND_AUTO_CREATE);       //绑定服务
        if(songs.size()>0) initPlayerViews();

    }


        //初始化界面
    private void initPlayerViews() {
        currentPlayName = findViewById(R.id.currentPlayName);
        currentProgress = findViewById(R.id.currentProgress);
        totalProgress = findViewById(R.id.totalProgress);
        seekBar = findViewById(R.id.play_seekbar);
        previous_button = findViewById(R.id.previous_button);
        play_button = findViewById(R.id.play_button);
        next_button = findViewById(R.id.next_button);
        stop_button = findViewById(R.id.stop_button);

        previous_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous();
            }
        });
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setEnabled(true);                   //设置播放进度条在音频播放过程时可拖动修改
                if (testMusicService.getPlayer_state().equals("playing")) {
                    testMusicService.setPlayer_state("pause");
                    testMusicService.pause();
                    play_button.setText("播放");
                    currentPlayName.setText("已暂停:"+songs.get(currentPosition).getName() +" - "+songs.get(currentPosition).getArtist());
                } else if (testMusicService.getPlayer_state().equals("pause")) {
                    testMusicService.setPlayer_state("playing");
                    testMusicService.start();
                } else if(testMusicService.getPlayer_state().equals("stop")){
                    testMusicService.setPlayer_state("playing");
                    testMusicService.play(songs.get(currentPosition));
                    //后加
                    if (timer != null) {
                        timer.cancel();
                        timer.purge();
                    }
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            seekBar.setProgress(testMusicService.getCurrentPosition());
                        }
                    }, 0, 1000);            //设置一秒更新一次
                }
            }
        });
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMusicService.stop();
                testMusicService.setPlayer_state("stop");
                play_button.setText("播放");
                seekBar.setProgress(0);
                seekBar.setEnabled(false);                  //停止时设置进度条不可调
                currentPlayName.setVisibility(View.INVISIBLE);  //currentposition还在，所以设置不可见
                currentProgress.setText("00:00");              //现在时间和最大时间归零
                totalProgress.setText("00:00");
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress.setText(time2str(seekBar.getProgress()));//开始时间
            }

            //开始滑动时的处理
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            //停止滑动时的处理
            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                testMusicService.seekTo(seekBar.getProgress());         //调用service的seekTo方法，在当前位置播放
                currentProgress.setText(time2str(testMusicService.getCurrentPosition()));
            }
        });
        SongAdapter songAdapter = new SongAdapter(songs, this);
        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) throws IOException {
                seekBar.setEnabled(true);
                currentPosition = position;
                testMusicService.play(songs.get(currentPosition));
                testMusicService.setPlayer_state("playing");
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                }
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        seekBar.setProgress(testMusicService.getCurrentPosition());
                    }
                }, 0, 1000);
            }
        });

        //为ListView设置Adapter适配器
        playList.setAdapter(songAdapter);
        currentPosition = 0;
        seekBar.setEnabled(false);
        play_button.setText("播放");
    }

    public void setPlayStateMainCallback(int total){
        currentPlayName.setVisibility(View  .VISIBLE);
        play_button.setText("暂停");
        currentPlayName.setText("正在播放:"+songs.get(currentPosition).getName() +" - "+songs.get(currentPosition).getArtist());
        seekBar.setMax(total);
        System.out.println("总长度"+total);
        totalProgress.setText(time2str(total));
    }


    // 获取音乐
    private List<Song> getSongs() {
        ArrayList<Song> songs = new ArrayList<>();
         // 通过MediaStore获取系统识别的媒体文件
        Cursor c = null;
        try {
            c = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));// 路径
                if (!new File(path).exists()) continue;
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)); // 歌曲名
                String artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)); // 作者
                int duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));// 时长
                Song song = new Song(name, path, artist, duration);
                songs.add(song);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) c.close();
        }

//        Song song1 = new Song("剪刀石头布","/storage/emulated/0/Music/风雷 - 剪刀石头布.mp3","aa",218);
//        Song song2 = new Song("好嗨哟","/storage/emulated/0/Music/T2o,洛天依 - 好嗨哦 (So High).flac","lty",183);
//        songs.add(song1);
//        songs.add(song2);
//        System.out.println(songs);

        return songs;
    }

    public void next() {
        seekBar.setEnabled(true);
        if (currentPosition == songs.size() - 1) {
            currentPosition = 0;
        } else {
            currentPosition = currentPosition + 1;
        }
        testMusicService.setPlayer_state("playing");
        testMusicService.play(songs.get(currentPosition));
    }

    private void previous() {
        seekBar.setEnabled(true);
        if (currentPosition == 0) {
            currentPosition = songs.size() - 1;
        } else {
            currentPosition = currentPosition - 1;
        }
        testMusicService.setPlayer_state("playing");
        testMusicService.play(songs.get(currentPosition));
    }

    // 动态获取权限 回调函数
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permission_Request_Code) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults.length > 0) {//安全写法，如果小于0，肯定会出错了
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        switch (grantResult) {
                            case PackageManager.PERMISSION_GRANTED://同意授权0
                                System.out.println(permissions[i] + "权限获取成功");
                                break;
                            case PackageManager.PERMISSION_DENIED://拒绝授权-1
                                Toast.makeText(this, permissions[i] + "权限获取失败!", Toast.LENGTH_SHORT).show();
                                finish();
                                break;
                        }
                    }
                }
            }
        }

    }

    public void initPermission(String[] permissions) {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionList = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {//for循环把需要授权的权限都添加进来
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {  //未授权就进行授权
                    permissionList.add(permissions[i]);
                }
            }
            //如果permissionList是空的，说明没有权限需要授权,什么都不做，该干嘛干嘛，否则就发起授权请求
            if (!permissionList.isEmpty()) {
                String[] need_permissions = permissionList.toArray(new String[permissionList.size()]);
                ActivityCompat.requestPermissions(this, need_permissions, Permission_Request_Code);
            }
        }
    }

    private String time2str(int time) {
        String str1 = "0" + time / 1000 / 60;
        str1 = str1.substring(str1.length() - 2);       //从0号位置开始检索到length-2，输出分钟
        String str2 = "0" + time / 1000 % 60;
        str2 = str2.substring(str2.length() - 2);
        return str1 + ":" + str2;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(con);
    }
}

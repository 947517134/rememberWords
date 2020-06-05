package com.example.internetpic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.internetpic.pojo.Song;
import com.example.internetpic.test.TestMusicActivity;

import java.io.IOException;

public class TestMusicService extends Service {

    MediaPlayer player;//声明播放器对象
    private String player_state = "stop";
    public Context mContext;
    public void start(){
        ((TestMusicActivity)mContext).setPlayStateMainCallback(player.getDuration());
        player.start();
    }

    public String getPlayer_state() {
        return player_state;
    }

    public void setPlayer_state(String player_state) {
        this.player_state = player_state;
    }

    public void play(Song song) {
        try {
            player.reset();     //重置音乐播放器
            player.setDataSource(song.getPath());       //播放存储设备的资源文件，其中路径是用song中的方法获取的
            player.prepare();       //player准备就绪，相当于缓冲
            player.start();
            ((TestMusicActivity)mContext).setPlayStateMainCallback(player.getDuration());
            // 设置播放结束后自动循环播放

        } catch (IllegalArgumentException | SecurityException | IllegalStateException
                | IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "播放器初始化失败！", Toast.LENGTH_SHORT).show();
            player.release();
        }
    }

    public void stop(){
        player.stop();
    }

    public int getCurrentPosition(){
        return player.getCurrentPosition();
    }

    public TestMusicService(Context mContext){
        super();
        this.mContext = mContext;
    }

    @Override
    public IBinder onBind(Intent intent) {//1、必须实现的绑定方法（绑定服务时调用），当其他组件通过BindService()请求与服务绑定时，调用
        return new MyBinder();          //返回一个IBinder对象，用于实现绑定的组件和该服务之间的通信
    }

    @Override
    public boolean onUnbind(Intent intent) {//2、当其他组件调用unbindService()方法解除被绑定到服务的所有组件的绑定后调用
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {//1、首次创建服务时调用，仅调用一次
        super.onCreate();
        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("下一曲");
                ((TestMusicActivity)mContext).next();
            }
        });
    }

    public void pause(){
        player.pause();
    }

    public void seekTo(int progress) {
        player.seekTo(progress);            //设置音乐的播放位置
    }

    //创建MyBinder内部类并获取服务对象与service状态
    public class MyBinder extends Binder{
        public TestMusicService getService(){
            return TestMusicService.this;
        }
    }

    public int getDuration(){
        return player.getDuration();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public TestMusicService() {
    }
}

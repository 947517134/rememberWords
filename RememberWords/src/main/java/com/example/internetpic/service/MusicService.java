package com.example.internetpic.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service {

    MediaPlayer player;//声明播放器对象
    static boolean isPlay;//定义播放器状态


    public MusicService(){
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {//1、必须实现的绑定方法（绑定服务时调用），当其他组件通过BindService()请求与服务绑定时，调用
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {//2、当其他组件调用unbindService()方法解除被绑定到服务的所有组件的绑定后调用
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {//1、首次创建服务时调用，仅调用一次
        super.onCreate();
        //player = MediaPlayer.create(this,R.raw);//获取播放器组件并添加资源
    }

    public void MusicPlay(int position) {
    }

    public boolean MusicStop() {
        return true;
    }

    public class MyBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }

    // 抱歉把你原来代码删掉了- - 那个switch

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//2、当其他组件通过startService()方法请求启动服务时调用（可调用多次）
        if (player == null){
            Toast.makeText(this, "未选中歌曲", Toast.LENGTH_SHORT).show();
        }else {
            if (!player.isPlaying()){//判断播放器状态，状态为停止时执行
                player.start();//启动播放器，开始播放
                isPlay = player.isPlaying();//改变播放器状态记录
            }else {
                player.pause();
                isPlay = false;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {//3、当销毁服务时调用，近且只调用一次
//        player.stop();//关闭播放器
//        if (isPlay){
//            player.release();//释放资源
//        }
        super.onDestroy();
    }
}

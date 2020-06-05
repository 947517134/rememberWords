package com.example.internetpic.test;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internetpic.R;
import com.example.internetpic.ScaleInAnimation;
import com.example.internetpic.pojo.Song;

import java.io.IOException;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
                                                                                //将泛型设定为只能使用viewholder
    private List<Song> songs;
    private Context mContext;
    private OnItemClickListener onItemClickListener = null;
    private ScaleInAnimation mSelectAnimation = new ScaleInAnimation();

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SongAdapter(List<Song> songs, Context context) {
        this.songs = songs;
        this.mContext = context;
    }

    @NonNull        //参数非空
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {     //创建ViewHolder，返回每一项的布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_music_list_item, parent, false); //用于获取view，而falte方法用于将layout打印成view对象
        return new SongAdapter.ViewHolder(view);        //传参
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {      //将数据和控件绑定，显示item的时候运行
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    try {
                        onItemClickListener.onItemClick(v, position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        final Song song = songs.get(position);
        holder.song_name_TextView.setText(song.getName());
        holder.song_artist_TextView.setText(song.getArtist());
        holder.song_duration_TextView.setText((song.getDuration() / 1000) / 60 + ":" + (song.getDuration() / 1000) % 60);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }           //返回Item总条数


    public class ViewHolder extends RecyclerView.ViewHolder {       //内部类，绑定控件
        LinearLayout song_item_layout;
        TextView song_name_TextView;
        TextView song_artist_TextView;
        TextView song_duration_TextView;

        public ViewHolder(@NonNull View itemView) {         //这里生成后不选择传参数值对，而直接通过itemview
            super(itemView);
            song_item_layout = (LinearLayout) itemView.getRootView();
            song_name_TextView = itemView.findViewById(R.id.song_item_name_tv);
            song_artist_TextView = itemView.findViewById(R.id.song_item_artist_tv);
            song_duration_TextView = itemView.findViewById(R.id.song_item_duration_tv);
        }
    }


//    @Override
//    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        addAnimation(holder);
//    }
//
//    private void addAnimation(ViewHolder holder) {
//        for (Animator anim : mSelectAnimation.getAnimators(holder.itemView)) {
//            anim.setDuration(300).start();
//            anim.setInterpolator(new LinearInterpolator());
//        }
//    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v, int position) throws IOException;
    }
}

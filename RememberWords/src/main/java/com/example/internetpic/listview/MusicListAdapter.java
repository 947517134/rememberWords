package com.example.internetpic.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.internetpic.R;

public class MusicListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mlayoutInflater;

    public MusicListAdapter(Context context){
        this.mContext = context;
        this.mlayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        public TextView tv_music1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = mlayoutInflater.inflate(R.layout.layout_music_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_music1 = convertView.findViewById(R.id.tv_music1);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }
}

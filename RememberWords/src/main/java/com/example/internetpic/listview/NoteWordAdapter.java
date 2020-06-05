package com.example.internetpic.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.internetpic.R;
import com.example.internetpic.liteOrm.liteOrmTable.CarData;

import java.util.List;

public class NoteWordAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mlayoutInflater;
    private List<CarData> list;

    public NoteWordAdapter(Context context, List<CarData> list) {
        this.mContext = context;
        this.list = list;
        this.mlayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        public TextView tv_music1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mlayoutInflater.inflate(R.layout.layout_note, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_music1 = convertView.findViewById(R.id.tv_music1);
            viewHolder.tv_music1.setText(list.get(position).getWord());
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}

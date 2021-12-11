package com.example.wangwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wangwang.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DataMangerAdpater extends BaseAdapter {
    private Context context;
    private List<HashMap> list = new LinkedList<HashMap>();

    public void addData(HashMap hashMap) {
        list.add(hashMap);
        notifyDataSetChanged();
    }

    public DataMangerAdpater(Context context, List<HashMap> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.data_manger_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = view.findViewById(R.id.data_manger_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        HashMap hashMap=list.get(i);
        String text= (String) hashMap.get("text");
        viewHolder.mTextView.setText(text);
        return view;
    }

    class ViewHolder {
        TextView mTextView;
    }
}

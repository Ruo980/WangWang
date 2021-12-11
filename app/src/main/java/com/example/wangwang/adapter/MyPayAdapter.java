package com.example.wangwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wangwang.R;
import com.example.wangwang.entity.Expenditure;

import java.util.LinkedList;
import java.util.List;

public class MyPayAdapter extends BaseAdapter {

    private Context context;
    private List<Expenditure> list = new LinkedList<Expenditure>();

    public void addData(Expenditure expenditure) {
        list.add(expenditure);
        notifyDataSetChanged();
    }
    
    public void changeData(List list){
        this.list=list;
        notifyDataSetChanged();
    }

    public MyPayAdapter(Context context, List<Expenditure> list) {
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
            view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.my_pay_list_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewNum = view.findViewById(R.id.my_pay_list_number);
            viewHolder.textViewType = view.findViewById(R.id.my_pay_list_type);
            viewHolder.textViewMoney = view.findViewById(R.id.my_pay_list_money);
            viewHolder.textViewTime = view.findViewById(R.id.my_pay_list_time);
            viewHolder.textViewId = view .findViewById(R.id.my_pay_list_id);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Expenditure expenditure = list.get(i);
        int num = i+1;
        String type = expenditure.getPayType();
        int money = expenditure.getPayMoney();
        String time = expenditure.getPayDate();
        int id = expenditure.getId();
        viewHolder.textViewNum.setText(""+num);
        viewHolder.textViewType.setText(type);
        viewHolder.textViewMoney.setText(""+money);
        viewHolder.textViewTime.setText(time);
        viewHolder.textViewId.setText(""+id);
        return view;
    }

    class ViewHolder {
        TextView textViewNum;
        TextView textViewType;
        TextView textViewMoney;
        TextView textViewTime;
        TextView textViewId;
    }
}

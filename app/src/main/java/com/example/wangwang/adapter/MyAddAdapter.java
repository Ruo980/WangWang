package com.example.wangwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wangwang.R;
import com.example.wangwang.entity.Income;

import java.util.LinkedList;
import java.util.List;

public class MyAddAdapter extends BaseAdapter {
    private Context context;
    private List<Income> list = new LinkedList<Income>();

    public void addData(Income income) {
        list.add(income);
        notifyDataSetChanged();
    }

    public void changeData(List list){
        this.list=list;
        notifyDataSetChanged();
    }

    public MyAddAdapter(Context context, List<Income> list) {
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
        MyAddAdapter.ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.my_add_list_item, viewGroup, false);
            viewHolder = new MyAddAdapter.ViewHolder();
            viewHolder.textViewNum = view.findViewById(R.id.my_add_list_number);
            viewHolder.textViewType = view.findViewById(R.id.my_add_list_type);
            viewHolder.textViewMoney = view.findViewById(R.id.my_add_list_money);
            viewHolder.textViewTime = view.findViewById(R.id.my_add_list_time);
            viewHolder.textViewId = view .findViewById(R.id.my_add_list_id);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyAddAdapter.ViewHolder) view.getTag();
        }
        Income income = list.get(i);
        int num = i+1;
        String type = income.getIncomeType();
        int money = income.getIncomeMoney();
        String time = income.getIncomeDate();
        int id = income.getId();
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

package com.example.wangwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wangwang.R;
import com.example.wangwang.entity.Notes;

import java.util.LinkedList;
import java.util.List;

public class NotesAdapter extends BaseAdapter {
    private Context context;
    private List<Notes> list = new LinkedList<Notes>();

    public void addData(Notes notes) {
        list.add(notes);
        notifyDataSetChanged();
    }

    public void changeData(List list){
        this.list=list;
        notifyDataSetChanged();
    }

    public NotesAdapter(Context context, List<Notes> list) {
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
            view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.my_notes_list_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewNum = view.findViewById(R.id.my_notes_list_number);
            viewHolder.textViewName = view.findViewById(R.id.my_notes_list_name);
            viewHolder.textViewNoteDate = view.findViewById(R.id.my_notes_list_notesDate);
            viewHolder.textViewId = view.findViewById(R.id.my_notes_list_id);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Notes notes = list.get(i);
        int num = i+1;
        String name = notes.getName();
        String notesDate = notes.getNotesDate();
        viewHolder.textViewNum.setText(""+num);
        viewHolder.textViewName.setText(name);
        viewHolder.textViewNoteDate.setText(notesDate);
        viewHolder.textViewId.setText(""+notes.getId());
        return view;
    }

    class ViewHolder {
        TextView textViewNum;
        TextView textViewName;
        TextView textViewNoteDate;
        TextView textViewId;
    }
}

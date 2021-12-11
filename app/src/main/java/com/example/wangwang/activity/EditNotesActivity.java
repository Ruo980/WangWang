package com.example.wangwang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangwang.R;
import com.example.wangwang.dao.NotesDao;
import com.example.wangwang.database.FinancialAssistant;
import com.example.wangwang.entity.Notes;

import java.util.LinkedList;
import java.util.List;

public class EditNotesActivity extends AppCompatActivity implements View.OnClickListener {

    private String account;
    private String id;
    private EditText editText;
    private TextView textView;
    private Button save;
    private Button delete;
    private String content;
    private String notesDate;
    private int month;
    private int week;
    private String name;
    private List<Notes> list = new LinkedList<Notes>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {//修改
                Toast.makeText(EditNotesActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditNotesActivity.this, NotesActivity.class);
                intent.putExtra("account",account);
                intent.putExtra("result",0);//让其刷新
                startActivity(intent);
            } else if (msg.what == 1) {//删除
                Toast.makeText(EditNotesActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditNotesActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        id = intent.getStringExtra("id");
        textView = findViewById(R.id.edit_notes_text_count);
        editText = findViewById(R.id.edit_notes_text);
        save =findViewById(R.id.save_edit_notes);
        delete=findViewById(R.id.delete_edit_notes);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                NotesDao notesDao = db.NotesDao();
                list = notesDao.loadNotesById(Integer.parseInt(id));
            }
        });
        thread.start();
        try {
            thread.join();
            Notes notes = list.get(0);
            content = notes.getContent();
            notesDate = notes.getNotesDate();
            month = notes.getMonth();
            week = notes.getWeek();
            editText.setText(content);
            EditText editText = findViewById(R.id.edit_notes_name);
            name = notes.getName();//方便修改时取值
            editText.setText(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textView.setText(String.valueOf(charSequence.length()));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textView.setText(String.valueOf(charSequence.length()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                content = editText.getText().toString();
                textView.setText(String.valueOf(editable.length()));
                if (editable.length() == 200) {
                    Toast.makeText(EditNotesActivity.this, "字数已上限", Toast.LENGTH_SHORT).show();
                }
            }
        });

        save.setOnClickListener(this);
        delete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        EditText editText = findViewById(R.id.edit_notes_name);
        name= editText.getText().toString();
        if (view.getId() == R.id.save_edit_notes) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                    NotesDao notesDao = db.NotesDao();
                    Notes notes = new Notes(account, name, notesDate, month, week, content);
                    notes.setId(Integer.parseInt(id));
                    notesDao.updateNotes(notes);
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                }
            }).start();
        } else if (view.getId() == R.id.delete_edit_notes) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                    NotesDao notesDao = db.NotesDao();
                    Notes notes = notesDao.loadNotesById(Integer.parseInt(id)).get(0);
                    notesDao.deleteNotes(notes);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }).start();

        }
    }
}
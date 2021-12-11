package com.example.wangwang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangwang.R;
import com.example.wangwang.dao.UserDao;
import com.example.wangwang.database.FinancialAssistant;
import com.example.wangwang.entity.Notes;
import com.example.wangwang.entity.User;

import java.util.LinkedList;
import java.util.List;

public class FunctionActivity extends AppCompatActivity implements View.OnClickListener {

    private String account;
    private List<User> list = new LinkedList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        LinearLayout payMoneyLayout = findViewById(R.id.new_pay_money);
        payMoneyLayout.setOnClickListener(this);
        LinearLayout addMoneyLayout = findViewById(R.id.new_add_money);
        addMoneyLayout.setOnClickListener(this);
        LinearLayout myPayMoneyLayout = findViewById(R.id.my_pay_money);
        myPayMoneyLayout.setOnClickListener(this);
        LinearLayout myAddMoneyLayout = findViewById(R.id.my_add_money);
        myAddMoneyLayout.setOnClickListener(this);
        LinearLayout dataMangerLayout = findViewById(R.id.data_manger);
        dataMangerLayout.setOnClickListener(this);
        LinearLayout myNotes = findViewById(R.id.my_notes);
        myNotes.setOnClickListener(this);
        LinearLayout updatePassword = findViewById(R.id.update_password);
        updatePassword.setOnClickListener(this);
        LinearLayout exitLayout = findViewById(R.id.exit);
        exitLayout.setOnClickListener(this);
        Button button = findViewById(R.id.update_password_bar_button);
        button.setOnClickListener(this);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                FinancialAssistant db = Room.databaseBuilder(getApplicationContext(), FinancialAssistant.class, "financial_assistant").build();
                UserDao userDao = db.userDao();
                list=userDao.findAllUserByAccount(account);
            }
        });
        thread.start();
        try {
            thread.join();
            User user =list.get(0);
            ImageView imageView = findViewById(R.id.head_portrait);
            TextView textView =findViewById(R.id.account_bar);
            if(user.getSex().equals("男")){
                textView.setText(account);
                imageView.setBackgroundResource(R.drawable.head_portrait_men);
            }
            else {
                textView.setText(account);
                imageView.setBackgroundResource(R.drawable.head_portrait_women);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.new_pay_money) {
            Intent intent = new Intent(this, PayMoneyActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        } else if (view.getId() == R.id.new_add_money) {
            Intent intent = new Intent(this, AddMoneyActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        } else if (view.getId() == R.id.my_pay_money) {
            Intent intent = new Intent(this, MyPayMoneyActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        } else if (view.getId() == R.id.my_add_money) {
            Intent intent = new Intent(this, MyAddMoneyActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        }else if (view.getId() == R.id.data_manger) {
            Intent intent = new Intent(this, DataMangerActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        }else if (view.getId() == R.id.my_notes) {
            Intent intent = new Intent(this, NotesActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        }else if (view.getId() == R.id.update_password) {
            Intent intent = new Intent(this, UpdatePasswordActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        }else if (view.getId() == R.id.exit) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }else{//即侧边栏修改密码
            Intent intent = new Intent(this, UpdatePasswordActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,1,1,"导出");
        return true;//super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
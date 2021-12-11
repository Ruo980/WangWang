package com.example.wangwang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wangwang.R;
import com.example.wangwang.fragement.poster.Poster1;
import com.example.wangwang.fragement.poster.Poster2;
import com.example.wangwang.fragement.poster.Poster3;
import com.example.wangwang.fragement.poster.main;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Fragment> fragments = new LinkedList<Fragment>();

        fragments.add(new Poster1());
        fragments.add(new Poster2());
        fragments.add(new Poster3());
        fragments.add(new main());
        FragAdapter fragAdapter=new FragAdapter(getSupportFragmentManager(),fragments);
        ViewPager viewPager=(ViewPager)findViewById(R.id.login_viewpager);
        viewPager.setAdapter(fragAdapter);

    }

    public class FragAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        public FragAdapter(FragmentManager fragmentManager,List<Fragment> fragments) {
            super(fragmentManager);
            this.fragmentList=fragments;

        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                Toast.makeText(this, R.string.welcome, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
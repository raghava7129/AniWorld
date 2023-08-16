package com.example.raghavateam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class chat_nav extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_nav);

        initialize();
        ab=getSupportActionBar();
        ab.setTitle("CHAT");
         viewPagerAdapter viewPagerAdapter=new viewPagerAdapter(getSupportFragmentManager());

         viewPagerAdapter.addFragment(new groupFragment(),"Chat");
         viewPagerAdapter.addFragment(new userFragment(),"Users");

         viewPager.setAdapter(viewPagerAdapter);
         tabLayout.setupWithViewPager(viewPager);

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


    }

    public static class viewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public viewPagerAdapter(FragmentManager fm){
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }

        public CharSequence getPageTitle(int position){
            return titles.get(position);
        }


    }

    public void initialize(){
        tabLayout=findViewById(R.id.nav_bar);
        viewPager=findViewById(R.id.pager);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first page (FragmentA),
            // allow the system to handle the back button.
            super.onBackPressed();
        } else {
            // Otherwise, move to the previous page (FragmentA from FragmentB).
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }



}
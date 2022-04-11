package com.example.chatbox;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter2 extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public MyAdapter2(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               chatfrag1 chat1 = new  chatfrag1();
                return chat1;
            case 1:
                chatfrag2 chat2 = new  chatfrag2();
                return chat2;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}

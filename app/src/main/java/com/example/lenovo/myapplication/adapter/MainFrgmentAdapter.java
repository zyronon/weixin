package com.example.lenovo.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lenovo.myapplication.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by ttent on 2017/3/14.
 */
public class MainFrgmentAdapter extends FragmentPagerAdapter {

    private final ArrayList<BaseFragment> mBf;

    public MainFrgmentAdapter(FragmentManager fm, ArrayList<BaseFragment> mf) {
        super(fm);
        mBf = mf;
    }

    @Override
    public Fragment getItem(int position) {
        return mBf.get(position);
    }

    @Override
    public int getCount() {
        return mBf.size();
    }
}

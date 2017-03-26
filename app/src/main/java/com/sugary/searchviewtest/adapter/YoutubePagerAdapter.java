package com.sugary.searchviewtest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Ethan on 17/3/20.
 *
 */
public class YoutubePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public YoutubePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public YoutubePagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }



}

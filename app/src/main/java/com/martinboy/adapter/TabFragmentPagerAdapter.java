package com.martinboy.adapter;

import android.os.Bundle;
import android.util.Log;

import com.martinboy.fragment.RootFragment;
import com.martinboy.parameter.Constants;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public TabFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.mNumOfTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.SELECT_TAB, position);
        RootFragment tab1 = new RootFragment();
        tab1.setArguments(bundle);
        return tab1;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
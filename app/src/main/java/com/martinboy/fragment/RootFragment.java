package com.martinboy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.martinboy.netspider.R;
import com.martinboy.parameter.Constants;

import androidx.fragment.app.FragmentTransaction;

public class RootFragment extends BaseFragment {

    int selectTab;

    public static RootFragment newInstance() {
        return new RootFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root, container, false);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        if(getArguments() != null && getArguments().containsKey(Constants.SELECT_TAB)) {
            selectTab = getArguments().getInt(Constants.SELECT_TAB);
        }

        switch (selectTab) {
            case 0:
                try {
                    fragmentTransaction.replace(R.id.root_frame, LotteryFragment.class.newInstance());
                } catch (InstantiationException | IllegalAccessException | java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    fragmentTransaction.replace(R.id.root_frame, BankExchangeRateFragment.class.newInstance());
                } catch (InstantiationException | IllegalAccessException | java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
                break;
        }

        fragmentTransaction.commit();
        return view;
    }
}
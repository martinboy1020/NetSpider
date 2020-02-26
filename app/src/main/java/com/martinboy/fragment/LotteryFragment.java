package com.martinboy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.martinboy.activity.HomeActivity;
import com.martinboy.adapter.SuperLottoAdapter;
import com.martinboy.bean.SuperLottoBean;
import com.martinboy.managertool.CheckConnectStatusManager;
import com.martinboy.netspider.R;
import com.martinboy.presenter.LotteryInterface;
import com.martinboy.presenter.LotteryPresenter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class LotteryFragment extends BaseFragment implements LotteryInterface.View {

    private RecyclerView lotto_list;
    private SuperLottoAdapter superLottoAdapter;
    private LotteryPresenter lotteryPresenter;
    private SwipeRefreshLayout refresh_layout;
    private TabLayout tab_layout;
    private int nowIndex = 0;

    public static LotteryFragment newInstance(String s) {
        Bundle args = new Bundle();
        LotteryFragment fragment = new LotteryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.framgent_lottery, container, false);
        tab_layout = rootView.findViewById(R.id.tab_layout);
        refresh_layout = rootView.findViewById(R.id.refresh_layout);
        lotto_list = rootView.findViewById(R.id.lotto_list);
        lotto_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        superLottoAdapter = new SuperLottoAdapter(getActivity());
        lotto_list.setAdapter(superLottoAdapter);
        refresh_layout.setOnRefreshListener(onRefreshListener);

        TabLayout.Tab tab1 = tab_layout.newTab();
        tab1.setTag(0);
        tab1.setText("威力彩");
        TabLayout.Tab tab2 = tab_layout.newTab();
        tab2.setTag(1);
        tab2.setText("大樂透");
        tab_layout.addTab(tab1);
        tab_layout.addTab(tab2);
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Log.d("tag1", "onTabSelected tab: " + tab);

                if (tab.getTag() != null)
                    nowIndex = (Integer) tab.getTag();

                Log.d("tag1", "onTabSelected nowIndex: " + nowIndex);

                if (lotteryPresenter != null)
                    getLotteryData(nowIndex);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }

    private void getLotteryData(int nowIndex) {

        if (getActivity() != null && CheckConnectStatusManager.checkNetWorkConnect(getActivity())) {

            switch (nowIndex) {

                case 0:
                    lotteryPresenter.getSuperLotto638Data();
                    break;
                case 1:
                    lotteryPresenter.getSuperLotto649Data();
                    break;

            }

        } else {

            refresh_layout.setRefreshing(false);
            Toast.makeText(getActivity(), "目前無網路", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {

        if (lotteryPresenter == null)
            lotteryPresenter = new LotteryPresenter(this);

        if (getActivity() != null && CheckConnectStatusManager.checkNetWorkConnect(getActivity())) {

            if (lotteryPresenter != null)
                getLotteryData(nowIndex);


        } else {

            if (getActivity() instanceof HomeActivity) {

                HomeActivity homeActivity = (HomeActivity) getActivity();

                if (homeActivity.nowPosition == 0) {
                    Toast.makeText(getActivity(), "Lottery No Internet", Toast.LENGTH_SHORT).show();
                }

            }

        }

        super.onResume();
    }

    @Override
    public void changeData(ArrayList<SuperLottoBean> lottoList) {

        refresh_layout.setRefreshing(false);

        if (lottoList != null && superLottoAdapter != null) {
            superLottoAdapter.setList(lottoList);
        }

    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (getActivity() != null && CheckConnectStatusManager.checkNetWorkConnect(getActivity())) {
                if (lotteryPresenter != null)
                    getLotteryData(nowIndex);
            }
        }
    };

}

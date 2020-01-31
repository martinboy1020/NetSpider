package com.martinboy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.martinboy.activity.HomeActivity;
import com.martinboy.adapter.ExchangeRateAdapter;
import com.martinboy.bean.ExchangeBean;
import com.martinboy.managertool.CheckConnectStatusManager;
import com.martinboy.netspider.R;
import com.martinboy.presenter.ExchangeInterface;
import com.martinboy.presenter.ExchangePresenter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BankExchangeRateFragment extends BaseFragment implements ExchangeInterface.View {

    private ViewStub view_warning;
    private TextView text_warning;
    private ExchangePresenter exchangePresenter;
    private RecyclerView recycle_view_exchange_rate;
    private Spinner spinner_coin;
    private ExchangeRateAdapter adapter;
    private int nowPosition = 0;

    public static BankExchangeRateFragment newInstance(String s) {
        Bundle args = new Bundle();
        BankExchangeRateFragment fragment = new BankExchangeRateFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_bank_exchange_rate, container, false);
        view_warning = rootView.findViewById(R.id.view_warning);
        View viewWaring = view_warning.inflate();
        view_warning.setVisibility(View.GONE);
        text_warning = viewWaring.findViewById(R.id.text_warning);
        text_warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(nowPosition);
            }
        });

        spinner_coin = rootView.findViewById(R.id.spinner_coin);
        if (getActivity() != null) {
            ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                    getActivity(), R.array.coin_type_string_array, android.R.layout.simple_spinner_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_coin.setAdapter(spinnerAdapter);
        }
        spinner_coin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                nowPosition = position;
                getData(nowPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recycle_view_exchange_rate = rootView.findViewById(R.id.recycle_view_exchange_rate);
        recycle_view_exchange_rate.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExchangeRateAdapter(getActivity());
        recycle_view_exchange_rate.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {

        if (exchangePresenter == null)
            exchangePresenter = new ExchangePresenter(this);

        getData(nowPosition);

        super.onResume();
    }

    private void getData(int position) {

        if (getActivity() != null && CheckConnectStatusManager.checkNetWorkConnect(getActivity())) {

            String[] array = getActivity().getResources().getStringArray(R.array.coin_type_array);
            exchangePresenter.getExchangeRateData(array[position]);

        } else {

            view_warning.setVisibility(View.VISIBLE);
            text_warning.setText("No Internet");

            if (getActivity() instanceof HomeActivity) {

                HomeActivity homeActivity = (HomeActivity) getActivity();

                if (homeActivity.nowPosition == 1) {
                    Toast.makeText(getActivity(), "Bank No Internet", Toast.LENGTH_SHORT).show();
                }

            }

        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exchangePresenter != null) {
            exchangePresenter.destroy();
            exchangePresenter = null;
            System.gc();
        }
    }

    @Override
    public void getExchangeRateData(ArrayList<ExchangeBean> list) {

        if (adapter != null) {
            view_warning.setVisibility(View.GONE);
            adapter.setList(list);
        } else {
            view_warning.setVisibility(View.VISIBLE);
            text_warning.setText("No Data");
        }

    }
}

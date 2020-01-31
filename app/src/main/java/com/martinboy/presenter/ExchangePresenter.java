package com.martinboy.presenter;

import android.os.Handler;
import android.os.Message;

import com.martinboy.bean.ExchangeBean;

import java.util.ArrayList;

public class ExchangePresenter implements ExchangeInterface.Presenter {

    private ExchangeInterface.View exchangeView;
    private ExchangeModel exchangeModel;

    public ExchangePresenter(ExchangeInterface.View exchangeView) {
        this.exchangeView = exchangeView;
        exchangeModel = new ExchangeModel(this);
    }

    public void getExchangeRateData(String coinType) {
        exchangeModel.parseHtmlFromFindRate(coinType);
    }

    @Override
    public void returnExchangeData(ArrayList<ExchangeBean> list) {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = list;
        mHandler.sendMessage(msg);
    }

    public void destroy() {
        exchangeView = null;
        System.gc();
        if (exchangeModel != null) {
            exchangeModel.cancelTasks();
            exchangeModel = null;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                ArrayList<ExchangeBean> list = (ArrayList<ExchangeBean>) msg.obj;
                exchangeView.getExchangeRateData(list);
            }
        }
    };

}

package com.martinboy.presenter;

import android.os.Handler;
import android.os.Message;

import com.martinboy.bean.ExchangeBean;
import com.martinboy.model.ExchangeModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ExchangePresenter implements ExchangeInterface.Presenter {

    private ExchangeInterface.View exchangeView;
    private ExchangeModel exchangeModel;
    private ExchangeHandler mHandler;

    public ExchangePresenter(ExchangeInterface.View exchangeView) {
        this.exchangeView = exchangeView;
        mHandler = new ExchangeHandler(exchangeView);
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

    @Override
    public void returnBankOnlineChange(ExchangeBean bean) {
        Message msg = new Message();
        msg.what = 1;
        msg.obj = bean;
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

//    private void getBestBankOnlineChange(ArrayList<ExchangeBean> list) {
//        if(exchangeModel != null)
//            exchangeModel.getBestBankOnlineChange(list);
//    }

    private static class ExchangeHandler extends Handler {

        WeakReference<ExchangeInterface.View> wk;

        ExchangeHandler(ExchangeInterface.View exchangeView) {
            wk = new WeakReference<>(exchangeView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ExchangeInterface.View exchangeView = wk.get();

            if (msg.what == 0) {

                ArrayList<ExchangeBean> list = (ArrayList<ExchangeBean>) msg.obj;

                if (exchangeView != null) {

                    exchangeView.getExchangeRateData(list);

                    for (ExchangeBean bean : list) {
                        if (!bean.getMoneySell().equals("--")) {
                            exchangeView.returnBankMoneyChange(bean);
                            break;
                        }
                    }

                }
            }

        }
    }

}

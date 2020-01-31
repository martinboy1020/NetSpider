package com.martinboy.presenter;

import com.martinboy.bean.ExchangeBean;

import java.util.ArrayList;

public class ExchangeInterface {

    public interface View{
        void getExchangeRateData(ArrayList<ExchangeBean> list);
    }

    public interface Presenter{
        void returnExchangeData(ArrayList<ExchangeBean> list);
    }

}

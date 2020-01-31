package com.martinboy.presenter;

import com.martinboy.bean.SuperLottoBean;

import java.util.ArrayList;

public class LotteryInterface {

    public interface View {

        void changeData(ArrayList<SuperLottoBean> lottoList);

    }

    public interface Presenter {

        void getSuperLottoData(ArrayList<SuperLottoBean> lottoList);

    }

}

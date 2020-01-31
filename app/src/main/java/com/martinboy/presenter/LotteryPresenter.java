package com.martinboy.presenter;

import android.os.Handler;
import android.os.Message;

import com.martinboy.bean.SuperLottoBean;

import java.util.ArrayList;

public class LotteryPresenter implements LotteryInterface.Presenter {

    private LotteryInterface.View lotteryView;

    public LotteryPresenter(LotteryInterface.View lotteryView) {
        this.lotteryView = lotteryView;
    }

    public void getSuperLotto638Data() {
        LotteryModel.parseSuperLotto638(this);
    }

    @Override
    public void getSuperLottoData(ArrayList<SuperLottoBean> lottoList) {
        if (lottoList != null) {
            Message message = new Message();
            message.what = 0;
            message.obj = lottoList;
            mHandler.sendMessage(message);
        } else {
            mHandler.sendEmptyMessage(1);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                ArrayList<SuperLottoBean> lottoList = (ArrayList<SuperLottoBean>) msg.obj;
                lotteryView.changeData(lottoList);
            } else {
                lotteryView.changeData(null);
            }
        }
    };

}

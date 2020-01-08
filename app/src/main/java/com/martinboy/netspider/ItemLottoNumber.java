package com.martinboy.netspider;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ItemLottoNumber extends LinearLayout {

    private TextView text_lotto_num;

    public ItemLottoNumber(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_lotto_num, this);
        text_lotto_num = findViewById(R.id.text_lotto_num);
    }

    public ItemLottoNumber(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_lotto_num, this);
        text_lotto_num = findViewById(R.id.text_lotto_num);
    }

    public void setLottoNumber(String num) {
        text_lotto_num.setText(num);
    }

    public void setLottoNumberColor(int color) {
        text_lotto_num.setTextColor(color);
    }

}

package com.martinboy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.martinboy.bean.ExchangeBean;
import com.martinboy.netspider.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExchangeRateAdapter extends RecyclerView.Adapter<ExchangeRateAdapter.ViewHolder> {

    private ArrayList<ExchangeBean> list;
    private Context context;

    public ExchangeRateAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchange_rate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.text_bank_name
                .setText(String.format(context.getResources().getString(R.string.string_bank_name), list.get(holder.getAdapterPosition()).getBankName()));
        holder.text_moneyBuy
                .setText(String.format(context.getResources().getString(R.string.string_money_buy), list.get(holder.getAdapterPosition()).getMoneyBuy()));
        holder.text_moneySell
                .setText(String.format(context.getResources().getString(R.string.string_money_sell), list.get(holder.getAdapterPosition()).getMoneySell()));
        holder.text_nowBuy
                .setText(String.format(context.getResources().getString(R.string.string_now_buy), list.get(holder.getAdapterPosition()).getNowBuy()));
        holder.text_nowSell
                .setText(String.format(context.getResources().getString(R.string.string_now_sell), list.get(holder.getAdapterPosition()).getMoneySell()));
    }

    @Override
    public int getItemCount() {

        if (list != null)
            return list.size();

        return 0;
    }

    public void setList(ArrayList<ExchangeBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_bank_name, text_moneyBuy, text_moneySell, text_nowBuy, text_nowSell;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_bank_name = itemView.findViewById(R.id.text_bank_name);
            text_moneyBuy = itemView.findViewById(R.id.text_moneyBuy);
            text_moneySell = itemView.findViewById(R.id.text_moneySell);
            text_nowBuy = itemView.findViewById(R.id.text_nowBuy);
            text_nowSell = itemView.findViewById(R.id.text_nowSell);
        }
    }
}

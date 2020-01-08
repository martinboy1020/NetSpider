package com.martinboy.netspider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SuperLottoAdapter extends RecyclerView.Adapter<SuperLottoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SuperLotto> superLottos;

    public SuperLottoAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SuperLottoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_super_lotto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuperLottoAdapter.ViewHolder holder, int position) {

        if(holder.itemView.getTag() != null && holder.itemView.getTag().equals(superLottos.get(holder.getAdapterPosition()))) {

            final SuperLotto superLotto = (SuperLotto) holder.itemView.getTag();
            holder.layout_first_section.removeAllViews();

            holder.text_drew_term.setText("期數: " + superLotto.getDrawTerm());
            holder.text_date.setText("兌獎日期: " + superLotto.getDate());
            holder.text_end_date.setText("最後兌換日期: " + superLotto.getEdate());
            holder.text_sell_amount.setText("銷售金額: " + superLotto.getSellAmount());
            holder.text_total.setText("獎金總額: " + superLotto.getTotal());

            ArrayList<String> arrayList = superLottos.get(holder.getAdapterPosition()).getFirstSectionNumberList();

            for(String num : arrayList) {
                ItemLottoNumber lottoNumber = new ItemLottoNumber(context);
                lottoNumber.setLottoNumber(num);
                holder.layout_first_section.addView(lottoNumber);
            }

            ItemLottoNumber lottoNumber2 = new ItemLottoNumber(context);
            lottoNumber2.setLottoNumber(superLotto.getSecondSectionNum());
            lottoNumber2.setLottoNumberColor(ContextCompat.getColor(context, android.R.color.holo_red_light));

            holder.layout_first_section.addView(lottoNumber2);


        } else {

            holder.itemView.setTag(superLottos.get(holder.getAdapterPosition()));
            holder.layout_first_section.removeAllViews();
            SuperLotto superLotto = superLottos.get(holder.getAdapterPosition());
            holder.text_drew_term.setText("期數: " + superLotto.getDrawTerm());
            holder.text_date.setText("兌獎日期: " + superLotto.getDate());
            holder.text_end_date.setText("最後兌換日期: " + superLotto.getEdate());
            holder.text_sell_amount.setText("銷售金額: " + superLotto.getSellAmount());
            holder.text_total.setText("獎金總額: " + superLotto.getTotal());

            ArrayList<String> arrayList = superLottos.get(holder.getAdapterPosition()).getFirstSectionNumberList();

            for(String num : arrayList) {
                ItemLottoNumber lottoNumber = new ItemLottoNumber(context);
                lottoNumber.setLottoNumber(num);
                holder.layout_first_section.addView(lottoNumber);
            }

            ItemLottoNumber lottoNumber2 = new ItemLottoNumber(context);
            lottoNumber2.setLottoNumber(superLotto.getSecondSectionNum());
            lottoNumber2.setLottoNumberColor(ContextCompat.getColor(context, android.R.color.holo_red_light));

            holder.layout_first_section.addView(lottoNumber2);

        }

    }

    @Override
    public int getItemCount() {
        return superLottos != null ? superLottos.size() : 0;
    }

    public void setList(ArrayList<SuperLotto> list) {
        this.superLottos = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_drew_term, text_date, text_end_date, text_sell_amount, text_total;
        LinearLayout layout_first_section;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_first_section = itemView.findViewById(R.id.layout_first_section);
            text_drew_term = itemView.findViewById(R.id.text_drew_term);
            text_date = itemView.findViewById(R.id.text_date);
            text_end_date = itemView.findViewById(R.id.text_end_date);
            text_sell_amount = itemView.findViewById(R.id.text_sell_amount);
            text_total = itemView.findViewById(R.id.text_total);
        }
    }

}

package com.martinboy.model;

import android.os.AsyncTask;

import com.martinboy.bean.SuperLottoBean;
import com.martinboy.parameter.Constants;
import com.martinboy.presenter.LotteryInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Lotto649Model {

    public static void parseSuperLotto649(final LotteryInterface.Presenter lotteryPresenterImpl) {

        ParseLotteryTask parseLotteryTask = new ParseLotteryTask(lotteryPresenterImpl);
        parseLotteryTask.execute();

    }

    private static SuperLottoBean collectionLottoNumber(int index, Elements elements) {

        SuperLottoBean superLotto = new SuperLottoBean();
        ArrayList<String> superLottoFirstSectionList = new ArrayList<>();

//        Log.d("tag1", "649 elements: " + elements.text());

        for (Element element : elements) {

            Element drawTerm = element.getElementById(Constants.Lottory649.stringSuperLottoTitle2 + Constants.Lottory649.stringDrawTerm + index);
            Element date = element.getElementById(Constants.Lottory649.stringSuperLottoTitle2 + Constants.Lottory649.stringDate + index);
            Element eDate = element.getElementById(Constants.Lottory649.stringSuperLottoTitle2 + Constants.Lottory649.stringEDate + index);
            Element sellAmount = element.getElementById(Constants.Lottory649.stringSuperLottoTitle2 + Constants.Lottory649.stringSellAmount + index);
            Element total = element.getElementById(Constants.Lottory649.stringSuperLottoTitle + Constants.Lottory649.stringTotal + index);

            superLotto.setDate(date.hasText() ? date.text() : "");
            superLotto.setEdate(eDate.hasText() ? eDate.text() : "");
            superLotto.setDrawTerm(drawTerm.hasText() ? drawTerm.text() : "");
            superLotto.setSellAmount(sellAmount.hasText() ? sellAmount.text() : "");
            superLotto.setTotal(total.hasText() ? total.text() : "");

            Element s1 = element.getElementById(Constants.Lottory649.stringSuperLottoTitle + Constants.Lottory649.stringNo1 + index);
            Element s2 = element.getElementById(Constants.Lottory649.stringSuperLottoTitle + Constants.Lottory649.stringNo2 + index);
            Element s3 = element.getElementById(Constants.Lottory649.stringSuperLottoTitle + Constants.Lottory649.stringNo3 + index);
            Element s4 = element.getElementById(Constants.Lottory649.stringSuperLottoTitle + Constants.Lottory649.stringNo4 + index);
            Element s5 = element.getElementById(Constants.Lottory649.stringSuperLottoTitle + Constants.Lottory649.stringNo5 + index);
            Element s6 = element.getElementById(Constants.Lottory649.stringSuperLottoTitle + Constants.Lottory649.stringNo6 + index);
            Element s7 = element.getElementById(Constants.Lottory649.stringSuperLottoTitle + Constants.Lottory649.stringNo7 + index);

            if (s1 != null && s1.hasText()) {
                superLottoFirstSectionList.add(s1.text());
            }

            if (s2 != null && s2.hasText()) {
                superLottoFirstSectionList.add(s2.text());
            }

            if (s3 != null && s3.hasText()) {
                superLottoFirstSectionList.add(s3.text());
            }

            if (s4 != null && s4.hasText()) {
                superLottoFirstSectionList.add(s4.text());
            }

            if (s5 != null && s5.hasText()) {
                superLottoFirstSectionList.add(s5.text());
            }

            if (s6 != null && s6.hasText()) {
                superLottoFirstSectionList.add(s6.text());
            }

            if (s7 != null && s7.hasText()) {
                superLotto.setSecondSectionNum(s7.text());
            }

            if (superLottoFirstSectionList.size() == 6) {
                break;
            }

        }

        superLotto.setFirstSectionNumberList(superLottoFirstSectionList);

        return superLotto;

    }

    public static class ParseLotteryTask extends AsyncTask<Void, Void, ArrayList<SuperLottoBean>> {

        LotteryInterface.Presenter lotteryPresenterImpl;

        public ParseLotteryTask(LotteryInterface.Presenter lotteryPresenterImpl) {
            this.lotteryPresenterImpl = lotteryPresenterImpl;
        }

        @Override
        protected ArrayList<SuperLottoBean> doInBackground(Void... voids) {

            final ArrayList<SuperLottoBean> lotteryList = new ArrayList<>();

            try {

                Document document = Jsoup.connect("https://www.taiwanlottery.com.tw/Lotto/Lotto649/history.aspx").timeout(5000).get();
                final Elements elements = document.select("table");

                if (!elements.isEmpty()) {

                    for (int i = 0; i < 10; i++) {
                        SuperLottoBean superLotto = collectionLottoNumber(i, elements);
                        if (superLotto != null)
                            lotteryList.add(superLotto);
                    }

                    return lotteryList;

                } else {
                    return null;
                }

            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<SuperLottoBean> lottoArrayList) {
            super.onPostExecute(lottoArrayList);
            lotteryPresenterImpl.getSuperLottoData(lottoArrayList);
        }
    }


}

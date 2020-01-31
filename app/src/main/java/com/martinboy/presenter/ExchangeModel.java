package com.martinboy.presenter;

import android.util.Log;
import android.util.SparseArray;

import com.martinboy.bean.ExchangeBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ExchangeModel {

    private ExchangeInterface.Presenter presenterInterface;
    private Thread thread;

    public ExchangeModel(ExchangeInterface.Presenter presenterInterface) {
        this.presenterInterface = presenterInterface;
    }

    public void parseHtmlFromFindRate(final String coinType) {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                SparseArray<String> bankMap = null;
                Document document = null;
                ArrayList<ExchangeBean> moneyList = new ArrayList<>();

                try {
                    document = Jsoup.connect("https://www.findrate.tw/" + coinType + "/#.Xhl0h5Mzab8").timeout(5000).get();

                    if (document != null) {

                        Elements elements = document.select("td.bank");

                        if (!elements.isEmpty()) {
                            Log.d("tag1", "1elements: " + elements.text());
                            bankMap = parseBankMap(elements);
                        }

                    }

                    if (document != null && bankMap != null && bankMap.size() > 0) {

                        Elements elements = document.select("td.WordB");
                        if (!elements.isEmpty()) {
                            Log.d("tag1", "2elements: " + elements.text());
                            moneyList = parseMoneyList(elements, bankMap);
                        }

                    }

                    if (presenterInterface != null)
                        presenterInterface.returnExchangeData(moneyList);

                } catch (IOException e) {
                    e.printStackTrace();
                    if (presenterInterface != null)
                        presenterInterface.returnExchangeData(null);
                }

            }
        });

        thread.start();

    }

    private SparseArray<String> parseBankMap(Elements elements) {

        SparseArray<String> sparseArray = new SparseArray<>();

        for (int i = 0; i < elements.size(); i++) {
            sparseArray.put(i + 1, elements.get(i).text());
        }

        return sparseArray;

    }

    private ArrayList<ExchangeBean> parseMoneyList(Elements elements, SparseArray<String> bankMap) {

        int count = 1;
        ArrayList<ExchangeBean> arrayList = new ArrayList<>();
        ExchangeBean bean = null;

        for (int i = 1; i < elements.size(); i++) {

            int target = i % 6;

            if (target == 0) {
                bean = new ExchangeBean();
                bean.setBankName(bankMap.get(count));
            }

            switch (target) {
                case 0:
                    bean.setMoneyBuy(elements.get(i).text());
                    break;
                case 1:
                    if (bean != null) {
                        bean.setMoneySell(elements.get(i).text());
                    }
                    break;
                case 2:
                    if (bean != null) {
                        bean.setNowBuy(elements.get(i).text());
                    }
                    break;
                case 3:
                    if (bean != null) {
                        bean.setNowSell(elements.get(i).text());
                    }
                    break;
                case 4:
                    if (bean != null) {
                        bean.setRefreshDate(elements.get(i).text());
                    }
                    break;
                case 5:
                    if (bean != null) {
                        bean.setFee(elements.get(i).text());
                        arrayList.add(bean);
                    }
                    break;
            }

            if (target == 0) {
                count++;
            }

        }

        return arrayList;

    }

    public void cancelTasks() {
        // TODO 终止线程池ThreadPool.shutDown()，AsyncTask.cancel()，或者调用框架的取消任务api

    }

}

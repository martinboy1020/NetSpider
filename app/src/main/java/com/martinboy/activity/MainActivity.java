package com.martinboy.activity;

import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import com.martinboy.adapter.SuperLottoAdapter;
import com.martinboy.bean.SuperLottoBean;
import com.martinboy.netspider.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView lotto_list;
    SuperLottoAdapter superLottoAdapter;
    ArrayList<SuperLottoBean> superLottos = new ArrayList<>();

    private static final String stringSuperLottoTitle = "SuperLotto638Control_history1_dlQuery_";
    private static final String stringDate = "Date_";
    private static final String stringEDate = "EDate_";
    private static final String stringDrawTerm = "DrawTerm_";
    private static final String stringSellAmount = "SellAmount_";
    private static final String stringTotal = "Total_";
    private static final String stringNo1 = "No1_";
    private static final String stringNo2 = "No2_";
    private static final String stringNo3 = "No3_";
    private static final String stringNo4 = "No4_";
    private static final String stringNo5 = "No5_";
    private static final String stringNo6 = "No6_";
    private static final String stringNo7 = "No7_";

    private static final String stringMoneyBuy = "MoneyBuy";
    private static final String stringMoneySell = "MoneySell";
    private static final String stringNowBuy = "NowBuy";
    private static final String stringNowSell = "NowSell";
    private static final String stringRefreshDate = "RefreshDate";
    private static final String stringFee = "Fee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lotto_list = findViewById(R.id.lotto_list);
        lotto_list.setLayoutManager(new LinearLayoutManager(this));
        superLottoAdapter = new SuperLottoAdapter(this);
        lotto_list.setAdapter(superLottoAdapter);
        parseHtmlFromJPY();
        parseHtmlFromSuperLotto638();
    }

    private void parseHtmlFromJPY() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                SparseArray<String> bankMap = null;
                Document document = null;
                HashMap<String, String> moneyMap = new HashMap<>();

                try {
                    document = Jsoup.connect("https://www.findrate.tw/JPY/#.Xhl0h5Mzab8").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (document != null) {

                    Elements elements = document.select("td.bank");

                    if (!elements.isEmpty()) {

                        bankMap = parseBankMap(elements);

                    } else {
                        Toast.makeText(MainActivity.this, "No Find", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                if (document != null && bankMap != null && bankMap.size() > 0) {

                    Elements elements = document.select("td.WordB");

                    if (!elements.isEmpty()) {

                       moneyMap = parseMoneyMap(elements);

                    } else {
                        Toast.makeText(MainActivity.this, "No Find", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                if (moneyMap == null && moneyMap.size() == 0)
                    return;

//                for (Map.Entry<String, String> stringStringEntry : moneyMap.entrySet()) {
//                    Log.d("tag1", "The key is: " + ((Map.Entry) stringStringEntry).getKey()
//                            + ",value is :" + ((Map.Entry) stringStringEntry).getValue());
//                }

            }
        }).start();

    }

    private SparseArray<String> parseBankMap(Elements elements) {

        SparseArray<String> sparseArray = new SparseArray<>();

        for (int i = 0; i < elements.size(); i++) {
            sparseArray.put(i + 1, elements.get(i).text());
        }

        return sparseArray;

    }

    private HashMap<String, String> parseMoneyMap(Elements elements) {

        int count = 1;
        HashMap<String, String> map = new HashMap<>();

        for (int i = 1; i < elements.size(); i++) {

            int target = i % 6;

            switch (target) {
                case 0:
                    map.put(stringMoneyBuy + count, elements.get(i).text());
                    break;
                case 1:
                    map.put(stringMoneySell + count, elements.get(i).text());
                    break;
                case 2:
                    map.put(stringNowBuy + count, elements.get(i).text());
                    break;
                case 3:
                    map.put(stringNowSell + count, elements.get(i).text());
                    break;
                case 4:
                    map.put(stringDate + count, elements.get(i).text());
                    break;
                case 5:
                    map.put(stringFee + count, elements.get(i).text());
                    break;
            }

            if (target == 0) {
                count++;
            }

        }

        return map;

    }

    private void parseHtmlFromSuperLotto638() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Document document = Jsoup.connect("https://www.taiwanlottery.com.tw/lotto/superlotto638/history.aspx").get();
//                    Document document1 = Jsoup.parse(new URL("https://www.taiwanlottery.com.tw/result_all.aspx").openStream(), "Big5", "https://www.taiwanlottery.com.tw/result_all.aspx");
                    final Elements elements = document.select("table");

                    if (!elements.isEmpty()) {

                        for (int i = 0; i < 10; i++) {
                            SuperLottoBean superLotto = collectionLottoNumber(i, elements);
                            if (superLotto != null)
                                superLottos.add(superLotto);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (superLottoAdapter != null) {
                                    superLottoAdapter.setList(superLottos);
                                }
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "No Find", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    private SuperLottoBean collectionLottoNumber(int index, Elements elements) {

        SuperLottoBean superLotto = new SuperLottoBean();
        ArrayList<String> superLottoFirstSectionList = new ArrayList<>();

        for (Element element : elements) {

            Element drawTerm = element.getElementById(stringSuperLottoTitle + stringDrawTerm + index);
            Element date = element.getElementById(stringSuperLottoTitle + stringDate + index);
            Element eDate = element.getElementById(stringSuperLottoTitle + stringEDate + index);
            Element sellAmount = element.getElementById(stringSuperLottoTitle + stringSellAmount + index);
            Element total = element.getElementById(stringSuperLottoTitle + stringTotal + index);

            if (!date.hasText()) {
                break;
            }

            superLotto.setDate(date.hasText() ? date.text() : "");
            superLotto.setEdate(eDate.hasText() ? eDate.text() : "");
            superLotto.setDrawTerm(drawTerm.hasText() ? drawTerm.text() : "");
            superLotto.setSellAmount(sellAmount.hasText() ? sellAmount.text() : "");
            superLotto.setTotal(total.hasText() ? total.text() : "");

            Element s1 = element.getElementById(stringSuperLottoTitle + stringNo1 + index);
            Element s2 = element.getElementById(stringSuperLottoTitle + stringNo2 + index);
            Element s3 = element.getElementById(stringSuperLottoTitle + stringNo3 + index);
            Element s4 = element.getElementById(stringSuperLottoTitle + stringNo4 + index);
            Element s5 = element.getElementById(stringSuperLottoTitle + stringNo5 + index);
            Element s6 = element.getElementById(stringSuperLottoTitle + stringNo6 + index);
            Element s7 = element.getElementById(stringSuperLottoTitle + stringNo7 + index);

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

    //    private void testParseString() {
//
//        String html = "\"\"\n" +
//                "        <!DOCTYPE html>\n" +
//                "        <html lang=\"zh-tw\">\n" +
//                "        <head>\n" +
//                "            <meta charset=\"UTF-8\">\n" +
//                "            <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
//                "            <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
//                "            <title>JSOUP Demo</title>\n" +
//                "        </head>\n" +
//                "        <body>\n" +
//                "            <p>This is jsoup demo by blog.tonycube.com</p>\n" +
//                "        </body>\n" +
//                "        </html>\n" +
//                "    \"\"";
//
//        Document document = Jsoup.parse(html);
//        Element element = document.select("p").first();
//        if (element != null)
//            text_test.setText(element.text());
//
//    }

//    private void testParseHtmlFromUrl() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    Document document = Jsoup.connect("https://blog.tonycube.com/").get();
//                    final Elements elements = document.select("h1.blog-post-title");
//                    if (!elements.isEmpty()) {
//                        Log.d("tag1", "element size(): " + elements.size());
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                            }
//                        });
//
//                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                text_test.setText("找不到文章標題");
//                            }
//                        });
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//
//
//    }


}

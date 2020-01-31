package com.martinboy.bean;

public class ExchangeBean {

    private String bankName;
    private int index;
    private String moneyBuy;
    private String moneySell;
    private String nowBuy;
    private String nowSell;
    private String refreshDate;
    private String fee;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMoneyBuy() {
        return moneyBuy;
    }

    public void setMoneyBuy(String moneyBuy) {
        this.moneyBuy = moneyBuy;
    }

    public String getMoneySell() {
        return moneySell;
    }

    public void setMoneySell(String moneySell) {
        this.moneySell = moneySell;
    }

    public String getNowBuy() {
        return nowBuy;
    }

    public void setNowBuy(String nowBuy) {
        this.nowBuy = nowBuy;
    }

    public String getNowSell() {
        return nowSell;
    }

    public void setNowSell(String nowSell) {
        this.nowSell = nowSell;
    }

    public String getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(String refreshDate) {
        this.refreshDate = refreshDate;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}

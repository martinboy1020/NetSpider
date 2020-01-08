package com.martinboy.netspider;

import java.util.ArrayList;

public class SuperLotto {

    private ArrayList<String> firstSectionNumberList;
    private String secondSectionNum;
    private String date;
    private String eDate;
    private String drawTerm;
    private String sellAmount;
    private String total;

    public ArrayList<String> getFirstSectionNumberList() {
        return firstSectionNumberList;
    }

    public void setFirstSectionNumberList(ArrayList<String> firstSectionNumberList) {
        this.firstSectionNumberList = firstSectionNumberList;
    }

    public String getSecondSectionNum() {
        return secondSectionNum;
    }

    public void setSecondSectionNum(String secondSectionNum) {
        this.secondSectionNum = secondSectionNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEdate() {
        return eDate;
    }

    public void setEdate(String eDate) {
        this.eDate = eDate;
    }

    public String getDrawTerm() {
        return drawTerm;
    }

    public void setDrawTerm(String drawTerm) {
        this.drawTerm = drawTerm;
    }

    public String getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(String sellAmount) {
        this.sellAmount = sellAmount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}

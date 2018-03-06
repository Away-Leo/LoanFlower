package com.pingxun.loanflower.data;

import java.io.Serializable;

/**
 * Created by LH on 2018/1/18.
 * Describe:贷款计算器计算结果
 */

public class CalculatorResult implements Serializable{
    private String monthNum;
    private double yueGongBenJin;
    private double yueGongLixi;
    private double haiQianJin;


    public String getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(String monthNum) {
        this.monthNum = monthNum;
    }

    public double getYueGongBenJin() {
        return yueGongBenJin;
    }

    public void setYueGongBenJin(double yueGongBenJin) {
        this.yueGongBenJin = yueGongBenJin;
    }

    public double getYueGongLixi() {
        return yueGongLixi;
    }

    public void setYueGongLixi(double yueGongLixi) {
        this.yueGongLixi = yueGongLixi;
    }

    public double getHaiQianJin() {
        return haiQianJin;
    }

    public void setHaiQianJin(double haiQianJin) {
        this.haiQianJin = haiQianJin;
    }
}

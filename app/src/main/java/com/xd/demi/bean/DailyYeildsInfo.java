package com.xd.demi.bean;

import java.util.ArrayList;

/**
 * Created by demi on 2017/6/21.
 */

public class DailyYeildsInfo {
    private ArrayList<Profits> HS300;
    private ArrayList<Profits> PORTFOLIO;

    public ArrayList<Profits> getHS300() {
        return HS300;
    }

    public void setHS300(ArrayList<Profits> HS300) {
        this.HS300 = HS300;
    }

    public ArrayList<Profits> getPORTFOLIO() {
        return PORTFOLIO;
    }

    public void setPORTFOLIO(ArrayList<Profits> PORTFOLIO) {
        this.PORTFOLIO = PORTFOLIO;
    }
}

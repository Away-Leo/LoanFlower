package com.pingxun.loanflower.ui.activity;


import com.pingxun.loanflower.R;
import com.pingxun.loanflower.ui.fragment.HomeFragment;
import com.pingxun.loanflower.ui.fragment.MineFragment;

public enum MainTab {

    FRAGMENT1(0, R.string.home_tab_loan, R.drawable.activity_main_home, HomeFragment.class),
//    FRAGMENT2(1, R.string.home_tab_accurate, R.drawable.activity_main_accurate, AccurateFragment.class),
//    FRAGMENT2(1, R.string.home_tab_calculator, R.drawable.activity_main_calculator, CalculatorFragment.class),
//    FRAGMENT3(2,R.string.home_tab_card,R.drawable.activity_main_credit_card, CreditCardFragment.class),
    FRAGMENT2(1, R.string.home_tab_mine, R.drawable.activity_main_mine, MineFragment.class);

    private final int index;
    private final int resName;
    private final int resIcon;
    private final Class<?> clazz;

    MainTab(int index, int resName, int resIcon, Class<?> clazz) {
        this.index = index;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clazz = clazz;
    }

    public int getIndex() {
        return index;
    }

    public int getResName() {
        return resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public Class<?> getClazz() {
        return clazz;
    }

}

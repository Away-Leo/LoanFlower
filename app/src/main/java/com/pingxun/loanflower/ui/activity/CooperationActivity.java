package com.pingxun.loanflower.ui.activity;


import android.annotation.SuppressLint;

import com.pingxun.loanflower.R;
import com.pingxun.loanflower.base.BaseActivity;
import com.pingxun.loanflower.databinding.ActivityCooperationBinding;


public class CooperationActivity extends BaseActivity<ActivityCooperationBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cooperation;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        initTopView("商务合作");
    }


}

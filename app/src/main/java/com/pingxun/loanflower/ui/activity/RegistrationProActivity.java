package com.pingxun.loanflower.ui.activity;


import com.pingxun.loanflower.R;
import com.pingxun.loanflower.base.BaseActivity;
import com.pingxundata.answerliu.pxcore.databinding.ActivityRegistrationProBinding;


public class RegistrationProActivity extends BaseActivity<ActivityRegistrationProBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registration_pro;
    }

    @Override
    protected void initData() {
         initTopView("注册协议");
    }


}

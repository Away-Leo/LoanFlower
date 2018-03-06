package com.pingxun.loanflower.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.pingxun.loanflower.R;
import com.pingxun.loanflower.base.App;
import com.pingxun.loanflower.base.BaseActivity;
import com.pingxun.loanflower.databinding.ActivityUserInfoThirdBinding;
import com.pingxun.loanflower.http.ServerApi;
import com.pingxun.loanflower.other.InitDatas;
import com.pingxundata.answerliu.pxcore.other.Constant;
import com.pingxundata.answerliu.pxcore.other.EventMessage;
import com.pingxundata.pxmeta.http.PXHttp;
import com.pingxundata.pxmeta.pojo.RequestResult;
import com.pingxundata.pxmeta.utils.ActivityUtil;
import com.pingxundata.pxmeta.utils.MyTools;
import com.pingxundata.pxmeta.utils.NetUtil;
import com.pingxundata.pxmeta.utils.SharedPrefsUtil;
import com.pingxundata.pxmeta.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import static com.pingxun.loanflower.other.RequestFlag.UPDATA_USER_INFO;


public class UserInfoThirdActivity extends BaseActivity<ActivityUserInfoThirdBinding> implements PXHttp.OnResultHandler,View.OnClickListener{



    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info_third;
    }

    @Override
    protected void initData() {
        initTopView("个人信息");
        readUserInfo();
        changeShap();

        bindingView.btnNext.setOnClickListener(this);
        bindingView.edWechatQuota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeShap();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        bindingView.edAlipayNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeShap();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next://去贷款
                keepUserInfo();
                ServerApi.updateUserInfo(App.getAppContext(),UserInfoThirdActivity.this);
                break;
        }
    }

    /**
     * 从SP中读取用户信息
     */
    private void readUserInfo() {
        bindingView.swCard.setOpened(SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME,InitDatas.UserIsCard,false));
        bindingView.swLoan.setOpened(SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME,InitDatas.UserIsLoan,false));
        bindingView.edWechatQuota.setText(SharedPrefsUtil.getValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserWechatQuota,""));
        bindingView.edWechatQuota.setSelection(bindingView.edWechatQuota.getText().length());//将光标移到末尾
        bindingView.edAlipayNum.setText(SharedPrefsUtil.getValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserAlipayNum,""));
        bindingView.edAlipayNum.setSelection(bindingView.edAlipayNum.getText().length());//将光标移到末尾
    }

    /**
     * 保存用户信息到SP中
     */
    private void keepUserInfo() {
        SharedPrefsUtil.putValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserIsCard,bindingView.swCard.isOpened());
        SharedPrefsUtil.putValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserIsLoan,bindingView.swLoan.isOpened());
        SharedPrefsUtil.putValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserWechatQuota, MyTools.getEdittextStr(bindingView.edWechatQuota));
        SharedPrefsUtil.putValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserAlipayNum, MyTools.getEdittextStr(bindingView.edAlipayNum));
    }


    /**
     * 判断下一步按钮是否变换shap
     */
    private void changeShap() {
        if (!TextUtils.isEmpty(MyTools.getEdittextStr(bindingView.edWechatQuota)) &&!TextUtils.isEmpty(MyTools.getEdittextStr(bindingView.edAlipayNum))) {
            bindingView.btnNext.setBackgroundResource(R.drawable.shap_login);
            bindingView.btnNext.setEnabled(true);
        } else {
            bindingView.btnNext.setBackgroundResource(R.drawable.shap_login_half_transparent);
            bindingView.btnNext.setEnabled(false);
        }
    }

    @Override
    public void onResult(RequestResult requestResult, String jsonStr, int flag) {
        switch (flag){
            case UPDATA_USER_INFO:
                try {
                    if (requestResult.isSuccess()){
                           EventBus.getDefault().post(new EventMessage(Constant.CloseUserInfoOne));
                           EventBus.getDefault().post(new EventMessage(Constant.CloseUserInfoTwo));
                           ActivityUtil.goForward(me,ProductListActivity.class,true);
                    }else {
                           ToastUtils.showToast(App.getAppContext(),"修改信息失败，请稍后再试！");
                       }
                   }catch (Exception e){
                    ToastUtils.showToast(App.getAppContext(),"修改信息失败，请稍后再试！");
                }
                break;
        }
    }

    @Override
    public void onError(int flag) {
        if (NetUtil.getNetWorkState(App.getAppContext())==-1){
            ToastUtils.showToast(App.getAppContext(), "修改信息失败,网络未连接，请检查您的网络设置!");
        }else {
            ToastUtils.showToast(App.getAppContext(), "修改信息失败，请稍后再试！");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

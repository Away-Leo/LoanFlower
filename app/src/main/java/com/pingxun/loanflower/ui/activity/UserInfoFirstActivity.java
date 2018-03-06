package com.pingxun.loanflower.ui.activity;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.orhanobut.logger.Logger;
import com.pingxun.loanflower.R;
import com.pingxun.loanflower.base.App;
import com.pingxun.loanflower.base.BaseActivity;
import com.pingxun.loanflower.databinding.ActivityUserInfoFirstBinding;
import com.pingxun.loanflower.http.ServerApi;
import com.pingxun.loanflower.other.InitDatas;
import com.pingxundata.answerliu.pxcore.data.ServerModelObject;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import static com.pingxun.loanflower.other.RequestFlag.GET_USER_INFO;
import static com.pingxun.loanflower.other.RequestFlag.UPDATA_USER_INFO;


public class UserInfoFirstActivity extends BaseActivity<ActivityUserInfoFirstBinding> implements PXHttp.OnResultHandler,View.OnClickListener {

    private ArrayList<String> huJiList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        EventBus.getDefault().register(this);//绑定事件接受
        return R.layout.activity_user_info_first;
    }

    @Override
    protected void initData() {
        initTopView("个人信息");
        huJiList.add(0, "本地户籍");
        huJiList.add(1, "外地户籍");
        bindingView.rlHuji.setOnClickListener(this);
        bindingView.btnNext.setOnClickListener(this);
        ServerApi.getUserInfo(UserInfoFirstActivity.this);
        bindingView.edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeShap();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        bindingView.edIdNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeShap();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        bindingView.edAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeShap();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_huji://户籍选项
                MyTools.hideInputSoftFromWindowMethod(App.getAppContext(), view);
                OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(me, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        bindingView.tvHuji.setText(huJiList.get(options1));
                        changeShap();
                    }
                }).build();
                optionsPickerView.setPicker(huJiList);
                optionsPickerView.show();
                break;
            case R.id.btn_next://下一步
                keepUserInfo();
                ServerApi.updateUserInfo(App.getAppContext(),UserInfoFirstActivity.this);
                break;
        }
    }






    /**
     * 保存用户所有信息到SP中
     *
     * @param nameStr          姓名
     * @param idNumStr         身份证号
     * @param hujiStr          户籍
     * @param addressStr       现居地
     * @param education        教育程度
     * @param socialIdentity   社会身份
     * @param income           收入
     * @param isSocial         有无社保
     * @param isFund           有无公积金
     * @param isCreditCard     有无信用卡
     * @param isHouseOrCarLoan 有无房/车贷
     * @param wechatAmount     微粒贷额度
     * @param sesameCredit     芝麻信用分
     */
    private void keepAllUserInfo(String nameStr, String idNumStr, String hujiStr, String addressStr, String education, String socialIdentity, String income, boolean isSocial, boolean isFund, boolean isCreditCard, boolean isHouseOrCarLoan, String wechatAmount, String sesameCredit) {
        //基本信息
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserName, nameStr);
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserId, idNumStr);
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserHuJi, hujiStr);
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserAddress, addressStr);
        //职业信息
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserEducation, education);
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserJob, socialIdentity);
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserIncome, income);
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserIsInsurance, isSocial);
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserIsAccumulation, isFund);
        //信用信息
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserIsCard, isCreditCard);
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserIsLoan, isHouseOrCarLoan);
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserWechatQuota, wechatAmount);
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserAlipayNum, sesameCredit);
    }

    /**
     * 保存该步骤中用户录入的信息
     */
    private void keepUserInfo() {
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserName, MyTools.getEdittextStr(bindingView.edName));
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserId, MyTools.getEdittextStr(bindingView.edIdNum));
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserHuJi, MyTools.getTextViewStr(bindingView.tvHuji));
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserAddress, MyTools.getEdittextStr(bindingView.edAddress));
    }

    /**
     * 初始化界面
     */
    private void initView() {
        bindingView.edName.setText(SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserName, ""));
        bindingView.edName.setSelection(bindingView.edName.getText().length());//将光标移到末尾
        bindingView.edIdNum.setText(SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserId, ""));
        bindingView.edIdNum.setSelection(bindingView.edIdNum.getText().length());
        bindingView.edAddress.setText(SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserAddress, ""));
        bindingView.edAddress.setSelection(bindingView.edAddress.getText().length());
        bindingView.tvHuji.setText(SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserHuJi, "请选择"));
        changeShap();
    }

    /**
     * 判断下一步按钮变换shape
     */
    private void changeShap() {
        if (!(bindingView.tvHuji.getText().equals("请选择")) && !TextUtils.isEmpty(MyTools.getEdittextStr(bindingView.edName)) && !TextUtils.isEmpty(MyTools.getEdittextStr(bindingView.edIdNum)) && !TextUtils.isEmpty(MyTools.getEdittextStr(bindingView.edAddress))) {
            bindingView.btnNext.setBackgroundResource(R.drawable.shap_login);
            bindingView.btnNext.setEnabled(true);
        } else {
            bindingView.btnNext.setBackgroundResource(R.drawable.shap_login_half_transparent);
            bindingView.btnNext.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//注销事件接受
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(EventMessage message) {
        if (message.message.equals(Constant.CloseUserInfoOne)) {
            finish();
        }
    }

    @Override
    public void onResult(RequestResult requestResult, String jsonStr, int flag) {
        switch (flag) {
            case GET_USER_INFO:
                try {
                    Logger.json(jsonStr);
                    if (requestResult.isSuccess()) {
                        ServerModelObject userInfo= (ServerModelObject) requestResult.getEntityResult();
                        String name = userInfo.getName();
                        String idNum = userInfo.getCertNo();
                        String huji = userInfo.getDomicilePlace();
                        String address = userInfo.getAddress();
                        String education = userInfo.getEducation();
                        String socialIdentity = userInfo.getSocialIdentity();
                        String income = String.valueOf(userInfo.getIncome());
                        boolean isSocial = userInfo.isIsSocial();
                        boolean isFund = userInfo.isIsFund();
                        boolean isCreditCard = userInfo.isIsCreditCard();
                        boolean isHouseOrCarLoan = userInfo.isIsHouseOrCarLoan();
                        String wechatAmount = String.valueOf(userInfo.getWechatAmount());
                        String sesameCredit = String.valueOf(userInfo.getSesameCredit());
                        keepAllUserInfo(name, idNum, huji, address, education, socialIdentity, income, isSocial, isFund, isCreditCard, isHouseOrCarLoan, wechatAmount, sesameCredit);
                        initView();
                    } else {
                        SharedPrefsUtil.putValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserIsLogin,false);
                        ActivityUtil.goForward(me,LoginActivity.class,null,true);
                    }
                } catch (Exception e) {
                    SharedPrefsUtil.putValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserIsLogin,false);
                    ActivityUtil.goForward(me,LoginActivity.class,null,true);
                }
                break;
            case UPDATA_USER_INFO:
                try {
                    if (requestResult.isSuccess()) {
                        ActivityUtil.goForward(me, UserInfoSencondActivity.class, null, false);
                    } else {
                        ToastUtils.showToast(App.getAppContext(), "修改信息失败，请稍后再试！");
                    }
                } catch (Exception e){
                    ToastUtils.showToast(App.getAppContext(), "修改信息失败，请稍后再试！");
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




}

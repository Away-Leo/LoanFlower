package com.pingxun.loanflower.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.pingxun.loanflower.R;
import com.pingxun.loanflower.base.App;
import com.pingxun.loanflower.base.BaseActivity;
import com.pingxun.loanflower.databinding.ActivityUserInfoSencondBinding;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.pingxun.loanflower.other.RequestFlag.UPDATA_USER_INFO;


public class UserInfoSencondActivity extends BaseActivity<ActivityUserInfoSencondBinding> implements PXHttp.OnResultHandler,View.OnClickListener{

    private List<String> educationList = new ArrayList<>();
    private List<String> jobList=new ArrayList<>();


    @Override
    protected int getLayoutId() {
        EventBus.getDefault().register(this);//绑定事件接受
        return R.layout.activity_user_info_sencond;
    }

    @Override
    protected void initData() {
        initTopView("个人信息");

        educationList.add(0,"初中及以下");
        educationList.add(1,"高中");
        educationList.add(2,"大专");
        educationList.add(3,"本科");
        educationList.add(4,"研究生及以上");

        jobList.add(0,"上班族");
        jobList.add(1,"个体");
        jobList.add(2,"企业主");
        jobList.add(3,"学生");
        jobList.add(4,"自由职业");

        bindingView.rlEducation.setOnClickListener(this);
        bindingView.rlJob.setOnClickListener(this);
        bindingView.btnNext.setOnClickListener(this);

        readUserInfo();
        changeShap();

        bindingView.edIncome.addTextChangedListener(new TextWatcher() {
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

            case R.id.rl_education://教育程度选项
                MyTools.hideInputSoftFromWindowMethod(App.getAppContext(), view);
                OptionsPickerView educationPickerView = new OptionsPickerView.Builder(me, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        bindingView.tvEducation.setText(educationList.get(options1));
                        changeShap();
                    }
                }).build();
                educationPickerView.setPicker(educationList);
                educationPickerView.show();
                break;
            case R.id.rl_job://社会身份选项
                MyTools.hideInputSoftFromWindowMethod(App.getAppContext(), view);
                OptionsPickerView jobPickerView = new OptionsPickerView.Builder(me, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        bindingView.tvJob.setText(jobList.get(options1));
                        changeShap();
                    }
                }).build();
                jobPickerView.setPicker(jobList);
                jobPickerView.show();
                break;
            case R.id.btn_next://下一步
                keepUserInfo();
                ServerApi.updateUserInfo(App.getAppContext(),UserInfoSencondActivity.this);

                break;
        }
    }




    /**
     * 从SP中读取用户信息
     */
    private void readUserInfo() {
        bindingView.tvEducation.setText(SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME,InitDatas.UserEducation,"请选择"));
        bindingView.tvJob.setText(SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME,InitDatas.UserJob,"请选择"));
        bindingView.edIncome.setText(SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME,InitDatas.UserIncome,""));
        bindingView.edIncome.setSelection(bindingView.edIncome.getText().length());//将光标移到末尾
        bindingView.swInsurance.setOpened(SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME,InitDatas.UserIsInsurance,false));
        bindingView.swAccumulation.setOpened(SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME,InitDatas.UserIsAccumulation,false));
    }

    /**
     * 保存该步骤用户录入的信息
     */
    private void keepUserInfo() {
        SharedPrefsUtil.putValue(App.getAppContext(), InitDatas.SP_NAME,InitDatas.UserEducation, MyTools.getTextViewStr(bindingView.tvEducation));
        SharedPrefsUtil.putValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserJob, MyTools.getTextViewStr(bindingView.tvJob));
        SharedPrefsUtil.putValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserIncome, MyTools.getEdittextStr(bindingView.edIncome));
        SharedPrefsUtil.putValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserIsInsurance,bindingView.swInsurance.isOpened());
        SharedPrefsUtil.putValue(App.getAppContext(),InitDatas.SP_NAME,InitDatas.UserIsAccumulation,bindingView.swAccumulation.isOpened());
    }



    /**
     * 判断下一步按钮是否变换shap
     */
    private void changeShap() {
        if (!(bindingView.tvEducation.getText().equals("请选择")) &&!(bindingView.tvJob.getText().equals("请选择"))&& !TextUtils.isEmpty(MyTools.getEdittextStr(bindingView.edIncome))) {
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
        if (message.message.equals(Constant.CloseUserInfoTwo)) {
            finish();
        }
    }

    @Override
    public void onResult(RequestResult requestResult, String jsonStr, int flag) {
        switch (flag) {
            case UPDATA_USER_INFO:
                try {
                    if (requestResult.isSuccess()) {
                        ActivityUtil.goForward(me, UserInfoThirdActivity.class, null, false);
                    } else {
                        ToastUtils.showToast(App.getAppContext(), "修改信息失败，请稍后再试！");
                    }
                } catch (Exception e) {
                    ToastUtils.showToast(App.getAppContext(), "修改信息失败，请稍后再试！");
                }
                break;
        }
    }

    @Override
    public void onError(int flag) {
        if (NetUtil.getNetWorkState(App.getAppContext())== -1){
            ToastUtils.showToast(App.getAppContext(), "修改信息失败,网络未连接，请检查您的网络设置!");
        }else {
            ToastUtils.showToast(App.getAppContext(), "修改信息失败，请稍后再试！");
        }
    }




}

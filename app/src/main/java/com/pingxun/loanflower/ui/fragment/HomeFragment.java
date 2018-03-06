package com.pingxun.loanflower.ui.fragment;

import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pingxun.loanflower.R;
import com.pingxun.loanflower.base.App;
import com.pingxun.loanflower.base.BaseFragment;
import com.pingxun.loanflower.databinding.FragmentLoanBinding;
import com.pingxun.loanflower.ui.activity.MsgListActivity;
import com.pingxun.loanflower.ui.activity.ProductListActivity;
import com.pingxundata.pxmeta.http.PXHttp;
import com.pingxundata.pxmeta.pojo.RequestResult;
import com.pingxundata.pxmeta.utils.ActivityUtil;
import com.pingxundata.pxmeta.utils.MyTools;
import com.pingxundata.pxmeta.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;


/**
 * Created by LH on 2017/9/6.
 * Describe:首页Fragment
 */

public class HomeFragment extends BaseFragment<FragmentLoanBinding> implements PXHttp.OnResultHandler, View.OnClickListener, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private int loan=1000;
    private ArrayList<String> timeLimitList=new ArrayList<>();


    @Override
    protected int getRootLayoutResID() {
        return R.layout.fragment_loan;
    }

    @Override
    protected void initData() {
        bindingView.ivSubtract.setOnClickListener(this);
        bindingView.ivAdd.setOnClickListener(this);
        bindingView.tvChooseTimeLimit.setOnClickListener(this);
        bindingView.rlMsg.setOnClickListener(this);
        bindingView.tvApply.setOnClickListener(this);
        bindingView.tvLoan.setText(loan+"元");
        bindingView.tvMinLoan.setText("最小额度1000元");
        bindingView.tvMaxLoan.setText("最大额度50000元");
        for (int i=0;i<12;i++){
           timeLimitList.add(i,(i+1)+"个月");
        }
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_subtract://减号
                if (loan<=1000){
                    ToastUtils.showToast(App.getAppContext(),"贷款金额不能小于最小额度!");
                }else {
                    loan-=1000;
                    bindingView.tvLoan.setText(loan+"元");
                }
                break;
            case R.id.iv_add://加号
                if (loan>=50000){
                    ToastUtils.showToast(App.getAppContext(),"贷款金额不能大于最大额度!");
                }else {
                    loan+=1000;
                    bindingView.tvLoan.setText(loan+"元");
                }
                break;
            case R.id.tv_choose_time_limit://期限选择
                MyTools.hideInputSoftFromWindowMethod(App.getAppContext(), view);
                OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(mActivity, (options1, options2, options3, v) -> {
                    bindingView.tvChooseTimeLimit.setText(timeLimitList.get(options1));
                }).build();
                optionsPickerView.setPicker(timeLimitList);
                optionsPickerView.show();
                break;
            case R.id.tv_apply://立即申请
                ActivityUtil.goForward(mActivity, ProductListActivity.class,null,false);
                break;

            case R.id.rl_msg://消息
                ActivityUtil.goForward(mActivity, MsgListActivity.class,null,false);
                break;
        }
    }


    @Override
    public void onResult(RequestResult requestResult, String jsonStr, int flag) {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }


    @Override
    public void onLoadMoreRequested() {

    }



}

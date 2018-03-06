package com.pingxun.loanflower.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.pingxun.loanflower.R;
import com.pingxun.loanflower.base.App;
import com.pingxun.loanflower.base.BaseActivity;
import com.pingxun.loanflower.databinding.ActivityMsgInfoBinding;
import com.pingxun.loanflower.http.ServerApi;
import com.pingxun.loanflower.other.InitDatas;
import com.pingxundata.answerliu.pxcore.data.ServerModelObject;
import com.pingxundata.answerliu.pxcore.other.EventMessage;
import com.pingxundata.answerliu.pxcore.view.EmptyLayout;
import com.pingxundata.pxmeta.http.PXHttp;
import com.pingxundata.pxmeta.pojo.RequestResult;
import com.pingxundata.pxmeta.utils.NetUtil;

import org.greenrobot.eventbus.EventBus;

import static com.pingxun.loanflower.other.RequestFlag.GET_INFOMATION_INFO;


/**
 * Created by LH on 2017/8/31.
 * Describe:消息详情Activity
 */

public class MsgInfoActivity extends BaseActivity<ActivityMsgInfoBinding> implements PXHttp.OnResultHandler,View.OnClickListener {

    private String sId;//消息ID
    private int position;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_msg_info;
    }

    @Override
    protected void initData() {
        bindingView.tvTopviewTitle.setText("消息详情");
        bindingView.ivTopviewBack.setOnClickListener(this);
        bindingView.emptyLayout.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sId = bundle.getString(InitDatas.INFOR_ID);
            position=bundle.getInt("position");
            bindingView.emptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            ServerApi.getInfomationInfo(MsgInfoActivity.this,sId);
        }
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.empty_layout:
              bindingView.emptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
              ServerApi.getInfomationInfo(MsgInfoActivity.this,sId);
              break;
          case R.id.iv_topview_back:
              finish();
              EventBus.getDefault().post(new EventMessage("notifyItemChanged",position));
              break;
      }
    }


    @Override
    public void onResult(RequestResult requestResult, String jsonStr, int flag) {
        switch (flag) {
            case GET_INFOMATION_INFO:
                try {
                    if (requestResult.isSuccess()) {
                        ServerModelObject serverModelObject= (ServerModelObject) requestResult.getEntityResult();
                        bindingView.tvTitle.setText(serverModelObject.getTitle());
                        bindingView.tvTime.setText(serverModelObject.getSendDate());
                        bindingView.tvContent.setText(serverModelObject.getContent());
                        bindingView.emptyLayout.setErrorType(EmptyLayout.NO_ERROR);
                    } else {
                        bindingView.emptyLayout.setErrorType(EmptyLayout.ERROR);
                    }
                } catch (Exception e) {
                    bindingView.emptyLayout.setErrorType(EmptyLayout.ERROR);
                }
                break;
        }
    }

    @Override
    public void onError(int flag) {
        if (NetUtil.getNetWorkState(App.getAppContext()) == -1) {
            bindingView.emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        } else {
            bindingView.emptyLayout.setErrorType(EmptyLayout.ERROR);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new EventMessage("notifyItemChanged",position));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

package com.pingxun.loanflower.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pingxun.loanflower.R;
import com.pingxun.loanflower.adapter.MsgListAdapter;
import com.pingxun.loanflower.base.App;
import com.pingxun.loanflower.base.BaseActivity;
import com.pingxun.loanflower.databinding.ActivityMsgListBinding;
import com.pingxun.loanflower.http.ServerApi;
import com.pingxun.loanflower.other.InitDatas;
import com.pingxundata.answerliu.pxcore.data.ApplyListBean;
import com.pingxundata.answerliu.pxcore.other.Constant;
import com.pingxundata.answerliu.pxcore.other.EventMessage;
import com.pingxundata.pxmeta.http.PXHttp;
import com.pingxundata.pxmeta.pojo.RequestResult;
import com.pingxundata.pxmeta.utils.ActivityUtil;
import com.pingxundata.pxmeta.utils.NetUtil;
import com.pingxundata.pxmeta.utils.SharedPrefsUtil;
import com.pingxundata.pxmeta.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.pingxun.loanflower.other.RequestFlag.LOADMORE;
import static com.pingxun.loanflower.other.RequestFlag.REFRESH;


/**
 * Created by LH on 2017/8/31.
 * Describe:消息Fragment
 */

public class MsgListActivity extends BaseActivity<ActivityMsgListBinding> implements PXHttp.OnResultHandler, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {



    private MsgListAdapter mAdapter;
    private List<ApplyListBean> mList;//消息集合

    private View notDataView;//无数据View
    private View errorView;//服务器返回数据异常View
    private View notNetView;//网络异常View;
    private View noLoginView;//未登录View;

    private int mCurrentCounter = 0;//上一次加载的个数
    private int TOTAL_COUNTER;//总数
    private int page = 1;



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//注销事件接受
    }


    @Override
    protected int getLayoutId() {
        EventBus.getDefault().register(this);//绑定事件接受
        return R.layout.activity_msg_list;
    }

    @Override
    protected void initData() {
        initTopView("消息中心");
        initAdapter();
        bindingView.swipeContainer.setColorSchemeResources(R.color.tab_font_bright);
        bindingView.swipeContainer.setOnRefreshListener(this);
        onRefresh();
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        notDataView = me.getLayoutInflater().inflate(R.layout.view_empty, (ViewGroup) bindingView.rv.getParent(), false);
        errorView = me.getLayoutInflater().inflate(R.layout.view_error, (ViewGroup) bindingView.rv.getParent(), false);
        notNetView = me.getLayoutInflater().inflate(R.layout.view_notnet, (ViewGroup) bindingView.rv.getParent(), false);
        noLoginView = me.getLayoutInflater().inflate(R.layout.view_no_login, (ViewGroup) bindingView.rv.getParent(), false);

        bindingView.rv.setLayoutManager(new LinearLayoutManager(App.getAppContext()));
        mAdapter = new MsgListAdapter(R.layout.rv_item_msg, mList);
        mAdapter.setOnLoadMoreListener(this, bindingView.rv);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        bindingView.rv.setAdapter(mAdapter);
        bindingView.rv.setHasFixedSize(true);
        bindingView.rv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserIsLogin, false)) {
                    ActivityUtil.recallGoForward(me,LoginActivity.class,MsgListActivity.class,null,true );
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(InitDatas.INFOR_ID, String.valueOf(mList.get(position).getId()));
                    bundle.putInt("position",position);
                    ActivityUtil.goForward(me, MsgInfoActivity.class, bundle, false);
                }
            }
        });
        errorView.setOnClickListener(view -> ActivityUtil.recallGoForward(me,LoginActivity.class,MsgListActivity.class,null,true ));
        notDataView.setOnClickListener(v -> onRefresh());
        notNetView.setOnClickListener(v -> onRefresh());
        noLoginView.setOnClickListener(v -> ActivityUtil.recallGoForward(me,LoginActivity.class,MsgListActivity.class,null,true ));
    }

    @Override
    public void onResult(RequestResult requestResult, String jsonStr, int flag) {
        switch (flag) {
            case REFRESH:
                try {
                    if (requestResult.isSuccess()) {
                        TOTAL_COUNTER = requestResult.getTotalElements();
                        mList = (List<ApplyListBean>) requestResult.getResultList();
                        if (mList.size() == 0) {
                            mAdapter.setNewData(null);
                            mAdapter.setEmptyView(notDataView);
                        } else {
                            mAdapter.setNewData(mList);
                            mAdapter.disableLoadMoreIfNotFullPage();
                            mCurrentCounter = mAdapter.getData().size();
                        }
                    } else {
                        mAdapter.setNewData(null);
                        mAdapter.setEmptyView(noLoginView);
                    }
                } catch (Exception e) {
                    mAdapter.setNewData(null);
                    mAdapter.setEmptyView(errorView);
                }
                bindingView.swipeContainer.setRefreshing(false);
                break;

            case LOADMORE:
                try {
                    if (requestResult.isSuccess()) {
                        mAdapter.loadMoreComplete();
                        List<ApplyListBean> mListMore;
                        mListMore = (List<ApplyListBean>) requestResult.getResultList();
                        mAdapter.addData(mListMore);
                        mCurrentCounter = mAdapter.getData().size();
                    }
                } catch (Exception e) {
                    mAdapter.loadMoreFail();
                }
                break;
        }
    }

    @Override
    public void onError(int flag) {
        mAdapter.setNewData(null);
        bindingView.swipeContainer.setRefreshing(false);
        if (NetUtil.getNetWorkState(App.getAppContext()) == -1) {
            mAdapter.setEmptyView(notNetView);
        } else {
            mAdapter.setEmptyView(errorView);
        }
    }

    @Override
    public void onRefresh() {
        bindingView.swipeContainer.setRefreshing(true);
        bindingView.swipeContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                page=1;
                ServerApi.getInformationList(MsgListActivity.this,page, REFRESH);
            }
        }, InitDatas.waitTime);

    }



    @Override
    public void onLoadMoreRequested() {
        int page_size = 10;
        if (mCurrentCounter < page_size) {
            mAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
                mAdapter.loadMoreEnd(false);
            } else {
                if (NetUtil.getNetWorkState(App.getAppContext()) != -1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page++;
                            ServerApi.getInformationList(MsgListActivity.this,page, LOADMORE);
                        }
                    },1000);
                } else {
                    ToastUtils.showToast(App.getAppContext(), Constant.NO_NETWORK_MSG);
                    mAdapter.loadMoreFail();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(EventMessage message) {
        if (message.message.equals("notifyItemChanged")) {
            int position=message.status;
            mList.get(position).setRead(true);
            mAdapter.notifyItemChanged(position);//RecyclerView更新指定下标数据
        }
        if (message.message.equals(Constant.RefreshInformation)){
            onRefresh();
        }
    }


}

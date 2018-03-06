package com.pingxun.loanflower.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pingxun.loanflower.R;
import com.pingxun.loanflower.adapter.ProductListAdapter;
import com.pingxun.loanflower.base.App;
import com.pingxun.loanflower.base.BaseActivity;
import com.pingxun.loanflower.databinding.ActivityMyProductListBinding;
import com.pingxun.loanflower.http.ServerApi;
import com.pingxun.loanflower.other.InitDatas;
import com.pingxundata.answerliu.pxcore.data.ProductListBean;
import com.pingxundata.answerliu.pxcore.data.ServerModelList;
import com.pingxundata.answerliu.pxcore.other.Constant;
import com.pingxundata.answerliu.pxcore.other.CustomAnimation;
import com.pingxundata.answerliu.pxcore.view.ListPopup;
import com.pingxundata.pxmeta.http.PXHttp;
import com.pingxundata.pxmeta.pojo.RequestResult;
import com.pingxundata.pxmeta.utils.ActivityUtil;
import com.pingxundata.pxmeta.utils.NetUtil;
import com.pingxundata.pxmeta.utils.SharedPrefsUtil;
import com.pingxundata.pxmeta.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import static com.pingxun.loanflower.other.RequestFlag.GET_ZS_TYPE;
import static com.pingxun.loanflower.other.RequestFlag.LOADMORE;
import static com.pingxun.loanflower.other.RequestFlag.REFRESH;

/**
 * Created by LH on 2018/1/19.
 * Describe:
 */

public class ProductListActivity extends BaseActivity<ActivityMyProductListBinding> implements PXHttp.OnResultHandler, View.OnClickListener, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private ListPopup mListPopup;
    private List<ServerModelList> mTypeList;
    private ProductListAdapter mAdapter;
    private List<ProductListBean> mList;//产品集合
    private View notDataView;//无数据View
    private View errorView;//网络异常View
    private View notNetView;//网络异常View;
    private int page_size = 10;//每一次请求加载的条数
    private int mCurrentCounter = 0;//上一次加载的个数
    private int TOTAL_COUNTER;//总数
    private int page = 1;

    private String zsType = "";//综合指数参数


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_product_list;
    }

    @Override
    protected void initData() {
        initAdapter();
        bindingView.tvMessageTip.setText(InitDatas.messageTip);
        bindingView.ivTopviewBack.setOnClickListener(this);
        bindingView.tvType.setOnClickListener(this);
        bindingView.smartRefreshLayout.setEnableHeaderTranslationContent(false);
        bindingView.smartRefreshLayout.setOnRefreshListener(this);
        bindingView.smartRefreshLayout.autoRefresh();
        ServerApi.getZsType(ProductListActivity.this);
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        notDataView = getLayoutInflater().inflate(R.layout.view_empty, (ViewGroup) bindingView.rv.getParent(), false);
        errorView = getLayoutInflater().inflate(R.layout.view_error, (ViewGroup) bindingView.rv.getParent(), false);
        notNetView = getLayoutInflater().inflate(R.layout.view_notnet, (ViewGroup) bindingView.rv.getParent(), false);

        bindingView.rv.setLayoutManager(new LinearLayoutManager(App.getAppContext()));
        mAdapter = new ProductListAdapter(R.layout.rv_item_product_style_two, mList);
        mAdapter.setOnLoadMoreListener(this, bindingView.rv);
        mAdapter.openLoadAnimation(new CustomAnimation());

        bindingView.rv.setAdapter(mAdapter);
        bindingView.rv.setHasFixedSize(true);
        bindingView.rv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                isLoginToProuctInfo(String.valueOf(mList.get(position).getId()));
            }
        });

        errorView.setOnClickListener(view -> bindingView.smartRefreshLayout.autoRefresh());
        notDataView.setOnClickListener(v -> bindingView.smartRefreshLayout.autoRefresh());
        notNetView.setOnClickListener(v -> bindingView.smartRefreshLayout.autoRefresh());

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_topview_back:
                finish();
                break;
            case R.id.tv_type:
                mListPopup.setPopupWindowFullScreen(true);
                mListPopup.showPopupWindow(bindingView.ivTopviewBack);
                break;
        }
    }


    @Override
    public void onResult(RequestResult requestResult, String jsonStr, int flag) {
        switch (flag) {
            case GET_ZS_TYPE:
                try {
                    if (requestResult.isSuccess()) {
                        mTypeList = (List<ServerModelList>) requestResult.getResultList();
                        ListPopup.Builder builder = new ListPopup.Builder(me);
                        for (int i = 0; i < mTypeList.size(); i++) {
                            builder.addItem(mTypeList.get(i).getName());
                        }
                        mListPopup = builder.build();
                        bindingView.tvType.setEnabled(true);
                        mListPopup.setOnListPopupItemClickListener(position -> {
                            bindingView.tvType.setText(mTypeList.get(position).getName());
                            zsType=mTypeList.get(position).getCode();
                            bindingView.smartRefreshLayout.autoRefresh();
                            mListPopup.dismiss();
                        });
                    } else {
                        bindingView.tvType.setEnabled(false);
                    }
                } catch (Exception e) {
                    bindingView.tvType.setEnabled(false);
                }
                break;

            case REFRESH://下拉刷新返回数据的回调
                try {
                    if (requestResult.isSuccess()) {
                        TOTAL_COUNTER = requestResult.getTotalElements();
                        mList = (List<ProductListBean>) requestResult.getResultList();
                        if (mList.size() == 0) {
                            mAdapter.setNewData(null);
                            mAdapter.setEmptyView(notDataView);
                        } else {
                            mAdapter.setNewData(mList);
                            mAdapter.disableLoadMoreIfNotFullPage();
                            bindingView.rv.scrollToPosition(0);
                            mCurrentCounter = mAdapter.getData().size();
                        }
                    } else {
                        mAdapter.setNewData(null);
                        mAdapter.setEmptyView(errorView);
                    }
                } catch (Exception e) {
                    mAdapter.setNewData(null);
                    mAdapter.setEmptyView(errorView);
                }
                bindingView.smartRefreshLayout.finishRefresh();
                break;
            case LOADMORE://上拉加载返回数据的回调
                try {
                    if (requestResult.isSuccess()) {
                        mAdapter.loadMoreComplete();
                        List<ProductListBean> mListMore;
                        mListMore = (List<ProductListBean>) requestResult.getResultList();
                        mAdapter.addData(mListMore);
                        mCurrentCounter = mAdapter.getData().size();
                    } else {
                        mAdapter.loadMoreFail();
                    }
                } catch (Exception e) {
                    mAdapter.loadMoreFail();
                }
                break;
        }
    }

    @Override
    public void onError(int i) {
        bindingView.tvType.setEnabled(false);
        mAdapter.setNewData(null);
        bindingView.smartRefreshLayout.finishRefresh();
        if (NetUtil.getNetWorkState(App.getAppContext()) == -1) {
            mAdapter.setEmptyView(notNetView);
        } else {
            mAdapter.setEmptyView(errorView);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshlayout.getLayout().postDelayed(() -> {
            page = 1;
            ServerApi.postProductSearch(App.getAppContext(),ProductListActivity.this,page, REFRESH,zsType, InitDatas.LeftCode);
        },InitDatas.waitTime);
    }


    @Override
    public void onLoadMoreRequested() {
        if (mCurrentCounter < page_size) {
            mAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
                mAdapter.loadMoreEnd(false);
            } else {
                if (NetUtil.getNetWorkState(App.getAppContext()) !=-1) {
                    new Handler().postDelayed(() -> {
                        page++;
                        ServerApi.postProductSearch(App.getAppContext(),ProductListActivity.this,page, LOADMORE,zsType,InitDatas.LeftCode);
                    },1000);
                } else {
                    ToastUtils.showToast(App.getAppContext(), Constant.NO_NETWORK_MSG);
                    mAdapter.loadMoreFail();
                }
            }
        }
    }


    /**
     * 判断是否登录，跳转到产品详情页
     */
    private void isLoginToProuctInfo(String sId) {
        if (!SharedPrefsUtil.getValue(App.getAppContext(), InitDatas.SP_NAME, InitDatas.UserIsLogin, false)) {
            Bundle bundle = new Bundle();
            bundle.putString(InitDatas.INFOR_ID, sId);
            ActivityUtil.recallGoForward(me, LoginActivity.class, ProductInfoActivity.class, bundle, false);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(InitDatas.INFOR_ID, sId);
            ActivityUtil.goForward(me, ProductInfoActivity.class, bundle, false);
        }
    }
}

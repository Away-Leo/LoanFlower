//package com.pingxun.loanflower.ui.fragment;
//
//import android.annotation.SuppressLint;
//import android.support.annotation.UiThread;
//import android.view.View;
//import android.widget.SeekBar;
//
//import com.pingxun.loanflower.R;
//import com.pingxun.loanflower.base.BaseFragment;
//import com.pingxun.loanflower.databinding.FragmentAccurateBinding;
//import com.pingxun.loanflower.http.ServerApi;
//import com.pingxun.loanflower.other.InitDatas;
//import com.pingxundata.answerliu.pxcore.data.ServerModelList;
//import com.pingxundata.answerliu.pxcore.data.ServerModelObject;
//import com.pingxundata.answerliu.pxcore.view.EmptyLayout;
//import com.pingxundata.pxcore.utils.WechatBanner;
//import com.pingxundata.pxmeta.http.PXHttp;
//import com.pingxundata.pxmeta.pojo.RequestResult;
//import com.pingxundata.pxmeta.utils.ActivityUtil;
//import com.pingxundata.pxmeta.utils.MyTools;
//import com.pingxundata.pxmeta.utils.NetUtil;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.pingxun.loanflower.other.RequestFlag.GET_FIND_PARAMETER;
//import static com.pingxun.loanflower.other.RequestFlag.GET_WX_BANNER;
//
//
///**
// * Created by LH on 2017/9/6.
// * Describe:精准Fragment
// */
//
//public class AccurateFragment extends BaseFragment<FragmentAccurateBinding> implements PXHttp.OnResultHandler,OnRefreshListener,View.OnClickListener {
//
//
//    private ArrayList<String> jobList=new ArrayList<>();
//    private int mLimitLow;//最低期限
//    private int mLimitHeight;//最高期限
//    private int progressData;//拖动条拖动的比例
//    private String mLoanUnitStr;
//    @Override
//    protected int getRootLayoutResID() {
//        return R.layout.fragment_accurate;
//    }
//
//    @Override
//    protected void initData() {
//        bindingView.swipeContainer.setOnRefreshListener(this);
//        bindingView.rulerView.setValue(13000.0f, 0.0f, 200000.0f, 1000.0f);//设置选中值、最小值、最大值、单位值
//        bindingView.emptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
//        initListener();
//
//    }
//
//    @Override
//    protected void onLazyLoadOnce() {
//        super.onLazyLoadOnce();
//        bindingView.swipeContainer.autoRefresh();
//    }
//
//    private void initListener() {
//
//        bindingView.btnNext.setOnClickListener(this);
//        bindingView.emptyLayout.setOnClickListener(this);
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_next://下一步
//                InitDatas.loanType="";
//                ActivityUtil.goForward(mActivity, ProductListActivity.class,null,false);
//                break;
//
//            case R.id.empty_layout://空布局
//                bindingView.emptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
//                bindingView.swipeContainer.autoRefresh();
//                break;
//        }
//    }
//
//
//
//    @Override
//    public void onRefresh(RefreshLayout refreshlayout) {
//        ServerApi.getParameter(AccurateFragment.this);
//    }
//    @Override
//    public void onResult(RequestResult requestResult, String jsonStr, int flag) {
//        switch (flag){
//            case GET_FIND_PARAMETER:
//                try {
//                    ServerModelObject moneyParamBean;
//                    if (requestResult.isSuccess()){
//                        moneyParamBean=(ServerModelObject)requestResult.getEntityResult();
////                        InitDatas.limitLowAmount = new BigDecimal(moneyParamBean.getData().getStartAmount() + "").intValue(); // 贷款最低金额
//                        InitDatas.limitHeightAmount = new BigDecimal(moneyParamBean.getEndAmount() + "").intValue();  //贷款最高金额
//                        InitDatas.dayStart = moneyParamBean.getStartPeriodDay() + "天"; //日 单位 起始
//                        InitDatas.dayEnd = moneyParamBean.getEndPeriodDay() + "天"; //日 单位 结束
//                        InitDatas.mounthStart = moneyParamBean.getStartPeriodMonth() + "月"; //月 单位 开始
//                        InitDatas.mounthEnd = moneyParamBean.getEndPeriodMonth() + "月"; //月 单位 结束
//                        InitDatas.rateYear = BigDecimal.valueOf(moneyParamBean.getLoanRate());  //年利率
//                        InitDatas.rateDay = InitDatas.rateYear.divide(new BigDecimal(100)).divide(new BigDecimal(360));  //日利率
//                        InitDatas.rateMounth = InitDatas.rateDay.multiply(new BigDecimal(30)); //月利率
//                        runinUI(InitDatas.loanDate);
//                    }
//                }catch (Exception e){
//                    e.getMessage();
//                }
//                ServerApi.getWxBanner(mActivity,AccurateFragment.this);
//                break;
//            case GET_WX_BANNER:
//                try {
//                    if (requestResult.isSuccess()){
//                        List<ServerModelList> mList=(List<ServerModelList>)requestResult.getResultList();
//                        WechatBanner.with(mActivity,mList.get(0).getBannerImg()).pop(mList.get(0).getBannerDetailImg());
//                    }
//                }catch (Exception e){
//                    e.getMessage();
//                }
//                bindingView.swipeContainer.finishRefresh();
//                bindingView.emptyLayout.setErrorType(EmptyLayout.NO_ERROR);
//                break;
//        }
//    }
//
//    @Override
//    public void onError(int flag) {
//        bindingView.swipeContainer.finishRefresh();
//        if (NetUtil.getNetWorkState(mActivity) == -1) {
//            bindingView.emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
//        } else {
//            bindingView.emptyLayout.setErrorType(EmptyLayout.ERROR);
//        }
//    }
//
//    /**
//     * 更新seekbar和输入框的监听
//     * @param loanDate 贷款期限
//     */
//    @SuppressLint("SetTextI18n")
//    private void runinUI(int loanDate) {
//        bindingView.tvStartTime.setText(InitDatas.mounthStart);//开始时间
//        bindingView.tvEndTime.setText(InitDatas.mounthEnd);//结束时间
//        bindingView.tvMoney.setText(MyTools.addComma(String.valueOf(bindingView.rulerView.getCurrLocation())));
//        InitDatas.loanAmount = bindingView.rulerView.getCurrLocation();
//        InitDatas.loanDate = loanDate;
//        calculateTimeInfo(0);
//
//        bindingView.rulerView.setOnValueChangeListener(value -> {
//            bindingView.tvMoney.setText(MyTools.addComma(String.valueOf((int)value)));
//            InitDatas.loanAmount = (int) value;
//            calculateTimeInfo(progressData);
//        });
//
//
//        //贷款期限SeekBar监听
//        bindingView.sbTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                calculateTimeInfo(progress);
//                progressData=progress;
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {}
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {}
//        });
//
//
//        //贷款单位选择CheckBox
//        bindingView.cbTime.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                InitDatas.loanUnit = "D";
//                mLimitLow = Integer.parseInt(InitDatas.dayStart.replaceAll("天", "").trim());
//                mLimitHeight = Integer.parseInt(InitDatas.dayEnd.replaceAll("天", "").trim());
//                mLoanUnitStr = "天";
//                limitDateUIThread(InitDatas.dayStart, InitDatas.dayEnd);
//            } else {
//                InitDatas.loanUnit = "M";
//                mLimitLow = Integer.parseInt(InitDatas.mounthStart.replaceAll("月", "").trim());
//                mLimitHeight = Integer.parseInt(InitDatas.mounthEnd.replaceAll("月", "").trim());
//                mLoanUnitStr = "月";
//                limitDateUIThread(InitDatas.mounthStart, InitDatas.mounthEnd);
//            }
//            calculateTimeInfo(progressData);
//        });
//
//    }
//
//
//
//
//    /**
//     * 计算贷款时间信息
//     *
//     * @param progress 滑动条进度
//     */
//    private void calculateTimeInfo(int progress) {
//        if (InitDatas.loanUnit.equals("M")) {
//            mLimitLow = Integer.parseInt(InitDatas.mounthStart.replaceAll("月", "").trim());
//            mLimitHeight = Integer.parseInt(InitDatas.mounthEnd.replaceAll("月", "").trim());
//            mLoanUnitStr = "月";
//
//        } else {
//            mLimitLow = Integer.parseInt(InitDatas.dayStart.replaceAll("天", "").trim());
//            mLimitHeight = Integer.parseInt(InitDatas.dayEnd.replaceAll("天", "").trim());
//            mLoanUnitStr = "天";
//        }
//        //贷款期限
//        Float loanTimefloat = mLimitLow + (mLimitHeight - mLimitLow) * (progress / 100F);
//        int loanTimeInt = loanTimefloat.intValue();
//        InitDatas.loanDate = loanTimeInt;
//        //贷款期限页面显示数据
//        String loanTimeStr = String.valueOf(loanTimeInt);
//        calculateParInfo(InitDatas.loanAmount);
//        repayDataUIThread(progress, loanTimeStr, mLimitHeight, mLimitLow, mLoanUnitStr);
//    }
//
//    /**
//     * 计算每期应还
//     * @param loanMoneyInt 贷款金额
//     */
//    private void calculateParInfo(int loanMoneyInt) {
//        //贷款金额
//        InitDatas.loanAmount = loanMoneyInt;
//
//        int count = loanMoneyInt;
//        //每期应还金额
//        int loanAmountBigDecimal;
//        if ("天".equals(mLoanUnitStr)) {
//            BigDecimal rateAmount = InitDatas.rateDay.multiply(new BigDecimal(InitDatas.loanDate)).multiply(new BigDecimal(count));
//            count = count + rateAmount.intValue();
//            loanAmountBigDecimal = new BigDecimal(count).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
//        } else {
//            //1+月利率
//            BigDecimal r1= InitDatas.rateMounth.add(BigDecimal.ONE);
//            //(1+月利率)次方
//            BigDecimal pore1=r1.pow(InitDatas.loanDate);
//            //被除数
//            BigDecimal byCount=new BigDecimal(count).multiply(InitDatas.rateMounth).multiply(pore1);
//            //除数
//            BigDecimal byCount2=r1.pow(InitDatas.loanDate).subtract(new BigDecimal(1));
//            BigDecimal resu=byCount.divide(byCount2,0, BigDecimal.ROUND_HALF_UP);
//            loanAmountBigDecimal=resu.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
//        }
//        bindingView.tvMqyh.setText(MyTools.addComma(String.valueOf(loanAmountBigDecimal)));
//    }
//
//
//
//    /**
//     * 贷款期限UI更新
//     */
//    @SuppressLint("SetTextI18n")
//    @UiThread
//    void repayDataUIThread(int progress, String loanTimeStr, int limitHeight, int limitLow, String loanUnitStr) {
//        bindingView.sbTime.setProgress(progress);
//        bindingView.sbTime.setValus(limitLow, limitHeight, loanUnitStr);
//        bindingView.tvHkqx.setText(loanTimeStr);//还款期限数字
//        bindingView.tvHkqxDanwei.setText(loanUnitStr);//还款期限单位
//    }
//
//    /**
//     * 拖动条最大时间以及最小时间UI刷新
//     *
//     */
//    @UiThread
//    void limitDateUIThread(String startData, String endData) {
//        bindingView.tvStartTime.setText(startData);
//        bindingView.tvEndTime.setText(endData);
//    }
//
//
//
//}

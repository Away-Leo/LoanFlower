//package com.pingxun.loanflower.ui.fragment;
//
//import android.support.v7.widget.LinearLayoutManager;
//import android.util.Log;
//import android.view.View;
//
//import com.bigkoo.pickerview.OptionsPickerView;
//import com.pingxun.loanflower.R;
//import com.pingxun.loanflower.adapter.CalculatorAdapter;
//import com.pingxun.loanflower.base.App;
//import com.pingxun.loanflower.base.BaseFragment;
//import com.pingxun.loanflower.data.CalculatorResult;
//import com.pingxun.loanflower.databinding.FragmentCalculatorBinding;
//import com.pingxundata.pxmeta.utils.MyTools;
//import com.pingxundata.pxmeta.utils.ObjectHelper;
//import com.pingxundata.pxmeta.utils.ToastUtils;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static java.math.BigDecimal.ROUND_HALF_DOWN;
//
///**
// * Created by LH on 2018/1/18.
// * Describe:计算器
// */
//
//public class CalculatorFragment extends BaseFragment<FragmentCalculatorBinding> implements View.OnClickListener{
//
//    private ArrayList<String> loanWayList;
//    private List<CalculatorResult> mResultList;
//    private int loanWay=0;//0:等额本金,1:等额本息
//    private CalculatorAdapter mAdapter;
//    private BigDecimal bYiHuanLiXi;//已还利息
//
//    @Override
//    protected int getRootLayoutResID() {
//        return R.layout.fragment_calculator;
//    }
//
//    @Override
//    protected void initData() {
//        loanWayList=new ArrayList<>();
//        loanWayList.add(0, "等额本金");
////        loanWayList.add(1, "等额本息");
//        bindingView.tvLoanWay.setOnClickListener(this);
//        bindingView.tvClear.setOnClickListener(this);
//        bindingView.tvStartCalculate.setOnClickListener(this);
//
//
//        mAdapter = new CalculatorAdapter(R.layout.rv_item_calculator_result, mResultList);
//        bindingView.rv.setAdapter(mAdapter);
//        bindingView.rv.setHasFixedSize(true);
//        bindingView.rv.setLayoutManager(new LinearLayoutManager(App.getAppContext()));
//    }
//
//    @Override
//    public void onClick(View v) {
//        String sLoanMoney=MyTools.getEdittextStr(bindingView.edLoanMoney);
//        String sLoanRate=MyTools.getEdittextStr(bindingView.edLoanRate);
//        String sLoanTimeLimit=MyTools.getEdittextStr(bindingView.edLoanTimeLimit);
//
//        switch (v.getId()){
//            case R.id.tv_loan_way://还款方式
//                MyTools.hideInputSoftFromWindowMethod(App.getAppContext(), v);
//                OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                        bindingView.tvLoanWay.setText(loanWayList.get(options1));
//                        loanWay=options1;
//                    }
//                }).build();
//                optionsPickerView.setPicker(loanWayList);
//                optionsPickerView.show();
//                break;
//            case R.id.tv_clear://清空
//                mAdapter.setNewData(null);
//                bindingView.edLoanMoney.setText("");
//                bindingView.edLoanRate.setText("");
//                bindingView.edLoanTimeLimit.setText("");
//                bYiHuanLiXi=null;
//                break;
//            case R.id.tv_start_calculate://计算
//                if (ObjectHelper.isEmpty(sLoanMoney)){
//                    ToastUtils.showToast(App.getAppContext(),"请输入贷款金额!");
//                    return;
//                }
//                if (ObjectHelper.isEmpty(sLoanRate)){
//                    ToastUtils.showToast(App.getAppContext(),"请输入贷款利率!");
//                    return;
//                }
//                if (ObjectHelper.isEmpty(sLoanTimeLimit)){
//                    ToastUtils.showToast(App.getAppContext(),"请输入贷款期限!");
//                    return;
//                }
//                bYiHuanLiXi=new BigDecimal(0.00);
//                BigDecimal dLoanMoney=BigDecimal.valueOf(Double.parseDouble(sLoanMoney));
//                BigDecimal dMonthLoanRate=BigDecimal.valueOf(Double.parseDouble(sLoanRate)).divide(new BigDecimal(100),20,ROUND_HALF_DOWN).divide(new BigDecimal(12),20,ROUND_HALF_DOWN);
////                double dLoanRate2=dLoanRate/100/12;
//                Log.e("月利率",dMonthLoanRate.doubleValue()+"");
//                BigDecimal timeLimit=BigDecimal.valueOf(Double.parseDouble(sLoanTimeLimit));
//                calculate(dLoanMoney,dMonthLoanRate,timeLimit);
//
////              ToastUtils.showToast(App.getAppContext(),loanWay+"");
//                break;
//        }
//
//
//    }
//
//
//    /**
//     * 计算
//     * @param dLoanMoney 贷款金额
//     * @param dLoanRate 贷款利率
//     * @param timeLimit 期限
//     */
//    private void calculate(BigDecimal dLoanMoney, BigDecimal dLoanRate, BigDecimal timeLimit) {
//        if (loanWay==0){
//            /// TODO: 等额本金
//            BigDecimal bYueGongBenJin=dLoanMoney.divide(timeLimit,20,ROUND_HALF_DOWN);//月供本金
//            BigDecimal bHuankuanZong=((timeLimit.add(new BigDecimal(1))).multiply(dLoanMoney).multiply(dLoanRate)).divide(new BigDecimal(2),20,ROUND_HALF_DOWN).add(dLoanMoney);//还款总额
//            double yueGongBenJin=bYueGongBenJin.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();//月供本金保留两位小数展示数据
//            double yueGongLiXi;
////            Log.e("还款总额==>>",bHuankuanZong+"");
//            mResultList=new ArrayList<>();
//
//            for (int i=0;i<timeLimit.intValue();i++){
//                BigDecimal bYiHuanBenJin= bYueGongBenJin.multiply(BigDecimal.valueOf(i));//已还本金
//                BigDecimal bYueGongLiXi=(dLoanMoney.subtract(bYiHuanBenJin)).multiply(dLoanRate);//月供利息
//                yueGongLiXi=bYueGongLiXi.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();//月供利息保留两位小数展示数据
//                bYiHuanLiXi = bYiHuanLiXi.add(bYueGongLiXi);//已还利息
//                BigDecimal bHaiQianJin=bHuankuanZong.subtract(bYiHuanBenJin.add(bYiHuanLiXi).add(bYueGongBenJin));
//                CalculatorResult calculatorResult=new CalculatorResult();
//                calculatorResult.setMonthNum("第"+(i+1)+"期");
//                calculatorResult.setYueGongBenJin(yueGongBenJin);
//                calculatorResult.setYueGongLixi(yueGongLiXi);
//                calculatorResult.setHaiQianJin(bHaiQianJin.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
//                mResultList.add(calculatorResult);
//            }
//            mAdapter.setNewData(mResultList);
//        }
//        if (loanWay==1){
//            /// TODO: 等额本息
////            BigDecimal bigDecimal=(dLoanRate.add(new BigDecimal(1))).pow(timeLimit.intValue());//1+月利率的贷款月数次方
////            BigDecimal bMeiYueZong=(dLoanMoney.multiply(dLoanRate).multiply(bigDecimal)).divide(bigDecimal.subtract(new BigDecimal(1)),20,ROUND_HALF_DOWN);//每月还款额
////            BigDecimal bHuanKuanZong=(timeLimit.multiply(dLoanMoney).multiply(dLoanRate).multiply(bigDecimal)).divide(bigDecimal.subtract(new BigDecimal(1)),20,ROUND_HALF_DOWN);//还款总额
////            BigDecimal bHuanKuanZongLiXi=bHuanKuanZong.subtract(dLoanMoney);
////
////            for (int i=0;i<timeLimit.intValue();i++){
////                BigDecimal bYueGongBenJin
////                BigDecimal bYueGongLiXi=;
////
////
////
////            }
//        }
//    }
//
//
//}

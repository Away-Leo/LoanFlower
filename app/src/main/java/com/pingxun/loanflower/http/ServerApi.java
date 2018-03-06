package com.pingxun.loanflower.http;


import android.content.Context;
import android.util.Log;

import com.pingxun.loanflower.data.CreditCardBean;
import com.pingxun.loanflower.other.InitDatas;
import com.pingxun.loanflower.other.Urls;
import com.pingxundata.answerliu.pxcore.data.ApplyListBean;
import com.pingxundata.answerliu.pxcore.data.LoginBean;
import com.pingxundata.answerliu.pxcore.data.ProductListBean;
import com.pingxundata.answerliu.pxcore.data.ServerModelList;
import com.pingxundata.answerliu.pxcore.data.ServerModelObject;
import com.pingxundata.pxmeta.http.PXHttp;
import com.pingxundata.pxmeta.utils.AppUtils;
import com.pingxundata.pxmeta.utils.SharedPrefsUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.pingxun.loanflower.other.RequestFlag.GET_APP_MODULE;
import static com.pingxun.loanflower.other.RequestFlag.GET_BANK;
import static com.pingxun.loanflower.other.RequestFlag.GET_BANNER;
import static com.pingxun.loanflower.other.RequestFlag.GET_CODE;
import static com.pingxun.loanflower.other.RequestFlag.GET_CREDIT_CARD_RECOMMEND;
import static com.pingxun.loanflower.other.RequestFlag.GET_FIND_BY_ID;
import static com.pingxun.loanflower.other.RequestFlag.GET_FIND_PARAMETER;
import static com.pingxun.loanflower.other.RequestFlag.GET_INFOMATION_INFO;
import static com.pingxun.loanflower.other.RequestFlag.GET_PRODUCT_RECOMMEND;
import static com.pingxun.loanflower.other.RequestFlag.GET_PRODUCT_TYPE;
import static com.pingxun.loanflower.other.RequestFlag.GET_USER_INFO;
import static com.pingxun.loanflower.other.RequestFlag.GET_WX_BANNER;
import static com.pingxun.loanflower.other.RequestFlag.GET_ZS_TYPE;
import static com.pingxun.loanflower.other.RequestFlag.POST_APPLY_CREDIT_CARD;
import static com.pingxun.loanflower.other.RequestFlag.POST_LOGIN;
import static com.pingxun.loanflower.other.RequestFlag.UPDATA_USER_INFO;


public class ServerApi {

    /**
     * 获取首页banner
     */
    public static void getBanner(Context context, PXHttp.OnResultHandler handler) {
        Map<String, String> params = new HashMap<>();
        params.put("position", InitDatas.APP_NAME + "_android_center");
        params.put("versionNo", InitDatas.APP_NAME + AppUtils.getVersionCode(context));
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_BANNER, params, GET_BANNER, ServerModelList.class);

    }

    /**
     * 获取微信Banner
     */
    public static void getWxBanner(Context context, PXHttp.OnResultHandler handler) {
        Map<String, String> params = new HashMap<>();
        params.put("position", "wx_banner");
        params.put("versionNo", InitDatas.APP_NAME + AppUtils.getVersionCode(context));
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_BANNER, params, GET_WX_BANNER,ServerModelList.class);

    }

    /**
     * 获取首页产品分类
     */
    public static void getProductType(PXHttp.OnResultHandler handler) {
        Map<String, String> params = new HashMap<>();
        params.put("appName", InitDatas.APP_NAME);
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_PRODUCT_TYPE, params, GET_PRODUCT_TYPE,ServerModelList.class);
    }

    /**
     * 获取产品推荐
     */
    public static void getProductRecommend(PXHttp.OnResultHandler handler, Context context) {
        Map<String, String> params = new HashMap<>();
        params.put("appName", InitDatas.APP_NAME);
        params.put("channelNo", InitDatas.CHANNEL_NO);
        params.put("versionNo", InitDatas.APP_NAME + AppUtils.getVersionCode(context));
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_PRODUCT_RECOMMEND, params, GET_PRODUCT_RECOMMEND,ServerModelList.class);
    }



    /**
     * 获取贷款参数
     */
    public static void getParameter(PXHttp.OnResultHandler handler) {
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_FIND_PARAMETER, null, GET_FIND_PARAMETER, ServerModelObject.class);
    }


    /**
     * 获取申请列表
     *
     * @param page   页码
     * @param status 状态:下拉刷新或者上拉加载
     */
    public static void getApplyList(PXHttp.OnResultHandler handler, int page, int status) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNo", page + "");
        params.put("appName", InitDatas.APP_NAME);
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_POST_FIND_APPLY_LIST, params, status,ApplyListBean.class);
    }


    /**
     * 获取产品详情
     */
    public static void getProductInfo(PXHttp.OnResultHandler handler, String sId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", sId);
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_FIND_BY_ID, params, GET_FIND_BY_ID,ServerModelObject.class);
    }

    /**
     * 获取综合指数
     */
    public static void getZsType(PXHttp.OnResultHandler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("type", "zsType");
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_FIND_BY_TYPE, map, GET_ZS_TYPE,ServerModelList.class);
    }


    /**
     * 获取银行
     */
    public static void getBank(PXHttp.OnResultHandler handler){
        Map<String, String> map = new HashMap<>();
        map.put("flag", "1");
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_FIND_BANK_BY_POSITION, map, GET_BANK,ServerModelList.class);
    }

    /**
     * 获取APP模块
     */
    public static void getAppModule(Context context, PXHttp.OnResultHandler handler){
        Map<String, String> params = new HashMap<>();
        params.put("appName", "ANDROID_" + InitDatas.APP_NAME + "_" + AppUtils.getVersionCode(context));
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_APP_MODULE, params, GET_APP_MODULE,String.class);
    }

    /**
     * 获取用户消息列表
     * @param handler
     * @param page
     * @param state
     */
    public static void getInformationList(PXHttp.OnResultHandler handler, int page, int state){
        Map<String, String> params = new HashMap<>();
        params.put("appName", InitDatas.APP_NAME);
        params.put("pageNo", page + "");
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_MESSAGE_LIST, params, state,ApplyListBean.class);
    }

    /**
     * 获取消息详情
     */
    public static void getInfomationInfo(PXHttp.OnResultHandler handler, String sId){
        Map<String, String> mParams = new HashMap<>();
        mParams.put("id", sId);
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_MESSAGE_INFO, mParams, GET_INFOMATION_INFO,ServerModelObject.class);
    }


    /**
     * 获取用户信息
     */
    public static void getUserInfo(PXHttp.OnResultHandler handler) {
        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_USER_INFO, null, GET_USER_INFO,ServerModelObject.class);
    }

//    /**
//     * 获取职业集合
//     */
//    public static void getJobData(PXHttp.OnResultHandler handler) {
//        Map<String, String> params = new HashMap<>();
//        params.put("type", "job");
//        PXHttp.getInstance().setHandleInterface(handler).getJson(Urls.URL_GET_FIND_BY_TYPE, params, GET_FIND_BY_TYPE,ServerModelList.class);
//    }



    //-------------------------------------------------------------------------------完美分割线---------------------------------------------------------------------------------//
    /**
     * 获取验证码
     *
     * @param sPhone 手机号
     */
    public static void postCode(PXHttp.OnResultHandler handler, String sPhone) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", sPhone);
        params.put("channelNo", InitDatas.CHANNEL_NO);
        params.put("applyArea", InitDatas.province + "/" + InitDatas.city + "/" + InitDatas.district);
        params.put("appName", InitDatas.APP_NAME);
        JSONObject jsonObject = new JSONObject(params);
        PXHttp.getInstance().setHandleInterface(handler).upJson(Urls.URL_POST_SEND_SMS, jsonObject, GET_CODE, LoginBean.class);
    }

    /**
     * 登录
     *
     * @param sPhone 手机号
     * @param sCode  验证码
     */
    public static void postLogin(PXHttp.OnResultHandler handler, String sPhone, String sCode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", sPhone);
        params.put("password", sCode);
        params.put("appName", InitDatas.APP_NAME);
        params.put("channelNo", InitDatas.CHANNEL_NO);
        JSONObject jsonObject = new JSONObject(params);
        PXHttp.getInstance().setHandleInterface(handler).upJson(Urls.URL_POST_LOGIN,jsonObject, POST_LOGIN,LoginBean.class);
    }

    /**
     * 获取信用卡列表
     */
    public static void getCreditCard(PXHttp.OnResultHandler handler, int page, int flag){
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", page+"");
        JSONObject jsonObject = new JSONObject(params);
        PXHttp.getInstance().setHandleInterface(handler).upJson(Urls.URL_POST_FIND_BY_CONDITION_CARD, jsonObject, flag,CreditCardBean.class);
    }

    /**
     * 获取信用卡推荐
     */
    public static void getCreditCardRecommend(PXHttp.OnResultHandler handler){
        Map<String, String> params = new HashMap<>();
        params.put("appName", InitDatas.APP_NAME);
        JSONObject jsonObject = new JSONObject(params);
        PXHttp.getInstance().setHandleInterface(handler).upJson(Urls.URL_POST_CREDIT_CARD, jsonObject, GET_CREDIT_CARD_RECOMMEND,CreditCardBean.class);
    }

    /**
     * 产品列表搜索
     *
     * @param page   请求的页码
     * @param flag   下拉或者上拉标识
     * @param zsType 综合指数参数
     */
    public static void postProductSearch(Context context, PXHttp.OnResultHandler handler, int page, int flag, String zsType, String productType) {

        HashMap<String, String> params = new HashMap<>();
        params.put("period", String.valueOf(InitDatas.loanDate));//期限
        params.put("dateCycle", InitDatas.loanUnit);//期限周期
        params.put("amount", String.valueOf(InitDatas.loanAmount));//借款金额
        params.put("loanType", InitDatas.loanType);
        params.put("channelNo", InitDatas.CHANNEL_NO);//渠道类型：ios,android,wechat
        params.put("appName", InitDatas.APP_NAME);
        params.put("pageNo", page + "");
        params.put("zsType", zsType);
        params.put("productType", productType);
        params.put("versionNo", InitDatas.APP_NAME + AppUtils.getVersionCode(context));
        JSONObject jsonObject = new JSONObject(params);
        PXHttp.getInstance().setHandleInterface(handler).upJson(Urls.URL_POST_FIND_BY_CONDITION, jsonObject, flag,ProductListBean.class);
//      Log.e("productType==>>",productType);
//      Log.e("zsType==>>",zsType);
      Log.e("channelNo==>>",InitDatas.CHANNEL_NO);
      Log.e("versionNo==>>",InitDatas.APP_NAME + AppUtils.getVersionCode(context));
    }

    public static void postApplyCreditCard(PXHttp.OnResultHandler handler, String productId, String deviceNumber, String applyArea, String channelNo, String appName){
        HashMap<String, String> params = new HashMap<>();
        params.put("productId",productId);
        params.put("deviceNumber",deviceNumber);
        params.put("applyArea",applyArea);
        params.put("channelNo",channelNo);
        params.put("appName",appName);
        JSONObject jsonObject=new JSONObject(params);
        PXHttp.getInstance().setHandleInterface(handler).upJson(Urls.URL_POST_APPLY_CREDIT_CARD,jsonObject,POST_APPLY_CREDIT_CARD,String.class);
    }


    /**
     * 更新用户信息
     */
    public static void updateUserInfo(Context context, PXHttp.OnResultHandler handler) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserName, ""));//姓名
        params.put("certNo", SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserId, ""));//身份证号码
        params.put("address", SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserAddress, ""));//地址
        params.put("city", SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserAddress, ""));//居住地
        params.put("domicilePlace", SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserHuJi, ""));//户籍
        params.put("education", SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserEducation, ""));//教育程度
        params.put("socialIdentity", SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserJob, ""));//社会身份
        params.put("income", SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserIncome, ""));//收入
        params.put("isSocial", String.valueOf(SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserIsInsurance, false)));//有无社保
        params.put("isFund", String.valueOf(SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserIsAccumulation, false)));//有无公积金
        params.put("isCreditCard", String.valueOf(SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserIsCard, false)));//有无信用卡
        params.put("isHouseOrCarLoan", String.valueOf(SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserIsLoan, false)));//有无贷款
        params.put("wechatAmount", SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserWechatQuota, ""));//微粒贷额度
        params.put("sesameCredit", SharedPrefsUtil.getValue(context, InitDatas.SP_NAME, InitDatas.UserAlipayNum, ""));//芝麻信用分
        JSONObject jsonObject = new JSONObject(params);

        PXHttp.getInstance().setHandleInterface(handler).upJson(Urls.URL_POST_USER_INFO, jsonObject, UPDATA_USER_INFO,LoginBean.class);
    }

}

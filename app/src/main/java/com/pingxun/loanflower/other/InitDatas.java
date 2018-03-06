package com.pingxun.loanflower.other;

import java.math.BigDecimal;

/**
 * 初始数据
 */

public class InitDatas {

    /**
     * 等待时间
     */
    public static int waitTime=666;

    public static int SUSPENDFLAG=1;//0显示，1隐藏

    /**
     * 贷款最低金额
     */
    public static int limitLowAmount=1000;


    /**
     * 贷款最高金额
     */
    public static int limitHeightAmount=50000;

    /**
     * 日 单位 起始
     */
    public static String dayStart="1天";
    /**
     * 日 单位 结束
     */
    public static String dayEnd="360天";

    /**
     * 月 单位 开始
     */
    public static String mounthStart="1月";
    /**
     * 月 单位 结束
     */
    public static String mounthEnd="36月";

    /**
     * 贷款单位
     */
    public static String loanUnit="M";//D:天,M:月

    /**
     * 贷款期限
     */
    public static int loanDate=0;

    /**
     * 贷款金额 0 天 1 月 2 年
     */
    public static int loanAmount=0;



    /**
     * 每页条数
     */
    public static int size;



    /**
     * 年利率
     */
    public static BigDecimal rateYear;

    /**
     * 日利率
     */
    public static BigDecimal rateDay= BigDecimal.ONE;

    /**
     * 月利率
     */
    public static BigDecimal rateMounth;


    /**
     * 省
     */
    public static String province="";
    /**
     * 市
     */
    public static String city="";
    /**
     * 区
     */
    public static String district="";

    /**
     * 产品超市左边的参数
     */
    public static String LeftCode="all";
    /**
     * 产品超市右边的参数
     */
    public static String RightCode="xkz";
    /**
     * 贷款类型参数
     */
    public static String loanType="";
    /**
     * 贷款列表顶部提示语
     */
    public static String messageTip="温馨提示:同时申请3个以上贷款产品，100%下款成功率!";



    public static String APP_NAME="JQHH";
    public static String CHANNEL_NO= "huawei";//渠道类型：ios,android,wechat
//  public static String CHANNEL_NO="OFFICIAL_TEST";
    public static String ErrorMsg="服务器数据异常,请稍后再试!";
    public static String NO_NETWORK_MSG="网络未连接，请检查您的网络设置!";
//    public static String SP_NAME=APP_NAME+"_UserInfo";//sp文件名

//    //用于SP存储用户信息的Key
//    public static String UserPhone=SP_NAME+"_Phone";//用户手机号key
//    public static String UserPw=SP_NAME+"_Pw";//用户验证码(密码)key
//    public static String UserIsLogin=SP_NAME+"_IsLogin";//用户是否登录key
//    public static String UserIsFirstSplash=SP_NAME+"_IsFirstSplash";//是否第一次启动key



    //用于SP存储用户信息的Key
    public static String SP_NAME=APP_NAME+"_UserInfo";//sp文件名
    public static String UserPhone=SP_NAME+"_Phone";//用户手机号
    public static String UserPw=SP_NAME+"_Pw";//用户验证码(密码)
    public static String UserIsLogin=SP_NAME+"_IsLogin";//用户是否登录
    public static String UserIsFirstSplash=SP_NAME+"_IsFirstSplash";//是否第一次启动
    public static String UserName=SP_NAME+"_Name";//用户姓名
    public static String UserId=SP_NAME+"_Id";//用户身份证号
    public static String UserHuJi=SP_NAME+"_HuJi";//用户户籍
    public static String UserAddress=SP_NAME+"_Address";//用户居住地
    public static String UserEducation=SP_NAME+"_Education";//用户教育程度
    public static String UserJob=SP_NAME+"_Job";//用户社会身份
    public static String UserIncome=SP_NAME+"_Income";//用户收入/月
    public static String UserIsInsurance=SP_NAME+"_IsInsurance";//用户有无社保
    public static String UserIsAccumulation=SP_NAME+"_IsAccumulation";//用户有无公积金
    public static String UserIsCard=SP_NAME+"_IsCard";//有无信用卡
    public static String UserIsLoan=SP_NAME+"_IsLoan";//有无贷款
    public static String UserWechatQuota=SP_NAME+"_WechatQuota";//微粒贷额度
    public static String UserAlipayNum=SP_NAME+"_AlipayNum";//芝麻信用分

    public static String AppModule=SP_NAME+"_AppModule";//APP模块

    //用于bundle传值的key
    public static String INFOR_ID="productId";//详情ID，产品详情，消息详情,攻略详情,首页bannerId
    public static String INFOR_URL="productUrl";//首页bannerUrl
    public static String INFOR_NAME="productName";//首页bannerName
    public static String PRODUCT_TYPE_ID="product_type";//产品分类
    //友盟AppKey
    public static String UMENG_APPKEY="59c0dfa875ca357945000031";

    /**
     * APP升级相关配置
     */
    public static String APP_UPDATE=CHANNEL_NO+"-"+APP_NAME;

}

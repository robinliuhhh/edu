package com.robin.edupayboot.commons;

public class PayConfig {

    //企业公众号ID
    public static String appid="wx8397f8696b538317";
    //财付通平台的商户帐号
    public static String partner="1473426802";
    //财付通平台的商户密钥
    public static String partnerKey="8A627A4578ACE384017C997F12D68B23";

    //企业公众号ID
    //财付通平台的商户密钥
    //回调URL
//    public static String notifyurl="http://edufront.lagou.com/front/pay/wxCallback"; //success
    public static String notifyurl="http://localhost:8006/pay/wxCallback"; //本地
}
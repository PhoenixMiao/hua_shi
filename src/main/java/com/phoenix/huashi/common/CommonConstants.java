package com.phoenix.huashi.common;

/**
 * @author phoenix
 * @version 2022/1/19 19:21
 */
public class CommonConstants {

    public final static String SESSION = "session";
    public final static String APP_NAME = "hua_shi";
    public final static String SHADOW_TEST = "shadow-test";
    public final static String SEPARATOR = ",";
    public final static String CHAT_RECORD_COLLECTION_NAME = "chat_record";
    public final static String WX_SESSION_REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";
    public final static String WX_ACCESS_TOKEN_REQUEST_URL = "https://api.weixin.qq.com/cgi-bin/token";
    public final static String WX_QRCODE_REQUEST_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";
    //https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_5_1.shtml
    public final static String WX_PAY_REQUEST_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
    public final static String CNY_CURRENCY = "CNY";
    public final static String SIGN_TYPE_RSA = "RSA";
    public final static String SIGN_TYPE_HMAC_SHA256 = "HMAC-SHA256";
    public final static String LANG_TYPE_ZH_CN = "zh_CN";

    public final static String COS_REGION = "ap-shanghai";
    public final static String COS_APP_ID = "1305159828";
    public final static String COS_SECRET_ID = "AKIDwgANJxKQGp78tNaVAYfW6QTFkyvb092e";
    public final static String COS_SECRET_KEY = "MTKuDF0UgbFcYCZgW5NO8bmhLiocARRA";
    public static final String COS_BUCKET_NAME = "huashi-1305159828";

    public final static String ADMIN1_NUMBER ="hsadminone";
    public final static String ADMIN1_PASSWORD ="hsadmin1";
    public final static String ADMIN1_SESSIONID ="9db3411c-dc82-49c5-ab39-14e01b62f97d1";

    public final static String ADMIN2_NUMBER ="hsadmintwo";
    public final static String ADMIN2_PASSWORD ="hsadmin2";
    public final static String ADMIN2_SESSIONID ="9db3411c-dc82-49c5-ab39-14e01b62f97d2";

    public final static String ADMIN3_NUMBER ="hsadminthree";
    public final static String ADMIN3_PASSWORD ="hsadmin3";
    public final static String ADMIN3_SESSIONID ="9db3411c-dc82-49c5-ab39-14e01b62f97d3";
}

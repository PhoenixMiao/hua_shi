package com.phoenix.huashi.util;

import com.phoenix.huashi.config.YmlConfig;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;

/**
 * https://blog.csdn.net/xiao__jia__jia/article/details/106871545
 * @author yannis
 * @version 2021/1/18 12:34
 */
@Component
public class WxPayHttpClientSingletonUtils {

    public static volatile HttpClient httpClient;

    @Autowired
    private YmlConfig ymlConfig;

//    public HttpClient getHttpClient() {
//
//        String mchId = ymlConfig.getMchId();
//        String mchSerialNo = ymlConfig.getMchSerialNo();
//        String mchPrivateKey = ymlConfig.getMchPrivateKey();
//        String apiV3Key = ymlConfig.getApiV3Key();
//
//        if(httpClient != null)return httpClient;
//        synchronized (WxPayHttpClientSingletonUtils.class){
//            if(httpClient != null)return httpClient;
//            WechatPayHttpClientBuilder builder = null;
//            PrivateKey merchantPrivateKey = getPrivateKey();
//            AutoUpdateCertificatesVerifier verifier = null;
//            try {
//                verifier = new AutoUpdateCertificatesVerifier(
//                        new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
//                        apiV3Key.getBytes("utf-8"));
//            } catch (UnsupportedEncodingException e) {
//                throw new RuntimeException("WxPayHttpClientSingletonUtil: " + e);
//            }
//            builder = WechatPayHttpClientBuilder.create()
//                    .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
//                    .withValidator(new WechatPay2Validator(verifier));
////                    .withWechatpay(wechatpayCertificates);
//            // ... ?????????????????????????????????builder????????????????????????????????????HttpClient
//
//// ??????WechatPayHttpClientBuilder?????????HttpClient????????????????????????????????????
//            httpClient = builder.build();
//        }
//        return httpClient;
//    }
//
//    public PrivateKey getPrivateKey(){
//
//        String mchPrivateKey = ymlConfig.getMchPrivateKey();
//        try {
//            return PemUtil.loadPrivateKey(new ByteArrayInputStream(mchPrivateKey.getBytes("utf-8")));
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("WechatPayHttpClientSingletonUtil: " + e);
//        }
//    }


}

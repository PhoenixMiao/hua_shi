package com.phoenix.kuangjia.util;

import com.phoenix.kuangjia.config.YmlConfig;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
//            // ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
//
//// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
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

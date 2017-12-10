package com.yhm.wst.hprose;

import android.os.Handler;
import android.os.Looper;

import com.yhm.wst.util.HProseArrayToJson;
import com.yhm.wst.util.CommonPreference;
import com.yhm.wst.util.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import hprose.client.HproseHttpClient;
import hprose.common.HproseCallback;
import hprose.common.HproseErrorEvent;

/**
 * Created by Administrator on 2017/11/21.
 */
public class HProseManager {
    private static HProseManager mInstance;
    private HproseHttpClient client;
    private Gson gson;
    private Handler mHandler;

//    public static HProseManager getInstance() {
//        if (mInstance == null) {
//            synchronized (HProseManager.class) {
//                if (mInstance == null) {
//                    mInstance = new HProseManager();
//                }
//            }
//        }
//        return mInstance;
//    }

    public static HProseManager getInstance() {
        return mInstance = new HProseManager();
    }

    private HProseManager() {
        client = new HproseHttpClient();
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public interface StringCallback {
        void onError(String s, Throwable throwable);

        void onResponse(String json, Object[] objects);
    }

    public static void postAsyn(String service, String method, Object[] params, StringCallback callback) {
        getInstance()._postAsyn(service, method, params, callback);
    }

    private void _postAsyn(String service, String method, Object[] params, final StringCallback callback) {
        client.useService(service);
        client.setHeader("AUTHENTICATION", CommonPreference.getUserToken());
        client.invoke(method, params, new HproseCallback<Object>() {
            @Override
            public void handler(Object object, final Object[] objects) {
                client.close();
                if(object == null) {
                    return;
                }
                LogUtil.i("HProseManager", "object: "+ object.toString());
                String json = HProseArrayToJson.mapToJson(object);
                sendResponseOnMain(objects, json, callback);
            }
        }, new HproseErrorEvent() {
            @Override
            public void handler(final String s, final Throwable throwable) {
                client.close();
                sendErrorOnMain(s, throwable, callback);
            }
        });
    }

    private void sendErrorOnMain(final String s, final Throwable throwable, final StringCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(s, throwable);
                }
            }
        });
    }

    private void sendResponseOnMain(final Object[] objects, final String json, final StringCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(json, objects);
                }
            }
        });
    }


////////////////////////    测试代码  /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void NetHelper(HproseHttpClient client) {
        X509HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        SSLContext sslContext = null;
        try {
            MyX509TrustManager mtm = new MyX509TrustManager();
            TrustManager[] tms = new TrustManager[]{mtm};

            // 初始化X509TrustManager中的SSLContext
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tms, new java.security.SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 为javax.net.ssl.HttpsURLConnection设置默认的SocketFactory和HostnameVerifier
        if (sslContext != null) {
            // client.setDefaultSSLSocketFactory(sslContext
            // .getSocketFactory());
            client.setSSLSocketFactory(sslContext.getSocketFactory());
        }
        // client.setDefaultHostnameVerifier(hostnameVerifier);
        client.setHostnameVerifier(hostnameVerifier);
    }

    public static class MyX509TrustManager implements X509TrustManager {
        X509TrustManager myJSSEX509TrustManager = null;

        public MyX509TrustManager() throws Exception {
            KeyStore ks = KeyStore.getInstance("BKS");
            // ks.load(new FileInputStream("trustedCerts"),
            // "passphrase".toCharArray()); //---->
            // 这是加载自己的数字签名证书文件和密码，在这里这里没有，所以不需要
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
            tmf.init(ks);
            TrustManager tms[] = tmf.getTrustManagers();
            for (int i = 0; i < tms.length; i++) {
                if (tms[i] instanceof X509TrustManager) {
                    myJSSEX509TrustManager = (X509TrustManager) tms[i];
                    return;
                }
            }
        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // sunJSSEX509TrustManager.checkClientTrusted(arg0, arg1);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // sunJSSEX509TrustManager.checkServerTrusted(arg0, arg1);
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // X509Certificate[] acceptedIssuers = sunJSSEX509TrustManager
            // .getAcceptedIssuers();
            // return acceptedIssuers;
            return null;
        }
    }

}

package cn.wsgwz.basemodule.utilities;

import cn.wsgwz.basemodule.BaseApplication;
import com.orhanobut.logger.Logger;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

public class OkHttpUtil {
    private static final String TAG = OkHttpClient.class.getSimpleName();

    private OkHttpUtil(OkHttpClient.Builder builder) {
        SSLSocketFactory sslSocketFactory = null;
        X509TrustManager trustManager = null;
        try {
            final InputStream inputStream;
            inputStream = BaseApplication.getInstance().getAssets().open("1727737_www.china185.com_public.crt"); // 得到证书的输入流
            trustManager = trustManagerForCertificates(inputStream);//以流的方式读入证书
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        builder.sslSocketFactory(sslSocketFactory, trustManager);
    }

    public static void assist(OkHttpClient.Builder builder){
        new OkHttpUtil(builder);
    }

    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (java.security.cert.Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    /**
     * 添加password
     *
     * @param password
     * @return
     * @throws GeneralSecurityException
     */
    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // 这里添加自定义的密码，默认
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            //keyStore.load(null);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }


    public static void cancel(OkHttpClient okHttpClient, Object tag) {


        Dispatcher dispatcher = okHttpClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
            Logger.t(TAG).d( call.request().tag() + "====dispatcher.queuedCalls()" );

        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }

            Logger.t(TAG).d(  call.request().tag() + "====dispatcher.runningCalls()");

        }
    }
}

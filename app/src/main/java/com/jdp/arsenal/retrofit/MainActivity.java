package com.jdp.arsenal.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jdp.arsenal.R;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        // http://apis.baidu.com/apistore/idservice/id?id=420984198704207896
//        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("http://apis.baidu.com/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                IDService IDService = retrofit.create(IDService.class);
//                Call<IDResponse> call = IDService.getID("420984198704207896");
//                call.enqueue(new Callback<IDResponse>() {
//                    @Override
//                    public void onResponse(Call<IDResponse> call, Response<IDResponse> response) {
//                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<IDResponse> call, Throwable t) {
//
//                    }
//                });
//            }
//        });

        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TrustManager[] tm = new TrustManager[]{
                                    new X509TrustManager() {
                                        @Override
                                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                                        }

                                        @Override
                                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                                        }

                                        @Override
                                        public X509Certificate[] getAcceptedIssuers() {
                                            return new X509Certificate[0];
                                        }
                                    }
                            };
                            SSLContext sslContext = SSLContext.getInstance("TLS");
                            sslContext.init(null, tm, new java.security.SecureRandom());
                            SSLSocketFactory ssf = sslContext.getSocketFactory();
                            URL myURL = new URL("https://sacasnap.neusoft.com/co/sysconfig/queryall");
                            HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();
                            httpsConn.setSSLSocketFactory(ssf);
                            httpsConn.setHostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String hostname, SSLSession session) {
                                    return true;
                                }
                            });
                            InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream());
                            int tmp;
                            StringBuilder sb = new StringBuilder();
                            while ((tmp = insr.read()) != -1) {
                                sb.append(tmp);
                            }
                            Log.i("tag", sb.toString());
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (KeyManagementException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }
}

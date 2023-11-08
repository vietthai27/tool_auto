package com.fis.tool.Security;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Component
public class SslWarningRemover {
	public SslWarningRemover() {
        log.info("Disabling SSL warning...");
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            
                        }
                        
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            
                        }
                    }
            };
            
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            
            log.info("SSL verification disabled");
        } catch (Exception e) {
            log.error("Error while trying to disable SSL verification: " + e.getMessage(), e);
        }
        
        log.info("SSL warning process remover has finished!");
    }
}

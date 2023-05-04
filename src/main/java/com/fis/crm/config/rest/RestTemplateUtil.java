package com.fis.crm.config.rest;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class RestTemplateUtil {

  public static Registry<ConnectionSocketFactory> getSocketFactoryRegistry() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    SSLContextBuilder builder = SSLContexts.custom();
    builder.loadTrustMaterial(null, (chain, authType) -> true);
    SSLContext sslContext = builder.build();
    SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
        (s, sslSession) -> true);
    return RegistryBuilder
        .<ConnectionSocketFactory>create()
        .register("https", sslSocketFactory)
        .register("http", new PlainConnectionSocketFactory())
        .build();
  }
}

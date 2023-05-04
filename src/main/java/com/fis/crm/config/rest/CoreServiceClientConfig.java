package com.fis.crm.config.rest;

import com.fis.crm.config.Constants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
@Getter
@Slf4j
public class CoreServiceClientConfig {

    @Value("${rest.core-service.proxy-host:#{null}}")
    private String proxyHost;
    @Value("${rest.core-service.proxy-port:#{null}}")
    private Integer proxyPort;
    @Value("${connection-time-out}")
    int mintConnectionTimeOut;


    @Bean
    @ConfigurationProperties(prefix = "rest.core-service.connection")
    public HttpComponentsClientHttpRequestFactory coreServiceHttpRequestFactory(PoolingHttpClientConnectionManager coreServiceHttpClientConnectionManager) {
        HttpClientBuilder builder = HttpClients.custom()
            .setConnectionManager(coreServiceHttpClientConnectionManager)
            .setConnectionManagerShared(true);
        if (StringUtils.hasLength(proxyHost)) {
            builder.setProxy(new HttpHost(proxyHost, proxyPort));
        }
        CloseableHttpClient closeableHttpClient = builder.build();
        return new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
    }

    @Bean
    public RestTemplate coreServiceRestTemplate(HttpComponentsClientHttpRequestFactory coreServiceHttpRequestFactory) {
        RequestConfig config = RequestConfig.custom() //
            .setConnectTimeout(mintConnectionTimeOut) //
            .setConnectionRequestTimeout(mintConnectionTimeOut) //
            .setSocketTimeout(mintConnectionTimeOut).build();
        CloseableHttpClient client = HttpClientBuilder.create() //
            .setDefaultRequestConfig(config) //
            .build();
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    @Bean
    @ConfigurationProperties(prefix = "rest.core-service.connection-pool")
    public PoolingHttpClientConnectionManager coreServiceHttpClientConnectionManager() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return new PoolingHttpClientConnectionManager(RestTemplateUtil.getSocketFactoryRegistry());
    }

    @Bean
    public HttpHeaders httpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT_LANGUAGE, Constants.EGP.ACCEPT_LANGUAGE_VI);
        return httpHeaders;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }
}

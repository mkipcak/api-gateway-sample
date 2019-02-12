package com.gateway.configuration;

import javax.net.ssl.SSLContext;
import com.gateway.conditional.LoadIfNotLocalProfileCondition;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/*
 * @Author: Nitishkumar Singh
 * Load SSL Trust Store only for other profile except local, which is for local development
*/
@Configuration
@Conditional(LoadIfNotLocalProfileCondition.class)
public class SSLConfiguration {
        @Value("${server.ssl.trust-store}")
        private Resource keyStore;
        @Value("${server.ssl.trust-store-password}")
        private String keyStorePassword;

        @Bean
        RestTemplate restTemplate() throws Exception {
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                                // Paths.get(keyStore).toUri().toURL(),
                                keyStore.getURL(), keyStorePassword.toCharArray())
                                .build();
                SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
                HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
                HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
                return new RestTemplate(factory);
        }

}
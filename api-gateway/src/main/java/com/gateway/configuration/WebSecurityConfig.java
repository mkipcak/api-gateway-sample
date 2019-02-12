package com.gateway.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig{

    @Value("${server.port}")
    private String httpsPort;
    @Value("${server.http.port}")
    private String httpPort;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange().pathMatchers("/**").permitAll();
        http.redirectToHttps().httpsRedirectWhen(exchange->exchange.getRequest().getURI().getPort()==Integer.parseInt(httpPort));
        return http.build();
    }


    // @Bean
    // public WebFilter httpsRedirectFilter() {
    //     return new WebFilter() {
    //         @Override
    //         public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    //             URI originalUri = exchange.getRequest().getURI();
    
    //             //here set your condition to http->https redirect
    //             List<String> forwardedValues = exchange.getRequest().getHeaders().get("x-forwarded-proto");
    //             if (forwardedValues != null && forwardedValues.contains("http")) {
    //                 try {
    //                     URI mutatedUri = new URI("https",
    //                             originalUri.getUserInfo(),
    //                             originalUri.getHost(),
    //                             originalUri.getPort(),
    //                             originalUri.getPath(),
    //                             originalUri.getQuery(),
    //                             originalUri.getFragment());
    //                     ServerHttpResponse response = exchange.getResponse();
    //                     response.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
    //                     response.getHeaders().setLocation(mutatedUri);
    //                     return Mono.empty();
    //                 } catch (URISyntaxException e) {
    //                     throw new IllegalStateException(e.getMessage(), e);
    //                 }
    //             }
    //             return chain.filter(exchange);
    //         }
    //     };
    // }
    
    // @PostConstruct
    // public void startRedirectServer() {
    //     NettyReactiveWebServerFactory httpNettyReactiveWebServerFactory = new NettyReactiveWebServerFactory(Integer.parseInt(httpPort));
    //     httpNettyReactiveWebServerFactory.getWebServer((request, response) -> {
    //         URI uri = request.getURI();
    //         URI httpsUri;
    //         try {
    //             httpsUri = new URI("https", uri.getUserInfo(), uri.getHost(), Integer.parseInt(httpsPort), uri.getPath(), uri.getQuery(), uri.getFragment());
    //         } catch (URISyntaxException e) {
    //             return Mono.error(e);
    //         }
    //         response.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
    //         response.getHeaders().setLocation(httpsUri);
    //         return response.setComplete();
    //     }).start();
    // }

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http
    //         .antMatcher("/**")
    //         .requiresChannel()
    //             .anyRequest().requiresSecure();
    //     http
    //         .antMatcher("/**")
    //         .portMapper()
    //             .http(Integer.parseInt(httpPort)).mapsTo(Integer.parseInt(httpsPort));
    // }

}
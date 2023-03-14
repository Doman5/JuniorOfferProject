package com.domanski.juniorofferproject.infrastucture.offer.client;

import com.domanski.juniorofferproject.domain.offer.OfferDownloader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class OfferDownloaderRestTemplateConfig {

    @Bean
    public ResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(ResponseErrorHandler restTemplateResponseErrorHandler,
                                     @Value("${offer.offer-downloader.http.client.config.connectionTimeOut}") int connectionTimeOut,
                                     @Value("${offer.offer-downloader.http.client.config.readTimeOut}") int readTimeOut) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeOut))
                .setReadTimeout(Duration.ofMillis(readTimeOut))
                .build();
    }

    @Bean
    public OfferDownloader offerDownloaderRestTemplate(RestTemplate restTemplate,
                                                       @Value("${offer.offer-downloader.http.client.config.uri}") String uri,
                                                       @Value("${offer.offer-downloader.http.client.config.port}") int port) {
        return new OfferDownloaderRestTemplate(restTemplate, uri, port);
    }
}

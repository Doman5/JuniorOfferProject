package com.domanski.juniorofferproject.http.offerdownloader;

import com.domanski.juniorofferproject.domain.offer.OfferDownloader;
import com.domanski.juniorofferproject.infrastucture.offer.client.OfferDownloaderRestTemplate;
import com.domanski.juniorofferproject.infrastucture.offer.client.OfferDownloaderRestTemplateConfig;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class OfferDownloaderRestTemplateIntegrationTestConfig extends OfferDownloaderRestTemplateConfig {

    public OfferDownloader offerDownloader(int connectionTimeout, int readTimeOut, int port) {
        ResponseErrorHandler responseErrorHandler = restTemplateResponseErrorHandler();
        RestTemplate restTemplate = restTemplate(responseErrorHandler, connectionTimeout, readTimeOut);
        return new OfferDownloaderRestTemplate(restTemplate, "http://localhost", port);
    }
}

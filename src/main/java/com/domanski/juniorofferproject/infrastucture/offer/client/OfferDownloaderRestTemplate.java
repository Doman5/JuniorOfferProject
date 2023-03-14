package com.domanski.juniorofferproject.infrastucture.offer.client;

import com.domanski.juniorofferproject.domain.offer.OfferDownloader;
import com.domanski.juniorofferproject.domain.offer.dto.DownloadedOffer;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class OfferDownloaderRestTemplate implements OfferDownloader {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<DownloadedOffer> downloadOffers() {

        String url = generateUrlForService();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<List<DownloadedOffer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        final List<DownloadedOffer> downloadedOffers = response.getBody();

        if(downloadedOffers == null) {
            return Collections.emptyList();
        }
        return downloadedOffers;
    }

    private String generateUrlForService() {
        return uri.concat(":").concat(String.valueOf(port)).concat("/offers");
    }
}

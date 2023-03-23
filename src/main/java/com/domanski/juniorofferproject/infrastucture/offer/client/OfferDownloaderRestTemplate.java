package com.domanski.juniorofferproject.infrastucture.offer.client;

import com.domanski.juniorofferproject.domain.offer.OfferDownloader;
import com.domanski.juniorofferproject.domain.offer.dto.DownloadedOffer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class OfferDownloaderRestTemplate implements OfferDownloader {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<DownloadedOffer> downloadOffers() {
        log.info("Started downloading offers using http client");
        String url = generateUrlForService();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);
        try{
            ResponseEntity<List<DownloadedOffer>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    });

            final List<DownloadedOffer> downloadedOffers = response.getBody();
            if(downloadedOffers == null) {
                log.info("Response body was null, returning empty list");
                return Collections.emptyList();
            }
            log.info("Success Response body returned: " + downloadedOffers);
            return downloadedOffers;
        } catch (ResourceAccessException e) {
            log.error("Error while downloading offers form external service: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateUrlForService() {
        return uri.concat(":").concat(String.valueOf(port)).concat("/offers");
    }
}

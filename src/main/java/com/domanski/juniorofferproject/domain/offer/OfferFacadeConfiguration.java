package com.domanski.juniorofferproject.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfferFacadeConfiguration {

    @Bean
    public OfferExistingChecker offerExistingChecker(OfferRepository offerRepository) {
        return new OfferExistingChecker(offerRepository);
    }

    @Bean
    public OfferFacade offerFacade(OfferRepository offerRepository, OfferDownloader offerDownloader, OfferExistingChecker offerExistingChecker) {
        return new OfferFacade(offerRepository, offerDownloader, offerExistingChecker);
    }
}

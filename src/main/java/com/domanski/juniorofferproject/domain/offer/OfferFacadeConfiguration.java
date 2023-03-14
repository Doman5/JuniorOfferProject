package com.domanski.juniorofferproject.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class OfferFacadeConfiguration {

    @Bean
    public OfferRepository offerRepository() {
        return new OfferRepository() {
            @Override
            public List<Offer> findAll() {
                return null;
            }

            @Override
            public Optional<Offer> findById(Long id) {
                return Optional.empty();
            }

            @Override
            public Offer save(Offer offer) {
                return null;
            }

            @Override
            public List<Offer> saveAll(List<Offer> offers) {
                return null;
            }

            @Override
            public boolean existsOfferByOfferUrl(String offerUrl) {
                return false;
            }
        };
    }

    @Bean
    public OfferExistingChecker offerExistingChecker(OfferRepository offerRepository) {
        return new OfferExistingChecker(offerRepository);
    }

    @Bean
    public OfferFacade offerFacade(OfferRepository offerRepository, OfferDownloader offerDownloader, OfferExistingChecker offerExistingChecker) {
        return new OfferFacade(offerRepository, offerDownloader, offerExistingChecker);
    }
}

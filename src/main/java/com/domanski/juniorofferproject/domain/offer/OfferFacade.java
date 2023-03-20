package com.domanski.juniorofferproject.domain.offer;

import com.domanski.juniorofferproject.domain.offer.dto.DownloadedOffer;
import com.domanski.juniorofferproject.domain.offer.dto.OfferResponse;
import com.domanski.juniorofferproject.domain.offer.dto.OfferRequest;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class OfferFacade {
    public static final List<Offer> NO_NEW_OFFERS = Collections.emptyList();
    private final OfferRepository offerRepository;
    private final OfferDownloader offerDownloader;
    private final OfferExistingChecker offerExistingChecker;

    public List<OfferResponse> downloadNewOffersAndSaveThemIfNotExist() {
        List<DownloadedOffer> downloadedOffers = offerDownloader.downloadOffers();
        List<Offer> noExistingOffers = offerExistingChecker.checkIfTheOffersExistingInTheDatabase(downloadedOffers);
        List<Offer> savedOffers = offerRepository.saveAll(noExistingOffers);
        if(savedOffers == NO_NEW_OFFERS) {
            return Collections.emptyList();
        }

        return savedOffers.stream()
                .map(OfferMapper::mapFromOffer)
                .toList();
    }

    public List<OfferResponse> findAllOffers() {
        List<Offer> allOffers = offerRepository.findAll();
        return allOffers.stream()
                .map(OfferMapper::mapFromOffer)
                .toList();
    }

    public OfferResponse findOfferById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapFromOffer)
                .orElseThrow(() -> new OfferNotFoundException("Offer not found"));
    }

    public OfferResponse saveOffer(OfferRequest userNewOffer) {
        Offer offerToSave = OfferMapper.mapFromUserNewOffer(userNewOffer);
        Offer savedOffer = offerRepository.save(offerToSave);
        return OfferMapper.mapFromOffer(savedOffer);
    }
}

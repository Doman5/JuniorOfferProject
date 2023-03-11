package com.domanski.juniorofferproject.domain.offer;

import com.domanski.juniorofferproject.domain.offer.dto.OfferDto;
import com.domanski.juniorofferproject.domain.offer.dto.OfferFromUser;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OfferFacade {
    private final OfferRepository offerRepository;
    private final OfferDownloader offerDownloader;
    private final OfferExistingChecker offerExistingChecker;

    public void downloadNewOffersAndSaveThemIfNotExist() {
        List<Offer> downloadedOffers = offerDownloader.downloadOffers()
                .stream()
                .map(OfferMapper::mapFromDownloadedOffer)
                .toList();

        List<Offer> offersNotExistingInDatabase = offerExistingChecker.checkIfTheOffersExistingInTheDatabase(downloadedOffers);
        offerRepository.saveAll(offersNotExistingInDatabase);
    }

    public List<OfferDto> findAllOffers() {
        List<Offer> allOffers = offerRepository.findAll();
        return allOffers.stream()
                .map(OfferMapper::mapFromOffer)
                .toList();
    }

    public OfferDto findOfferById(Long id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapFromOffer)
                .orElseThrow(() -> new OfferNotFoundException("Offer not found"));
    }

    public OfferDto saveOffer(OfferFromUser userNewOffer) {
        Offer offerToSave = OfferMapper.mapFromUserNewOffer(userNewOffer);
        Offer savedOffer = offerRepository.save(offerToSave);
        return OfferMapper.mapFromOffer(savedOffer);
    }
}

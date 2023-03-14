package com.domanski.juniorofferproject.domain.offer;

import com.domanski.juniorofferproject.domain.offer.dto.DownloadedOffer;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class OfferExistingChecker {

    private final OfferRepository offerRepository;

    List<Offer> checkIfTheOffersExistingInTheDatabase(List<DownloadedOffer> downloadedOffers) {
        List<Offer> offersToCheckInDatabase = downloadedOffers.stream().map(OfferMapper::mapFromDownloadedOffer)
                .toList();

        return offersToCheckInDatabase.stream()
                .filter(offer -> !offerRepository.existsOfferByOfferUrl(offer.offerUrl()))
                .toList();
    }
}

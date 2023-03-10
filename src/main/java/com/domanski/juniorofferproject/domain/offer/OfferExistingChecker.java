package com.domanski.juniorofferproject.domain.offer;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class OfferExistingChecker {

    private final OfferRepository offerRepository;

    List<Offer> checkIfTheOffersExistingInTheDatabase(List<Offer> offersToCheckInDatabase) {
        return offersToCheckInDatabase.stream()
                .filter(offer -> !offerRepository.existsOfferByOfferUrl(offer.offerUrl()))
                .toList();
    }
}

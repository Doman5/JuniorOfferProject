package com.domanski.juniorofferproject.domain.offer;

import java.util.List;
import java.util.Optional;

interface OfferRepository {
    List<Offer> findAll();
    Optional<Offer> findById(Long id);
    Offer save(Offer offer);
    List<Offer> saveAll(List<Offer> offers);

    boolean existsOfferByOfferUrl(String offerUrl);
}

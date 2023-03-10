package com.domanski.juniorofferproject.domain.offer;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
class OfferRepositoryTestImpl implements OfferRepository {
    Map<Long, Offer> inMemoryOfferDataBase = new HashMap<>();
    long indexCounter = 1L;

    @Override
    public List<Offer> findAll() {
        return inMemoryOfferDataBase.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Offer> findById(Long id) {
        Offer offer = inMemoryOfferDataBase.get(id);
        if(offer == null) {
            return Optional.empty();
        }
        return Optional.of(offer);
    }

    @Override
    public Offer save(Offer offer) {
        inMemoryOfferDataBase.put(indexCounter, offer);
        return offer;
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        offers.forEach(offer -> {
            inMemoryOfferDataBase.put(indexCounter,offer);
            indexCounter++;
        });
        return inMemoryOfferDataBase.values()
                .stream()
                .toList();
    }

    @Override
    public boolean existsOfferByOfferUrl(String offerUrl) {
        return inMemoryOfferDataBase.values()
                .stream()
                .anyMatch(offer -> offer.offerUrl().equals(offerUrl));
    }

}

package com.domanski.juniorofferproject.domain.offer;

import com.domanski.juniorofferproject.domain.offer.dto.DownloadedOffer;
import com.domanski.juniorofferproject.domain.offer.dto.OfferResponse;
import com.domanski.juniorofferproject.domain.offer.dto.OfferRequest;

class OfferMapper {
    public static OfferResponse mapFromOffer(Offer offer) {
        return OfferResponse.builder()
                .offerUrl(offer.offerUrl())
                .companyName(offer.company())
                .jobTittle(offer.title())
                .salary(offer.salary())
                .build();
    }

    public static Offer mapFromUserNewOffer(OfferRequest userNewOffer) {
        return Offer.builder()
                .offerUrl(userNewOffer.offerUrl())
                .company(userNewOffer.company())
                .title(userNewOffer.title())
                .salary(userNewOffer.salary())
                .build();
    }

    public static Offer mapFromDownloadedOffer(DownloadedOffer downloadedOffer) {
        return Offer.builder()
                .offerUrl(downloadedOffer.offerUrl())
                .company(downloadedOffer.company())
                .title(downloadedOffer.title())
                .salary(downloadedOffer.salary())
                .build();
    }
}

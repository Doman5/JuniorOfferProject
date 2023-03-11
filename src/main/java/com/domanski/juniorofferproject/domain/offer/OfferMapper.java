package com.domanski.juniorofferproject.domain.offer;

import com.domanski.juniorofferproject.domain.offer.dto.DownloadedOffer;
import com.domanski.juniorofferproject.domain.offer.dto.OfferDto;
import com.domanski.juniorofferproject.domain.offer.dto.OfferFromUser;

class OfferMapper {
    public static OfferDto mapFromOffer(Offer offer) {
        return OfferDto.builder()
                .offerUrl(offer.offerUrl())
                .companyName(offer.companyName())
                .jobTittle(offer.jobTittle())
                .salary(offer.salary())
                .build();
    }

    public static Offer mapFromUserNewOffer(OfferFromUser userNewOffer) {
        return Offer.builder()
                .offerUrl(userNewOffer.jobUrl())
                .companyName(userNewOffer.companyName())
                .jobTittle(userNewOffer.jobTittle())
                .salary(userNewOffer.salary())
                .build();
    }

    public static Offer mapFromDownloadedOffer(DownloadedOffer downloadedOffer) {
        return Offer.builder()
                .offerUrl(downloadedOffer.offerUrl())
                .companyName(downloadedOffer.companyName())
                .jobTittle(downloadedOffer.jobTittle())
                .salary(downloadedOffer.salary())
                .build();
    }
}

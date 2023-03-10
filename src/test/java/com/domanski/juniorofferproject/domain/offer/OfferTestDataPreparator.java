package com.domanski.juniorofferproject.domain.offer;

import com.domanski.juniorofferproject.domain.offer.dto.DownloadedOffer;
import com.domanski.juniorofferproject.domain.offer.dto.OfferFromUser;

import java.util.List;

class OfferTestDataPreparator {

    static OfferFromUser prepareOfferFromUser() {
        return OfferFromUser.builder()
                .companyName("User's company")
                .jobTittle("User's job tittle")
                .jobUrl("https://www.test-offer.pl/User's-job-title")
                .salary("3000 - 4000 zł")
                .build();
    }

    static List<DownloadedOffer> prepareDownloadedOffersListWithThreeOffers() {
        return List.of(
                DownloadedOffer.builder()
                        .companyName("company 1")
                        .offerUrl("url 1")
                        .jobTittle("job 1")
                        .salary("3000 - 4000")
                        .build(),
                DownloadedOffer.builder()
                        .companyName("company 2")
                        .offerUrl("url 2")
                        .jobTittle("job 2")
                        .salary("2000 - 3000")
                        .build(),
                DownloadedOffer.builder()
                        .companyName("company 3")
                        .offerUrl("url 3")
                        .jobTittle("job 3")
                        .salary("4000 - 5000")
                        .build());
    }

    static List<DownloadedOffer> prepareDownloadedOffersListWithFourOffers() {
        return List.of(
                DownloadedOffer.builder()
                        .companyName("company 1")
                        .offerUrl("url 1")
                        .jobTittle("job 1")
                        .salary("3000 - 4000")
                        .build(),
                DownloadedOffer.builder()
                        .companyName("company 2")
                        .offerUrl("url 2")
                        .jobTittle("job 2")
                        .salary("2000 - 3000")
                        .build(),
                DownloadedOffer.builder()
                        .companyName("company 3")
                        .offerUrl("url 3")
                        .jobTittle("job 3")
                        .salary("4000 - 5000")
                        .build(),
                DownloadedOffer.builder()
                        .companyName("company 4")
                        .offerUrl("url 4")
                        .jobTittle("job 4")
                        .salary("9000 - 10000")
                        .build());
    }
}

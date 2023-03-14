package com.domanski.juniorofferproject.domain.offer;

import com.domanski.juniorofferproject.domain.offer.dto.DownloadedOffer;
import com.domanski.juniorofferproject.domain.offer.dto.OfferRequest;

import java.util.List;

class OfferTestDataPreparator {

    static OfferRequest prepareOfferFromUser() {
        return OfferRequest.builder()
                .company("User's company")
                .title("User's job tittle")
                .offerUrl("https://www.test-offer.pl/User's-job-title")
                .salary("3000 - 4000 zł")
                .build();
    }

    static List<DownloadedOffer> prepareDownloadedOffersListWithThreeOffers() {
        return List.of(
                DownloadedOffer.builder()
                        .company("company 1")
                        .offerUrl("url 1")
                        .title("job 1")
                        .salary("3000 - 4000")
                        .build(),
                DownloadedOffer.builder()
                        .company("company 2")
                        .offerUrl("url 2")
                        .title("job 2")
                        .salary("2000 - 3000")
                        .build(),
                DownloadedOffer.builder()
                        .company("company 3")
                        .offerUrl("url 3")
                        .title("job 3")
                        .salary("4000 - 5000")
                        .build());
    }

    static List<DownloadedOffer> prepareDownloadedOffersListWithFourOffers() {
        return List.of(
                DownloadedOffer.builder()
                        .company("company 1")
                        .offerUrl("url 1")
                        .title("job 1")
                        .salary("3000 - 4000")
                        .build(),
                DownloadedOffer.builder()
                        .company("company 2")
                        .offerUrl("url 2")
                        .title("job 2")
                        .salary("2000 - 3000")
                        .build(),
                DownloadedOffer.builder()
                        .company("company 3")
                        .offerUrl("url 3")
                        .title("job 3")
                        .salary("4000 - 5000")
                        .build(),
                DownloadedOffer.builder()
                        .company("company 4")
                        .offerUrl("url 4")
                        .title("job 4")
                        .salary("9000 - 10000")
                        .build());
    }
}

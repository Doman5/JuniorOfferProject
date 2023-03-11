package com.domanski.juniorofferproject.domain.offer;

import com.domanski.juniorofferproject.domain.offer.dto.OfferResponse;
import com.domanski.juniorofferproject.domain.offer.dto.OfferRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.domanski.juniorofferproject.domain.offer.OfferTestDataPreparator.prepareDownloadedOffersListWithFourOffers;
import static com.domanski.juniorofferproject.domain.offer.OfferTestDataPreparator.prepareDownloadedOffersListWithThreeOffers;
import static com.domanski.juniorofferproject.domain.offer.OfferTestDataPreparator.prepareOfferFromUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OfferFacadeTest {

    private final OfferDownloader offerDownloader;
    private final OfferRepositoryTestImpl offerRepositoryTest = new OfferRepositoryTestImpl();

    OfferFacade offerFacade = new OfferFacade(
            offerRepositoryTest,
            offerDownloader = mock(OfferDownloader.class),
            new OfferExistingChecker(offerRepositoryTest)
    );

    @Test
    public void should_return_three_new_offers_downloaded_from_external_service() {
        //given
        given(offerDownloader.downloadOffers()).willReturn(prepareDownloadedOffersListWithThreeOffers());
        offerFacade.downloadNewOffersAndSaveThemIfNotExist();
        //when
        List<OfferResponse> result = offerFacade.findAllOffers();
        //then
        assertThat(result).hasSize(3);
    }

    @Test
    public void should_return_four_offers_when_first_download_three_new_and_then_add_only_one_new_offer_which_no_exist_in_database() {
        //given
        given(offerDownloader.downloadOffers())
                .willReturn(prepareDownloadedOffersListWithThreeOffers())
                .willReturn(prepareDownloadedOffersListWithFourOffers());
        //when
        List<OfferResponse> firstOfferDownload = downloadNewOffersAndFindThem();
        List<OfferResponse> secondOfferDownload = downloadNewOffersAndFindThem();
        //then
        assertThat(firstOfferDownload).hasSize(3);
        assertThat(secondOfferDownload).hasSize(4);
    }

    @Test
    public void should_return_offer_searcher_for_by_id() {
        //given
        Long givenId = 2L;
        given(offerDownloader.downloadOffers())
                .willReturn(prepareDownloadedOffersListWithThreeOffers());
        offerFacade.downloadNewOffersAndSaveThemIfNotExist();
        //when
        OfferResponse result = offerFacade.findOfferById(givenId);
        //then
        assertThat(result.jobTittle()).isEqualTo("job 2");
        assertThat(result.companyName()).isEqualTo("company 2");
        assertThat(result.offerUrl()).isEqualTo("url 2");
    }

    @Test
    public void should_throw_offer_not_found_exception_when_offer_which_that_id_no_exist_in_database() {
        //given
        Long givenId = 2L;
        // ...
        assertThrows(OfferNotFoundException.class, () -> offerFacade.findOfferById(givenId), "Offer not found");
    }

    @Test
    public void should_saved_offer_from_user() {
        //given
        OfferRequest offerFromUser = prepareOfferFromUser();
        //when
        OfferResponse result = offerFacade.saveOffer(offerFromUser);
        //then
        assertThat(result).isNotNull();
        assertThat(result.companyName()).isEqualTo("User's company");
        assertThat(result.jobTittle()).isEqualTo("User's job tittle");
        assertThat(result.salary()).isEqualTo("3000 - 4000 zł");
        assertThat(result.offerUrl()).isEqualTo("https://www.test-offer.pl/User's-job-title");
    }

    private List<OfferResponse> downloadNewOffersAndFindThem() {
        offerFacade.downloadNewOffersAndSaveThemIfNotExist();
        return offerFacade.findAllOffers();
    }
}
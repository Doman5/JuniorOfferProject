package com.domanski.juniorofferproject.features;

import com.domanski.juniorofferproject.BaseIntegrationTest;
import com.domanski.juniorofferproject.JuniorOfferProjectApplication;
import com.domanski.juniorofferproject.domain.offer.dto.OfferResponse;
import com.domanski.juniorofferproject.infrastucture.offer.scheduler.OfferDownloadScheduler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JuniorOfferProjectApplication.class)
public class TypicalScenarioUserWantToSeeOffersIntegrationTest extends BaseIntegrationTest implements SampleJobOffers {

    @Autowired
    OfferDownloadScheduler offerDownloadScheduler;

    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {
        // step 1: there are no offers in external http server.
        // given && when && then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody(bodyWithZeroOffersJson())));


        // step 2: scheduler ran 1st time and made Get to external server and system, added 0 offers to database.
        //given && when
        List<OfferResponse> offerResponsesFromExternalService1 = offerDownloadScheduler.DownloadOfferFromExternalServer();
        //then
        assertThat(offerResponsesFromExternalService1).hasSize(0);


        // step 3: user tried to get jwt token by requesting Post /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401).
        // step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401).
        // step 5: user made Post /register with username=someUser, password=somePassword and system register with status OK(200).
        // step 6: user tried get jwt token by requesting POST /token with username=someUSer, password=somePassword and system returned OK(200) and jwt token=AAAA.BBBB.CCCC
        // step 7: user made GET /offers with header"Authorization: Bearer AAAA.BBBB.CCCC" and system returned OK(200) with 0 offers
        //given && when
        MvcResult performGetOffersFirstTime = mockMvc.perform(MockMvcRequestBuilders.get("/offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String offersJsonTakenFirstTime = performGetOffersFirstTime.getResponse().getContentAsString();
        List<OfferResponse> offerResponsesFromOfferController1 = objectMapper.readValue(offersJsonTakenFirstTime, new TypeReference<>() {
        });
        //then
        assertThat(offerResponsesFromOfferController1).isEmpty();

        // step 8: there are 2 new offers in external HTTP server
        // given && when && then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody(bodyWithTwoOffersJson())));


        // step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        //given && when
        List<OfferResponse> offerResponsesFromExternalService2 = offerDownloadScheduler.DownloadOfferFromExternalServer();
        //then
        assertThat(offerResponsesFromExternalService2).hasSize(2);


        //  step 10: user made GET /offers with header"Authorization: Bearer AAAA.BBBB.CCCC" and system returned OK(200) with 2 offers with
        //  ids: 1000 and 2000
        //  step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message "Offer with id 9999 not found"
        // given
        String offerId = "9999";
        // when
        ResultActions performGetOfferWithNoExistingId = mockMvc.perform(MockMvcRequestBuilders.get("/offers/" + offerId)
                .contentType(MediaType.APPLICATION_JSON));
        // then
        performGetOfferWithNoExistingId
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                        "message": "Offer with id 9999 not found",
                        "status": "NOT_FOUND"
                        }
                        """.trim()));


        //  step 12: user made GET /offers/1000 and system returned OK(200) with offer
        //  step 13: there are 2 new offers in external HTTP server
        //  step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000
        //  to database
        //  step 15: user made GET /offers with header"Authorization: Bearer AAAA.BBBB.CCCC" and system returned OK(200) with 4 offers
        //  with ids: 1000, 2000, 3000 and 4000


        // step 16: user made Post /offers with header"Authorization: Bearer AAAA.BBBB.CCCC" and offer and system returned CREATED(201) with saved offer
        //given && when
        MvcResult performPostOffers = mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "offerUrl": "offerUrl.com/java-junior",
                                "title": "java junior",
                                "company": "app company",
                                "salary": "5000 - 6000"
                                }
                                """.trim()))
                .andExpect(status().isCreated())
                .andReturn();
        String responseOffersJson = performPostOffers.getResponse().getContentAsString();
        OfferResponse offerResponse = objectMapper.readValue(responseOffersJson, OfferResponse.class);
        //then
        assertThat(offerResponse).isNotNull();


        // step 17: user made GET /offers with header"Authorization: Bearer AAAA.BBBB.CCCC" and system returned OK(200) with 3 offers
        //given && when
        MvcResult performGetOffersAfterUserAddOffer = mockMvc.perform(MockMvcRequestBuilders.get("/offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String offersJsonTakenThirdTime = performGetOffersAfterUserAddOffer.getResponse().getContentAsString();
        List<OfferResponse> offerResponsesFromOfferController3 = objectMapper.readValue(offersJsonTakenThirdTime, new TypeReference<>() {
        });
        //then
        assertThat(offerResponsesFromOfferController3.size()).isEqualTo(3);
    }
}

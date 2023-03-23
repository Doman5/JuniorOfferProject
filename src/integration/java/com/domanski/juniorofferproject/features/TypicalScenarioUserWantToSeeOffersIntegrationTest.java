package com.domanski.juniorofferproject.features;

import com.domanski.juniorofferproject.BaseIntegrationTest;
import com.domanski.juniorofferproject.JuniorOfferProjectApplication;
import com.domanski.juniorofferproject.domain.offer.dto.OfferResponse;
import com.domanski.juniorofferproject.infrastucture.offer.scheduler.OfferDownloadScheduler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JuniorOfferProjectApplication.class)
public class TypicalScenarioUserWantToSeeOffersIntegrationTest extends BaseIntegrationTest implements SampleJobOffers {

    @Autowired
    OfferDownloadScheduler offerDownloadScheduler;

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
            .build();

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("offer.offer-downloader.http.client.config.port", () -> wireMockServer.getPort());
        registry.add("offer.offer-downloader.http.client.config.uri", () -> WIRE_MOCK_HOST);
    }

    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {
        // step 1: there are no offers in external http server.
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody(bodyWithZeroOffersJson())));


        // step 2: scheduler ran 1st time and made Get to external server and system, added 0 offers to database.
        //given && when
        List<OfferResponse> offerResponsesFromSchedulerWithoutAnyOffer = offerDownloadScheduler.DownloadOfferFromExternalServer();
        //then
        assertThat(offerResponsesFromSchedulerWithoutAnyOffer).hasSize(0);


        // step 3: user tried to get jwt token by requesting Post /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401).
        // step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401).
        // step 5: user made Post /register with username=someUser, password=somePassword and system register with status OK(200).
        // step 6: user tried get jwt token by requesting POST /token with username=someUSer, password=somePassword and system returned OK(200) and jwt token=AAAA.BBBB.CCCC
        // step 7: user made GET /offers with header"Authorization: Bearer AAAA.BBBB.CCCC" and system returned OK(200) with 0 offers
        //given && when
        String performGetOffersFirstTime = mockMvc.perform(MockMvcRequestBuilders.get("/offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<OfferResponse> offerResponsesWithoutAnyOffer = objectMapper.readValue(performGetOffersFirstTime, new TypeReference<>() {
        });
        //then
        assertThat(offerResponsesWithoutAnyOffer).isEmpty();


        // step 8: there are 2 new offers in external HTTP server
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody(bodyWithTwoOffersJson())));


        // step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        //given && when
        List<OfferResponse> offerResponsesFromSchedulerWithTwoOffers = offerDownloadScheduler.DownloadOfferFromExternalServer();
        //then
        assertThat(offerResponsesFromSchedulerWithTwoOffers).hasSize(2);


        //  step 10: user made GET /offers with header"Authorization: Bearer AAAA.BBBB.CCCC" and system returned OK(200) with 2 offers with
        //  ids: 1000 and 2000
        //given && when
        String performGetOffersSecondTime = mockMvc.perform(MockMvcRequestBuilders.get("/offers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<OfferResponse> offerResponsesWithTwoOffers = objectMapper.readValue(performGetOffersSecondTime, new TypeReference<>() {
        });
        // then
        OfferResponse expectedFirstOffer = offerResponsesFromSchedulerWithTwoOffers.get(0);
        OfferResponse expectedSecondOffer = offerResponsesFromSchedulerWithTwoOffers.get(1);
        assertAll(
                () -> assertThat(offerResponsesWithTwoOffers).hasSize(2),
                () -> assertThat(offerResponsesWithTwoOffers).contains(expectedFirstOffer,expectedSecondOffer)
        );


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
        //given
        String existingOfferId = expectedFirstOffer.id();
        //when
        String performGetOffersWhereOfferIdEquals1000 = mockMvc.perform(MockMvcRequestBuilders.get("/offers/" + existingOfferId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OfferResponse offerResponseWithOfferId1000 = objectMapper.readValue(performGetOffersWhereOfferIdEquals1000, OfferResponse.class);
        //then
        assertThat(offerResponseWithOfferId1000).isEqualTo(expectedFirstOffer);


        //  step 13: there are 2 new offers in external HTTP server
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody(bodyWithFourOffersJson())));


        //  step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000
        //  to database
        //given && when
        List<OfferResponse> offerResponsesFromSchedulerWithTwoNewOffers = offerDownloadScheduler.DownloadOfferFromExternalServer();
        //then
        assertThat(offerResponsesFromSchedulerWithTwoNewOffers).hasSize(2);


        //  step 15: user made GET /offers with header"Authorization: Bearer AAAA.BBBB.CCCC" and system returned OK(200) with 4 offers
        //  with ids: 1000, 2000, 3000 and 4000
        //given && when
        String performGetOffersThirdTime = mockMvc.perform(MockMvcRequestBuilders.get("/offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<OfferResponse> offerResponseWithFourOffers = objectMapper.readValue(performGetOffersThirdTime, new TypeReference<>() {
        });
        //then
        OfferResponse expectedThirdOffer = offerResponsesFromSchedulerWithTwoNewOffers.get(0);
        OfferResponse expectedFourthOffer = offerResponsesFromSchedulerWithTwoNewOffers.get(1);
        assertAll(
                () -> assertThat(offerResponseWithFourOffers).hasSize(4),
                () -> assertThat(offerResponseWithFourOffers).contains(expectedFirstOffer,expectedSecondOffer,expectedThirdOffer,expectedFourthOffer)
        );

        // step 16: user made Post /offers with header"Authorization: Bearer AAAA.BBBB.CCCC" and offer and system returned CREATED(201) with saved offer
        //given && when
        ResultActions performPostOffer = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "offerUrl": "https://offerUrl.com/java-junior",
                        "title": "java junior",
                        "company": "app company",
                        "salary": "5000 - 6000"
                        }
                        """.trim()));

        String responseOffersJson = performPostOffer.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferResponse offerResponse = objectMapper.readValue(responseOffersJson, OfferResponse.class);
        //then
        assertAll(
                () -> assertThat(offerResponse.offerUrl()).isEqualTo("https://offerUrl.com/java-junior"),
                () -> assertThat(offerResponse.jobTittle()).isEqualTo("java junior"),
                () -> assertThat(offerResponse.companyName()).isEqualTo("app company"),
                () -> assertThat(offerResponse.salary()).isEqualTo("5000 - 6000"),
                () -> assertThat(offerResponse.id()).isNotNull()
        );


        // step 17: user made GET /offers with header"Authorization: Bearer AAAA.BBBB.CCCC" and system returned OK(200) with 5 offers
        //given && when
        MvcResult performGetOffersAfterUserAddOffer = mockMvc.perform(MockMvcRequestBuilders.get("/offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String offersJsonTakenThirdTime = performGetOffersAfterUserAddOffer.getResponse().getContentAsString();
        List<OfferResponse> offerResponsesWithThreeOffers = objectMapper.readValue(offersJsonTakenThirdTime, new TypeReference<>() {
        });
        //then
        assertThat(offerResponsesWithThreeOffers.size()).isEqualTo(5);
    }
}

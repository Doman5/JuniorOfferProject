package com.domanski.juniorofferproject.controller.error;

import com.domanski.juniorofferproject.BaseIntegrationTest;
import com.domanski.juniorofferproject.infrastucture.offer.controller.error.OfferPostErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DuplicateOffersUrlErrorIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_409_conflict_when_user_try_add_offer_which_already_exist() throws Exception {
        // step 1: user add offer for first time
        //given && when
        ResultActions performPostOfferFirstTime = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "offerUrl": "offerUrl.com/java-junior",
                        "title": "java junior",
                        "company": "app company",
                        "salary": "5000 - 6000"
                        }
                        """.trim()));
        // then
        performPostOfferFirstTime.andExpect(status().isCreated());


        // step 2: user try to add the same offer for second time
        //given && when
        ResultActions performPostTheSameOfferSecondTime = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "offerUrl": "offerUrl.com/java-junior",
                        "title": "java junior",
                        "company": "app company",
                        "salary": "5000 - 6000"
                        }
                        """.trim()));
        //then
        String secondPerformResponseAsJson = performPostTheSameOfferSecondTime.andExpect(status().isConflict())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferPostErrorResponse offerPostErrorResponse = objectMapper.readValue(secondPerformResponseAsJson, OfferPostErrorResponse.class);
        assertThat(offerPostErrorResponse.message()).isEqualTo("Given offer is already exist");
    }
}

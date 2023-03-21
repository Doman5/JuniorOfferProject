package com.domanski.juniorofferproject.apivalidationerror;

import com.domanski.juniorofferproject.BaseIntegrationTest;
import com.domanski.juniorofferproject.infrastucture.offer.apivalidation.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_400_bad_request_and_validation_message_when_request_has_empty_body() throws Exception {
        //given
        //when
        ResultActions perform = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {}
                        """.trim()));
        //then
        MvcResult mvcResult = perform
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto apiValidationError = objectMapper.readValue(json, ApiValidationErrorDto.class);

        assertThat(apiValidationError.messages()).containsExactlyInAnyOrder("offer url muss not be null",
                "offer title muss not be null",
                "company name muss not be null",
                "salary muss not be null",
                "offer url muss not be blank",
                "company name muss not be blank",
                "offer title muss not be blank",
                "salary muss not be blank");
    }

    @Test
    public void should_return_400_bad_request_and_validation_message_when_requests_arguments_were_blanc() throws Exception {
        //given
        //when
        ResultActions perform = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "offerUrl": " ",
                        "company": " ",
                        "title": " ",
                        "salary": " "
                        }
                        """.trim()));
        //then
        MvcResult mvcResult = perform
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto apiValidationError = objectMapper.readValue(json, ApiValidationErrorDto.class);

        assertThat(apiValidationError.messages()).containsExactlyInAnyOrder(
                "offer url muss not be blank",
                "company name muss not be blank",
                "offer title muss not be blank",
                "salary muss not be blank");
    }

    @Test
    public void should_return_400_bad_request_and_validation_message_when_requests_arguments_have_null_values() throws Exception {
        //given
        //when
        ResultActions perform = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "offerUrl": null,
                        "company": null,
                        "title": null,
                        "salary": null
                        }
                        """.trim()));
        //then
        MvcResult mvcResult = perform
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto apiValidationError = objectMapper.readValue(json, ApiValidationErrorDto.class);

        assertThat(apiValidationError.messages()).containsExactlyInAnyOrder("offer url muss not be null",
                "offer title muss not be null",
                "company name muss not be null",
                "salary muss not be null",
                "offer url muss not be blank",
                "company name muss not be blank",
                "offer title muss not be blank",
                "salary muss not be blank");
    }
}

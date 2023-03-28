package com.domanski.juniorofferproject.apivalidationerror.register;

import com.domanski.juniorofferproject.BaseIntegrationTest;
import com.domanski.juniorofferproject.infrastucture.offer.apivalidation.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiRegisterValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_400_bad_request_and_validation_message_when_request_has_empty_body() throws Exception {
        //given
        //when
        ResultActions perform = mockMvc.perform(post("/register")
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

        assertThat(apiValidationError.messages()).containsExactlyInAnyOrder(
                "username muss not be blank",
                "password muss not be blank"
                );
    }

    @Test
    public void should_return_400_bad_request_and_validation_message_when_request_arguments_has_empty_values() throws Exception {
        //given
        //when
        ResultActions perform = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "username": " ",
                        "password": " "
                        }
                        """.trim()));
        //then
        MvcResult mvcResult = perform
                .andExpect(status().isBadRequest())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto apiValidationError = objectMapper.readValue(json, ApiValidationErrorDto.class);

        assertThat(apiValidationError.messages()).containsExactlyInAnyOrder(
                "username muss not be blank",
                "password muss not be blank"
        );
    }
}

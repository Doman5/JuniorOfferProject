package com.domanski.juniorofferproject.controller.error.token;

import com.domanski.juniorofferproject.BaseIntegrationTest;
import com.domanski.juniorofferproject.infrastucture.offer.controller.error.OfferPostErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DuplicateUsernameErrorIntegrationTest extends BaseIntegrationTest {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void should_return_409_conflict_when_user_try_register_user_who_already_exist() throws Exception {
        // step 1: user add offer for first time
        //given && when
        ResultActions performPostRegisterFirstTime = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "username": "user",
                        "password": "password"
                        }
                        """.trim()));
        // then
        performPostRegisterFirstTime.andExpect(status().isCreated());


        // step 2: user try to add the same offer for second time
        //given && when
        ResultActions performPostTheSameUserSecondTime = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "username": "user",
                        "password": "password"
                        }
                        """.trim()));
        //then
        String secondPerformResponseAsJson = performPostTheSameUserSecondTime.andExpect(status().isConflict())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferPostErrorResponse offerPostErrorResponse = objectMapper.readValue(secondPerformResponseAsJson, OfferPostErrorResponse.class);
        assertThat(offerPostErrorResponse.message()).isEqualTo("User is already exist");
    }
}

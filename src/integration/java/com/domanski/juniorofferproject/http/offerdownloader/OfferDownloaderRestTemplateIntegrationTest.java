package com.domanski.juniorofferproject.http.offerdownloader;

import com.domanski.juniorofferproject.domain.offer.OfferDownloader;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class OfferDownloaderRestTemplateIntegrationTest {

    private final static String APPLICATION_JSON_CONTENT_TYPE_VALUE = "application/json";
    private final static String CONTENT_TYPE_HEADER_KEY = "Content-Type";

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    OfferDownloader offerDownloader = new OfferDownloaderRestTemplateIntegrationTestConfig().offerDownloader(
            1000,
            1000,
            wireMockServer.getPort());

    @Test
    public void should_return_internal_server_error_when_fault_connection_reset_by_peer() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.CONNECTION_RESET_BY_PEER))
                );
        //when
        Throwable throwable = catchThrowable(() -> offerDownloader.downloadOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void should_return_internal_server_error_when_fault_empty_response() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.EMPTY_RESPONSE))
        );
        //when
        Throwable throwable = catchThrowable(() -> offerDownloader.downloadOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void should_return_internal_server_error_when_fault_malformed_response_chunk() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.MALFORMED_RESPONSE_CHUNK))
        );
        //when
        Throwable throwable = catchThrowable(() -> offerDownloader.downloadOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void should_return_internal_server_error_when_fault_random_data_then_closed() {
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.RANDOM_DATA_THEN_CLOSE))
        );
        //when
        Throwable throwable = catchThrowable(() -> offerDownloader.downloadOffers());
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void should_return_internal_server_error_when_response_delay_is_5000_ms_nad_client_has_1000_ms_read_timeout() {
        //given
        OfferDownloader offerDownloader = new OfferDownloaderRestTemplateIntegrationTestConfig().offerDownloader(
                2000,
                1000,
                wireMockServer.getPort());

        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)
                        .withFixedDelay(5000))
        );
        //when
        Throwable throwable = catchThrowable(offerDownloader::downloadOffers);
        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
}

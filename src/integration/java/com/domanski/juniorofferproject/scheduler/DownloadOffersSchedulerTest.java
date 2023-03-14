package com.domanski.juniorofferproject.scheduler;

import com.domanski.juniorofferproject.BaseIntegrationTest;
import com.domanski.juniorofferproject.JuniorOfferProjectApplication;
import com.domanski.juniorofferproject.domain.offer.OfferDownloader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = JuniorOfferProjectApplication.class, properties = "scheduling.enabled=true")
public class DownloadOffersSchedulerTest extends BaseIntegrationTest {

    @SpyBean
    OfferDownloader offerDownloader;

    @Test
    public void should_run_download_offers_exactly_given_times() {
        await()
                .atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(offerDownloader, times(2)).downloadOffers());
    }
}

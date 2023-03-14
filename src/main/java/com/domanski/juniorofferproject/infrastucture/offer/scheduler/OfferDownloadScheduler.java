package com.domanski.juniorofferproject.infrastucture.offer.scheduler;

import com.domanski.juniorofferproject.domain.offer.OfferFacade;
import com.domanski.juniorofferproject.domain.offer.dto.OfferResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class OfferDownloadScheduler {

    private final OfferFacade offerFacade;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final static String STARTED_DOWNLOAD_OFFERS = "Started downloading offers {}";
    private final static String END_DOWNLOAD_OFFERS = "End downloading offers {}";
    private final static String SAVED_NEW_OFFERS = "Saved new {} offers";

    @Scheduled(fixedDelayString = "${offer.scheduler.request.delay}")
    public List<OfferResponse> DownloadOfferFromExternalServer() {
        log.info(STARTED_DOWNLOAD_OFFERS, dateFormat.format(new Date()));
        List<OfferResponse> offers = offerFacade.downloadNewOffersAndSaveThemIfNotExist();
        log.info(END_DOWNLOAD_OFFERS, dateFormat.format(new Date()));
        log.info(SAVED_NEW_OFFERS, offers.size());
        return offers;
    }
}

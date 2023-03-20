package com.domanski.juniorofferproject.infrastucture.offer.controller;

import com.domanski.juniorofferproject.domain.offer.OfferFacade;
import com.domanski.juniorofferproject.domain.offer.dto.OfferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfferController {

    private final OfferFacade offerFacade;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferResponse>> getAllOffers() {
        List<OfferResponse> allOffers = offerFacade.findAllOffers();
        return ResponseEntity.ok(allOffers);
    }
}

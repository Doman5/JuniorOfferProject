package com.domanski.juniorofferproject.infrastucture.offer.controller;

import com.domanski.juniorofferproject.domain.offer.OfferFacade;
import com.domanski.juniorofferproject.domain.offer.dto.OfferRequest;
import com.domanski.juniorofferproject.domain.offer.dto.OfferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @GetMapping("/offers/{id}")
    public ResponseEntity<OfferResponse> getOffer(@PathVariable String id) {
        OfferResponse offer = offerFacade.findOfferById(id);
        return ResponseEntity.ok(offer);
    }

    @PostMapping("/offers")
    public ResponseEntity<OfferResponse> saveOffer(@RequestBody @Valid OfferRequest offerRequest) {
        OfferResponse offerResponse = offerFacade.saveOffer(offerRequest);
        return new ResponseEntity<>(offerResponse, HttpStatus.CREATED);
    }
}

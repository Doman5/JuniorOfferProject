package com.domanski.juniorofferproject.domain.offer;

import com.domanski.juniorofferproject.domain.offer.dto.DownloadedOffer;

import java.util.List;

interface OfferDownloader {
    List<DownloadedOffer> downloadOffers();
}

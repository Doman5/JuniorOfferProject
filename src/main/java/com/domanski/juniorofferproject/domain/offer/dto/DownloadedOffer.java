package com.domanski.juniorofferproject.domain.offer.dto;

import lombok.Builder;

@Builder
public record DownloadedOffer(String offerUrl,
                              String title,
                              String company,
                              String salary) {
}

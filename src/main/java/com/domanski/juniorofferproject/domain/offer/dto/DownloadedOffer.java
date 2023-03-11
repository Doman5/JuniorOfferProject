package com.domanski.juniorofferproject.domain.offer.dto;

import lombok.Builder;

@Builder
public record DownloadedOffer(String offerUrl,
                              String jobTittle,
                              String companyName,
                              String salary) {
}

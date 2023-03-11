package com.domanski.juniorofferproject.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferDto(String offerUrl,
                       String jobTittle,
                       String companyName,
                       String salary) {
}

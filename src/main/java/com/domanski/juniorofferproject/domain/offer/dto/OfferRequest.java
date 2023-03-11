package com.domanski.juniorofferproject.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferRequest(String jobUrl,
                           String jobTittle,
                           String companyName,
                           String salary) {
}

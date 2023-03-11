package com.domanski.juniorofferproject.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferResponse(String offerUrl,
                            String jobTittle,
                            String companyName,
                            String salary) {
}

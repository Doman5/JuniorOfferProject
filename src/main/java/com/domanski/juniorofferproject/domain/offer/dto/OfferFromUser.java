package com.domanski.juniorofferproject.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferFromUser(String jobUrl,
                            String jobTittle,
                            String companyName,
                            String salary) {
}

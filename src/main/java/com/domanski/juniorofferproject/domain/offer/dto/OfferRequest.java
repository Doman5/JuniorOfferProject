package com.domanski.juniorofferproject.domain.offer.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
public record OfferRequest(
        @NotNull(message = "{offer.url.not.null}")
        @NotBlank(message = "{offer.url.not.blank}")
        String offerUrl,
        @NotNull(message = "{offer.title.not.null}")
        @NotBlank(message = "{offer.title.not.blank}")
        String title,
        @NotNull(message = "{offer.company.not.null}")
        @NotBlank(message = "{offer.company.not.blank}")
        String company,
        @NotNull(message = "{offer.salary.not.null}")
        @NotBlank(message = "{offer.salary.not.blank}")
        String salary) {
}

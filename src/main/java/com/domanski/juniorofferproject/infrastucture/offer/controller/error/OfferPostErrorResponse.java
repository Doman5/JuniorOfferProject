package com.domanski.juniorofferproject.infrastucture.offer.controller.error;

import org.springframework.http.HttpStatus;

public record OfferPostErrorResponse(String message,
                                     HttpStatus status) {
}

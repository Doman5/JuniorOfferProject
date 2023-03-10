package com.domanski.juniorofferproject.domain.offer;

class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(String message) {
        super(message);
    }
}

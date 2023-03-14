package com.domanski.juniorofferproject.features;

public interface SampleJobOffers {

    default String bodyWithZeroOffersJson() {
        return "[]";
    }
}

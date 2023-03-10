package com.domanski.juniorofferproject.domain.offer;

import lombok.Builder;

@Builder
record Offer(Long id,
                    String offerUrl,
                    String jobTittle,
                    String companyName,
                    String salary) {
}

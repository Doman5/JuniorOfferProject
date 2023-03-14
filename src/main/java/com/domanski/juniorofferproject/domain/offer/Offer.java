package com.domanski.juniorofferproject.domain.offer;

import lombok.Builder;

@Builder
record Offer(Long id,
                    String offerUrl,
                    String title,
                    String company,
                    String salary) {
}

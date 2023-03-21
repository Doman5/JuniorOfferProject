package com.domanski.juniorofferproject.domain.offer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
record Offer(@Id String id,
                    @Indexed(unique = true) String offerUrl,
                    String title,
                    String company,
                    String salary) {
}

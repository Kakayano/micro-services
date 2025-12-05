package org.example.reviewservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    int productId;
    int reviewId;
    String author;
    String subject;
    String content;
}

package com.epam.training.travel.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Travelling {
    private long id;
    private String name;
    private String location;
    private LocalDate visitedAt;
    private Category category;
    private String review;
}

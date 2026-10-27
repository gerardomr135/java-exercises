package com.epam.training.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieSummaryDto {

    private String id;

    @JsonProperty("primaryTitle")
    private String title;

    @JsonProperty("startYear")
    private Integer year;
}

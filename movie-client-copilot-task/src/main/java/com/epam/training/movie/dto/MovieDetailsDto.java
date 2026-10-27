package com.epam.training.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDetailsDto {

    private String id;

    @JsonProperty("primaryTitle")
    private String title;

    @JsonProperty("startYear")
    private Integer year;

    private List<String> genres;
    private List<PersonDto> directors;
    private List<PersonDto> writers;
    private List<PersonDto> stars;
    private String plot;

    @JsonProperty("rating")
    private RatingDto rating;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PersonDto {
        @JsonProperty("displayName")
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RatingDto {
        @JsonProperty("aggregateRating")
        private Double aggregateRating;
    }
}

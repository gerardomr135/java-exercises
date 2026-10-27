package com.epam.training.movie.client;

import com.epam.training.movie.dto.MovieDetailsDto;
import com.epam.training.movie.dto.MovieSummaryDto;
import com.epam.training.movie.dto.MovieSearchResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImdbApiClient {

    private final RestTemplate restTemplate;

    @Value("${app.imdb.base-url}")
    private String baseUrl;

    @Value("${app.imdb.search-path:/search/titles}")
    private String searchPath;

    @Value("${app.imdb.details-path:/titles/{id}}")
    private String detailsPath;

    public List<MovieSummaryDto> searchMovies(String query) {
        log.info("Calling IMDb search API for query: {}", query);
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + searchPath)
                .queryParam("query", query)
                .build()
                .toUriString();
        MovieSearchResultDto response = restTemplate.getForObject(url, MovieSearchResultDto.class);
        return response != null && response.getTitles() != null ? response.getTitles() : Collections.emptyList();
    }

    public MovieDetailsDto getMovieDetails(String id) {
        log.info("Calling IMDb details API for id: {}", id);
        String url = baseUrl + detailsPath.replace("{id}", id);
        return restTemplate.getForObject(url, MovieDetailsDto.class);
    }
}

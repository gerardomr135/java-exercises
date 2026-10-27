package com.epam.training.movie.service.impl;

import com.epam.training.movie.client.ImdbApiClient;
import com.epam.training.movie.dto.MovieDetailsDto;
import com.epam.training.movie.dto.MovieSummaryDto;
import com.epam.training.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final ImdbApiClient imdbApiClient;

    @Override
    public List<MovieSummaryDto> searchMovies(String query) {
        log.info("Searching movies for query: {}", query);
        return imdbApiClient.searchMovies(query);
    }

    @Override
    public MovieDetailsDto getMovieDetails(String id) {
        log.info("Loading movie details for id: {}", id);
        return imdbApiClient.getMovieDetails(id);
    }
}

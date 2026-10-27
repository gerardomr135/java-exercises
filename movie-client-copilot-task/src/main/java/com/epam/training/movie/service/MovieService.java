package com.epam.training.movie.service;

import com.epam.training.movie.dto.MovieDetailsDto;
import com.epam.training.movie.dto.MovieSummaryDto;

import java.util.List;

public interface MovieService {

    List<MovieSummaryDto> searchMovies(String query);

    MovieDetailsDto getMovieDetails(String id);
}

package com.epam.training.movie.controller;

import com.epam.training.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/search")
    public String showSearchPage(Model model) {
        log.info("Displaying search page");
        model.addAttribute("searchQuery", "");
        return "search";
    }

    @PostMapping("/search")
    public String searchMovies(@RequestParam String query, Model model) {
        log.info("Searching movies for query: {}", query);
        model.addAttribute("searchQuery", query);
        model.addAttribute("movies", movieService.searchMovies(query));
        return "search";
    }

    @GetMapping("/movies/{id}")
    public String showMovieDetails(@PathVariable String id, Model model) {
        log.info("Loading movie details for id: {}", id);
        model.addAttribute("movie", movieService.getMovieDetails(id));
        return "movie-details";
    }
}

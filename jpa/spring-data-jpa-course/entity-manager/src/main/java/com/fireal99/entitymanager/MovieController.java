package com.fireal99.entitymanager;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieDAO movieDAO;

    public MovieController(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        movieDAO.persist(movie);
        return movie;
    }

    @GetMapping
    public List<Movie> findAll() {
        return movieDAO.findAll();
    }

    @GetMapping("/{id}")
    public Movie findOne(@PathVariable Long id) {
        return movieDAO.findById(id);
    }
}

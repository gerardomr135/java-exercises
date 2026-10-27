package com.fireal99.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Transactional
public class MovieDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public MovieDAO() {}

    public void persist(Movie movie) {
        entityManager.persist(movie);
    }

    public Movie findById(Long id) {
        return entityManager.find(Movie.class, id);
    }

    public List<Movie> findAll() {
        return entityManager.createQuery("select m from Movie m", Movie.class).getResultList();
    }

    public void remove(Movie movie) {
        entityManager.remove(movie);
    }

    public void update(Movie movie) {
        entityManager.merge(movie);
    }

    public void test() {
        List<Genre> genres = new ArrayList<Genre>();
        genres.add(new Genre(GenreEnum.DRAMA));
        genres.add(new Genre(GenreEnum.HORROR));
        genres.add(new Genre(GenreEnum.THRILLER));

        for (Genre genre : genres) {
            entityManager.persist(genre);
        }

        Movie movie = new Movie("American History X", 1999, "English", List.of(genres.get(0), genres.get(1)));

        entityManager.persist(movie);

        movie.setReleaseYear(1998);
        entityManager.merge(movie);

        movie.getGenres().add(genres.get(genres.size()-1));

        movie = entityManager.find(Movie.class, movie.getId());

        System.out.println(movie);
    }
}

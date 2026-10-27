package com.epam.training.travel.data;

import com.epam.training.travel.domain.Travelling;

import java.util.List;
import java.util.Optional;

public interface TravellingRepository {
    List<Travelling> findAll();

    Travelling create(Travelling travelling);

    Optional<Travelling> getById(long id);

    void addReview(long id, String review);
}

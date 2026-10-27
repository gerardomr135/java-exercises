package com.epam.training.travel.data;

import com.epam.training.travel.domain.Category;
import com.epam.training.travel.domain.Travelling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryTravellingRepository implements TravellingRepository {

        private final List<Travelling> travellings = new ArrayList<>();

        @PostConstruct
        private void createTestData() {
                create(Travelling.builder()
                                .id(1)
                                .name("Pergamonmuseum")
                                .location("Berlin, Germany")
                                .visitedAt(LocalDate.of(2003, 9, 10))
                                .category(Category.CULTURE)
                                .build());

                create(Travelling.builder()
                                .id(1)
                                .name("Montmorency Falls")
                                .location("Québec City, Quebec, Canada")
                                .visitedAt(LocalDate.of(2008, 2, 15))
                                .category(Category.NATURE)
                                .build());

                create(Travelling.builder()
                                .id(1)
                                .name("Buckingham Palace")
                                .location("London, United Kingdom")
                                .visitedAt(LocalDate.of(2013, 7, 20))
                                .category(Category.ARCHITECTURE)
                                .build());

                create(Travelling.builder()
                                .id(1)
                                .name("Tower Bridge")
                                .location("London, United Kingdom")
                                .visitedAt(LocalDate.of(2025, 1, 15))
                                .category(Category.ARCHITECTURE)
                                .build());
        }

        @Override
        public List<Travelling> findAll() {
                return travellings;
        }

        @Override
        public Travelling create(Travelling travelling) {
                long currentMax = travellings.stream()
                                .map(Travelling::getId)
                                .max(Long::compare)
                                .orElse(0L);
                travelling.setId(currentMax + 1);
                travellings.add(travelling);
                return travelling;
        }

        @Override
        public Optional<Travelling> getById(long id) {
                return travellings.stream()
                                .filter(travelling -> travelling.getId() == id)
                                .findFirst();
        }

        @Override
        public void addReview(long id, String review) {
                getById(id).ifPresent(travelling -> {
                        travelling.setReview(review);
                });
        }
}

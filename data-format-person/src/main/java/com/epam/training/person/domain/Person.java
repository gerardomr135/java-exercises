package com.epam.training.person.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Person(
        UUID id,
        String name,
        LocalDate dateOfBirth,
        Location placeOfBirth,
        Gender gender,
        List<Phone> phones
) {
}

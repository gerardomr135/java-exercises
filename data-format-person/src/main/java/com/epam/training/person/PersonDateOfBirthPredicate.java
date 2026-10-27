package com.epam.training.person;

import com.epam.training.person.domain.Person;

import java.time.LocalDate;

public class PersonDateOfBirthPredicate implements Predicate<Person> {
    private final LocalDate dateLimit;

    public PersonDateOfBirthPredicate(LocalDate dateLimit) {
        this.dateLimit = dateLimit;
    }

    @Override
    public boolean test(Person person) {
        return person.dateOfBirth().isBefore(dateLimit);
    }
}

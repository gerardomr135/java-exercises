package com.epam.training.person;

import com.epam.training.person.domain.Person;
import com.epam.training.person.domain.Phone;
import com.epam.training.person.domain.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class PersonSubscriberTransformer implements Transformer<List<Person>, List<Subscriber>> {
    private final Predicate<Person> predicate;

    public PersonSubscriberTransformer(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public List<Subscriber> transform(List<Person> persons) {
        List<Subscriber> subscribers = new ArrayList<>();

        for (Person person : persons) {
            if (predicate.test(person)) {
                for (Phone phone: person.phones()) {
                    subscribers.add(new Subscriber(
                        String.join("-", phone.area(), phone.region(), phone.local()),
                            person.name()
                    ));
                }
            }
        }

        return subscribers;
    }
}

package com.epam.training.person.persistence;

import com.epam.training.person.domain.Gender;
import com.epam.training.person.domain.Location;
import com.epam.training.person.domain.Person;
import com.epam.training.person.domain.Phone;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CSVPersonReader implements DataReader<List<Person>> {
    private final Scanner scanner;

    public CSVPersonReader(InputStream in) {
        scanner = new Scanner(in);
    }

    @Override
    public List<Person> read() {
        List<Person> people = new ArrayList<>();

        while (scanner.hasNextLine()) {
            List<String> parts = getCSVRowValues(scanner.nextLine());

            var person = new Person(
                   UUID.fromString(parts.get(0)),
                    parts.get(1),
                    LocalDate.parse(parts.get(2)),
                    new Location(
                            parts.get(3),
                            parts.get(4),
                            parts.get(5),
                            parts.get(6)
                    ),
                    Gender.valueOf(parts.get(7).toUpperCase()),
                    parsePhones(parts.subList(8, parts.size()))
            );

            people.add(person);
        }

        return people;
    }

    private List<String> getCSVRowValues(String line) {
        List<String> values = new ArrayList<>();

        try(Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }

        return values;
    }

    private List<Phone> parsePhones(List<String> phones) {
        List<Phone> phonesList = new ArrayList<>();

        for (String phone : phones) {
            var phoneLength = phone.length();

            if (phoneLength == 1 && phone.equals("-")) {
                continue;
            }

            if (phoneLength != 10 && phoneLength != 12) {
                throw  new IllegalArgumentException("invalid phone number length");
            }

            if (phone.charAt(3) == '-') {
                if (phone.charAt(3) != '-' || phone.charAt(7) != '-') {
                    throw  new IllegalArgumentException("invalid phone number format");
                }

                var parts = phone.split("-");

                phonesList.add(new Phone(parts[0], parts[1], parts[2]));

                continue;
            }

            phonesList.add(new Phone(
                    phone.substring(0, 3),
                    phone.substring(3, 6),
                    phone.substring(6)
            ));
        }

        return  phonesList;
    }

    @Override
    public void close() throws Exception {
        scanner.close();
    }
}

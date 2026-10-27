package com.example.university.repo;

import com.example.university.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    List<Student> findByFullTime(boolean fullTime);

    List<Student> findByAge(Integer age);

    List<Student> findByAttendeeLastName(String lastName);

    List<Student> findByAgeLessThan(Integer age);

    // Optional<Student> findTopByOrderByAgeDesc();
    @Query(value = "SELECT * FROM Student s ORDER BY s.age DESC LIMIT 1", nativeQuery = true)
    Optional<Student> findOldest();

    // List<Student> findByAttendeeFirstNameAndAttendeeLastName(String firstName, String lastName);
    @Query("SELECT s FROM Student s WHERE s.attendee.firstName = :firstName and s.attendee.lastName = :lastName")
    List<Student> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    // List<Student> findByAttendeeLastNameLike(String nameCriteria);
    @Query("SELECT s FROM Student s where s.attendee.lastName like :nameCriteria")
    List<Student> findSimilarLastName(@Param("nameCriteria") String nameCriteria);

    // Optional<Student> findFirstByOrderByAttendeeLastNameAsc();
    @Query(value = "Select * FROM Student  s ORDER BY  s.last_Name ASC LIMIT 1", nativeQuery = true)
    Optional<Student> findFirstInAlphabet();

    // List<Student> findTop3OByOrderByAgeDesc();
    @Query(value = "SELECT * FROM Student s ORDER BY s.age DESC LIMIT 3", nativeQuery = true)
    List<Student> find3Oldest();
}

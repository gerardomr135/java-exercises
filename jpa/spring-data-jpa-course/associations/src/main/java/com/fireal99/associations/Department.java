package com.fireal99.associations;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "department")
    //@JoinColumn(name = "department_id")
    List<Employee> employees;

    public Department() {}
}

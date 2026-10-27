package com.fireal99.associations;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer salary;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guide")
    private List<Student> students;

    public Guide() {}

    public Guide(String name, Integer salary) {
        this.name = name;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}

package com.mjobb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "languages")
    @JsonIgnore
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getLanguages().add(this);
    }

    public void removeEmployee(long empId) {
        Employee employee = this.employees.stream().filter(t -> t.getId() == empId).findFirst().orElse(null);
        if (employee != null) {
            this.employees.remove(employee);
            employee.getLanguages().remove(this);
        }
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "languages")
    @JsonIgnore
    private Set<JobAdvertisement> jobAdvertisements = new HashSet<>();
}

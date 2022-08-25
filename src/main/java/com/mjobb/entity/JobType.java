package com.mjobb.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class JobType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    public JobType() {

    }

    public JobType(String name) {
        this.name = name;
    }
}

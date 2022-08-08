package com.mjobb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String reason;
    private String description;
    @ManyToOne
    private User complainant;
    @ManyToOne
    private User complainedOf;


}

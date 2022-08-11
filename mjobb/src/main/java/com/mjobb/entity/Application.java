package com.mjobb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "employee_applications",
            joinColumns = {@JoinColumn(name = "application_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    private Employee employee;
    @ManyToOne
    @JoinTable(name = "job_applications",
            joinColumns = {@JoinColumn(name = "application_id")},
            inverseJoinColumns = {@JoinColumn(name = "job_advertisement_id")})
    private JobAdvertisement jobAdvertisement;
    private boolean accepted;

}

package com.mjobb.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String comment;
    private Long point;
    @OneToOne
    private User fromUser;
    @OneToOne
    private User toUser;
    @OneToOne
    private JobAdvertisement job;
    private boolean accepted;
}

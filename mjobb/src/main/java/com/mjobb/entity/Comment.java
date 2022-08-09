package com.mjobb.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
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
}

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
    @ManyToOne
    private User fromUser;
    @ManyToOne
    private User toUser;
    @ManyToOne
    @JoinTable(name = "job_comments",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = {@JoinColumn(name = "job_advertisement_id")})
    private JobAdvertisement job;
    private boolean accepted;
}

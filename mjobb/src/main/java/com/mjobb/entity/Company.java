package com.mjobb.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table
public class Company extends User {

    private String website;
    private Date foundationDate;

}

package com.siri.practice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")

public class Student implements Serializable {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentId;

    @Column(name = "FIRST_NAME")
    @JsonProperty(value= "first_name")
    private String firstName;

    @Column(name = "LAST_NAME")
    @JsonProperty(value= "last_name")
    private String lastName;

    @Column(name = "EMAIL")
    @JsonProperty(value= "mail")
    private String mail;

    @Column(name = "PHONE_NUMBER")
    @JsonProperty(value= "phone_number")
    private String phoneNumber;

    @Column(name = "GENDER")
    @JsonProperty(value= "gender")
    private String gender;

    @Column(name = "COURSE")
    @JsonProperty(value= "course")
    private String course;

}

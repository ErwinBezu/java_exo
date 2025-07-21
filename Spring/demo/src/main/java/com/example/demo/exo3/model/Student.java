package com.example.demo.exo3.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}

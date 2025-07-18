package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
    private long id;
    private String name;
    private String description;
    private Boolean isDone;

    @Override
    public String toString() {
        return "Todo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isDone=" + isDone +
                '}';
    }
}

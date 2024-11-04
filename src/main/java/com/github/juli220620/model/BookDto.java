package com.github.juli220620.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    private String name;
    private String author;
    private String description;
}

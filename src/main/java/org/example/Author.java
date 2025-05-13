package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class Author {
    @Id
    public String ssn;

    public String name;

    public Address address;

    @ManyToMany
    Set<Book> books;
}


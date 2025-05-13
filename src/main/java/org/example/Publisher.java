package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Publisher {
    @Id
    public long id;

    public String name;

    @OneToMany(mappedBy = Book_.PUBLISHER)
    Set<Book> books;
}

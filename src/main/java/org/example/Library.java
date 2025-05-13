package org.example;

import io.smallrye.mutiny.Uni;
import jakarta.data.Sort;
import jakarta.data.repository.*;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.processing.Pattern;
import org.hibernate.query.Order;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.List;

@Transactional
@Repository
public interface Library {

    Mutiny.StatelessSession session();

    @Find
    Uni<Book> byIsbn(String isbn);

    @Find
    @OrderBy("isbn")
    Uni<List<Book>> byTitle(@Pattern String title);

    @Find
    Uni<List<Book>> byTitle(@Pattern String title, Order<Book> order);

    @Insert
    Uni<Void> add(Book book);

//    @Delete
//    Uni<Void> delete(String isbn);

    @Find
    Uni<List<Book>> allBooks(Sort<Book> bookSort);

    @Find
    @OrderBy("isbn")
    Uni<List<Book>> allBooks();

    @Query("""
            select b.isbn, b.title, listagg(a.name, ' & ')
            from Book b join b.authors a
            group by b
            order by b.isbn
            """)
    Uni<List<Summary>> summarize();

}

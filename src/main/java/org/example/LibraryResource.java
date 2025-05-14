package org.example;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;

import java.util.List;

@WithSession // enables the request-scoped stateless session
@Path("/")
public class LibraryResource {

    @Inject Library library;

    @GET
    @Path("/book/{isbn}")
    public Uni<Book> byIsbn(@PathParam("isbn") String isbn) {
        return library.byIsbn(isbn)
                .onFailure().transform(e -> new NotFoundException(isbn));
    }

    @GET
    @Path("/title/{title}")
    public Uni<List<Book>> byTitle(@PathParam("title") String title) {
        String pattern = '%' + title.replace('*', '%') + '%';
        return library.byTitle(pattern);
    }

    @GET
    @Path("/books")
    public Uni<List<Book>> allBooks() {
        return library.allBooks();
    }

    @POST
    @Path("/new")
    public Uni<String> create(Book book) {
        return library.add(book).map(v ->"Added " + book.isbn);
    }

    @POST
    @Path("/delete/{isbn}")
    @Transactional
    public Uni<String> delete(@PathParam("isbn") String isbn) {
        var session = library.session();
        return session.delete(session.get(Book.class, isbn))
                .map(b -> "Deleted " + isbn);
    }

    @GET
    @Path("/summary")
    public Uni<List<Summary>> summary() {
        return library.summarize();
    }

}

package org.example;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@WithSession // enables the request-scoped stateless session
@Path("/books")
public class LibraryResource {

    @Inject Library library;

    @GET
    public Uni<List<Book>> allBooks() {
        return library.allBooks(_Book.title.ascIgnoreCase());
    }

    @GET
    @Path("/{isbn}")
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

    @POST
    public Uni<String> create(Book book) {
        return library.add(book).map(v ->"Added " + book.isbn);
    }

    @DELETE
    @Path("/{isbn}")
    public Uni<String> delete(@PathParam("isbn") String isbn) {
        return library.byIsbn(isbn)
                .call(library::delete)
                .map(b -> "Deleted " + isbn)
                .onFailure().transform(e -> new NotAcceptableException(isbn));
    }

    @GET
    @Path("/summary")
    public Uni<List<Summary>> summary() {
        return library.summarize();
    }

}

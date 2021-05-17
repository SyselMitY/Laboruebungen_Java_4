package cf.soisi.rest_simple;

import cf.soisi.rest_simple.service.BookService;
import cf.soisi.rest_simple.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class SimpleRestController {

    private final BookService bookService;

    public SimpleRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/books")
    private Collection<Book> getAllBooks() {
        return bookService.getBooks();
    }

    @GetMapping(path="/books/{pattern}")
    private Collection<Book> findBooks(@PathVariable String pattern) {
        return bookService.getBooks()
                .stream()
                .filter(book -> book.getTitel().contains(pattern))
                .collect(Collectors.toList());
    }

}

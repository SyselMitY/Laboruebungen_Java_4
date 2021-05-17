package cf.soisi.rest_simple.service;

import cf.soisi.rest_simple.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

  private static List<Book> books;

  static {
    books = new ArrayList<>();
    books.add(new Book("978-1-59059-925-9",
                       39.99,
                       "XNA 2.0 Game Programming",
                       "Riemer, Grootjans"));
    books.add(new Book("978-3-89721-522-1",
                       9.90,
                       "SQL kurz & gut",
                       "Gennick, Jonathan"));
    books.add(new Book("978-0-59600-885-7",
                       14.99,
                       "Oracle SQL*Plus Pocket Reference",
                       "Gennick, Jonathan"));
    books.add(new Book("978-0-12345-331-7",
                       16.99,
                       "SQL: The Ultimate Beginners Guide",
                       "Tale, Steve"));
    books.add(new Book("978-3-89842-886-6",
                       49.90,
                       "Cinema 4D 10",
                       "Asanger, Andreas"));
    books.add(new Book("978-0-13-134796-0",
                       35.75,
                       "JBoss Seam",
                       "Juanto Yuan, Michael"));
    books.add(new Book("978-3-8273-2199-9",
                       49.95,
                       "Entwurfsmuster",
                       "Gamme, Erich"));
    books.add(new Book("978-3836227629",
                       39.90,
                       "Entwurfsmuster - das umfassende Handbuch",
                       "Gheiros, Matthias"));

  }

  public List<Book> getBooks() {
    return books;
  }


}

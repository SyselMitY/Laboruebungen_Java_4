package cf.soisi.rest_simple.model;

public class Book {

  private String isbn;
  private double preis;
  private String titel;
  private String author;

  private Book() {
  }

  public Book(String isbn, double preis, String titel, String author) {
    this.isbn = isbn;
    this.preis = preis;
    this.titel = titel;
    this.author = author;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public double getPreis() {
    return preis;
  }

  public void setPreis(double preis) {
    this.preis = preis;
  }

  public String getTitel() {
    return titel;
  }

  public void setTitel(String titel) {
    this.titel = titel;
  }

}

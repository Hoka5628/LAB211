package data;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bookID;
    private String title;
    private String author;
    private int publicationYear;
    private String publisher;
    private String isbn;
    private boolean activeBook;
    private String type;

    public Book(String bookID, String title, String author, int publicationYear, String publisher, String isbn, String type, boolean activeBook) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.isbn = isbn;
        this.type=type;
        this.activeBook = activeBook;
    }

    public Book() {
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    public boolean isActiveBook() {
        return activeBook;
    }

    public void setActiveBook(boolean activeBook) {
        this.activeBook = activeBook;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void display() {
        String format = "|%-10s|%-30s|%-30s|%-20s|%-30s|%-15s|%-20s|%-10s|%n";
        String result = String.format(format, bookID, title, author, publicationYear, publisher, isbn, type, activeBook);
        System.out.print(result);
    }
}
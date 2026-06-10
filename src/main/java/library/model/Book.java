package main.java.library.model;

public class Book {
    private int id;
    private String title;
    private String textFilePath;
    private String author;
    private String publisher;
    private int publicationYear;


    public Book(int id, String title, String textFilePath, String author, String publisher, int publicationYear) {
        this.id = id;
        this.title = title;
        this.textFilePath = textFilePath;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
    }
    //setters

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextFilePath(String textFilePath) {
        this.textFilePath = textFilePath;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}

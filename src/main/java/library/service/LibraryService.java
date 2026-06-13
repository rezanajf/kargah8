package main.java.library.service;

import main.java.library.model.Book;
import main.java.library.util.FileManager;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class LibraryService {

    private List<Book> books;

    public LibraryService() {
        this.books = FileManager.loadBooksFromCSV();
        FileManager.saveBooksToCSV(this.books);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void saveBooks() {
        FileManager.saveBooksToCSV(books);
    }

    public Optional<Book> findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    public boolean editBookMetadata(int bookId, String newTitle, String newAuthor,String newPublisher, Integer newYear){
        Optional<Book> optionalBook = findBookById(bookId);
        if (optionalBook.isEmpty()) {
            return false;
        }

        Book book = optionalBook.get();
        if (newTitle != null && !newTitle.isEmpty()) {
            book.setTitle(newTitle);
        }
        if (newAuthor != null && !newAuthor.isEmpty()) {
            book.setAuthor(newAuthor);
        }
        if (newPublisher != null && !newPublisher.isEmpty()) {
            book.setPublisher(newPublisher);
        }
        if (newYear != null) {
            book.setPublicationYear(newYear);
        }
        saveBooks();
        return true;
    }

    public List<String> getBookPages(Book book, int linesPerPage){
        String filePath = book.getTextFilePath();
        return FileManager.readBookPages(filePath, linesPerPage);
    }

    public String readFullText(int bookId) {
        Optional<Book> optionalBook = findBookById(bookId);

        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return FileManager.readFullText(book.getTextFilePath());
        }
        return "";
    }

    public boolean editBookContent(int bookId, String newContent) throws IOException {
        Optional<Book> optionalBook = findBookById(bookId);

        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            FileManager.writeBookText(book.getTextFilePath(), newContent);
            return true;
        }

        return false;
    }

    public int countLines(Book book) {
        return FileManager.countLines(book.getTextFilePath());
    }
}
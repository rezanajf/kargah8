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

    public Optional<Book> findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }
}
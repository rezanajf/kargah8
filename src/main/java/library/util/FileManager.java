package main.java.library.util;

import main.java.library.model.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String BOOKS_TEXT_DIR = "data/books_text/";
    private static final String BOOK_LIST_CSV = "data/books_text/Book_List.txt";


    public static List<Book> loadBooksFromCSV() {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOK_LIST_CSV);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                String title = words[0];
                String author = words[1];
                String publisher = words[2];
                int publicationYear = Integer.parseInt(words[3]);
                String textFilePath = words[4];
                Book book = new Book(title, textFilePath, author, publisher, publicationYear);
                books.add(book);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

}
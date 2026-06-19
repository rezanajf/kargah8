package main.java.library.util;

import main.java.library.model.Book;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void saveBooksToCSV(List<Book> books) {
        File file = new File(BOOK_LIST_CSV);
        //check if the directory exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Title,Author,Publisher,Publication Year,Path");
            writer.newLine();

            for (Book book : books){
                String line = book.getTitle() + "," + book.getAuthor() + "," + book.getPublisher() + "," + book.getPublicationYear() + "," + book.getTextFilePath();
                writer.write(line);
                writer.newLine();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readBookPages(String filePath, int linesPerPage){
        List<String> pages = new ArrayList<>();
        String currentPage = "";
        int lineCount = 0;
        File file = new File(BOOKS_TEXT_DIR + filePath);

        if (!file.exists()) {
            pages.add("This file does not exist.");
            return pages;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                currentPage += line + "\n";
                lineCount++;

                if (lineCount >= linesPerPage) {
                    pages.add(currentPage);
                    currentPage = "";
                    lineCount = 0;
                }
            }
            if (currentPage.length() > 0)
                pages.add(currentPage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return pages;
    }

    public static int countLines(String filePath) {
        int countLine = 0;

        String path = BOOKS_TEXT_DIR + filePath;
        File file = new File(path);

        if (!file.exists()) {
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((reader.readLine()) != null) {
                countLine++;
            }
            return countLine;

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error. Unable to read file: " + e.getMessage());
            return 0;
        }
    }

    public static void writeBookText(String filePath, String content) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.write(content);
        }
    }

    public static String readFullText(String filePath) {

        if(filePath == null || filePath.trim().isEmpty()) {
            return "";
        }

        try {
            return Files.readString(Paths.get(filePath));
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
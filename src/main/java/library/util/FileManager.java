package main.java.library.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {

    public static final String BOOKS_TEXT_DIR = "data/books_text/";
    public static final String BOOK_LIST_CSV = "data/books_text/Book_List.txt";

    public static int countLines(String filePath) {

        int countLine = 0;

        File file = new File(filePath);

        if (!file.exists()) {
        return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            while ((reader.readLine()) != null) {
                countLine++;
            }

            return countLine;

        } catch (IOException e) {
            System.out.println("خطا در خواندن فایل" + e.getMessage());
            return 0;
        }


    }
}
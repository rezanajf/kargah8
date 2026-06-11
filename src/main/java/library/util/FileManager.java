package main.java.library.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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
            return "";
        }
    }
}
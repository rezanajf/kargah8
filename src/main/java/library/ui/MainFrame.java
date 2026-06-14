package main.java.library.ui;

import main.java.library.service.LibraryService;
import main.java.library.model.Book;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private LibraryService service;
    private JPanel booksPanel;
    private JPanel menuPanel;
    private JPanel readerPanel;
    private Book selectedBook;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField publisherField;
    private JTextField yearField;
    private JTextArea pageArea;
    private List<String> pages;
    private int currentPage;


    public MainFrame(){
        this.service = new LibraryService();
        setTitle("Library management");
        setSize(900,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createBooksPanel();
        setLayout(new BorderLayout());
        add(booksPanel,BorderLayout.CENTER);
        setVisible(true);
    }

    public void createBooksPanel(){
        List<Book>  books = service.getBooks();
        booksPanel = new JPanel();
        booksPanel.setLayout(new BoxLayout(booksPanel,BoxLayout.Y_AXIS));

        for (Book book : books) {
            JButton bookButton = new JButton(book.getTitle());
            bookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            bookButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedBook = book;
                    showBookMenu();
                }
            });
            booksPanel.add(bookButton);
        }
        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                createBooksPanel();
                add(booksPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
        booksPanel.add(refreshButton);
    }

    public void showBookMenu(){

    }
}

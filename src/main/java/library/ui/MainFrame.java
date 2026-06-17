package main.java.library.ui;

import main.java.library.service.LibraryService;
import main.java.library.model.Book;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private static final int linesInPage = 20;


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

        booksPanel.add(Box.createVerticalStrut(200));

        for (Book book : books) {
            JButton bookButton = new JButton(book.getTitle());
            bookButton.setMaximumSize(new Dimension(210,40));
            bookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            bookButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedBook = book;
                    showBookMenu();
                }
            });
            booksPanel.add(bookButton);
            booksPanel.add(Box.createVerticalStrut(15));
        }
        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setMaximumSize(new Dimension(210,40));
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
        booksPanel.add(Box.createVerticalStrut(15));
        //exit bitton
        JButton exitButton = new JButton("Exit");
        exitButton.setMaximumSize(new Dimension(210,40));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        booksPanel.add(exitButton);
    }

    public void showBookMenu(){
        if (menuPanel!=null)
            remove(menuPanel);

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.Y_AXIS));
        menuPanel.add(Box.createVerticalStrut(60));

        //title
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(titleLabel);
        titleField = new JTextField(selectedBook.getTitle());
        titleField.setMaximumSize(new Dimension(260,25));
        titleField.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(titleField);
        menuPanel.add(Box.createVerticalStrut(10));

        //author
        JLabel authorLabel = new JLabel("Author");
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(authorLabel);
        authorField = new JTextField(selectedBook.getAuthor());
        authorField.setMaximumSize(new Dimension(260, 25));
        authorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(authorField);
        menuPanel.add(Box.createVerticalStrut(10));

        //publisher
        JLabel publisherLabel = new JLabel("Publisher");
        publisherLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(publisherLabel);
        publisherField = new JTextField(selectedBook.getPublisher());
        publisherField.setMaximumSize(new Dimension(260, 25));
        publisherField.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(publisherField);
        menuPanel.add(Box.createVerticalStrut(10));

        //publication Year
        JLabel yearLabel = new JLabel("Publication Year");
        yearLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(yearLabel);
        yearField = new JTextField(String.valueOf(selectedBook.getPublicationYear()));
        yearField.setMaximumSize(new Dimension(260, 25));
        yearField.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(yearField);
        menuPanel.add(Box.createVerticalStrut(10));

        //number of Lines
        JLabel linesLabel = new JLabel("Number of Lines: " + service.countLines(selectedBook));
        linesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(linesLabel);
        menuPanel.add(Box.createVerticalStrut(60));
        //--------------------------------------
        //save button
        JButton saveButton = new JButton("Save Metadata");
        saveButton.setMaximumSize(new Dimension(180, 35));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newTitle = titleField.getText();
                String newAuthor = authorField.getText();
                String newPublisher = publisherField.getText();
                int newYear = 0;
                try {
                    newYear = Integer.parseInt(yearField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,"Pleas enter a number correctly without any space!");
                    return;
                }

                boolean isDone = service.editBookMetadata(selectedBook.getId(),newTitle,newAuthor,newPublisher,newYear);

                if (isDone)
                    JOptionPane.showMessageDialog(MainFrame.this, "changes saved successfully");

                else
                    JOptionPane.showMessageDialog(MainFrame.this,"EROR!!changes didn't saved");

            }
        });
        menuPanel.add(saveButton);
        menuPanel.add(Box.createVerticalStrut(15));

        //read button
        JButton readButton = new JButton("Read Book");
        readButton.setMaximumSize(new Dimension(180, 35));
        readButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBook(false);
            }
        });
        menuPanel.add(readButton);
        menuPanel.add(Box.createVerticalStrut(15));

        //edit button
        JButton editButton = new JButton("Edit Book");
        editButton.setMaximumSize(new Dimension(180, 35));
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBook(true);
            }
        });
        menuPanel.add(editButton);
        menuPanel.add(Box.createVerticalStrut(15));

        //back button
        JButton backButton = new JButton("Back");
        backButton.setMaximumSize(new Dimension(180, 35));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (menuPanel != null) {
                    remove(menuPanel);
                    menuPanel = null;
                }
                createBooksPanel();
                add(booksPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
        menuPanel.add(backButton);



        remove(booksPanel);
        add(menuPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    public void openBook(boolean editable) {

        if (menuPanel != null) {
            remove(menuPanel);
        }

        if (readerPanel != null) {
            remove(readerPanel);
        }

        if (selectedBook == null) {
            JOptionPane.showMessageDialog(MainFrame.this, "No books selected, Please select a book");
            return;
        }

        readerPanel = new JPanel();
        readerPanel.setLayout(new BoxLayout(readerPanel, BoxLayout.Y_AXIS));

        pages = service.getBookPages(selectedBook, linesInPage);
        currentPage = 0;

        pageArea = new JTextArea();
        pageArea.setLineWrap(true);
        pageArea.setWrapStyleWord(true);
        pageArea.setEditable(editable);

        if (pages != null) {
            pageArea.setText(pages.get(0));
        }

        JScrollPane scrollPane = new JScrollPane(pageArea);
        readerPanel.add(scrollPane);

        if (editable) {
            JButton applyButton = new JButton("Apply changes");
            applyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        String content = pageArea.getText();
                        boolean success = service.editBookContent(selectedBook.getId(), content);

                        if (success) {
                            JOptionPane.showMessageDialog(MainFrame.this, "Saved changes successfully.");
                            pages = service.getBookPages(selectedBook, linesInPage);
                        } else {
                            JOptionPane.showMessageDialog(MainFrame.this, "Unable to save changes.");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Error" + ex.getMessage());
                    }
                }
            });

            readerPanel.add(applyButton);
            readerPanel.add(Box.createVerticalStrut(15));
        }

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JButton previousButton = new JButton("<");
        previousButton.setPreferredSize(new Dimension(180, 35));
        previousButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentPage <= 0) {
                    JOptionPane.showMessageDialog(MainFrame.this, "You are on the first page. ");
                }

                else {
                    currentPage--;
                    pageArea.setText(pages.get(currentPage));
                }
            }
        });

        JButton nextButton = new JButton(">");
        nextButton.setPreferredSize(new Dimension(180, 35));
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentPage + 1 >= pages.size()) {
                    JOptionPane.showMessageDialog(MainFrame.this, "You are on the last page. ");
                }

                else {
                    currentPage ++;
                    pageArea.setText(pages.get(currentPage));
                }
            }
        });

        JButton backButton = new JButton("back");
        backButton.setPreferredSize(new Dimension(180, 35));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(readerPanel);
                createBooksPanel();
                add(booksPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        buttonsPanel.add(previousButton);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(backButton);
        readerPanel.add(buttonsPanel);

        add(readerPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
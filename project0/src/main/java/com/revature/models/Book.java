package com.revature.models;

import com.revature.repositories.BookRepo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean checkedOut;
    private LocalDateTime DueDate;
    private int borrowedBy;
    private String genre;

    public Book() {

    }


}

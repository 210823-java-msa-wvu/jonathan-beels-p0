package com.revature.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean checkedOut;
    private LocalDateTime DueDate;
    private int borrowedBy;
}

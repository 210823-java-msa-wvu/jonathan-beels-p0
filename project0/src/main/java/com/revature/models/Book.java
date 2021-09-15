package com.revature.models;

import com.revature.repositories.BookRepo;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean checkedOut;
    private Date dueDate;
    private Integer borrowedBy;
    private String genre;

    public Book() {

    }

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.checkedOut = false;
        this.genre = genre;
    }

    public Book add() {
        BookRepo repo = new BookRepo();

        Book b = repo.addBook(this);

        return b;
    }

    public boolean remove() {
        BookRepo repo = new BookRepo();

        return repo.deleteBook(this.bookId);
    }

    public String toString() {
        return "Book ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Checked out: " + checkedOut +
                ", Due Date: " + dueDate + ", Borrowed By: " + borrowedBy + ", Genre: " + genre;
    }

    public Date checkOut(int userId) {
        if (this.checkedOut) {
            return null;
        }

        else {
            Date date = BookRepo.updateCheckedOut(this.bookId, userId, true);

            if (date != null) {
                this.checkedOut = true;
                this.borrowedBy = userId;
                this.dueDate = date;
            }

            return date;
        }
    }

    public boolean returnBook(int userId) {
        if (!checkedOut || (checkedOut && (borrowedBy != userId))) {
            return false;
        }

        else {
            BookRepo.updateCheckedOut(this.bookId, userId, false);
            this.checkedOut = false;
            this.borrowedBy = null;
            this.dueDate = null;

            return true;
        }
    }

    // Getters and Setters

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        dueDate = dueDate;
    }

    public int getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(int borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

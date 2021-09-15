package com.revature.views;

import java.util.HashMap;
import java.util.Scanner;
import java.sql.Date;
import com.revature.models.Book;
import com.revature.models.Staff;
import com.revature.repositories.BookRepo;
import com.revature.utils.NoneOverdueException;
import com.revature.views.MainMenu;

public class StaffMenu {

    public static void display(Staff s) {
        boolean loggedIn = true;
        HashMap<Integer, Book> catalogue;
        Scanner scan = new Scanner(System.in);

        while (loggedIn) {

            catalogue = BookRepo.viewCatalogue();

            System.out.println("1) View Catalogue");
            System.out.println("2) Check out book");
            System.out.println("3) Return book");
            System.out.println("4) Check fees due");
            System.out.println("5) Pay fees");
            System.out.println("6) Add book to catalogue");
            System.out.println("7) Remove book from catalogue");
            System.out.println("8) Charge account");
            System.out.println("9) Log out");

            String input = scan.nextLine();

            switch (input) {
                case "1":
                    for (HashMap.Entry<Integer, Book> book : catalogue.entrySet()) {
                        System.out.println(book.getValue().toString());
                    }
                    break;
                case "2":
                    for (HashMap.Entry<Integer, Book> book : catalogue.entrySet()) {
                        System.out.println(book.getValue().toString());
                    }

                    System.out.println("Enter Book ID:");
                    String idString = scan.nextLine();
                    int id = Integer.parseInt(idString);

                    Date date = catalogue.get(id).checkOut(MainMenu.m.getAccountId());

                    if (date != null) {
                        System.out.println("Book is due on " + date);
                    } else {
                        System.out.println("Book cannot be checked out");
                    }
                    break;
                case "3":
                    for (HashMap.Entry<Integer, Book> book : catalogue.entrySet()) {
                        if (book.getValue().getBorrowedBy() == MainMenu.m.getAccountId()) {
                            System.out.println(book.getValue().toString());
                        }
                    }

                    System.out.println("Enter Book ID:");
                    idString = scan.nextLine();
                    id = Integer.parseInt(idString);

                    boolean success = catalogue.get(id).returnBook(MainMenu.m.getAccountId());
                    if (success) {
                        System.out.println("Book returned");
                    } else {
                        System.out.println("Cannot return book");
                    }
                    break;
                case "4":
                    MainMenu.m.checkFee();
                    System.out.println("Amount due: $" + MainMenu.m.getFee());
                    break;
                case "5":
                    MainMenu.m.checkFee();
                    System.out.println("Amount due: $" + MainMenu.m.getFee());
                    boolean isNum = false;
                    int payment = 0;
                    while (!isNum) {
                        System.out.println("Enter payment amount in dollars (numbers only)");
                        String paymentStr = scan.nextLine();

                        try {
                            payment = Integer.parseInt(paymentStr);
                            isNum = true;
                        } catch (NumberFormatException nfe) {
                            System.out.println("Please enter numbers only");
                        }

                        if (payment == 0) {
                            isNum = false;
                            System.out.println("Payment must be greater than zero");
                        }
                    }

                    int remainder = MainMenu.m.payFee(payment);

                    System.out.println("Remaining amount on account is: $" + remainder);
                    break;
                case "6":
                    System.out.println("Enter title of book");
                    String title = scan.nextLine();

                    System.out.println("Enter the name of the author");
                    String author = scan.nextLine();

                    System.out.println("Enter genre");
                    String genre = scan.nextLine();

                    Book b = new Book(title, author, genre);

                    b = b.add();

                    if (b == null) {
                        System.out.println("Failed to add book");
                    } else {
                        System.out.println("Book added");
                    }

                    break;
                case "7":
                    for (HashMap.Entry<Integer, Book> book : catalogue.entrySet()) {
                        System.out.println(book.getValue().toString());
                    }

                    isNum = false;
                    int bookId;
                    success = false;
                    while (!isNum) {
                        System.out.println("Choose a book to remove");
                        input = scan.nextLine();

                        try {
                            bookId = Integer.parseInt(input);
                            isNum = true;

                            success = catalogue.get(bookId).remove();
                        } catch (NumberFormatException nfe) {
                            System.out.println("Please enter numbers only");
                        }
                    }

                    if (success) {
                        System.out.println("Book removed");
                    } else {
                        System.out.println("Book was not removed");
                    }

                    break;
                case "8":
                    try {
                        HashMap<Integer, Book> overdue = getOverdue(catalogue);
                        for (HashMap.Entry<Integer, Book> book : catalogue.entrySet()) {
                            if (book.getValue().getCheckedOut()) {
                                System.out.println(book.getValue().toString());
                            }
                        }

                        isNum = false;
                        int userId = -1;
                        int days = -1;
                        while (!isNum) {
                            System.out.println("Select a user to charge");
                            String uidStr = scan.nextLine();
                            try {
                                userId = Integer.parseInt(uidStr);
                                isNum = true;
                            } catch (NumberFormatException nfe) {
                                System.out.println("Please enter numbers only");
                            }
                        }

                        isNum = false;
                        while (!isNum) {
                            System.out.println("Enter the amount of days overdue");
                            String dayStr = scan.nextLine();
                            try {
                                days = Integer.parseInt(dayStr);
                                isNum = true;
                            } catch (NumberFormatException nfe) {
                                System.out.println("Please enter numbers only");
                            }
                        }

                        if (userId < 0 || days < 0) {
                            System.out.println("Can't charge account");
                        } else {
                            int fee = s.chargeAccount(days, userId);
                            System.out.println("Account charged $" + fee);
                        }

                    } catch (NoneOverdueException noe) {
                        System.out.println("Exception Occured: " + noe);
                    }
                    break;
                case "9":
                    System.out.println("Are you sure you want to log off?");
                    System.out.println("1) Yes");
                    System.out.println("2) No");

                    input = scan.nextLine();

                    if (input.equals("1")) {
                        System.out.println("Logging off...");
                        MainMenu.m = null;
                        loggedIn = false;
                    }
                    break;
                default:
                    System.out.println("Not a valid command");
            }
        }
    }

    public static HashMap<Integer, Book> getOverdue(HashMap<Integer, Book> catalogue) throws NoneOverdueException {
        HashMap<Integer, Book> overdue = new HashMap<>();
        for (HashMap.Entry<Integer, Book> book : catalogue.entrySet()) {
            if (book.getValue().getCheckedOut()) {
                overdue.put(book.getKey(), book.getValue());
            }
        }

        if (overdue.isEmpty()) {
            throw new NoneOverdueException("No books are overdue.");
        }

        return overdue;
    }
}

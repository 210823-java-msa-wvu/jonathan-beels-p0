package com.revature.views;

import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.Date;
import com.revature.models.Book;
import com.revature.repositories.BookRepo;
import com.revature.views.MainMenu;

public class MemberMenu {

    public static int display() {
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
            System.out.println("6) Log out");

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

                    System.out.println((date != null));

                    if (date != null) {
                        System.out.println("Book is due on " + date);
                    }

                    else {
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
                    }

                    else {
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
        return 1;
    }
}

package com.revature.repositories;

import com.revature.utils.ConnectionUtil;
import com.revature.models.Book;

import java.sql.*;
import java.time.Period;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;

public class BookRepo {
    private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

    // Create
    public Book addBook(Book b) {
        try (Connection conn = cu.getConnection()) {
            String sql = "insert into books (title, author, checked_out, due_date, account_id, genre) values (?, ?, ?, ?, ?, ?) returning *";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setBoolean(3, false);
            ps.setNull(4, Types.DATE);
            ps.setNull(5, Types.INTEGER);
            ps.setString(6, b.getGenre());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                b.setBookId(rs.getInt("book_id"));
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Read
    public static HashMap<Integer, Book> viewCatalogue() {
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from books";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            HashMap<Integer, Book> catalogue = new HashMap<>();
            while (rs.next()) {
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setCheckedOut(rs.getBoolean("checked_out"));
                b.setDueDate(rs.getDate("due_date"));
                b.setBorrowedBy(rs.getInt("account_id"));
                b.setGenre(rs.getString("genre"));

                catalogue.put(b.getBookId(), b);
            }

            return catalogue;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Book getById(int id) {
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from books where book_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setCheckedOut(rs.getBoolean("checked_out"));
                b.setDueDate(rs.getDate("due_date"));
                b.setBorrowedBy(rs.getInt("account_id"));
                b.setGenre(rs.getString("genre"));

                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update
    public static Date updateCheckedOut(int bookId, int userId, boolean checkedOut) {
       try (Connection conn = cu.getConnection()) {
           String sql = "update books set checked_out = ?, account_id = ?, due_date = ? where book_id = ? returning due_date";

           PreparedStatement ps = conn.prepareStatement(sql);
           ps.setBoolean(1, checkedOut);

           if (checkedOut) {
               ps.setInt(2, userId);
               ps.setDate(3, Date.valueOf(LocalDate.now().plusWeeks(2)));
           }
           else {
               ps.setNull(2, Types.INTEGER);
               ps.setNull(3, Types.DATE);
           }

           ps.setInt(4, bookId);

           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               return rs.getDate("due_date");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }

       return null;
    }

    // Delete
    public boolean deleteBook(int id) {
        try (Connection conn = cu.getConnection()) {
            String sql = "delete from books where book_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ps.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

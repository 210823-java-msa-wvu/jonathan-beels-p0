package com.revature.repositories;

import com.revature.utils.ConnectionUtil;
import com.revature.models.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepo {
    private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

    // Create
    public Member addMember(Member m) {
        try (Connection conn = cu.getConnection()) {
            String sql = "insert into Member values (default, ?, ?, ?, ?, ?, ?, ?) returning *";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, m.getUsername());
            ps.setString(2, m.getPassword());
            ps.setString(3, m.getName());
            ps.setString(4, m.getAddress());
            ps.setInt(5, m.getPhoneNumber());
            ps.setInt(6, m.getFee());
            ps.setBoolean(7, m.getIsStaff());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                m.setAccountId(rs.getInt("AccountID"));
                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Read
    public Member getById(int id) {
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from Member where AccountID = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Member m = new Member();
                m.setAccountId(rs.getInt("AccountID"));
                m.setUsername(rs.getString("UserName"));
                m.setPassword(rs.getString("PassWord"));
                m.setName(rs.getString("Name"));
                m.setAddress(rs.getString("Address"));
                m.setPhoneNumber(rs.getInt("PhoneNumber"));
                m.setFee(rs.getInt("Fee"));
                m.setStaff(rs.getBoolean("IsStaff"));

                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Member getByLogin(String username, String password) {
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from Member where UserName = ? and PassWord = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Member m = new Member();
                m.setAccountId(rs.getInt("AccountID"));
                m.setUsername(rs.getString("UserName"));
                m.setPassword(rs.getString("PassWord"));
                m.setName(rs.getString("Name"));
                m.setAddress(rs.getString("Address"));
                m.setPhoneNumber(rs.getInt("PhoneNumber"));
                m.setFee(rs.getInt("Fee"));
                m.setStaff(rs.getBoolean("IsStaff"));

                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update
    public int updateFee(int id, int fee) {
        try (Connection conn = cu.getConnection()) {
            String sql = "update Member set Fee = ? where AccountID = ? returning Fee";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fee);
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int newFee = rs.getInt("Fee");
                return newFee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fee;
    }
}

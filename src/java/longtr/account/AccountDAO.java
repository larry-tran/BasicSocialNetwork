/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.account;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class AccountDAO implements Serializable{

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public AccountDTO checkLogin(String email, String password) throws SQLException, NamingException {
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT email, password, name, status, role FROM Account WHERE email=? and password=? and status=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                stm.setString(3, "New");
                rs = stm.executeQuery();

                if (rs.next()) {
                    email = rs.getString("email");
                    password = rs.getString("password");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    String role = rs.getString("role");
                    AccountDTO dto = new AccountDTO(email, password, name, status, role);
                    return dto;
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean insertNewAccount(String email, String password, String name) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO Account(email, password, name, status, role) VALUES (?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                stm.setString(3, name);
                stm.setString(4, "New");
                stm.setString(5, "member");

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean checkEmail(String email) throws NamingException, SQLException {
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT email FROM Account WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
}

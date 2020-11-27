/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.article;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class ArticleDAO implements Serializable{

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public boolean insertArticle(String owner, Timestamp date, String title, String description) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO Article(owner, date, title, description, status) VALUES(?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, owner);
                stm.setTimestamp(2, date);
                stm.setString(3, title);
                stm.setString(4, description);
                stm.setString(5, "Active");

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

    public int pageCount(String des) throws NamingException, SQLException {
        int result = 1;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT COUNT(owner) AS pageCount FROM Article WHERE description LIKE ? AND status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + des + "%");
                stm.setString(2, "Active");
                rs = stm.executeQuery();
                if (rs.next()) {
                    int row = rs.getInt("pageCount");
                    int t = row % 20;
                    if (t == 0) {
                        result = row / 20;
                    } else {
                        result = row / 20 + 1;
                    }
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
        return result;
    }
}

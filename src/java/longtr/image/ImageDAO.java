/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.image;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.naming.NamingException;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class ImageDAO implements Serializable{

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public boolean insertImage2(String owner, Timestamp date, InputStream input) throws SQLException, NamingException, FileNotFoundException {
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO Image(owner, date, status,image) VALUES(?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, owner);
                stm.setTimestamp(2, date);
                stm.setString(3, "new");
                stm.setBinaryStream(4, input);
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

    public List<String> loadImage(String owner, Timestamp date) throws NamingException, SQLException {
        List<String> listImage = new ArrayList<>();

        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT image FROM Image WHERE status=? AND owner=? AND date=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "new");
                stm.setString(2, owner);
                stm.setTimestamp(3, date);
                rs = stm.executeQuery();
                while (rs.next()) {
                    byte[] image = rs.getBytes("image");
                    String base64 = Base64.getEncoder().encodeToString(image);
                    if(base64.length() != 0){
                        listImage.add(base64);
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

        return listImage;
    }
}

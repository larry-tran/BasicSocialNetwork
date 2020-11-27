/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.comment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class CommentDAO implements Serializable{
    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;
    
    private List<CommentDTO> listComment;

    public List<CommentDTO> getListComment() {
        return listComment;
    }
       
    public void loadComment(String owner, Timestamp dateOfArticle) throws NamingException, SQLException{
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "SELECT Comment.sender,Comment.receiver,Comment.dateOfArticle, Comment.date, Comment.detail , Account.name "                      
                        + "FROM Comment "
                        + "LEFT JOIN Account ON Comment.sender = Account.email "
                        + "WHERE Comment.status = ? AND receiver = ? AND dateOfArticle = ? "
                        + "ORDER BY Comment.date DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, "new");
                stm.setString(2, owner);
                stm.setTimestamp(3, dateOfArticle);
                rs = stm.executeQuery();
                while(rs.next()){
                    Timestamp date = rs.getTimestamp("date");
                    dateOfArticle = rs.getTimestamp("dateOfArticle");
                    String detail = rs.getString("detail");
                    String name = rs.getString("name");
                    String receiver = rs.getString("receiver");
                    String sender = rs.getString("sender");
                    if(this.listComment == null){
                        this.listComment = new ArrayList<>();
                    }
//                    this.listComment.add(new CommentDTO(date, detail, sender, receiver, dateOfArticle));
                    this.listComment.add(new CommentDTO(detail, sender, name, date, dateOfArticle));
                }
            }
        } finally{
            if(rs!=null){
                rs.close();
            }
            if(stm!=null){
                stm.close();
            }
            if(con!=null){
                con.close();
            }
        }
    }
    
    public boolean addComment(CommentDTO dto) throws NamingException, SQLException{
        boolean result = false;
        try {
            con= DBHelper.getConnection();
            if(con!=null){
                String sql = "INSERT INTO Comment(sender, date, detail, receiver, dateOfArticle ,status) VALUES (?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getSender());
                stm.setTimestamp(2, dto.getDate());
                stm.setString(3, dto.getDetail());
                stm.setString(4, dto.getReceiver());
                stm.setTimestamp(5, dto.getDateOfArticle());
                stm.setString(6, "new");
                int row = stm.executeUpdate();
                if(row > 0){
                    result = true;
                }
            }
        } finally{
            if(stm!=null){
                stm.close();
            }
            if(con!=null){
                con.close();
            }
        }
        return result;
    }
    
    public boolean removeComment(String sender, Timestamp date) throws NamingException, SQLException{
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "UPDATE Comment SET status = ? WHERE sender = ? AND date = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1,"delete");
                stm.setString(2,sender);
                stm.setTimestamp(3,date);
                int row = stm.executeUpdate();
                if(row>0){
                    result = true;
                }
            }
        } finally{
            if(stm!=null){
                stm.close();
            }
            if(con!=null){
                con.close();
            }
        }       
        return result;
    }
    
    public List<CommentDTO> loadAuth(String receiver) throws NamingException, SQLException{
        List<CommentDTO> commentList = new ArrayList();
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "SELECT sender,date,detail,receiver,dateOfArticle FROM Comment WHERE receiver = ? AND status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, receiver);
                stm.setString(2, "new");
                rs = stm.executeQuery();
                while(rs.next()){
                    String sender = rs.getString("sender");
                    Timestamp date = rs.getTimestamp("date");
                    String detail = rs.getString("detail");
                    receiver = rs.getString("receiver");
                    Timestamp dateOfArticle = rs.getTimestamp("dateOfArticle");
                    commentList.add(new CommentDTO(detail, sender, receiver, date, dateOfArticle));
                }                
            }
        } finally{
            if(rs!=null){
                rs.close();
            }
            if(stm!=null){
                stm.close();
            }
            if(con!=null){
                con.close();
            }
        }
        return commentList;
    }
    
}
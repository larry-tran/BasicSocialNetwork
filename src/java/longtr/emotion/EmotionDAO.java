/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.emotion;

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
public class EmotionDAO implements Serializable{

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public int getLikeNumber(String owner, Timestamp dateOfArticle) throws NamingException, SQLException {
        int result = 0;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT COUNT(emotion) AS numOfLike FROM Emotion WHERE emotion = ? AND receiver = ? AND dateOfArticle = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "like");
                stm.setString(2, owner);
                stm.setTimestamp(3, dateOfArticle);
                
                rs = stm.executeQuery();
                if(rs.next()){
                    result = rs.getInt("numOfLike");
                }
            }
        } finally {
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
        return result;
    }
    
    public int getDislikeNumber(String owner, Timestamp dateOfArticle) throws NamingException, SQLException {
        int result = 0;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT COUNT(emotion) AS numOfDislike FROM Emotion WHERE emotion = ? AND receiver = ? AND dateOfArticle = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "dislike");
                stm.setString(2, owner);
                stm.setTimestamp(3, dateOfArticle);
                
                rs = stm.executeQuery();
                if(rs.next()){
                    result = rs.getInt("numOfDislike");
                }
            }
        } finally {
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
        return result;
    }
    
//    public boolean addEmotion (boolean emotion, String sender,String receiver, Timestamp date) throws NamingException, SQLException{
    public boolean addEmotion (EmotionDTO dto) throws NamingException, SQLException{
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "INSERT INTO Emotion(sender,date, emotion, receiver, dateOfArticle) VALUES (?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getSender());
                stm.setTimestamp(2, dto.getDate());
                stm.setString(3, dto.getEmotion());
                stm.setString(4, dto.getReceiver());
                stm.setTimestamp(5, dto.getDateOfArticle());
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
    
    public String checkExistEmo(String sender, String receiver, Timestamp dateOfArticle) throws NamingException, SQLException{
        String result = null;
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "SELECT emotion FROM Emotion WHERE sender = ? AND receiver = ? AND dateOfArticle = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, sender);
                stm.setString(2, receiver);
                stm.setTimestamp(3, dateOfArticle);
                rs = stm.executeQuery();
                if(rs.next()){
                    result = rs.getString("emotion");
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
        return result;
    }
    
//    public boolean editEmotion(boolean editEmo, String sender, String receiver, Timestamp date) throws NamingException, SQLException{
    public boolean editEmotion(EmotionDTO dto) throws NamingException, SQLException{
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "UPDATE Emotion SET emotion = ? WHERE sender = ? AND receiver = ? AND dateOfArticle = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getEmotion());
                stm.setString(2, dto.getSender());
                stm.setString(3, dto.getReceiver());
                stm.setTimestamp(4, dto.getDateOfArticle());
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
    
    public List<EmotionDTO> loadAuthEmotion(String receiver) throws NamingException, SQLException{
        List<EmotionDTO> listEmotion = new ArrayList<>();
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "SELECT emotion, sender ,dateOfArticle ,date FROM Emotion WHERE receiver = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, receiver);
                rs = stm.executeQuery();
                while(rs.next()){
                    String emotion = rs.getString("emotion");
                    String sender = rs.getString("sender");
                    Timestamp date = rs.getTimestamp("date");
                    Timestamp dateOfArticle = rs.getTimestamp("dateOfArticle");
//                    listEmotion.add(new EmotionDTO(emotion, sender, receiver, date));
                    listEmotion.add(new EmotionDTO(emotion, sender, receiver, date, dateOfArticle));
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
        return listEmotion;
    }
}

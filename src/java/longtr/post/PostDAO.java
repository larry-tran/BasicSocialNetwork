/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.post;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longtr.article.ArticleDTO;
import longtr.emotion.EmotionDAO;
import longtr.image.ImageDAO;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class PostDAO implements Serializable{
    
    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;
    
    private List<PostDTO> listPost;

    public List<PostDTO> getListPost() {
        return listPost;
    }

    public void loadPost(String des, int index) throws SQLException, NamingException {
        int x = index * 20 - 19;
        int y = x + 19;

        ImageDAO iDao = new ImageDAO();
        EmotionDAO eDao = new EmotionDAO();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT Post.Owner, Post.date, Post.title, Post.description, Account.name "
                        + "FROM (SELECT ROW_NUMBER() OVER(ORDER BY date) AS Row,Owner,date,title,description FROM Article WHERE status = 'Active' AND description LIKE ?) AS Post "
                        + "LEFT JOIN Account ON Post.Owner = Account.email "
                        + "WHERE (Row >= ?) and (Row <= ?) "
                        + "ORDER BY Post.date DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + des + "%");
                stm.setInt(2, x);
                stm.setInt(3, y);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String owner = rs.getString("owner");
                    Timestamp date = rs.getTimestamp("date");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String name = rs.getString("name");
                    ArticleDTO arti = new ArticleDTO(owner, date, title, description);
                    List<String> imageList = iDao.loadImage(owner, date);
                    if (this.listPost == null) {
                        this.listPost = new ArrayList<>();
                    }
                     this.listPost.add(new PostDTO(arti, imageList, name,eDao.getLikeNumber(owner, date),eDao.getDislikeNumber(owner, date)));
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
    }
    
    public PostDTO viewPost(String owner, Timestamp date) throws NamingException, SQLException {
        ImageDAO iDao = new ImageDAO();
        EmotionDAO eDao = new EmotionDAO();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT Article.owner, Article.title , Article.description, Article.date ,Account.name "
                        + "FROM Article "
                        + "LEFT JOIN Account ON Article.owner = Account.email "
                        + "WHERE owner = ? AND date = ? AND Article.status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, owner);
                stm.setTimestamp(2, date);
                stm.setString(3, "Active");
                rs = stm.executeQuery();
                if (rs.next()) {
                    owner = rs.getString("owner");
                    String name = rs.getString("name");
                    date = rs.getTimestamp("date");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    List<String> listImage = iDao.loadImage(owner, date);
                    ArticleDTO artDto = new ArticleDTO(owner, date, title, description);                    
                    PostDTO dto = new PostDTO(artDto, listImage, name,eDao.getLikeNumber(owner, date),eDao.getDislikeNumber(owner, date));
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
    
    public List<PostDTO> mangePost(String owner, String name) throws NamingException, SQLException{
        List<PostDTO> listOfPost = new ArrayList<>();
        ImageDAO iDao = new ImageDAO();
        EmotionDAO eDao = new EmotionDAO();
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "SELECT owner, title , description, date FROM Article WHERE owner = ? AND status = ? ORDER BY date DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, owner);
                stm.setString(2, "Active");
                rs = stm.executeQuery();
                while(rs.next()){
                    owner = rs.getString("owner");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    Timestamp date = rs.getTimestamp("date");
                    ArticleDTO articleDto = new ArticleDTO(owner, date, title, description);
                    List<String> listImage = iDao.loadImage(owner, date);
                    listOfPost.add(new PostDTO(articleDto, listImage, name,eDao.getLikeNumber(owner, date),eDao.getDislikeNumber(owner, date)));
                }
            }
        } finally{
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
        return listOfPost;
    }
    
    public boolean deletePost(String owner, Timestamp date) throws NamingException, SQLException{
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "UPDATE Article SET status = ? WHERE owner = ? AND date = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "delete");
                stm.setString(2, owner);
                stm.setTimestamp(3, date);
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
    
}

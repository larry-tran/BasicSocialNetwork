/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.article;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class ArticleDTO implements Serializable {

    private String owner;
    private Timestamp date;
    private String title;
    private String description;
    private int like;
    private int dislike;
    private String status;

    public ArticleDTO() {
    }

    public ArticleDTO(String owner, Timestamp date, String title, String description, int like, int dislike, String status) {
        this.owner = owner;
        this.date = date;
        this.title = title;
        this.description = description;
        this.like = like;
        this.dislike = dislike;
        this.status = status;
    }

    public ArticleDTO(String owner, Timestamp date, String title, String description) {
        this.owner = owner;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    public ArticleDTO(String owner, Timestamp date) {
        this.owner = owner;
        this.date = date;
    }

    public ArticleDTO(String owner, Timestamp date, String title, String description, int like, int dislike) {
        this.owner = owner;
        this.date = date;
        this.title = title;
        this.description = description;
        this.like = like;
        this.dislike = dislike;
    }

    public ArticleDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

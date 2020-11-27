/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.comment;

import java.io.Serializable;
import java.sql.Timestamp;
import longtr.notification.NotificationDTO;

/**
 *
 * @author Admin
 */
public class CommentDTO extends NotificationDTO implements Serializable{
    
    private String detail;
    private boolean status;

    public CommentDTO(String detail, String sender, String receiver, Timestamp date, Timestamp dateOfArticle) {
        super(sender, receiver, date, dateOfArticle);
        this.detail = detail;
    }

    public CommentDTO(String sender, String receiver, Timestamp date, Timestamp dateOfArticle) {
        super(sender, receiver, date, dateOfArticle);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public Timestamp getDate(){
        return date;
    }
    
    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String getReceiver() {
        return receiver;
    }

    @Override
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public Timestamp getDateOfArticle() {
        return dateOfArticle;
    }

    @Override
    public void setDateOfArticle(Timestamp dateOfArticle) {
        this.dateOfArticle = dateOfArticle;
    }

    @Override
    public String showNotification() {
        return date + " " + sender + " COMMENTED your post: " + detail;
    }

    @Override
    public int compareTo(NotificationDTO o){
        return this.getDate().compareTo(o.getDate());
    }
    
}

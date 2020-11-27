/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.notification;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public abstract class NotificationDTO implements Serializable, Comparable<NotificationDTO> {
    protected String sender;
    protected String receiver;
    protected Timestamp date;
    protected Timestamp dateOfArticle;

    public NotificationDTO(String sender, String receiver, Timestamp date, Timestamp dateOfArticle) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.dateOfArticle = dateOfArticle;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Timestamp getDateOfArticle() {
        return dateOfArticle;
    }

    public void setDateOfArticle(Timestamp dateOfArticle) {
        this.dateOfArticle = dateOfArticle;
    }
    
    public abstract String showNotification();
}

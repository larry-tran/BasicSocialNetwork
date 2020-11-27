/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.emotion;

import java.io.Serializable;
import java.sql.Timestamp;
import longtr.notification.NotificationDTO;

/**
 *
 * @author Admin
 */
public class EmotionDTO extends NotificationDTO implements Serializable{

    private String emotion;

    public EmotionDTO(String sender, String receiver, Timestamp date, Timestamp dateOfArticle) {
        super(sender, receiver, date, dateOfArticle);
    }

    public EmotionDTO(String emotion, String sender, String receiver, Timestamp date, Timestamp dateOfArticle) {
        super(sender, receiver, date, dateOfArticle);
        this.emotion = emotion;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
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
        return date + " " + sender + " " + emotion +"D your post";
    }

     @Override
    public int compareTo(NotificationDTO o){
        return this.getDate().compareTo(o.getDate());
    }
}

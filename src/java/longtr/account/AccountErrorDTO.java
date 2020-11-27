/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.account;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class AccountErrorDTO implements Serializable{
    private String errEmail;
    private String errPassword;
    private String errName;
    private String errConfirm;
    private String errCode;

    public AccountErrorDTO() {
    }

    public AccountErrorDTO(String errEmail, String errPassword, String errName, String errConfirm, String errCode) {
        this.errEmail = errEmail;
        this.errPassword = errPassword;
        this.errName = errName;
        this.errConfirm = errConfirm;
        this.errCode = errCode;
    }

    public String getErrEmail() {
        return errEmail;
    }

    public void setErrEmail(String errEmail) {
        this.errEmail = errEmail;
    }

    public String getErrPassword() {
        return errPassword;
    }

    public void setErrPassword(String errPassword) {
        this.errPassword = errPassword;
    }

    public String getErrName() {
        return errName;
    }

    public void setErrName(String errName) {
        this.errName = errName;
    }

    public String getErrConfirm() {
        return errConfirm;
    }

    public void setErrConfirm(String errConfirm) {
        this.errConfirm = errConfirm;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
    
    
}

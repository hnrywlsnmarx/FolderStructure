/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bws.backupdms.model;

import java.util.Date;

/**
 *
 * @author adminapp
 */
public class Comment {
    private long id;
    private String loan_ap_no;
    private String comment;
    private int user_name;
    private String level_spv;
    private Date comment_date;
    private int flag_spv;
    private Date tbo_date;
    private String reason;
    private int flag_process;

    public int getFlag_process() {
        return flag_process;
    }

    public void setFlag_process(int flag_process) {
        this.flag_process = flag_process;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoan_ap_no() {
        return loan_ap_no;
    }

    public void setLoan_ap_no(String loan_ap_no) {
        this.loan_ap_no = loan_ap_no;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUser_name() {
        return user_name;
    }

    public void setUser_name(int user_name) {
        this.user_name = user_name;
    }

    public String getLevel_spv() {
        return level_spv;
    }

    public void setLevel_spv(String level_spv) {
        this.level_spv = level_spv;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }

    public int getFlag_spv() {
        return flag_spv;
    }

    public void setFlag_spv(int flag_spv) {
        this.flag_spv = flag_spv;
    }

    public Date getTbo_date() {
        return tbo_date;
    }

    public void setTbo_date(Date tbo_date) {
        this.tbo_date = tbo_date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
}

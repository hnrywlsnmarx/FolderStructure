/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bws.backupdms.model;

/**
 *
 * @author guita
 */
public class Detail_file {
    private long id;
    private String loan_app_no;
    private String file;
    private String branch_dir;
    private String alias;
    private int flag;
    private int flag_exist;

    public Detail_file() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoan_app_no() {
        return loan_app_no;
    }

    public void setLoan_app_no(String loan_app_no) {
        this.loan_app_no = loan_app_no;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getBranch_dir() {
        return branch_dir;
    }

    public void setBranch_dir(String branch_dir) {
        this.branch_dir = branch_dir;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag_exist() {
        return flag_exist;
    }

    public void setFlag_exist(int flag_exist) {
        this.flag_exist = flag_exist;
    }
}

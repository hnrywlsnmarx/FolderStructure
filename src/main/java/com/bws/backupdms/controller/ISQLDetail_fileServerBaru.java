/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bws.backupdms.controller;

import com.bws.backupdms.model.Comment;
import com.bws.backupdms.model.Data_file;
import com.bws.backupdms.model.Detail_file;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author guita
 */
public interface ISQLDetail_fileServerBaru {
    public Detail_file[] getAll(Connection conn)  throws SQLException;
    public Detail_file[] addDetail_file(Detail_file dFile, Connection conn) throws SQLException;
    public Detail_file[] updateExistFlag(Detail_file detailFile, Connection conn) throws SQLException;
    public Detail_file[] updateExistFlagNullSubstr(Detail_file detailFile, Connection conn, String namafile) throws SQLException;
    public Detail_file[] updateExistFlagFileNull(Detail_file detailFile, Connection conn, String namafile) throws SQLException;
    public Data_file[] addData_file(Data_file dataFile, Connection conn) throws SQLException;
    public Data_file[] updateFlagProcess(Data_file dataFile, Connection conn, String where) throws SQLException;
    public Comment[]  addComment(Comment comment, Connection conn) throws SQLException;
    public Comment[] updateFlagComment(Comment comment, Connection conn, String where) throws SQLException;
}

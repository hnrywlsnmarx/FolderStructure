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
public interface ISQLDetail_fileServerLama {
    public Detail_file[] ambilDetailfile(Connection conn) throws SQLException;
    public Detail_file[] searchDetailfile (String where, Connection conn) throws SQLException;
    public Detail_file[] cariDetailfile(String where, Connection conn) throws SQLException;
    public Detail_file[] getNullFlag(Connection conn, String where) throws SQLException;
    public Detail_file[] getAllNullFlag(Connection conn, String where) throws SQLException;
    public Detail_file[] getBranchDir(Connection conn, String where) throws SQLException;
    public Detail_file[] getAllExistFlag(Connection conn) throws SQLException;
    public Detail_file[] updateFlag(Detail_file detailFile, Connection conn, String where) throws SQLException;
    public Detail_file[] updateAllFlag(Detail_file detailFile, Connection conn, String where, String branch_dir) throws SQLException;
    public Data_file[] getAllDataByDate(Connection conn, String where) throws SQLException;
    public Data_file[] updateFlagProcess(Data_file dataFile, Connection conn, String where) throws SQLException;
    public Comment[] getCommentByDate(Connection conn, String where) throws SQLException;
    public Comment[] updateCommentFlag(Comment comment, Connection conn, String where) throws SQLException;
}

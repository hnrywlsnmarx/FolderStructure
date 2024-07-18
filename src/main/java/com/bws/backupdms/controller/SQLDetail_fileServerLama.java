/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bws.backupdms.controller;

import com.bws.backupdms.model.Comment;
import com.bws.backupdms.model.Data_file;
import com.bws.backupdms.model.Detail_file;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 *
 * @author guita
 */
public class SQLDetail_fileServerLama implements ISQLDetail_fileServerLama{
    @Override
    public Detail_file[] ambilDetailfile(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM detail_file");
            Vector v = new Vector();
            Detail_file detailFile = null;
            while(rs.next()){
                detailFile = new Detail_file();
                detailFile.setId(rs.getLong("id"));
                detailFile.setLoan_app_no(rs.getString("loan_app_no"));
                detailFile.setFile(rs.getString("file"));
                detailFile.setBranch_dir(rs.getString("branch_dir"));
                detailFile.setAlias(rs.getString("alias"));
                detailFile.setFlag(rs.getInt("flag"));
                detailFile.setFlag_exist(rs.getInt("flag_exist"));
                v.add(detailFile);
            }
            Detail_file[] detailFiles = new Detail_file[v.size()];
            v.copyInto(detailFiles);
            return detailFiles;
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        }
        return null;
    }

    @Override
    public Detail_file[] searchDetailfile(String where, Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM detail_file WHERE loan_app_no LIKE '%"+where+"%'");
            Vector v = new Vector();
            Detail_file detailFile = null;
            while(rs.next()){
                detailFile = new Detail_file();
                detailFile.setId(rs.getLong("id"));
                detailFile.setLoan_app_no(rs.getString("loan_app_no"));
                detailFile.setFile(rs.getString("file"));
                detailFile.setBranch_dir(rs.getString("branch_dir"));
                detailFile.setAlias(rs.getString("alias"));
                detailFile.setFlag(rs.getInt("flag"));
                detailFile.setFlag_exist(rs.getInt("flag_exist"));
                v.add(detailFile);

            }
            Detail_file[] detailFiles = new Detail_file[v.size()];
            v.copyInto(detailFiles);
            return detailFiles;
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        }
        return null;
    }

    @Override
    public Detail_file[] cariDetailfile(String where, Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM detail_file WHERE loan_app_no = " + where);
            Vector v = new Vector();
            Detail_file detailFile = null;
            while(rs.next()){
                detailFile = new Detail_file();
                detailFile.setId(rs.getLong("id"));
                detailFile.setLoan_app_no(rs.getString("loan_app_no"));
                detailFile.setFile(rs.getString("file"));
                detailFile.setBranch_dir(rs.getString("branch_dir"));
                detailFile.setAlias(rs.getString("alias"));
                detailFile.setFlag(rs.getInt("flag"));
                detailFile.setFlag_exist(rs.getInt("flag_exist"));
                v.add(detailFile);
                Detail_file[] detailFiles = new Detail_file[v.size()];
                v.copyInto(detailFiles);
                return detailFiles;
            }
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        }
        return null;
    }

    @Override
    public Detail_file[] getNullFlag(Connection conn, String where) throws SQLException {
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM detail_file where flag IS NULL AND branch_dir like '%"+where+"%'");
            Vector v = new Vector();
            Detail_file detailFile = null;
            while(rs.next()){
                detailFile = new Detail_file();
                detailFile.setId(rs.getLong("id"));
                detailFile.setLoan_app_no(rs.getString("loan_app_no"));
                detailFile.setFile(rs.getString("file"));
                detailFile.setBranch_dir(rs.getString("branch_dir"));
                detailFile.setAlias(rs.getString("alias"));
                detailFile.setFlag(rs.getInt("flag"));
                detailFile.setFlag_exist(rs.getInt("flag_exist"));
                v.add(detailFile);
            }
            Detail_file[] detailFiles = new Detail_file[v.size()];
            v.copyInto(detailFiles);
            return detailFiles;
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        }
        return null;
    
    }

    @Override
    public Detail_file[] updateFlag(Detail_file detailFile, Connection conn, String where) throws SQLException {
        PreparedStatement psql = null;
        try {
            psql = conn.prepareStatement("UPDATE detail_file SET flag = 1 where branch_dir like '%"+where+"%'");
            psql.executeUpdate();
//            System.out.println("updating success");        
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        } finally{
            if(psql != null){
                 try{
                psql.close();
                }catch(SQLException e){
                    System.out.println("Error = "+e.getMessage());
                }
            }  
            return null;
        }
    }

    @Override
    public Detail_file[] getAllNullFlag(Connection conn, String where) throws SQLException {
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM detail_file where flag IS NULL and "
                    + "SUBSTR(file, 6, 10)  LIKE '%"+where+"%'");
            Vector v = new Vector();
            Detail_file detailFile = null;
            while(rs.next()){
                detailFile = new Detail_file();
                detailFile.setId(rs.getLong("id"));
                detailFile.setLoan_app_no(rs.getString("loan_app_no"));
                detailFile.setFile(rs.getString("file"));
                detailFile.setBranch_dir(rs.getString("branch_dir"));
                detailFile.setAlias(rs.getString("alias"));
                detailFile.setFlag(rs.getInt("flag"));
                detailFile.setFlag_exist(rs.getInt("flag_exist"));
                v.add(detailFile);
            }
            Detail_file[] detailFiles = new Detail_file[v.size()];
            v.copyInto(detailFiles);
            return detailFiles;
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        }
        return null;
    }

    @Override
    public Detail_file[] updateAllFlag(Detail_file detailFile, Connection conn, String where, String branch_dir) throws SQLException {
        PreparedStatement psql = null;
        try {
            psql = conn.prepareStatement("UPDATE detail_file SET flag = 1 where SUBSTR(file, 6, 10)  "
                    + "LIKE '%"+where+"%' and flag is NULL and branch_dir = "+branch_dir);
            psql.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        } finally{
            if(psql != null){
                 try{
                psql.close();
                }catch(SQLException e){
                    System.out.println("Error = "+e.getMessage());
                }
            }  
            return null;
        }
    }

    @Override
    public Detail_file[] getAllExistFlag(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM detail_file where flag = 1");
            Vector v = new Vector();
            Detail_file detailFile = null;
            while(rs.next()){
                detailFile = new Detail_file();
                detailFile.setId(rs.getLong("id"));
                detailFile.setLoan_app_no(rs.getString("loan_app_no"));
                detailFile.setFile(rs.getString("file"));
                detailFile.setBranch_dir(rs.getString("branch_dir"));
                detailFile.setAlias(rs.getString("alias"));
                detailFile.setFlag(rs.getInt("flag"));
                detailFile.setFlag_exist(rs.getInt("flag_exist"));
                v.add(detailFile);
            }
            Detail_file[] detailFiles = new Detail_file[v.size()];
            v.copyInto(detailFiles);
            return detailFiles;
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        }
        return null; 
    }

    @Override
    public Detail_file[] getBranchDir(Connection conn, String where) throws SQLException {
        int row = 0;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        try (Statement st = conn.createStatement()) {
//            ResultSet rs = st.executeQuery("SELECT DISTINCT id,branch_dir from detail_file where SUBSTR(file, 6, 7) = '"+where+"' group by branch_dir");
            ResultSet rs = st.executeQuery("SELECT DISTINCT id,branch_dir from detail_file where  SUBSTR(file, 6, 10) = '" + where + "' group by branch_dir");
            Vector v = new Vector();
            Detail_file detailFile = null;
            while(rs.next()){
                row = row+1;
                detailFile = new Detail_file();
                detailFile.setId(rs.getLong("id"));
                detailFile.setBranch_dir(rs.getString("branch_dir"));
                v.add(detailFile);
                System.out.println("counting rows "+row+" timestamps "+ timeStamp);
            }
            Detail_file[] detailFiles = new Detail_file[v.size()];
            v.copyInto(detailFiles);
            return detailFiles;
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        }
        return null;
    }

    @Override
    public Data_file[] getAllDataByDate(Connection conn, String where) throws SQLException {
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * from data_file where flag_process IS NULL "
                    + "and SUBSTR(date_input, 1, 10) LIKE '%"+where+"%'");
            Vector v = new Vector();
            Data_file dataFile = null;
            while (rs.next()) {
                dataFile = new Data_file();
                dataFile.setModul(rs.getString("modul"));
                dataFile.setKode_cabang(rs.getString("kode_cabang"));
                dataFile.setLoan_app_no(rs.getString("loan_app_no"));
                dataFile.setNo_cif(rs.getString("no_cif"));
                dataFile.setNo_ktp(rs.getString("no_ktp"));
                dataFile.setNama_debitur(rs.getString("nama_debitur"));
                dataFile.setTtl(rs.getString("ttl"));
                dataFile.setAlamat_rumah(rs.getString("alamat_rumah"));
                dataFile.setNo_telp_rumag(rs.getString("no_tlp_rumah"));
                dataFile.setInstansi(rs.getString("instansi"));
                dataFile.setAlamat_kantor(rs.getString("alamat_kantor"));
                dataFile.setNo_tlp_kantor(rs.getString("no_tlp_kantor"));
                dataFile.setPlafond(rs.getDouble("plafond"));
                dataFile.setJangka_waktu(rs.getString("jangka_waktu"));
                dataFile.setRate(rs.getDouble("rate"));
                dataFile.setAngsuran(rs.getDouble("angsuran"));
                dataFile.setTanggal_jatuh_tempo(rs.getInt("tanggal_jatuh_tempo"));
                dataFile.setProduk(rs.getString("produk"));
                dataFile.setUser_input(rs.getString("user_input"));
                dataFile.setBranch_input(rs.getString("branch_input"));
                dataFile.setDate_input(rs.getDate("date_input"));
                dataFile.setUser_spv1(rs.getString("user_spv1"));
                dataFile.setFinal_status_spv1(rs.getInt("final_status_spv1"));
                dataFile.setDate_flag_spv1(rs.getDate("date_flag_spv1"));
                dataFile.setUser_spv2(rs.getString("user_spv2"));
                dataFile.setFinal_status_spv2(rs.getInt("final_status_spv2"));
                dataFile.setDate_flag_spv2(rs.getDate("date_flag_spv2"));
                dataFile.setUser_spv3(rs.getString("user_spv3"));
                dataFile.setFinal_status_spv3(rs.getInt("final_status_spv3"));
                dataFile.setDate_flag_spv3(rs.getDate("date_flag_spv3"));
                dataFile.setFinal_status(rs.getInt("final_status"));
                dataFile.setFlag_process(rs.getInt("flag_process"));
                v.add(dataFile);
            }
            Data_file[] dataFiles = new Data_file[v.size()];
            v.copyInto(dataFiles);
            return dataFiles;
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        }
        return null;
    
    }

    @Override
    public Data_file[] updateFlagProcess(Data_file dataFile, Connection conn, String where) throws SQLException {
        PreparedStatement psql = null;
        try {
            psql = conn.prepareStatement("UPDATE data_file SET flag_process = 1 where "
                    + "SUBSTR(date_input, 1, 10) LIKE '%"+where+"%' and flag_process is null");
            psql.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        } finally{
            if(psql != null){
                try {
                    psql.close();
                } catch (SQLException e) {
                    System.out.println("Error = "+e.getMessage());
                }
            }
            return null;
        }
    }

    @Override
    public Comment[] getCommentByDate(Connection conn, String where) throws SQLException {
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * from comment where flag_process IS NULL "
                    + "and SUBSTR(comment_date, 1, 10) LIKE '%"+where+"%'");
            Vector v = new Vector();
            Comment comment = null;
            while(rs.next()){
                comment = new Comment();
                comment.setId(rs.getLong("id"));
                comment.setLoan_ap_no(rs.getString("loan_app_no"));
                comment.setComment(rs.getString("comment"));
                comment.setUser_name(rs.getInt("user_name"));
                comment.setLevel_spv(rs.getString("level_spv"));
                comment.setComment_date(rs.getDate("comment_date"));
                comment.setFlag_spv(rs.getInt("flag_spv"));
                comment.setTbo_date(rs.getDate("tbo_date"));
                comment.setReason(rs.getString("reason"));
                comment.setFlag_process(rs.getInt("flag_process"));
                v.add(comment);
            }
            Comment[] comments = new Comment[v.size()];
            v.copyInto(comments);
            return comments;
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        }
        return null;
    }

    @Override
    public Comment[] updateCommentFlag(Comment comment, Connection conn, String where) throws SQLException {
        PreparedStatement psql = null;
        try {
            psql = conn.prepareStatement("UPDATE comment SET flag_process = 1 where SUBSTR(comment_date, 1, 10) LIKE '%"+where+"%' "
                    + "and flag_process is null");
            psql.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        } finally{
            if(psql != null){
                try {
                    psql.close();
                } catch (SQLException e) {
                    System.out.println("Error = "+e.getMessage());
                }
            }
            return null;
        }
    }
}
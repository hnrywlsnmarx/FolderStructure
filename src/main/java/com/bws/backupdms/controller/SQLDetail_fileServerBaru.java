/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bws.backupdms.controller;

import com.bws.backupdms.model.Comment;
import com.bws.backupdms.model.Data_file;
import com.bws.backupdms.model.Detail_file;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author guita
 */
public class SQLDetail_fileServerBaru implements ISQLDetail_fileServerBaru{

    @Override
    public Detail_file[] addDetail_file(Detail_file dFile, Connection conn) throws SQLException {
        PreparedStatement psql = null;
        try {
            psql = conn.prepareStatement("INSERT INTO detail_file(loan_app_no, file, branch_dir, alias, flag, flag_exist) "
                    + "values (?,?,?,?,?,NULL)");
            psql.setString(1, dFile.getLoan_app_no());
            psql.setString(2, dFile.getFile());
            psql.setString(3, dFile.getBranch_dir());
            psql.setString(4, dFile.getAlias());
            psql.setInt(5, 1);
//            psql.setInt(6, 1);
            psql.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(psql != null){
                psql.close();
            }
        }
        return null;
    }  

    @Override
    public Detail_file[] updateExistFlag(Detail_file detailFile, Connection conn) throws SQLException {
        PreparedStatement psql = null;
        try {
            psql = conn.prepareStatement("UPDATE detail_file SET flag_exist = 1");
            psql.executeUpdate();
//            System.out.println("updating success");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(psql != null){
                try {
                    psql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    @Override
    public Detail_file[] updateExistFlagNullSubstr(Detail_file detailFile, Connection conn, String namafile) throws SQLException {
        PreparedStatement psql = null;
        try {
            psql = conn.prepareStatement("UPDATE detail_file SET flag_exist = 1 where "
                    + "SUBSTR(file,17) like '%"+namafile+"%' and flag_exist is null");
            psql.executeUpdate();
//            System.out.println("updating success");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(psql != null){
                try {
                    psql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    @Override
    public Detail_file[] updateExistFlagFileNull(Detail_file detailFile, Connection conn, String namafile) throws SQLException {
        PreparedStatement psql = null;
        try {
            psql = conn.prepareStatement("UPDATE detail_file SET flag_exist = 1 where "
                    + "file like '%"+namafile+"%' and flag_exist is null");
            psql.executeUpdate();
//            System.out.println("updating success");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(psql != null){
                try {
                    psql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    @Override
    public Detail_file[] getAll(Connection conn) throws SQLException {
        Statement st = null;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM detail_file where flag_exist is null");
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
            e.printStackTrace();
        } finally{
            if(st != null){
                st.close();
            }
        }
        return null;
    }

    @Override
    public Data_file[] addData_file(Data_file dataFile, Connection conn) throws SQLException {
        PreparedStatement psql = null;
        try {
            psql = conn.prepareStatement("INSERT INTO data_file(modul, kode_cabang, loan_app_no, no_cif, no_ktp, nama_debitur, "
                    + "ttl, alamat_rumah, no_tlp_rumah, instansi, alamat_kantor, no_tlp_kantor, plafond, jangka_waktu, rate, angsuran, "
                    + "tanggal_jatuh_tempo, produk, user_input, branch_input, date_input, user_spv1, final_status_spv1, date_flag_spv1, "
                    + "user_spv2, final_status_spv2, date_flag_spv2, user_spv3, final_status_spv3, date_flag_spv3, final_status, processed,"
                    + "updated_at, created_at, status_pernikahan, pekerjaan, fasilitas, flag_process) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            psql.setString(1, dataFile.getModul());
            psql.setString(2, dataFile.getKode_cabang());
            psql.setString(3, dataFile.getLoan_app_no());
            psql.setString(4, dataFile.getNo_cif());
            psql.setString(5, dataFile.getNo_ktp());
            psql.setString(6, dataFile.getNama_debitur());
            psql.setString(7, dataFile.getTtl());
            psql.setString(8, dataFile.getAlamat_rumah());
            psql.setString(9, dataFile.getNo_telp_rumag());
            psql.setString(10, dataFile.getInstansi());
            psql.setString(11, dataFile.getAlamat_kantor());
            psql.setString(12, dataFile.getNo_tlp_kantor());
            psql.setDouble(13, dataFile.getPlafond());
            psql.setString(14, dataFile.getJangka_waktu());
            psql.setDouble(15, dataFile.getRate());
            psql.setDouble(16, dataFile.getAngsuran());
            psql.setInt(17, dataFile.getTanggal_jatuh_tempo());
            psql.setString(18, dataFile.getProduk());
            psql.setString(19, dataFile.getUser_input());
            psql.setString(20, dataFile.getBranch_input());
            psql.setDate(21, (Date) dataFile.getDate_input());
            psql.setString(22, dataFile.getUser_spv1());
            psql.setInt(23, dataFile.getFinal_status_spv1());
            psql.setDate(24, (Date) dataFile.getDate_flag_spv1());
            psql.setString(25, dataFile.getUser_spv2());
            psql.setInt(26, dataFile.getFinal_status_spv2());
            psql.setDate(27, (Date) dataFile.getDate_flag_spv2());
            psql.setString(28, dataFile.getUser_spv3());
            psql.setInt(29, dataFile.getFinal_status_spv3());
            psql.setDate(30, (Date) dataFile.getDate_flag_spv3());
            psql.setInt(31, dataFile.getFinal_status());
            psql.setInt(32, dataFile.getProcessed());
            psql.setDate(33, (Date) dataFile.getUpdated_at());
            psql.setDate(34, (Date) dataFile.getCreated_at());
            psql.setInt(35, dataFile.getStatus_pernikahan());
            psql.setString(36, dataFile.getPekerjaan());
            psql.setInt(37, dataFile.getFasilitas());
            psql.setInt(38, 1);            
            psql.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(psql != null){
                psql.close();
            }
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
            e.printStackTrace();
        } finally{
            if(psql != null){
                try {
                    psql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    @Override
    public Comment[] addComment(Comment comment, Connection conn) throws SQLException {
        PreparedStatement psql = null;
        try{
            psql = conn.prepareStatement("INSERT INTO comment(loan_app_no, comment, user_name, level_spv, comment_date, flag_spv,"
                + " tbo_date, reason, flag_process) values (?,?,?,?,?,?,?,?,1)");
            psql.setString(1, comment.getLoan_ap_no());
            psql.setString(2, comment.getComment());
            psql.setInt(3, comment.getUser_name());
            psql.setString(4, comment.getLevel_spv());
            psql.setDate(5, (Date) comment.getComment_date());
            psql.setInt(6, comment.getFlag_spv());
            psql.setDate(7, (Date) comment.getTbo_date());
            psql.setString(8, comment.getReason());
            psql.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        } finally {
            if(psql != null){
                psql.close();
            }
        }
        return null;
    }

    @Override
    public Comment[] updateFlagComment(Comment comment, Connection conn, String where) throws SQLException {
        PreparedStatement psql = null;
        try {
            psql = conn.prepareStatement("UPDATE comment SET flag_process = 1 where "
                    + "SUBSTR(comment_date, 1, 10) LIKE '%"+where+"%' and flag_process is null");
            psql.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error = "+e.getMessage());
        } finally {
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

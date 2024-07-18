/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bws.backupdms.resource;

//import com.dms.migrasi.controller.SQLDetail_fileServerLama;
//import com.dms.migrasi.controller.SQLDetail_fileServerBaru;
//import com.dms.migrasi.koneksi.Koneksi;
//import com.dms.migrasi.model.Detail_file;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTPClient;
import com.bws.backupdms.controller.ISQLDetail_fileServerLama;
import com.bws.backupdms.controller.ISQLDetail_fileServerBaru;
import com.bws.backupdms.controller.SQLDetail_fileServerBaru;
import com.bws.backupdms.controller.SQLDetail_fileServerLama;
import com.bws.backupdms.koneksi.Koneksi;
import com.bws.backupdms.model.Comment;
import com.bws.backupdms.model.Data_file;
import com.bws.backupdms.model.Detail_file;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author guita
 */
public class CreateFolder {

    static Detail_file df = null;
    static ISQLDetail_fileServerLama sql = new SQLDetail_fileServerLama();
    static ISQLDetail_fileServerBaru sqlLocal = new SQLDetail_fileServerBaru();
    static Connection connServerLama = new Koneksi().getConnectionServerLama();
    static Connection connServerBaru = new Koneksi().getConnectionServerBaru();
    String curDate;
    String curDateForDataFile;
    Detail_file det;
    Detail_file detbranch;
    Data_file dat;
    Comment com;
    static String branch;

    String namabarux;
    static String oldfiles;
    static String oldDirectory;

    public CreateFolder() {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        this.curDate = formatter.format(cal.getTime());
        System.out.println("H Min 1 = " + this.curDate);
        Calendar calForDataFile = Calendar.getInstance();
        calForDataFile.add(5, -1);
        SimpleDateFormat formatterForDataFile = new SimpleDateFormat("yyyy-MM-dd");
        this.curDateForDataFile = formatterForDataFile.format(cal.getTime());
        uploadDir();
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private void uploadDir() {
        System.out.println("curDate= " + this.curDate);
        Date date = new Date();
        SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formattermonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatterdate = new SimpleDateFormat("dd");
        String year = formatteryear.format(date);
//        String month = "02";
        String month = formattermonth.format(date);
        String tgl = formatterdate.format(date);
        String yesterday = formatterdate.format(yesterday());
        String testgl = "06";
        System.out.println("tahun= " + year);
        System.out.println("bulan= " + month);
        System.out.println("hari ini= " + tgl);
        System.out.println("kemaren= " + yesterday);
        System.out.println("tes tgl= " + testgl);
        try {
            Detail_file[] detailFiles = sql.getBranchDir(connServerLama, this.curDate);
            if (detailFiles.length != 0) {
                for (Detail_file detailFile : detailFiles) {
                    String branch_dir = detailFile.getBranch_dir();
                    String server = "172.28.97.30";
                    int port = 21;
                    String user = "admin";
                    String pass = "systechdc28";

//                    String server = "172.28.100.78";
//                    int port = 21;
//                    String user = "aku";
//                    String pass = "aku123";

                    FTPClient ftpClient = new FTPClient();
                    ftpClient.setConnectTimeout(0);
                    ftpClient.setDataTimeout(0);
                    try {
                        ftpClient.connect(server, port);
                        ftpClient.login(user, pass);
                        ftpClient.enterLocalPassiveMode();
                        System.out.println("Connected");
                        String remoteDirPath = "/structuredfolder/" + branch_dir + "/" + year + "/" + month;
                        String localDirPath = "D:/storage_file/indexed/" + branch_dir + "/" + year + "/" + month;
                        FTPUploadUtil.uploadDirStructure(ftpClient, remoteDirPath, localDirPath, "");
                        ftpClient.logout();
                        ftpClient.disconnect();
                        System.out.println("Disconnected");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error SQL " + e.toString());
        }
    }

    private void addDatafile() {
        try {
            Data_file[] dataFiles = sql.getAllDataByDate(connServerLama, curDateForDataFile);
            if (dataFiles.length != 0) {
                for (Data_file dataFile : dataFiles) {
                    dat = dataFile;
                    sqlLocal.addData_file(dataFile, connServerBaru);
                    writeLog("Writing data with Loan App No = " + dataFile.getLoan_app_no() + " on New DB. Started on:");
                    System.out.println("Done Writing dataFile on New DB with the Loan App No = " + dataFile.getLoan_app_no() + " and "
                            + "CIF No = " + dataFile.getNo_cif());
                }
                writeLog("Writing " + dataFiles.length + " datas from DATA_FILE table in Old DB to New DB. Started on:");
            } else {
                System.out.println("No data");
            }
        } catch (SQLException e) {
            System.out.println("Error = " + e.toString());
        }

    }

    private void addToNewDatabase() {
        try {
            Detail_file[] detailFiles = sql.getAllNullFlag(connServerLama, curDate);
            if (detailFiles.length != 0) {
                for (Detail_file detailFile : detailFiles) {
                    det = detailFile;

                    String file = detailFile.getFile();
                    String branch_dir = detailFile.getBranch_dir();
                    sqlLocal.addDetail_file(detailFile, connServerBaru);
                    writeLog("Writing data with Loan App No = " + detailFile.getLoan_app_no() + " on New DB. Started on:");
                    System.out.println("Done Writing detailFile on New DB with the Loan App No = " + detailFile.getLoan_app_no() + " and "
                            + "Document in = " + detailFile.getFile());
                    String nFile;
                    String path = "D:/storage_file/indexed";

                    if (file.length() >= 31) {
                        if ((!"ID01".equals(file.substring(0, 4))) && (!"REKE".equals(file.substring(0, 4))) && (!"BUKU".equals(file.substring(0, 4)))) {
                            String tahun = file.substring(5, 9);
                            String bulan = file.substring(10, 12);
                            String tanggal = file.substring(13, 15);
                            path = path + "/" + branch_dir + "/" + tahun + "/" + bulan + "/" + tanggal;
                            nFile = file.substring(16);
                        } else {
                            path = path + "/" + branch_dir;
                            nFile = file;
                        }
                    } else {
                        path = path + "/" + branch_dir;
                        nFile = file;
                    }

                    File f1 = new File(path);

                    File TempFile = new File(f1 + "/" + nFile);
                    boolean exist = TempFile.exists();
                    if (exist) {
                        if (file.length() >= 31) {
                            if ((!"ID01".equals(file.substring(0, 4))) && (!"REKE".equals(file.substring(0, 4))) && (!"BUKU".equals(file.substring(0, 4)))) {
                                System.out.println("File " + nFile + " Update Exist 1");
                                sqlLocal.updateExistFlagNullSubstr(detailFile, connServerBaru, nFile);
                            } else {
                                System.out.println("File " + nFile + " Update Exist 2");
                                sqlLocal.updateExistFlagFileNull(detailFile, connServerBaru, nFile);
                            }
                        } else {
                            System.out.println("File " + nFile + " Update Exist 3");
                            sqlLocal.updateExistFlagFileNull(detailFile, connServerBaru, nFile);
                        }
                    }
                }
                writeLog("Writing " + detailFiles.length + " datas from DETAIL_FILE table in Old DB to New DB. Started on:");
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada data dengan Flag Null");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addComment() {
        try {
            Comment[] comments = sql.getCommentByDate(connServerLama, curDateForDataFile);
            if (comments.length != 0) {
                for (Comment comment : comments) {
                    com = comment;
                    sqlLocal.addComment(comment, connServerBaru);
                    writeLog("Writing comment of data with Loan App No = " + comment.getLoan_ap_no() + " on New DB. Started on:");
                    System.out.println("Done Writing comment on New DB with the Loan App No = " + comment.getLoan_ap_no());
                }
                writeLog("Writing " + comments.length + " datas from COMMENTS table in Old DB to New DB. Started on: ");
            } else {
                System.out.println("No Data");
            }
        } catch (SQLException e) {
            System.out.println("Error = " + e.toString());
        }
    }

    private void updateDataFile() {
        try {
            Data_file[] dataFiles = sql.getAllDataByDate(connServerLama, curDateForDataFile);
            if (dataFiles.length != 0) {
                for (Data_file dataFile : dataFiles) {
                    dat = dataFile;
                    sql.updateFlagProcess(dataFile, connServerLama, curDateForDataFile);
                    writeLog("Updating data with Loan App No = " + dataFile.getLoan_app_no() + " on Old DB. Started on: ");
                    System.out.println("Done Updating data_file on Old DB = " + dataFile.getBranch_input());
                }
                writeLog("Updating " + dataFiles.length + " datas from DATA_FILE table in Old DB. Started on:");
            }
        } catch (SQLException e) {
            System.out.println("Error UpdateDataFile = " + e.toString());
        }
    }

    private void updateDetailFile() {
        try {
            Detail_file[] detailFiles = sql.getAllNullFlag(connServerLama, curDate);
            if (detailFiles.length != 0) {
                for (Detail_file detailFile : detailFiles) {
                    det = detailFile;
                    String branch_dir = detailFile.getBranch_dir();
                    sql.updateAllFlag(detailFile, connServerLama, curDate, branch_dir);
                    writeLog("Updating detail file with Loan App No = " + detailFile.getLoan_app_no() + " on Old DB. Started on: ");
                    System.out.println("Done Updating detail_file on Old DB = " + detailFile.getBranch_dir());
                }
                writeLog("Updating " + detailFiles.length + " datas from DETAIL_ILE table in Old DB. Started on:");
            }
        } catch (SQLException e) {
            System.out.println("Error UpdateDetailFile = " + e.toString());
        }
    }

    private void updateComment() {
        try {
            Comment[] comments = sql.getCommentByDate(connServerLama, curDateForDataFile);
            if (comments.length != 0) {
                for (Comment comment : comments) {
                    com = comment;
                    sql.updateCommentFlag(comment, connServerLama, curDateForDataFile);
                    writeLog("Updating comment of data with Loan App No = " + comment.getLoan_ap_no() + " on Old DB. Started on: ");
                    System.out.println("Done Updating comment on Old DB = " + comment.getLoan_ap_no());
                }
                writeLog("Updating " + comments.length + " datas from COMMENT table in Old DB. Started on:");
            }
        } catch (SQLException e) {
            System.out.println("Error UpdateComment = " + e.toString());
        }
    }

    private static void writeLog(String ket) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String tgl = formatter.format(date);
        System.out.println(tgl);
        try {
            FileWriter log = new FileWriter("D:/storage_file/logFTPUploadStuctures.txt", true);
            BufferedWriter bw = new BufferedWriter(log);
            bw.newLine();
            bw.write(ket + " " + tgl);
            bw.close();
        } catch (IOException e) {
            System.out.println("Failed to Create Log");
        }
    }

    public static void main(String[] args) {
        new CreateFolder();
    }
}

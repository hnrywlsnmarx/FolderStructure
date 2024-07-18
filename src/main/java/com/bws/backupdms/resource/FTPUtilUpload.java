/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bws.backupdms.resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Administrator
 */
public class FTPUtilUpload {

    public static void uploadDirectory(FTPClient ftpClient,
            String remoteDirPath, String localParentDir, String remoteParentDir)
            throws IOException {

        System.out.println("LISTING directory: " + localParentDir);

        File localDir = new File(localParentDir);
        File[] subFiles = localDir.listFiles();

        if (subFiles != null && subFiles.length > 0) {
            for (File item : subFiles) {
                String remoteFilePath = remoteDirPath + "/" + remoteParentDir
                        + "/" + item.getName();
                if (remoteParentDir.equals("")) {
                    remoteFilePath = remoteDirPath + "/" + item.getName();
                }

                if (item.isFile()) {
                    int ctLocalFile = 0;
                    long flsz;
                    // upload the file
                    String localFilePath = item.getAbsolutePath();
                    System.out.println("About to upload the file: " + localFilePath);
                    boolean uploaded = uploadSingleFile(ftpClient,
                            localFilePath, remoteFilePath);
                    if (uploaded) {
                        System.out.println("UPLOADED a file to: "
                                + remoteFilePath);

                        writeLog("Uploading file " + localFilePath + ". Started at: ");
                    } else {
                        System.out.println("COULD NOT upload the file: "
                                + localFilePath);
                        writeLog("COULD NOT Uploading file " + localFilePath + ". Started at: ");
                    }

                    ctLocalFile = subFiles.length;
                    System.out.println("ctLocalFile= " + ctLocalFile);
//                    
//                    if (ctLocalFile < 50) {
//                        // System.out.println("baris = "+baris);
//                        ctLocalFile = 0;                        
//                        System.gc();
//                        long current = Runtime.getRuntime().freeMemory();
//                        try {
//                            // System.out.println("sleep1..");
//                            Thread.sleep(200);
//                        } catch (InterruptedException ex) {
//                            Thread.currentThread().interrupt();
//                        }
//                        System.out.println("Waking Up");
//                    }

                } else {
                    // create directory on the server
                    boolean created = ftpClient.makeDirectory(remoteFilePath);
                    if (created) {
                        System.out.println("CREATED the directory: "
                                + remoteFilePath);
                        writeLog("Creating directory " + remoteDirPath + ". Started at: ");
                    } else {
                        System.out.println("COULD NOT create the directory: "
                                + remoteFilePath);
                        writeLog("COULD NOT Creating directory " + remoteDirPath + ". Started at: ");
                    }

                    // upload the sub directory
                    String parent = remoteParentDir + "/" + item.getName();
                    if (remoteParentDir.equals("")) {
                        parent = item.getName();
                    }

                    localParentDir = item.getAbsolutePath();
                    uploadDirectory(ftpClient, remoteDirPath, localParentDir,
                            parent);
                }
            }
        }
    }

    public static boolean uploadSingleFile(FTPClient ftpClient,
            String localFilePath, String remoteFilePath) throws IOException {
        File localFile = new File(localFilePath);
        File remoteFile = new File(remoteFilePath);
        File parentDir = remoteFile.getParentFile();
        File singlefile = remoteFile.getAbsoluteFile();
        int code;
        code = ftpClient.getReplyCode();
        System.out.println("replyCode= " + code);
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        } else {
            if (!singlefile.exists()) {
                InputStream inputStream = new FileInputStream(localFile);
                try {
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    return ftpClient.storeFile(remoteFilePath, inputStream);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private static void writeLog(String ket) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String tgl = formatter.format(date);
        System.out.println(tgl);
        try {
            FileWriter log = new FileWriter("D:/storage_file/logFTPUploadFiles.txt", true);
            BufferedWriter bw = new BufferedWriter(log);
            bw.newLine();
            bw.write(ket + " " + tgl);
            bw.close();
        } catch (IOException e) {
            System.out.println("Failed to Create Log");
        }
    }

    public static boolean makeDirectories(FTPClient ftpClient, String dirPath)
            throws IOException {
        String[] pathElements = dirPath.split("/");
        if (pathElements != null && pathElements.length > 0) {
            for (String singleDir : pathElements) {
                boolean existed = ftpClient.changeWorkingDirectory(singleDir);
                if (!existed) {
                    boolean created = ftpClient.makeDirectory(singleDir);
                    if (created) {
                        System.out.println("CREATED directory: " + singleDir);
                        ftpClient.changeWorkingDirectory(singleDir);
                    } else {
                        System.out.println("COULD NOT create directory: " + singleDir);
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bws.backupdms.resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.net.ftp.FTPClient;
 
/**
 *
 * @author adminapp
 */
public class FTPUploadUtil {
    /**
     * Upload structure of a directory (without uploading files) to a FTP
     * server.
     *
     * @param ftpClient
     *            an instance of org.apache.commons.net.ftp.FTPClient class.
     * @param remoteDirPath
     *            Path of the destination directory on the server.
     * @param localParentDir
     *            Path of the local directory being uploaded.
     * @param remoteParentDir
     *            Path of the parent directory of the current directory on the
     *            server (used by recursive calls).
     * @throws IOException
     *             if any network or IO error occurred.
     */
    public static void uploadDirStructure(FTPClient ftpClient,
            String remoteDirPath, String localParentDir, String remoteParentDir)
            throws IOException {
 
        File localDir = new File(localParentDir);
        File[] subFiles = localDir.listFiles();
        if (subFiles != null && subFiles.length > 0) {
            for (File item : subFiles) {
                if (item.isDirectory()) {
                    String remoteFilePath = remoteDirPath + "/"
                            + remoteParentDir + "/" + item.getName();
                    if (remoteParentDir.equals("")) {
                        remoteFilePath = remoteDirPath + "/" + item.getName();
                    }
 
                    // create directory on the server
                    boolean created = ftpClient.makeDirectory(remoteFilePath);
                    if (created) {
                        System.out.println("CREATED the directory: "
                                + remoteFilePath);
                        writeLog("CREATED the directory " + remoteDirPath + ". Started at: ");
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
                    uploadDirStructure(ftpClient, remoteDirPath, localParentDir,
                            parent);
                }
            }
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
}

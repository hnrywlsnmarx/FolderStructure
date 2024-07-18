/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bws.backupdms.resource;

/**
 *
 * @author wilson
 */
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FTPFolderListing {

    private static final String LOG_FILE_PATH = "logBPRStatisticFiles.txt";

    public static void main(String[] args) {
        // Konfigurasi FTP
        String server = "172.28.97.30";
        int port = 21;
        String user = "irfan";
        String pass = "bws@dipo28";
        String directoryPath = "/DMS_Backup/";

        // Panggil metode untuk melakukan listing folder
        listFoldersFTP(server, port, user, pass, directoryPath);
    }

    public static void listFoldersFTP(String server, int port, String user, String pass, String directoryPath) {
        FTPClient ftpClient = new FTPClient();

        try {
            // Koneksi ke server FTP
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Ganti direktori ke yang diinginkan
            ftpClient.changeWorkingDirectory(directoryPath);

            // Buat BufferedWriter untuk menulis log ke file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH))) {
                // Panggil metode untuk melakukan listing folder
                listFoldersRecursivelyFTP(ftpClient, "", writer);
                long[] dirInfo = calculateDirectoryInfo(ftpClient, directoryPath, "");
                System.out.println("Total dirs: " + dirInfo[0]);
                System.out.println("Total files: " + dirInfo[1]);
                System.out.println("Total size: " + dirInfo[2]);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Tutup koneksi FTP
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void listFoldersRecursivelyFTP(FTPClient ftpClient, String path, BufferedWriter writer)
            throws IOException {
        // Dapatkan daftar file dan folder dalam direktori
        FTPFile[] files = ftpClient.listFiles(path);

        if (files != null) {
            // Iterasi melalui setiap file/folder
            for (FTPFile file : files) {
                // Jika itu adalah folder dan bukan "." atau "..", cetak namanya dan rekursif untuk subfolder
                if (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..")) {
                    String folderPath = path + file.getName() + "/";
                    System.out.println("Folder: " + folderPath);
                    writer.write("Folder: " + folderPath);
                    writer.newLine();
                    listFoldersRecursivelyFTP(ftpClient, folderPath, writer);
                }
            }
        }
    }

    public static long[] calculateDirectoryInfo(FTPClient ftpClient,
            String parentDir, String currentDir) throws IOException {
//        System.out.println("Start 7!");
        long[] info = new long[3];
        long totalSize = 0;
        int totalDirs = 0;
        int totalFiles = 0;

        String dirToList = parentDir;
//        System.out.println("Start 8!");
        if (!currentDir.equals("")) {
//            System.out.println("Start 9!");
            dirToList += "/" + currentDir;
        }
//        System.out.println("Start 10!");

        try {
//            System.out.println("Start 11!");
            FTPFile[] subFiles = ftpClient.listFiles(dirToList);
//            System.out.println("Start 12!");
            if (subFiles != null && subFiles.length > 0) {
//                System.out.println("Start 13!");
                for (FTPFile aFile : subFiles) {
                    String currentFileName = aFile.getName();
                    System.out.println("checked: " + aFile.getName());
                    if (currentFileName.equals(".") || currentFileName.equals("..")) {
                        continue;
                    }

                    if (aFile.isDirectory()) {
//                        System.out.println("Start 14!");
                        totalDirs++;
                        long[] subDirInfo = calculateDirectoryInfo(ftpClient, dirToList, currentFileName);
                        totalDirs += subDirInfo[0];
                        totalFiles += subDirInfo[1];
                        totalSize += subDirInfo[2];
                    } else {
//                        System.out.println("Start 15!");
                        totalSize += aFile.getSize();
                        totalFiles++;
                    }
                }
            }

            info[0] = totalDirs;
            info[1] = totalFiles;
            info[2] = totalSize;

            return info;
        } catch (IOException ex) {
            throw ex;
        }
    }
}

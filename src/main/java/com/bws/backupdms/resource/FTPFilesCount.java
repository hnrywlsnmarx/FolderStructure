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

public class FTPFilesCount {

    private static final String LOG_FILE_PATH = "backupedFiles.txt";

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

    public static int[] listFoldersRecursivelyFTP(FTPClient ftpClient, String path, BufferedWriter writer)
            throws IOException {
        // Dapatkan daftar file dan folder dalam direktori
        FTPFile[] files = ftpClient.listFiles(path);

        // Penghitungan file dan ukuran file dalam folder saat ini
        int fileCount = 0;
        long totalSize = 0;

        if (files != null) {
            // Iterasi melalui setiap file/folder
            for (FTPFile file : files) {
                // Jika itu adalah folder dan bukan "." atau "..", cetak namanya dan rekursif untuk subfolder
                if (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..")) {
                    String folderPath = path + file.getName() + "/";
                    System.out.println("Folder: " + folderPath);
                    writer.write("Folder: " + folderPath);
                    writer.newLine();

                    // Rekursif untuk subfolder dan tambahkan jumlah file dan ukuran file di subfolder
                    int[] subfolderStats = listFoldersRecursivelyFTP(ftpClient, folderPath, writer);
                    fileCount += subfolderStats[0];
                    totalSize += subfolderStats[1];
                } else {
                    // Jika itu adalah file, tambahkan ke penghitungan file dan ukuran total
                    fileCount++;
                    totalSize += file.getSize();
                }
            }

            // Cetak statistik file di folder saat ini
            System.out.println("Folder: " + path);
            System.out.println("Jumlah file: " + fileCount);
            System.out.println("Total ukuran file: " + totalSize + " bytes");
            writer.write("Folder: " + path);
            writer.newLine();
            writer.write("Jumlah file: " + fileCount);
            writer.newLine();
            writer.write("Total ukuran file: " + totalSize + " bytes");
            writer.newLine();
        }

        // Mengembalikan statistik file dalam folder saat ini sebagai array int[]
        return new int[]{fileCount, (int) totalSize};
    }
   
}

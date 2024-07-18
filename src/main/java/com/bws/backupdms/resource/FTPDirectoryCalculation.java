package com.bws.backupdms.resource;

/**
 *
 * @author wilson
 */
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FTPDirectoryCalculation {

    public static void main(String[] args) {
        System.out.println("Start!");
        String host = "172.28.97.30";
        int port = 21;
        String username = "irfan";
        String password = "bws@dipo28";
//        System.out.println("Start 2!");
        FTPClient ftpClient = new FTPClient();
//        System.out.println("Start 3!");
        try {
//            System.out.println("Start 4!");
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();

            String remoteDirPath = "/DMS_Backup/";

            long[] dirInfo = calculateDirectoryInfo(ftpClient, remoteDirPath, "");
            writeLog("Total dirs: " + dirInfo[0] + " . Checked On: ");
            writeLog("Total files: " + dirInfo[1] + " . Checked On: ");
            writeLog("Total size: " + dirInfo[2] + " . Checked On: ");
            writeLog("________________________________________________________");
            System.out.println("Total dirs: " + dirInfo[0]);
            System.out.println("Total files: " + dirInfo[1]);
            System.out.println("Total size: " + dirInfo[2]);
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException ex) {
//            System.out.println("Start 5!");
            System.err.println(ex);
        }
//        System.out.println("Start 6!");
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

    private static void writeLog(String ket) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String tgl = formatter.format(date);
        System.out.println(tgl);
        try {
            FileWriter log = new FileWriter("statisticFiles.txt", true);
            BufferedWriter bw = new BufferedWriter(log);
            bw.newLine();
            bw.write(ket + " " + tgl);
            bw.close();
        } catch (IOException e) {
            System.out.println("Failed to Create Log");
        }
    }
}

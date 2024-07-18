/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bws.backupdms.resource;

/**
 *
 * @author wilson
 */
import java.io.File;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class CreateDirectories {

    public static void main(String[] args) {

        String server = "172.28.97.30";
        int port = 21;
        String user = "irfan";
        String pass = "bws@dipo28";
//        String server = "172.28.100.78";
//        int port = 21;
//        String user = "slip";
//        String pass = "slip123456";

        String[] branch_dir = {
            "110", "111", "112", "113", "114", "115", "116", "117", "118", "119",
            "120", "140", "141", "142", "143", "144", "145", "146", "147", "148", "149",
            "150", "160", "170", "171", "180", "181", "200", "201", "202", "203", "210",
            "211", "212", "213", "214", "215", "216", "217", "230", "231", "232", "233",
            "234", "235", "236", "237", "238", "239", "240", "245", "250", "251", "252",
            "253", "254", "255", "256", "260", "261", "262", "263", "264", "265", "266",
            "267", "268", "269", "270", "271", "300", "301", "302", "303", "304", "305",
            "306", "307", "308", "309", "310", "311", "312", "313", "314", "315", "316",
            "317", "318", "319", "320", "321", "322", "323", "324", "325", "330", "331",
            "332", "333", "334", "335", "336", "340", "341", "342", "343", "344", "345",
            "350", "351", "352", "353", "354", "355", "356", "357", "358", "359", "360",
            "361", "362", "363", "364", "365", "370", "371", "372", "373", "374", "376",
            "377", "380", "381", "390", "391", "400", "401", "410", "411", "420", "421",
            "430", "431", "440", "450", "451", "460", "470", "913", "931", "932", "933",
            "934", "935", "936", "937", "938", "939", "940", "941", "942", "943"
        };

//         String[] branch_dir = {
//            "113","116"
//        };
         
        String[] tahunArray = {
            "2025", "2026", "2027", "2029", "2030"
        };

        String[] bulanArray = {
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
        };

//        String[] tanggalArray = generateTanggalArray(tahunArray, bulanArray);

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            // Membuat direktori
            for (String kodeCabang : branch_dir) {
                for (String tahun : tahunArray) {
                    for (String bulan : bulanArray) {
//                        for (String tanggal : tanggalArray) {
//                            System.out.println("tanggal: "+tanggal);
                            String directoryPath = "/DMS_Backup/" + kodeCabang + "/" + tahun + "/" + bulan;
//                            String directoryPath = "DMS_Backup/" + kodeCabang + "/" + tahun + "/" + bulan;
                            File directory = new File(directoryPath);
                            boolean success = ftpClient.makeDirectory(directoryPath);

//                    if (!directory.exists()) {
//                        if (directory.mkdirs()) {
//                            System.out.println("Direktori berhasil dibuat: " + directory.getAbsolutePath());
//                        } else {
//                            System.out.println("Gagal membuat direktori: " + directory.getAbsolutePath());
//                        }
//                    } else {
//                        System.out.println("Direktori sudah ada: " + directory.getAbsolutePath());
//                    }
                            if (success) {
                                System.out.println("Direktori berhasil dibuat: " + directoryPath);
                            } else {
                                System.out.println("Gagal membuat direktori: " + directoryPath);
                            }
                        }
                    }
//                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String[] generateTanggalArray(String[] tahunArray, String[] bulanArray) {
        int totalTahun = tahunArray.length;
        int totalBulan = bulanArray.length;
        String[] tanggalArray = new String[totalTahun * totalBulan];

        int index = 0;
        for (String tahun : tahunArray) {
            for (String bulan : bulanArray) {
                tanggalArray[index] = tahun + "-" + bulan + "-01"; // Hari dimulai dari 01
                index++;
            }
        }

        return tanggalArray;
    }
}

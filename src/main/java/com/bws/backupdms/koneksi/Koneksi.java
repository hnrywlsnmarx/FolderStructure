/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bws.backupdms.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author guita
 */
public class Koneksi {

Statement st = null;
public static Connection connnewserver;
public static Connection connoldserver;

    public Connection getConnectionServerBaru(){
        try {
//                String urllocal = "jdbc:mysql://172.28.140.200/dms";
                String urllocal = "jdbc:mysql://localhost/dms";
                String userlocal = "root";
                String passlocal = "systech@dipo28";
//                String passlocal = "";
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                connnewserver = DriverManager.getConnection(urllocal, userlocal, passlocal);
                
                connnewserver.setNetworkTimeout(new Executor
                () {
                    @Override
                    public void execute(Runnable command) {
                    }
                }, 1000);
                
       } catch (ClassNotFoundException e) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, e);
       } catch (SQLException e){
            e.printStackTrace();
      }
      if(connnewserver == null){
          JOptionPane.showMessageDialog(null, "Database disconnected");
          System.exit(0);
      } else {
          System.out.println("Koneksi ke Server Baru sukses");
//          JOptionPane.showMessageDialog(null, "Koneksi ke Local");
      }
        return connnewserver;
    }
//
    public Connection getConnectionServerLama(){
        try {
//                String urlserver = "jdbc:mysql://172.28.97.131/dms";
                String urlserver = "jdbc:mysql://localhost/dms";
                String userserver = "root";
                String passserver = "systech@dipo28";

//                
                Class.forName("com.mysql.cj.jdbc.Driver");
                connoldserver = DriverManager.getConnection(urlserver, userserver, passserver);
                
                connoldserver.setNetworkTimeout(new Executor
                () {
                    @Override
                    public void execute(Runnable command) {
                    }
                }, 1000); 	
                
       } catch (ClassNotFoundException e) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, e);
       } catch (SQLException e){
            e.printStackTrace();
      }
      if(connoldserver == null){
          JOptionPane.showMessageDialog(null, "Server Database disconnected ");
          System.exit(0);
      } else {
          System.out.println("Koneksi ke Server Lama sukses");
////          JOptionPane.showMessageDialog(null, "Koneksi ke Server");
      }
        return connoldserver;
    }
    
    public static void main(String[] args) {
        Koneksi co = new Koneksi();
//        co.getConnectionServerBaru();
        co.getConnectionServerLama();
    }
}

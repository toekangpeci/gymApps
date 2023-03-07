/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;
import  java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class koneksi {
    private Connection koneksi;
    public Connection connect(){
        String db = "gymapps";
        String url = "jdbc:mysql://localhost:3306/"+db+"";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("wait . .  is trying to reach "+url+"");            
            System.out.println("now, connecting to database ("+db+")'s  driver . . . .");
        }
        catch(ClassNotFoundException ex){
        System.out.println("oh no, conection to database driver failure :( heres why : "+ex);
        }        
        try{
            koneksi=DriverManager.getConnection(url,"root","");
            System.out.println("Woohoo! is connected to database "+db+"! congrats! ;)");
          }
        catch (SQLException ex){
            System.out.println("connection to database "+db+" failure :( heres why : "+ex);
        }
        return koneksi;
    }
}

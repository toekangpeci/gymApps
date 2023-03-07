/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import java.awt.*;
import java.awt.Font; 
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PAKUAN-IT
 */
public class home extends javax.swing.JFrame {
    int int_data_list,int_home_list,int_items_list, int_trans_list, int_report_list, int_input_list = 0;
    public static String idMember,idTipe,idUser,idTrainer,idKelas,idMembership,idJadwalKelas,idProduk,idTransaksi,date1,date2,userSession,idMembershipBaru,namaUser,idTrans = "";
    private DefaultTableModel tabmode,tabmode2;
    private Connection conn = new koneksi().connect();
    

    /**
     * Creates new form home
     */
    public home() {
        initComponents();
        paneling();
        Locale locale = new Locale("id","ID");
        Locale.setDefault(locale);
    }
    
    public void getId(String id){
        userSession=login_form.user;
        txtUserHome.setText(id);   
        paneling();
        panelHome();
    }
    
     public void comboJadwalKelas() {
       
         cbAbsensiKelas.removeAllItems();
         cbAbsensiKelas.addItem(" - Pilih Jadwal Kelas - ");  
        try{
            String sql = "select id,tanggal, mulai from jadwal_kelas";           
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery(sql); 
            while (r.next()) {
             cbAbsensiKelas.addItem(r.getString(1)+" - "+r.getString(2)+" - "+r.getString(3));}
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }   
    }
     
      public void combo_member() {
        cbLapMember.removeAllItems();
        cbLapMember.addItem(" - Pilih Member - ");  
        try{
            String sql = "select id_member,nama from member_gym";           
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery(sql); 
            while (r.next()) {
            cbLapMember.addItem(r.getString(1)+" - "+r.getString(2));}
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }   
    }
    
    public void panelHome(){
    try {
            String sql="select nama_user, no_telp, email,tipe_user from user where id_user='"+userSession+"'";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                
                txtNama.setText(hasil.getString(1));
                namaUser=txtNama.getText();
                txtTelp.setText(hasil.getString(2));
                txtEmail.setText(hasil.getString(3));
                txtTipeHome.setText(hasil.getString(4));
                
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
        
    }
    
    public void paneling(){
    menManagement.setVisible(false);
    menKelas.setVisible(false);
    menMembership.setVisible(false);
    menProduk.setVisible(false);
    }
    
    public void tabMember(){
        tabMember.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Member","Nama Member","Gender","Usia","Alamat"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txtMember.getText();
        try {
            String sql="SELECT id_member,nama,gender,usia,alamat "
                    + "FROM member_gym "
                    + "where (id_member like '%"+cariitem+"%' or nama like'%"+cariitem+"%') order by id_member asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tabMember.setModel(tabmode);
            tabMember.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
  
    
    public void tabTrainer(){
        tabTrainer.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Instruktur","Nama","Gender","Usia","Alamat","Aktif"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txtTrainer.getText();
        try {
            String sql="SELECT id_instruktur,nama_instruktur,gender,usia,alamat,aktif "
                    + "FROM instruktur_gym "
                    + "where (id_instruktur like '%"+cariitem+"%' or nama_instruktur like'%"+cariitem+"%') order by id_instruktur asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5),
                hasil.getString(6)
            });
            }
            tabTrainer.setModel(tabmode);
            tabTrainer.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
    public void tabJadwal(){
        tabJadwal.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Jadwal","ID Kelas","Nama Kelas","Tanggal","Mulai","Akhir"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txtTrainer.getText();
        try{ 
                String sql="SELECT jadwal_kelas.id,jadwal_kelas.id_kelas, kelas_gym.nama_kelas, jadwal_kelas.tanggal, jadwal_kelas.mulai, jadwal_kelas.selesai "
                    + "FROM jadwal_kelas inner join kelas_gym on jadwal_kelas.id_kelas=kelas_gym.id_kelas "
                    + "where (jadwal_kelas.id like '%"+cariitem+"%' or kelas_gym.nama_kelas like '%"+cariitem+"%') "
                    + "and jadwal_kelas.tanggal >= curdate() order by jadwal_kelas.id asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5),
                hasil.getString(6)
            });
            }
            tabJadwal.setModel(tabmode);
            tabJadwal.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    

    
    public void tabTipe(){
        tabTipe.setAutoCreateRowSorter(true);
        Object[] Baris = {"Kode Membership","Nama Membership","Durasi (Hari)","Harga"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txtTipe.getText();
        try {
            String sql="SELECT id_tipe_membership,nama_membership,durasi,harga_membership "
                    + "FROM tipe_membership "
                    + "where (id_tipe_membership like '%"+cariitem+"%' or nama_membership like '%"+cariitem+"%') order by id_tipe_membership asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4)
            });
            }
            tabTipe.setModel(tabmode);
            tabTipe.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
    public void tabMembership(){
        tabMembership.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Transaksi","ID Member","Tipe Membership","Mulai","Akhir"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txtMembership.getText();
        try {
            String sql="SELECT id, id_member, id_tipe_membership,mulai,akhir "
                    + "FROM logs_membership "
                    + "where (id like '%"+cariitem+"%' or id_member like '%"+cariitem+"%') order by id asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tabMembership.setModel(tabmode);
            tabMembership.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
    public void tabTransaksi(){
        tabTransaksi.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Transaksi","ID Member","Waktu Transaksi","Total"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txtTransaksi.getText();
        try {
            String sql="SELECT id_transaksi, id_member, timestamp,total "
                    + "FROM logs_transaksi "
                    + "where (id_transaksi like '%"+cariitem+"%' or id_member like '%"+cariitem+"%') order by id_transaksi asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4)
            });
            }
            tabTransaksi.setModel(tabmode);
            tabTransaksi.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
    public void tabKelas(){
        tabKelas.setAutoCreateRowSorter(true);
        Object[] Baris = {"Kode Kelas","Nama Kelas","Instruktur Kelas","Harga Persesi"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txtKelas.getText();
        try {
            String sql="SELECT id_kelas,nama_kelas,id_instruktur,harga_sesi "
                    + "FROM kelas_gym "
                    + "where (id_kelas like '%"+cariitem+"%' or nama_kelas like '%"+cariitem+"%') order by id_kelas asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4)
            });
            }
            tabKelas.setModel(tabmode);
            tabKelas.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
    public void tabProduk(){
        tabProduk.setAutoCreateRowSorter(true);
        Object[] Baris = {"Kode Produk","Nama Produk","Jenis Produk","Harga Produk","Stock"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txtProduk.getText();
        try {
            String sql="SELECT id_produk,nama_produk,jenis_produk,harga_produk,stok "
                    + "FROM produk "
                    + "where (id_produk like '%"+cariitem+"%' or nama_produk like '%"+cariitem+"%') order by id_produk asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tabProduk.setModel(tabmode);
            tabProduk.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data Produk gagal ditemukan, Kode : "+ e);
        }    
    }
    
    public void tabUser(){
        tabUser.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID User","Nama User","NO. Telp","Email", "Tipe User"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txtUser.getText();
        try {
            String sql="SELECT id_user,nama_user,no_telp, email,tipe_user "
                    + "FROM user "
                    + "where (id_user like '%"+cariitem+"%' or nama_user like '%"+cariitem+"%') order by id_user asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tabUser.setModel(tabmode);
            tabUser.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel65 = new javax.swing.JPanel();
        kButton2 = new com.k33ptoo.components.KButton();
        mainFrame = new javax.swing.JPanel();
        pSide = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        kButton3 = new com.k33ptoo.components.KButton();
        jPanel2 = new javax.swing.JPanel();
        kButton4 = new com.k33ptoo.components.KButton();
        menManagement = new javax.swing.JPanel();
        kButton6 = new com.k33ptoo.components.KButton();
        kButton8 = new com.k33ptoo.components.KButton();
        kButton7 = new com.k33ptoo.components.KButton();
        jPanel4 = new javax.swing.JPanel();
        kButton9 = new com.k33ptoo.components.KButton();
        menKelas = new javax.swing.JPanel();
        kButton10 = new com.k33ptoo.components.KButton();
        kButton11 = new com.k33ptoo.components.KButton();
        kButton12 = new com.k33ptoo.components.KButton();
        jPanel5 = new javax.swing.JPanel();
        kButton13 = new com.k33ptoo.components.KButton();
        menMembership = new javax.swing.JPanel();
        kButton14 = new com.k33ptoo.components.KButton();
        kButton15 = new com.k33ptoo.components.KButton();
        kButton16 = new com.k33ptoo.components.KButton();
        kButton17 = new com.k33ptoo.components.KButton();
        jPanel6 = new javax.swing.JPanel();
        kButton18 = new com.k33ptoo.components.KButton();
        menProduk = new javax.swing.JPanel();
        kButton19 = new com.k33ptoo.components.KButton();
        kButton20 = new com.k33ptoo.components.KButton();
        kButton21 = new com.k33ptoo.components.KButton();
        pMain = new javax.swing.JPanel();
        pHome = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel64 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        txtUserHome = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        txtNama = new javax.swing.JLabel();
        txtTelp = new javax.swing.JLabel();
        txtEmail = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        txtTipeHome = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        txtUserHome1 = new javax.swing.JLabel();
        txtUserHome2 = new javax.swing.JLabel();
        txtUserHome3 = new javax.swing.JLabel();
        txtUserHome4 = new javax.swing.JLabel();
        txtUserHome5 = new javax.swing.JLabel();
        pMember = new javax.swing.JPanel();
        jPanel66 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel67 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabMember = new javax.swing.JTable();
        txtMember = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        bAddMember = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        bDelMember = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        bCariMember = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        bEditMember = new javax.swing.JLabel();
        pTipe = new javax.swing.JPanel();
        jPanel68 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel69 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabTipe = new javax.swing.JTable();
        txtTipe = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        bAddTipe = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        bDelTipe = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        bCariTipe = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        bEditTipe = new javax.swing.JLabel();
        pUser = new javax.swing.JPanel();
        jPanel70 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel71 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabUser = new javax.swing.JTable();
        txtUser = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        bAddUser = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        bDelUser = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        bCariUser = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        bEditUser = new javax.swing.JLabel();
        pTrainer = new javax.swing.JPanel();
        jPanel72 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel73 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabTrainer = new javax.swing.JTable();
        txtTrainer = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        bAddTrainer = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        bDelTrainer = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        bCariTrainer = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        bEditTrainer = new javax.swing.JLabel();
        pKelas = new javax.swing.JPanel();
        jPanel74 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel75 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabKelas = new javax.swing.JTable();
        txtKelas = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        bAddKelas = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        bDelKelas = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        bCariKelas = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        bEditKelas = new javax.swing.JLabel();
        pJadwalKelas = new javax.swing.JPanel();
        jPanel76 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel77 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tabJadwal = new javax.swing.JTable();
        txtJadwal = new javax.swing.JTextField();
        jPanel38 = new javax.swing.JPanel();
        bAddJadwal = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        bDelJadwal = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        bCariJadwal = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        bEditJadwal = new javax.swing.JLabel();
        pProduk = new javax.swing.JPanel();
        jPanel78 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel79 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tabProduk = new javax.swing.JTable();
        txtProduk = new javax.swing.JTextField();
        jPanel42 = new javax.swing.JPanel();
        bAddProduk = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        bDelProduk = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        bCariProduk = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        bEditProduk = new javax.swing.JLabel();
        pReport = new javax.swing.JPanel();
        p_perusahaan_header8 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        p_perusahaan_main8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        cbLapMember = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        membershipMonth = new com.toedter.calendar.JMonthChooser();
        membershipYear = new com.toedter.calendar.JYearChooser();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel70 = new javax.swing.JLabel();
        cbAbsensiKelas = new javax.swing.JComboBox<>();
        jLabel78 = new javax.swing.JLabel();
        jadwalKelasMonth = new com.toedter.calendar.JMonthChooser();
        jadwalKelasYear = new com.toedter.calendar.JYearChooser();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel80 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel83 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel84 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel79 = new javax.swing.JLabel();
        memberMonth = new com.toedter.calendar.JMonthChooser();
        memberYear = new com.toedter.calendar.JYearChooser();
        jLabel86 = new javax.swing.JLabel();
        penMemshipMonth = new com.toedter.calendar.JMonthChooser();
        penMemshipYear = new com.toedter.calendar.JYearChooser();
        jLabel87 = new javax.swing.JLabel();
        penProdukMonth = new com.toedter.calendar.JMonthChooser();
        penProdukYear = new com.toedter.calendar.JYearChooser();
        repTrainer = new javax.swing.JLabel();
        repHargaTrainer = new javax.swing.JLabel();
        repUser = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        repKelas = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        repJadwalKelas = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        repAbsensiKelas = new javax.swing.JLabel();
        repMember = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        date_mulai = new com.toedter.calendar.JDateChooser();
        jLabel89 = new javax.swing.JLabel();
        date_selesai = new com.toedter.calendar.JDateChooser();
        repCheckin = new javax.swing.JLabel();
        repMembership = new javax.swing.JLabel();
        repPenjualan = new javax.swing.JLabel();
        repIncomeMembership = new javax.swing.JLabel();
        repIncomeProduk = new javax.swing.JLabel();
        pMembership = new javax.swing.JPanel();
        jPanel82 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jPanel83 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tabMembership = new javax.swing.JTable();
        txtMembership = new javax.swing.JTextField();
        jPanel50 = new javax.swing.JPanel();
        bAddMembership = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        bCariMembership = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        bEditKelas2 = new javax.swing.JLabel();
        pTransaksi = new javax.swing.JPanel();
        jPanel84 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jPanel85 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tabTransaksi = new javax.swing.JTable();
        txtTransaksi = new javax.swing.JTextField();
        jPanel51 = new javax.swing.JPanel();
        bAddTransaksi = new javax.swing.JLabel();
        jPanel54 = new javax.swing.JPanel();
        bCariTransaksi = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        bEditTransaksi = new javax.swing.JLabel();

        jPanel65.setBackground(new java.awt.Color(230, 72, 72));

        kButton2.setForeground(new java.awt.Color(255, 253, 227));
        kButton2.setText("H O M E");
        kButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton2.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton2.setkBorderRadius(0);
        kButton2.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton2.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton2.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton2.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton2.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton2.setkIndicatorThickness(10);
        kButton2.setkPressedColor(new java.awt.Color(169, 51, 51));
        kButton2.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(kButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 506, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        mainFrame.setPreferredSize(new java.awt.Dimension(1370, 740));

        pSide.setBackground(new java.awt.Color(230, 72, 72));
        pSide.setPreferredSize(new java.awt.Dimension(241, 786));

        jPanel1.setBackground(new java.awt.Color(230, 72, 72));

        kButton3.setForeground(new java.awt.Color(32, 74, 86));
        kButton3.setText("HOME ");
        kButton3.setToolTipText("");
        kButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        kButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kButton3.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton3.setkBorderRadius(0);
        kButton3.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton3.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton3.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton3.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton3.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton3.setkIndicatorThickness(10);
        kButton3.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton3.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton3ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(230, 72, 72));

        kButton4.setForeground(new java.awt.Color(32, 74, 86));
        kButton4.setText("MANAGEMENT");
        kButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        kButton4.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton4.setkBorderRadius(0);
        kButton4.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton4.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton4.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton4.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton4.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton4.setkIndicatorThickness(10);
        kButton4.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton4.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton4ActionPerformed(evt);
            }
        });

        menManagement.setBackground(new java.awt.Color(230, 72, 72));

        kButton6.setForeground(new java.awt.Color(32, 74, 86));
        kButton6.setText("Instruktur & Trainer");
        kButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton6.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton6.setkBorderRadius(30);
        kButton6.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton6.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton6.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton6.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton6.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton6.setkIndicatorThickness(10);
        kButton6.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton6.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton6ActionPerformed(evt);
            }
        });

        kButton8.setForeground(new java.awt.Color(32, 74, 86));
        kButton8.setText("User Login");
        kButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton8.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton8.setkBorderRadius(30);
        kButton8.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton8.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton8.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton8.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton8.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton8.setkIndicatorThickness(10);
        kButton8.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton8.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton8ActionPerformed(evt);
            }
        });

        kButton7.setForeground(new java.awt.Color(32, 74, 86));
        kButton7.setText("Harga Persesi Instruktur & Trainer");
        kButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton7.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton7.setkBorderRadius(30);
        kButton7.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton7.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton7.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton7.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton7.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton7.setkIndicatorThickness(10);
        kButton7.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton7.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menManagementLayout = new javax.swing.GroupLayout(menManagement);
        menManagement.setLayout(menManagementLayout);
        menManagementLayout.setHorizontalGroup(
            menManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menManagementLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(kButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(kButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                .addContainerGap())
        );
        menManagementLayout.setVerticalGroup(
            menManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menManagementLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(kButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menManagement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(kButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(kButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menManagement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(230, 72, 72));

        kButton9.setForeground(new java.awt.Color(32, 74, 86));
        kButton9.setText("KELAS GYM");
        kButton9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        kButton9.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton9.setkBorderRadius(0);
        kButton9.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton9.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton9.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton9.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton9.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton9.setkIndicatorThickness(10);
        kButton9.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton9.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton9ActionPerformed(evt);
            }
        });

        menKelas.setBackground(new java.awt.Color(230, 72, 72));

        kButton10.setForeground(new java.awt.Color(32, 74, 86));
        kButton10.setText("Jenis Kelas");
        kButton10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton10.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton10.setkBorderRadius(30);
        kButton10.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton10.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton10.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton10.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton10.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton10.setkIndicatorThickness(10);
        kButton10.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton10.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton10ActionPerformed(evt);
            }
        });

        kButton11.setForeground(new java.awt.Color(32, 74, 86));
        kButton11.setText("Jadwal Kelas");
        kButton11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton11.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton11.setkBorderRadius(30);
        kButton11.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton11.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton11.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton11.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton11.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton11.setkIndicatorThickness(10);
        kButton11.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton11.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton11ActionPerformed(evt);
            }
        });

        kButton12.setForeground(new java.awt.Color(32, 74, 86));
        kButton12.setText("Absensi Kelas");
        kButton12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton12.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton12.setkBorderRadius(30);
        kButton12.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton12.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton12.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton12.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton12.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton12.setkIndicatorThickness(10);
        kButton12.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton12.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menKelasLayout = new javax.swing.GroupLayout(menKelas);
        menKelas.setLayout(menKelasLayout);
        menKelasLayout.setHorizontalGroup(
            menKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menKelasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(kButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(kButton12, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                .addContainerGap())
        );
        menKelasLayout.setVerticalGroup(
            menKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menKelasLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(kButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(kButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(kButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(230, 72, 72));

        kButton13.setForeground(new java.awt.Color(32, 74, 86));
        kButton13.setText("GYM MEMBERSHIP");
        kButton13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        kButton13.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton13.setkBorderRadius(0);
        kButton13.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton13.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton13.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton13.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton13.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton13.setkIndicatorThickness(10);
        kButton13.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton13.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton13ActionPerformed(evt);
            }
        });

        menMembership.setBackground(new java.awt.Color(230, 72, 72));

        kButton14.setForeground(new java.awt.Color(32, 74, 86));
        kButton14.setText("Tipe Membership");
        kButton14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton14.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton14.setkBorderRadius(30);
        kButton14.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton14.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton14.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton14.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton14.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton14.setkIndicatorThickness(10);
        kButton14.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton14.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton14ActionPerformed(evt);
            }
        });

        kButton15.setForeground(new java.awt.Color(32, 74, 86));
        kButton15.setText("Pendaftaran Membership");
        kButton15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton15.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton15.setkBorderRadius(30);
        kButton15.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton15.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton15.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton15.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton15.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton15.setkIndicatorThickness(10);
        kButton15.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton15.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton15ActionPerformed(evt);
            }
        });

        kButton16.setForeground(new java.awt.Color(32, 74, 86));
        kButton16.setText("Database Identitas Member");
        kButton16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton16.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton16.setkBorderRadius(30);
        kButton16.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton16.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton16.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton16.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton16.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton16.setkIndicatorThickness(10);
        kButton16.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton16.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton16ActionPerformed(evt);
            }
        });

        kButton17.setForeground(new java.awt.Color(32, 74, 86));
        kButton17.setText("Check-in Harian Membership");
        kButton17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton17.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton17.setkBorderRadius(30);
        kButton17.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton17.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton17.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton17.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton17.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton17.setkIndicatorThickness(10);
        kButton17.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton17.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menMembershipLayout = new javax.swing.GroupLayout(menMembership);
        menMembership.setLayout(menMembershipLayout);
        menMembershipLayout.setHorizontalGroup(
            menMembershipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menMembershipLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menMembershipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kButton14, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(kButton15, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(kButton16, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(kButton17, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                .addContainerGap())
        );
        menMembershipLayout.setVerticalGroup(
            menMembershipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menMembershipLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(kButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menMembership, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(kButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(kButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menMembership, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(230, 72, 72));

        kButton18.setForeground(new java.awt.Color(32, 74, 86));
        kButton18.setText("PRODUK & MERCHANDISE");
        kButton18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        kButton18.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton18.setkBorderRadius(0);
        kButton18.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton18.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton18.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton18.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton18.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton18.setkIndicatorThickness(10);
        kButton18.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton18.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton18ActionPerformed(evt);
            }
        });

        menProduk.setBackground(new java.awt.Color(230, 72, 72));

        kButton19.setForeground(new java.awt.Color(32, 74, 86));
        kButton19.setText("Database Produk");
        kButton19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton19.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton19.setkBorderRadius(30);
        kButton19.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton19.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton19.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton19.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton19.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton19.setkIndicatorThickness(10);
        kButton19.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton19.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton19ActionPerformed(evt);
            }
        });

        kButton20.setForeground(new java.awt.Color(32, 74, 86));
        kButton20.setText("Transaksi Penjualan Produk");
        kButton20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton20.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton20.setkBorderRadius(30);
        kButton20.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton20.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton20.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton20.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton20.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton20.setkIndicatorThickness(10);
        kButton20.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton20.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menProdukLayout = new javax.swing.GroupLayout(menProduk);
        menProduk.setLayout(menProdukLayout);
        menProdukLayout.setHorizontalGroup(
            menProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menProdukLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kButton19, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(kButton20, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                .addContainerGap())
        );
        menProdukLayout.setVerticalGroup(
            menProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menProdukLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(kButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menProduk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(kButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(kButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        kButton21.setForeground(new java.awt.Color(32, 74, 86));
        kButton21.setText("REPORT");
        kButton21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        kButton21.setkBackGroundColor(new java.awt.Color(255, 253, 227));
        kButton21.setkBorderRadius(0);
        kButton21.setkEndColor(new java.awt.Color(230, 72, 72));
        kButton21.setkForeGround(new java.awt.Color(255, 253, 227));
        kButton21.setkHoverEndColor(new java.awt.Color(255, 253, 227));
        kButton21.setkHoverForeGround(new java.awt.Color(255, 253, 227));
        kButton21.setkHoverStartColor(new java.awt.Color(230, 72, 72));
        kButton21.setkIndicatorThickness(10);
        kButton21.setkPressedColor(new java.awt.Color(183, 59, 59));
        kButton21.setkStartColor(new java.awt.Color(230, 72, 72));
        kButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(kButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(kButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 228, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pSideLayout = new javax.swing.GroupLayout(pSide);
        pSide.setLayout(pSideLayout);
        pSideLayout.setHorizontalGroup(
            pSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pSideLayout.setVerticalGroup(
            pSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pSideLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pMain.setLayout(new java.awt.CardLayout());

        jPanel63.setBackground(new java.awt.Color(21, 52, 98));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 253, 227));
        jLabel19.setText("HOME / HOME");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 253, 227));
        jLabel20.setText("Tampilan awal/ dashboard/ pintasan ke menu yang dituju.");

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel64.setBackground(new java.awt.Color(255, 253, 227));

        jPanel8.setBackground(new java.awt.Color(254, 255, 230));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 52, 98), 2));

        jLabel45.setBackground(new java.awt.Color(204, 204, 204));
        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(9, 10, 54));
        jLabel45.setText("NAMA : ");
        jLabel45.setIconTextGap(8);

        jLabel62.setBackground(new java.awt.Color(204, 204, 204));
        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(9, 10, 54));
        jLabel62.setText("NO TELP :");
        jLabel62.setIconTextGap(8);

        jLabel63.setBackground(new java.awt.Color(204, 204, 204));
        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(9, 10, 54));
        jLabel63.setText("EMAIL :");
        jLabel63.setIconTextGap(8);

        jPanel9.setBackground(new java.awt.Color(21, 52, 98));

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 253, 227));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("INFORMASI  USER LOGIN, ID : ");

        txtUserHome.setBackground(new java.awt.Color(204, 204, 204));
        txtUserHome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUserHome.setForeground(new java.awt.Color(255, 253, 227));
        txtUserHome.setText("..");
        txtUserHome.setIconTextGap(8);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUserHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(79, 79, 79))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(txtUserHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel65.setBackground(new java.awt.Color(204, 204, 204));
        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(9, 10, 54));
        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_guest_filled_70px_1.png"))); // NOI18N
        jLabel65.setIconTextGap(8);

        txtNama.setBackground(new java.awt.Color(204, 204, 204));
        txtNama.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtNama.setForeground(new java.awt.Color(9, 10, 54));
        txtNama.setText("..");
        txtNama.setIconTextGap(8);

        txtTelp.setBackground(new java.awt.Color(204, 204, 204));
        txtTelp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTelp.setForeground(new java.awt.Color(9, 10, 54));
        txtTelp.setText("..");
        txtTelp.setIconTextGap(8);

        txtEmail.setBackground(new java.awt.Color(204, 204, 204));
        txtEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(9, 10, 54));
        txtEmail.setText("..");
        txtEmail.setIconTextGap(8);

        jLabel66.setBackground(new java.awt.Color(204, 204, 204));
        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(9, 10, 54));
        jLabel66.setText("TIPE USER : ");
        jLabel66.setIconTextGap(8);

        txtTipeHome.setBackground(new java.awt.Color(204, 204, 204));
        txtTipeHome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTipeHome.setForeground(new java.awt.Color(9, 10, 54));
        txtTipeHome.setText("..");
        txtTipeHome.setIconTextGap(8);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtTipeHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(218, 218, 218))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(68, 68, 68))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtTelp, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTelp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipeHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel10.setBackground(new java.awt.Color(254, 255, 230));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 52, 98), 0));

        txtUserHome1.setBackground(new java.awt.Color(21, 52, 98));
        txtUserHome1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUserHome1.setForeground(new java.awt.Color(255, 253, 227));
        txtUserHome1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtUserHome1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_person_male_100px_1.png"))); // NOI18N
        txtUserHome1.setText("REGISTRASI MEMBER");
        txtUserHome1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtUserHome1.setIconTextGap(8);
        txtUserHome1.setOpaque(true);
        txtUserHome1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserHome1MouseClicked(evt);
            }
        });

        txtUserHome2.setBackground(new java.awt.Color(21, 52, 98));
        txtUserHome2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUserHome2.setForeground(new java.awt.Color(255, 253, 227));
        txtUserHome2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtUserHome2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_check_100px.png"))); // NOI18N
        txtUserHome2.setText("PENDAFTARAN MEMBERSHIP");
        txtUserHome2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtUserHome2.setIconTextGap(8);
        txtUserHome2.setOpaque(true);
        txtUserHome2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserHome2MouseClicked(evt);
            }
        });

        txtUserHome3.setBackground(new java.awt.Color(21, 52, 98));
        txtUserHome3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUserHome3.setForeground(new java.awt.Color(255, 253, 227));
        txtUserHome3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtUserHome3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_todo_list_100px.png"))); // NOI18N
        txtUserHome3.setText("CHECK-IN MEMBER");
        txtUserHome3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtUserHome3.setIconTextGap(8);
        txtUserHome3.setOpaque(true);
        txtUserHome3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserHome3MouseClicked(evt);
            }
        });

        txtUserHome4.setBackground(new java.awt.Color(21, 52, 98));
        txtUserHome4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUserHome4.setForeground(new java.awt.Color(255, 253, 227));
        txtUserHome4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtUserHome4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_dancing_100px.png"))); // NOI18N
        txtUserHome4.setText("ABSENSI KELAS");
        txtUserHome4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtUserHome4.setIconTextGap(8);
        txtUserHome4.setOpaque(true);
        txtUserHome4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserHome4MouseClicked(evt);
            }
        });

        txtUserHome5.setBackground(new java.awt.Color(21, 52, 98));
        txtUserHome5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUserHome5.setForeground(new java.awt.Color(255, 253, 227));
        txtUserHome5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtUserHome5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_cash_register_100px.png"))); // NOI18N
        txtUserHome5.setText("TRANSAKSI PRODUK");
        txtUserHome5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtUserHome5.setIconTextGap(8);
        txtUserHome5.setOpaque(true);
        txtUserHome5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserHome5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(txtUserHome1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtUserHome2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(txtUserHome3, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserHome4, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserHome5, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserHome1)
                    .addComponent(txtUserHome2))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUserHome5, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(txtUserHome4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUserHome3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel64Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel64Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pHomeLayout = new javax.swing.GroupLayout(pHome);
        pHome.setLayout(pHomeLayout);
        pHomeLayout.setHorizontalGroup(
            pHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pHomeLayout.setVerticalGroup(
            pHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pHomeLayout.createSequentialGroup()
                .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pMain.add(pHome, "pHome");

        jPanel66.setBackground(new java.awt.Color(21, 52, 98));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 253, 227));
        jLabel21.setText("MEMBERSHIP  / IDENTITAS  MEMBER");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 253, 227));
        jLabel22.setText("Tambah, Ubah Dan Hapus Data Master Identitas Member Gym Di Dalam Database Aplikasi ___________");

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel21))
                .addContainerGap(372, Short.MAX_VALUE))
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel67.setBackground(new java.awt.Color(255, 253, 227));

        tabMember.setBackground(new java.awt.Color(254, 255, 230));
        tabMember.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabMember.setForeground(new java.awt.Color(5, 32, 56));
        tabMember.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabMember.setOpaque(false);
        tabMember.setRowHeight(25);
        tabMember.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tabMember.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tabMember.getTableHeader().setReorderingAllowed(false);
        tabMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMemberMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabMember);

        txtMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtMember.setForeground(new java.awt.Color(5, 32, 56));
        txtMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMemberActionPerformed(evt);
            }
        });
        txtMember.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMemberKeyPressed(evt);
            }
        });

        jPanel18.setBackground(new java.awt.Color(67, 131, 113));

        bAddMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bAddMember.setForeground(new java.awt.Color(231, 238, 126));
        bAddMember.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        bAddMember.setText("TAMBAH");
        bAddMember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAddMember.setIconTextGap(8);
        bAddMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAddMemberMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAddMember, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bAddMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel19.setBackground(new java.awt.Color(162, 34, 39));

        bDelMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bDelMember.setForeground(new java.awt.Color(231, 238, 126));
        bDelMember.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        bDelMember.setText("   HAPUS");
        bDelMember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bDelMember.setIconTextGap(8);
        bDelMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bDelMemberMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bDelMember, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bDelMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel20.setBackground(new java.awt.Color(5, 32, 56));

        bCariMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCariMember.setForeground(new java.awt.Color(231, 238, 126));
        bCariMember.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        bCariMember.setText("CARI/REFRESH");
        bCariMember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCariMember.setIconTextGap(8);
        bCariMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCariMemberMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bCariMember, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bCariMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel21.setBackground(new java.awt.Color(5, 32, 56));

        bEditMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bEditMember.setForeground(new java.awt.Color(231, 238, 126));
        bEditMember.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        bEditMember.setText("     EDIT");
        bEditMember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bEditMember.setIconTextGap(8);
        bEditMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bEditMemberMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bEditMember, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bEditMember, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel67Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel67Layout.createSequentialGroup()
                        .addComponent(txtMember, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(253, 253, 253)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel67Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMember, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout pMemberLayout = new javax.swing.GroupLayout(pMember);
        pMember.setLayout(pMemberLayout);
        pMemberLayout.setHorizontalGroup(
            pMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pMemberLayout.setVerticalGroup(
            pMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMemberLayout.createSequentialGroup()
                .addComponent(jPanel66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pMain.add(pMember, "pMember");

        jPanel68.setBackground(new java.awt.Color(21, 52, 98));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 253, 227));
        jLabel23.setText("MEMBERSHIP /  TIPE MEMBERSHIP");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 253, 227));
        jLabel24.setText("Tambah, Ubah Dan Hapus Data Master Tipe Membership Gym Di Dalam Database Aplikasi ___________");

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23))
                .addContainerGap(373, Short.MAX_VALUE))
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel69.setBackground(new java.awt.Color(255, 253, 227));

        tabTipe.setBackground(new java.awt.Color(254, 255, 230));
        tabTipe.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabTipe.setForeground(new java.awt.Color(5, 32, 56));
        tabTipe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabTipe.setOpaque(false);
        tabTipe.setRowHeight(25);
        tabTipe.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tabTipe.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tabTipe.getTableHeader().setReorderingAllowed(false);
        tabTipe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabTipeMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tabTipe);

        txtTipe.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTipe.setForeground(new java.awt.Color(5, 32, 56));
        txtTipe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipeActionPerformed(evt);
            }
        });
        txtTipe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTipeKeyPressed(evt);
            }
        });

        jPanel22.setBackground(new java.awt.Color(67, 131, 113));

        bAddTipe.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bAddTipe.setForeground(new java.awt.Color(231, 238, 126));
        bAddTipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        bAddTipe.setText("TAMBAH");
        bAddTipe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAddTipe.setIconTextGap(8);
        bAddTipe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAddTipeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAddTipe, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bAddTipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel23.setBackground(new java.awt.Color(162, 34, 39));

        bDelTipe.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bDelTipe.setForeground(new java.awt.Color(231, 238, 126));
        bDelTipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        bDelTipe.setText("   HAPUS");
        bDelTipe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bDelTipe.setIconTextGap(8);
        bDelTipe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bDelTipeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bDelTipe, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bDelTipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel24.setBackground(new java.awt.Color(5, 32, 56));

        bCariTipe.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCariTipe.setForeground(new java.awt.Color(231, 238, 126));
        bCariTipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        bCariTipe.setText("CARI/REFRESH");
        bCariTipe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCariTipe.setIconTextGap(8);
        bCariTipe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCariTipeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bCariTipe, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bCariTipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel25.setBackground(new java.awt.Color(5, 32, 56));

        bEditTipe.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bEditTipe.setForeground(new java.awt.Color(231, 238, 126));
        bEditTipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        bEditTipe.setText("     EDIT");
        bEditTipe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bEditTipe.setIconTextGap(8);
        bEditTipe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bEditTipeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bEditTipe, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bEditTipe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel69Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel69Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel69Layout.createSequentialGroup()
                        .addComponent(txtTipe, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(253, 253, 253)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))))
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel69Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTipe, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout pTipeLayout = new javax.swing.GroupLayout(pTipe);
        pTipe.setLayout(pTipeLayout);
        pTipeLayout.setHorizontalGroup(
            pTipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pTipeLayout.setVerticalGroup(
            pTipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTipeLayout.createSequentialGroup()
                .addComponent(jPanel68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pMain.add(pTipe, "pTipe");

        jPanel70.setBackground(new java.awt.Color(21, 52, 98));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 253, 227));
        jLabel25.setText("MANAGEMENT  /  USER");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 253, 227));
        jLabel26.setText("Tambah, Ubah Dan Hapus Data Master USER Untuk Dapat Akses Ke Aplikasi ___________");

        javax.swing.GroupLayout jPanel70Layout = new javax.swing.GroupLayout(jPanel70);
        jPanel70.setLayout(jPanel70Layout);
        jPanel70Layout.setHorizontalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25))
                .addContainerGap(472, Short.MAX_VALUE))
        );
        jPanel70Layout.setVerticalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel71.setBackground(new java.awt.Color(255, 253, 227));

        tabUser.setBackground(new java.awt.Color(254, 255, 230));
        tabUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabUser.setForeground(new java.awt.Color(5, 32, 56));
        tabUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabUser.setOpaque(false);
        tabUser.setRowHeight(25);
        tabUser.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tabUser.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tabUser.getTableHeader().setReorderingAllowed(false);
        tabUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabUserMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tabUser);

        txtUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtUser.setForeground(new java.awt.Color(5, 32, 56));
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });
        txtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserKeyPressed(evt);
            }
        });

        jPanel26.setBackground(new java.awt.Color(67, 131, 113));

        bAddUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bAddUser.setForeground(new java.awt.Color(231, 238, 126));
        bAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        bAddUser.setText("TAMBAH");
        bAddUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAddUser.setIconTextGap(8);
        bAddUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAddUserMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAddUser, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bAddUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel27.setBackground(new java.awt.Color(162, 34, 39));

        bDelUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bDelUser.setForeground(new java.awt.Color(231, 238, 126));
        bDelUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        bDelUser.setText("   HAPUS");
        bDelUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bDelUser.setIconTextGap(8);
        bDelUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bDelUserMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bDelUser, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bDelUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel28.setBackground(new java.awt.Color(5, 32, 56));

        bCariUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCariUser.setForeground(new java.awt.Color(231, 238, 126));
        bCariUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        bCariUser.setText("CARI/REFRESH");
        bCariUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCariUser.setIconTextGap(8);
        bCariUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCariUserMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bCariUser, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bCariUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel29.setBackground(new java.awt.Color(5, 32, 56));

        bEditUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bEditUser.setForeground(new java.awt.Color(231, 238, 126));
        bEditUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        bEditUser.setText("     EDIT");
        bEditUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bEditUser.setIconTextGap(8);
        bEditUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bEditUserMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bEditUser, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bEditUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel71Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel71Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel71Layout.createSequentialGroup()
                        .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(253, 253, 253)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))))
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel71Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUser, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout pUserLayout = new javax.swing.GroupLayout(pUser);
        pUser.setLayout(pUserLayout);
        pUserLayout.setHorizontalGroup(
            pUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pUserLayout.setVerticalGroup(
            pUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pUserLayout.createSequentialGroup()
                .addComponent(jPanel70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pMain.add(pUser, "pUser");

        jPanel72.setBackground(new java.awt.Color(21, 52, 98));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 253, 227));
        jLabel27.setText("MANAGEMENT  /  INSTRUKTUR & TRAINER");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 253, 227));
        jLabel28.setText("Tambah, Ubah Dan Hapus Data Master Identitas Instruktur dan Trainer Gym Di Dalam Database Aplikasi ___________");

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27))
                .addContainerGap(284, Short.MAX_VALUE))
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel73.setBackground(new java.awt.Color(255, 253, 227));

        tabTrainer.setBackground(new java.awt.Color(254, 255, 230));
        tabTrainer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabTrainer.setForeground(new java.awt.Color(5, 32, 56));
        tabTrainer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabTrainer.setOpaque(false);
        tabTrainer.setRowHeight(25);
        tabTrainer.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tabTrainer.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tabTrainer.getTableHeader().setReorderingAllowed(false);
        tabTrainer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabTrainerMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tabTrainer);

        txtTrainer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTrainer.setForeground(new java.awt.Color(5, 32, 56));
        txtTrainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrainerActionPerformed(evt);
            }
        });
        txtTrainer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTrainerKeyPressed(evt);
            }
        });

        jPanel30.setBackground(new java.awt.Color(67, 131, 113));

        bAddTrainer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bAddTrainer.setForeground(new java.awt.Color(231, 238, 126));
        bAddTrainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        bAddTrainer.setText("TAMBAH");
        bAddTrainer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAddTrainer.setIconTextGap(8);
        bAddTrainer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAddTrainerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAddTrainer, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bAddTrainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel31.setBackground(new java.awt.Color(162, 34, 39));

        bDelTrainer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bDelTrainer.setForeground(new java.awt.Color(231, 238, 126));
        bDelTrainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        bDelTrainer.setText("   HAPUS");
        bDelTrainer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bDelTrainer.setIconTextGap(8);
        bDelTrainer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bDelTrainerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bDelTrainer, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bDelTrainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel32.setBackground(new java.awt.Color(5, 32, 56));

        bCariTrainer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCariTrainer.setForeground(new java.awt.Color(231, 238, 126));
        bCariTrainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        bCariTrainer.setText("CARI/REFRESH");
        bCariTrainer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCariTrainer.setIconTextGap(8);
        bCariTrainer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCariTrainerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bCariTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bCariTrainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel33.setBackground(new java.awt.Color(5, 32, 56));

        bEditTrainer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bEditTrainer.setForeground(new java.awt.Color(231, 238, 126));
        bEditTrainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        bEditTrainer.setText("     EDIT");
        bEditTrainer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bEditTrainer.setIconTextGap(8);
        bEditTrainer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bEditTrainerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bEditTrainer, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bEditTrainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel73Layout = new javax.swing.GroupLayout(jPanel73);
        jPanel73.setLayout(jPanel73Layout);
        jPanel73Layout.setHorizontalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel73Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel73Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel73Layout.createSequentialGroup()
                        .addComponent(txtTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(253, 253, 253)
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))))
        );
        jPanel73Layout.setVerticalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel73Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTrainer, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout pTrainerLayout = new javax.swing.GroupLayout(pTrainer);
        pTrainer.setLayout(pTrainerLayout);
        pTrainerLayout.setHorizontalGroup(
            pTrainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pTrainerLayout.setVerticalGroup(
            pTrainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTrainerLayout.createSequentialGroup()
                .addComponent(jPanel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pMain.add(pTrainer, "pTrainer");

        jPanel74.setBackground(new java.awt.Color(21, 52, 98));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 253, 227));
        jLabel29.setText("KELAS GYM  /  JENIS KELAS GYM");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 253, 227));
        jLabel30.setText("Tambah, Ubah Dan Hapus Data Master Kelas Gym Di Dalam Database Aplikasi ___________");

        javax.swing.GroupLayout jPanel74Layout = new javax.swing.GroupLayout(jPanel74);
        jPanel74.setLayout(jPanel74Layout);
        jPanel74Layout.setHorizontalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel74Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29))
                .addContainerGap(455, Short.MAX_VALUE))
        );
        jPanel74Layout.setVerticalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel74Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel75.setBackground(new java.awt.Color(255, 253, 227));

        tabKelas.setBackground(new java.awt.Color(254, 255, 230));
        tabKelas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabKelas.setForeground(new java.awt.Color(5, 32, 56));
        tabKelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabKelas.setOpaque(false);
        tabKelas.setRowHeight(25);
        tabKelas.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tabKelas.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tabKelas.getTableHeader().setReorderingAllowed(false);
        tabKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabKelasMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tabKelas);

        txtKelas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtKelas.setForeground(new java.awt.Color(5, 32, 56));
        txtKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKelasActionPerformed(evt);
            }
        });
        txtKelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKelasKeyPressed(evt);
            }
        });

        jPanel34.setBackground(new java.awt.Color(67, 131, 113));

        bAddKelas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bAddKelas.setForeground(new java.awt.Color(231, 238, 126));
        bAddKelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        bAddKelas.setText("TAMBAH");
        bAddKelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAddKelas.setIconTextGap(8);
        bAddKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAddKelasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAddKelas, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bAddKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel35.setBackground(new java.awt.Color(162, 34, 39));

        bDelKelas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bDelKelas.setForeground(new java.awt.Color(231, 238, 126));
        bDelKelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        bDelKelas.setText("   HAPUS");
        bDelKelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bDelKelas.setIconTextGap(8);
        bDelKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bDelKelasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bDelKelas, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bDelKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel36.setBackground(new java.awt.Color(5, 32, 56));

        bCariKelas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCariKelas.setForeground(new java.awt.Color(231, 238, 126));
        bCariKelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        bCariKelas.setText("CARI/REFRESH");
        bCariKelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCariKelas.setIconTextGap(8);
        bCariKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCariKelasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bCariKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bCariKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel37.setBackground(new java.awt.Color(5, 32, 56));

        bEditKelas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bEditKelas.setForeground(new java.awt.Color(231, 238, 126));
        bEditKelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        bEditKelas.setText("     EDIT");
        bEditKelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bEditKelas.setIconTextGap(8);
        bEditKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bEditKelasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bEditKelas, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bEditKelas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel75Layout = new javax.swing.GroupLayout(jPanel75);
        jPanel75.setLayout(jPanel75Layout);
        jPanel75Layout.setHorizontalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel75Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel75Layout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel75Layout.createSequentialGroup()
                        .addComponent(txtKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(253, 253, 253)
                        .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))))
        );
        jPanel75Layout.setVerticalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel75Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtKelas, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout pKelasLayout = new javax.swing.GroupLayout(pKelas);
        pKelas.setLayout(pKelasLayout);
        pKelasLayout.setHorizontalGroup(
            pKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pKelasLayout.setVerticalGroup(
            pKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pKelasLayout.createSequentialGroup()
                .addComponent(jPanel74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pMain.add(pKelas, "pKelas");

        jPanel76.setBackground(new java.awt.Color(21, 52, 98));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 253, 227));
        jLabel31.setText("KELAS GYM  /  JADWAL KELAS GYM");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 253, 227));
        jLabel32.setText("Tambah, Ubah Dan Hapus Data Jadwal Kelas Gym Mendatang Di Dalam Database Aplikasi ___________");

        javax.swing.GroupLayout jPanel76Layout = new javax.swing.GroupLayout(jPanel76);
        jPanel76.setLayout(jPanel76Layout);
        jPanel76Layout.setHorizontalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31))
                .addContainerGap(375, Short.MAX_VALUE))
        );
        jPanel76Layout.setVerticalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel77.setBackground(new java.awt.Color(255, 253, 227));

        tabJadwal.setBackground(new java.awt.Color(254, 255, 230));
        tabJadwal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabJadwal.setForeground(new java.awt.Color(5, 32, 56));
        tabJadwal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabJadwal.setOpaque(false);
        tabJadwal.setRowHeight(25);
        tabJadwal.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tabJadwal.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tabJadwal.getTableHeader().setReorderingAllowed(false);
        tabJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabJadwalMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tabJadwal);

        txtJadwal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtJadwal.setForeground(new java.awt.Color(5, 32, 56));
        txtJadwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJadwalActionPerformed(evt);
            }
        });
        txtJadwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtJadwalKeyPressed(evt);
            }
        });

        jPanel38.setBackground(new java.awt.Color(67, 131, 113));

        bAddJadwal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bAddJadwal.setForeground(new java.awt.Color(231, 238, 126));
        bAddJadwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        bAddJadwal.setText("TAMBAH");
        bAddJadwal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAddJadwal.setIconTextGap(8);
        bAddJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAddJadwalMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAddJadwal, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bAddJadwal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel39.setBackground(new java.awt.Color(162, 34, 39));

        bDelJadwal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bDelJadwal.setForeground(new java.awt.Color(231, 238, 126));
        bDelJadwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        bDelJadwal.setText("   HAPUS");
        bDelJadwal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bDelJadwal.setIconTextGap(8);
        bDelJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bDelJadwalMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bDelJadwal, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bDelJadwal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel40.setBackground(new java.awt.Color(5, 32, 56));

        bCariJadwal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCariJadwal.setForeground(new java.awt.Color(231, 238, 126));
        bCariJadwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        bCariJadwal.setText("CARI/REFRESH");
        bCariJadwal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCariJadwal.setIconTextGap(8);
        bCariJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCariJadwalMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bCariJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bCariJadwal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel41.setBackground(new java.awt.Color(5, 32, 56));

        bEditJadwal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bEditJadwal.setForeground(new java.awt.Color(231, 238, 126));
        bEditJadwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        bEditJadwal.setText("     EDIT");
        bEditJadwal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bEditJadwal.setIconTextGap(8);
        bEditJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bEditJadwalMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bEditJadwal, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bEditJadwal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel77Layout = new javax.swing.GroupLayout(jPanel77);
        jPanel77.setLayout(jPanel77Layout);
        jPanel77Layout.setHorizontalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel77Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel77Layout.createSequentialGroup()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel77Layout.createSequentialGroup()
                        .addComponent(txtJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(253, 253, 253)
                        .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))))
        );
        jPanel77Layout.setVerticalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel77Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtJadwal, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout pJadwalKelasLayout = new javax.swing.GroupLayout(pJadwalKelas);
        pJadwalKelas.setLayout(pJadwalKelasLayout);
        pJadwalKelasLayout.setHorizontalGroup(
            pJadwalKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pJadwalKelasLayout.setVerticalGroup(
            pJadwalKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pJadwalKelasLayout.createSequentialGroup()
                .addComponent(jPanel76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pMain.add(pJadwalKelas, "pJadwalKelas");

        jPanel78.setBackground(new java.awt.Color(21, 52, 98));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 253, 227));
        jLabel33.setText("PRODUK & MERCHANDISE /  DATABASE PRODUK");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 253, 227));
        jLabel34.setText("Tambah, Ubah Dan Hapus Data Produk & Merchandise Di Dalam Database Aplikasi ___________");

        javax.swing.GroupLayout jPanel78Layout = new javax.swing.GroupLayout(jPanel78);
        jPanel78.setLayout(jPanel78Layout);
        jPanel78Layout.setHorizontalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel78Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33))
                .addContainerGap(421, Short.MAX_VALUE))
        );
        jPanel78Layout.setVerticalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel78Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel79.setBackground(new java.awt.Color(255, 253, 227));

        tabProduk.setBackground(new java.awt.Color(254, 255, 230));
        tabProduk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabProduk.setForeground(new java.awt.Color(5, 32, 56));
        tabProduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabProduk.setOpaque(false);
        tabProduk.setRowHeight(25);
        tabProduk.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tabProduk.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tabProduk.getTableHeader().setReorderingAllowed(false);
        tabProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabProdukMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tabProduk);

        txtProduk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtProduk.setForeground(new java.awt.Color(5, 32, 56));
        txtProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProdukActionPerformed(evt);
            }
        });
        txtProduk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProdukKeyPressed(evt);
            }
        });

        jPanel42.setBackground(new java.awt.Color(67, 131, 113));

        bAddProduk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bAddProduk.setForeground(new java.awt.Color(231, 238, 126));
        bAddProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        bAddProduk.setText("TAMBAH");
        bAddProduk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAddProduk.setIconTextGap(8);
        bAddProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAddProdukMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAddProduk, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bAddProduk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel43.setBackground(new java.awt.Color(162, 34, 39));

        bDelProduk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bDelProduk.setForeground(new java.awt.Color(231, 238, 126));
        bDelProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        bDelProduk.setText("   HAPUS");
        bDelProduk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bDelProduk.setIconTextGap(8);
        bDelProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bDelProdukMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bDelProduk, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bDelProduk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel44.setBackground(new java.awt.Color(5, 32, 56));

        bCariProduk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCariProduk.setForeground(new java.awt.Color(231, 238, 126));
        bCariProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        bCariProduk.setText("CARI/REFRESH");
        bCariProduk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCariProduk.setIconTextGap(8);
        bCariProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCariProdukMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bCariProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bCariProduk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel45.setBackground(new java.awt.Color(5, 32, 56));

        bEditProduk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bEditProduk.setForeground(new java.awt.Color(231, 238, 126));
        bEditProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        bEditProduk.setText("     EDIT");
        bEditProduk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bEditProduk.setIconTextGap(8);
        bEditProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bEditProdukMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bEditProduk, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bEditProduk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel79Layout = new javax.swing.GroupLayout(jPanel79);
        jPanel79.setLayout(jPanel79Layout);
        jPanel79Layout.setHorizontalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel79Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel79Layout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel79Layout.createSequentialGroup()
                        .addComponent(txtProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(253, 253, 253)
                        .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))))
        );
        jPanel79Layout.setVerticalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel79Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProduk, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout pProdukLayout = new javax.swing.GroupLayout(pProduk);
        pProduk.setLayout(pProdukLayout);
        pProdukLayout.setHorizontalGroup(
            pProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pProdukLayout.setVerticalGroup(
            pProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pProdukLayout.createSequentialGroup()
                .addComponent(jPanel78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pMain.add(pProduk, "pProduk");

        pReport.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header8.setBackground(new java.awt.Color(21, 52, 98));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 253, 227));
        jLabel35.setText("R E P O R T S");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 253, 227));
        jLabel36.setText("Mencetak data - data yang sudah terekam masuk kedalam database ___________");

        javax.swing.GroupLayout p_perusahaan_header8Layout = new javax.swing.GroupLayout(p_perusahaan_header8);
        p_perusahaan_header8.setLayout(p_perusahaan_header8Layout);
        p_perusahaan_header8Layout.setHorizontalGroup(
            p_perusahaan_header8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header8Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jLabel35))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header8Layout.setVerticalGroup(
            p_perusahaan_header8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header8Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main8.setBackground(new java.awt.Color(254, 255, 230));

        jPanel3.setBackground(new java.awt.Color(254, 255, 230));

        jPanel7.setBackground(new java.awt.Color(254, 255, 230));

        jLabel37.setBackground(new java.awt.Color(32, 74, 86));
        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(32, 74, 86));
        jLabel37.setText("CETAK DATA IDENTITAS TRAINER");

        jLabel38.setBackground(new java.awt.Color(32, 74, 86));
        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(32, 74, 86));
        jLabel38.setText("CETAK IDENTITAS MEMBER TERDAFTAR");

        jLabel39.setBackground(new java.awt.Color(32, 74, 86));
        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(32, 74, 86));
        jLabel39.setText("CETAK LAPORAN PENJUALAN PRODUK, PILIH MEMBER");

        cbLapMember.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cbLapMember.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel68.setBackground(new java.awt.Color(32, 74, 86));
        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(32, 74, 86));
        jLabel68.setText("CETAK PENDAFTARAN MEMBERSHIP PERIODE");

        jLabel70.setBackground(new java.awt.Color(32, 74, 86));
        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(32, 74, 86));
        jLabel70.setText("PILIH ID KELAS :");

        cbAbsensiKelas.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cbAbsensiKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih Department -" }));
        cbAbsensiKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbAbsensiKelasMouseClicked(evt);
            }
        });
        cbAbsensiKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAbsensiKelasActionPerformed(evt);
            }
        });

        jLabel78.setBackground(new java.awt.Color(32, 74, 86));
        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(32, 74, 86));
        jLabel78.setText("PILIH PERIODE :");

        jLabel80.setBackground(new java.awt.Color(32, 74, 86));
        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(32, 74, 86));
        jLabel80.setText("CETAK DATA IDENTITAS USER LOGIN");

        jLabel82.setBackground(new java.awt.Color(32, 74, 86));
        jLabel82.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(32, 74, 86));
        jLabel82.setText("CETAK LOGS PERUBAHAN HARGA TRAINER");

        jLabel83.setBackground(new java.awt.Color(32, 74, 86));
        jLabel83.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(32, 74, 86));
        jLabel83.setText("CETAK LAPORAN PENDAPATAN MEMBERSHIP");

        jLabel84.setBackground(new java.awt.Color(32, 74, 86));
        jLabel84.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(32, 74, 86));
        jLabel84.setText("CETAK LAPORAN PENDAPATAN PENJUALAN PRODUK");

        jLabel79.setBackground(new java.awt.Color(32, 74, 86));
        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(32, 74, 86));
        jLabel79.setText("SAMPAI DENGAN");

        jLabel86.setBackground(new java.awt.Color(32, 74, 86));
        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(32, 74, 86));
        jLabel86.setText("PILIH PERIODE :");

        jLabel87.setBackground(new java.awt.Color(32, 74, 86));
        jLabel87.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(32, 74, 86));
        jLabel87.setText("PILIH PERIODE :");

        repTrainer.setBackground(new java.awt.Color(21, 52, 98));
        repTrainer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repTrainer.setForeground(new java.awt.Color(255, 253, 227));
        repTrainer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repTrainer.setText("C E T A K");
        repTrainer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repTrainer.setIconTextGap(8);
        repTrainer.setOpaque(true);
        repTrainer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repTrainerMouseClicked(evt);
            }
        });

        repHargaTrainer.setBackground(new java.awt.Color(21, 52, 98));
        repHargaTrainer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repHargaTrainer.setForeground(new java.awt.Color(255, 253, 227));
        repHargaTrainer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repHargaTrainer.setText("C E T A K");
        repHargaTrainer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repHargaTrainer.setIconTextGap(8);
        repHargaTrainer.setOpaque(true);
        repHargaTrainer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repHargaTrainerMouseClicked(evt);
            }
        });

        repUser.setBackground(new java.awt.Color(21, 52, 98));
        repUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repUser.setForeground(new java.awt.Color(255, 253, 227));
        repUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repUser.setText("C E T A K");
        repUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repUser.setIconTextGap(8);
        repUser.setOpaque(true);
        repUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repUserMouseClicked(evt);
            }
        });

        jLabel41.setBackground(new java.awt.Color(32, 74, 86));
        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(32, 74, 86));
        jLabel41.setText("CETAK DATA INFORMASI KELAS");

        repKelas.setBackground(new java.awt.Color(21, 52, 98));
        repKelas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repKelas.setForeground(new java.awt.Color(255, 253, 227));
        repKelas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repKelas.setText("C E T A K");
        repKelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repKelas.setIconTextGap(8);
        repKelas.setOpaque(true);
        repKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repKelasMouseClicked(evt);
            }
        });

        jLabel42.setBackground(new java.awt.Color(32, 74, 86));
        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(32, 74, 86));
        jLabel42.setText("CETAK JADWAL PELAKSANAAN KELAS");

        repJadwalKelas.setBackground(new java.awt.Color(21, 52, 98));
        repJadwalKelas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repJadwalKelas.setForeground(new java.awt.Color(255, 253, 227));
        repJadwalKelas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repJadwalKelas.setText("C E T A K");
        repJadwalKelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repJadwalKelas.setIconTextGap(8);
        repJadwalKelas.setOpaque(true);
        repJadwalKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repJadwalKelasMouseClicked(evt);
            }
        });

        jLabel43.setBackground(new java.awt.Color(32, 74, 86));
        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(32, 74, 86));
        jLabel43.setText("CETAK ABSENSI KELAS");

        repAbsensiKelas.setBackground(new java.awt.Color(21, 52, 98));
        repAbsensiKelas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repAbsensiKelas.setForeground(new java.awt.Color(255, 253, 227));
        repAbsensiKelas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repAbsensiKelas.setText("C E T A K");
        repAbsensiKelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repAbsensiKelas.setIconTextGap(8);
        repAbsensiKelas.setOpaque(true);
        repAbsensiKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repAbsensiKelasMouseClicked(evt);
            }
        });

        repMember.setBackground(new java.awt.Color(21, 52, 98));
        repMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repMember.setForeground(new java.awt.Color(255, 253, 227));
        repMember.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repMember.setText("C E T A K");
        repMember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repMember.setIconTextGap(8);
        repMember.setOpaque(true);
        repMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repMemberMouseClicked(evt);
            }
        });

        jLabel44.setBackground(new java.awt.Color(32, 74, 86));
        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(32, 74, 86));
        jLabel44.setText("CETAK LAPORAN CHECKIN MEMBER PERIODE :");

        date_mulai.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                date_mulaiPropertyChange(evt);
            }
        });

        jLabel89.setBackground(new java.awt.Color(32, 74, 86));
        jLabel89.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(32, 74, 86));
        jLabel89.setText("PILIH PERIODE :");

        date_selesai.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                date_selesaiPropertyChange(evt);
            }
        });

        repCheckin.setBackground(new java.awt.Color(21, 52, 98));
        repCheckin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repCheckin.setForeground(new java.awt.Color(255, 253, 227));
        repCheckin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repCheckin.setText("C E T A K");
        repCheckin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repCheckin.setIconTextGap(8);
        repCheckin.setOpaque(true);
        repCheckin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repCheckinMouseClicked(evt);
            }
        });

        repMembership.setBackground(new java.awt.Color(21, 52, 98));
        repMembership.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repMembership.setForeground(new java.awt.Color(255, 253, 227));
        repMembership.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repMembership.setText("C E T A K");
        repMembership.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repMembership.setIconTextGap(8);
        repMembership.setOpaque(true);
        repMembership.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repMembershipMouseClicked(evt);
            }
        });

        repPenjualan.setBackground(new java.awt.Color(21, 52, 98));
        repPenjualan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repPenjualan.setForeground(new java.awt.Color(255, 253, 227));
        repPenjualan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repPenjualan.setText("C E T A K");
        repPenjualan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repPenjualan.setIconTextGap(8);
        repPenjualan.setOpaque(true);
        repPenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repPenjualanMouseClicked(evt);
            }
        });

        repIncomeMembership.setBackground(new java.awt.Color(21, 52, 98));
        repIncomeMembership.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repIncomeMembership.setForeground(new java.awt.Color(255, 253, 227));
        repIncomeMembership.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repIncomeMembership.setText("C E T A K");
        repIncomeMembership.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repIncomeMembership.setIconTextGap(8);
        repIncomeMembership.setOpaque(true);
        repIncomeMembership.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repIncomeMembershipMouseClicked(evt);
            }
        });

        repIncomeProduk.setBackground(new java.awt.Color(21, 52, 98));
        repIncomeProduk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repIncomeProduk.setForeground(new java.awt.Color(255, 253, 227));
        repIncomeProduk.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repIncomeProduk.setText("C E T A K");
        repIncomeProduk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repIncomeProduk.setIconTextGap(8);
        repIncomeProduk.setOpaque(true);
        repIncomeProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repIncomeProdukMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1027, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel38)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(repMember, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(membershipMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(membershipYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(repMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(repTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel82)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(repHargaTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel80)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(repUser, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel39)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbLapMember, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel89)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(memberMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(memberYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(repPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                            .addComponent(jLabel84)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel87)
                                            .addGap(18, 18, 18)
                                            .addComponent(penProdukMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(penProdukYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(repIncomeProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(24, 24, 24)))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel44)
                                        .addGap(18, 18, 18)
                                        .addComponent(date_mulai, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel79)
                                        .addGap(12, 12, 12)
                                        .addComponent(date_selesai, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(repCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                            .addComponent(jLabel83)
                                            .addGap(20, 20, 20)
                                            .addComponent(jLabel86)
                                            .addGap(18, 18, 18)
                                            .addComponent(penMemshipMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(penMemshipYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(repIncomeMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(24, 24, 24)))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel41)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(repKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel42)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel78)
                                        .addGap(18, 18, 18)
                                        .addComponent(jadwalKelasMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jadwalKelasYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(repJadwalKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel43)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel70)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbAbsensiKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(repAbsensiKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 839, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(repTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(repHargaTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(repUser, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(repKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jadwalKelasYear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel78, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jadwalKelasMonth, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(repJadwalKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbAbsensiKelas)
                        .addComponent(repAbsensiKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(membershipYear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(membershipMonth, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(repMember, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(date_mulai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(repCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(memberYear, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(memberMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(cbLapMember, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addComponent(repPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(penMemshipYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(penMemshipMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(repIncomeMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(penProdukYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(penProdukMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(repIncomeProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(107, 107, 107))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(200, 200, 200))))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(date_selesai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(repMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(315, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout p_perusahaan_main8Layout = new javax.swing.GroupLayout(p_perusahaan_main8);
        p_perusahaan_main8.setLayout(p_perusahaan_main8Layout);
        p_perusahaan_main8Layout.setHorizontalGroup(
            p_perusahaan_main8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p_perusahaan_main8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        p_perusahaan_main8Layout.setVerticalGroup(
            p_perusahaan_main8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main8Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pReportLayout = new javax.swing.GroupLayout(pReport);
        pReport.setLayout(pReportLayout);
        pReportLayout.setHorizontalGroup(
            pReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pReportLayout.setVerticalGroup(
            pReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pReportLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pMain.add(pReport, "pReport");

        jPanel82.setBackground(new java.awt.Color(21, 52, 98));

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 253, 227));
        jLabel51.setText("MEMBERSHIP  /  PENDAFTARAN MEMBERSHIP GYM");

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 253, 227));
        jLabel52.setText("Tambah dan Cetak Struk Transaksi Pendaftaran Membership GYM Dalam Database Aplikasi ___________");

        javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
        jPanel82.setLayout(jPanel82Layout);
        jPanel82Layout.setHorizontalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel82Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel52)
                    .addComponent(jLabel51))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel82Layout.setVerticalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel82Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        jPanel83.setBackground(new java.awt.Color(255, 253, 227));

        tabMembership.setBackground(new java.awt.Color(254, 255, 230));
        tabMembership.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabMembership.setForeground(new java.awt.Color(5, 32, 56));
        tabMembership.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabMembership.setOpaque(false);
        tabMembership.setRowHeight(25);
        tabMembership.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tabMembership.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tabMembership.getTableHeader().setReorderingAllowed(false);
        tabMembership.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMembershipMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tabMembership);

        txtMembership.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtMembership.setForeground(new java.awt.Color(5, 32, 56));
        txtMembership.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMembershipActionPerformed(evt);
            }
        });
        txtMembership.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMembershipKeyPressed(evt);
            }
        });

        jPanel50.setBackground(new java.awt.Color(67, 131, 113));

        bAddMembership.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bAddMembership.setForeground(new java.awt.Color(231, 238, 126));
        bAddMembership.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        bAddMembership.setText("TAMBAH");
        bAddMembership.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAddMembership.setIconTextGap(8);
        bAddMembership.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAddMembershipMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAddMembership, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bAddMembership, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel52.setBackground(new java.awt.Color(5, 32, 56));

        bCariMembership.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCariMembership.setForeground(new java.awt.Color(231, 238, 126));
        bCariMembership.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        bCariMembership.setText("CARI/REFRESH");
        bCariMembership.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCariMembership.setIconTextGap(8);
        bCariMembership.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCariMembershipMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bCariMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bCariMembership, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel53.setBackground(new java.awt.Color(5, 32, 56));

        bEditKelas2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bEditKelas2.setForeground(new java.awt.Color(231, 238, 126));
        bEditKelas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        bEditKelas2.setText("     CETAK");
        bEditKelas2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bEditKelas2.setIconTextGap(8);
        bEditKelas2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bEditKelas2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bEditKelas2, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bEditKelas2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel83Layout = new javax.swing.GroupLayout(jPanel83);
        jPanel83.setLayout(jPanel83Layout);
        jPanel83Layout.setHorizontalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel83Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel83Layout.createSequentialGroup()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel83Layout.createSequentialGroup()
                        .addComponent(txtMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(307, 307, 307)
                        .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))))
        );
        jPanel83Layout.setVerticalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel83Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel52, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMembership, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel53, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout pMembershipLayout = new javax.swing.GroupLayout(pMembership);
        pMembership.setLayout(pMembershipLayout);
        pMembershipLayout.setHorizontalGroup(
            pMembershipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pMembershipLayout.setVerticalGroup(
            pMembershipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMembershipLayout.createSequentialGroup()
                .addComponent(jPanel82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pMain.add(pMembership, "pMembership");

        jPanel84.setBackground(new java.awt.Color(21, 52, 98));

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 253, 227));
        jLabel53.setText("PRODUK  /  TRANSAKSI PENJUALAN PRODUK & MERCHANDISE");

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 253, 227));
        jLabel54.setText("Tambah dan Cetak Struk Transaksi Penjualan Produk & Merchandise Dalam Database Aplikasi ___________");

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54)
                    .addComponent(jLabel53))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel84Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );

        jPanel85.setBackground(new java.awt.Color(255, 253, 227));

        tabTransaksi.setBackground(new java.awt.Color(254, 255, 230));
        tabTransaksi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabTransaksi.setForeground(new java.awt.Color(5, 32, 56));
        tabTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabTransaksi.setOpaque(false);
        tabTransaksi.setRowHeight(25);
        tabTransaksi.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tabTransaksi.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tabTransaksi.getTableHeader().setReorderingAllowed(false);
        tabTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabTransaksiMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tabTransaksi);

        txtTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTransaksi.setForeground(new java.awt.Color(5, 32, 56));
        txtTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransaksiActionPerformed(evt);
            }
        });
        txtTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTransaksiKeyPressed(evt);
            }
        });

        jPanel51.setBackground(new java.awt.Color(67, 131, 113));

        bAddTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bAddTransaksi.setForeground(new java.awt.Color(231, 238, 126));
        bAddTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        bAddTransaksi.setText("TAMBAH");
        bAddTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAddTransaksi.setIconTextGap(8);
        bAddTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAddTransaksiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bAddTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bAddTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel54.setBackground(new java.awt.Color(5, 32, 56));

        bCariTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCariTransaksi.setForeground(new java.awt.Color(231, 238, 126));
        bCariTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        bCariTransaksi.setText("CARI/REFRESH");
        bCariTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCariTransaksi.setIconTextGap(8);
        bCariTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCariTransaksiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bCariTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bCariTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel55.setBackground(new java.awt.Color(5, 32, 56));

        bEditTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bEditTransaksi.setForeground(new java.awt.Color(231, 238, 126));
        bEditTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        bEditTransaksi.setText("     CETAK");
        bEditTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bEditTransaksi.setIconTextGap(8);
        bEditTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bEditTransaksiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bEditTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bEditTransaksi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel85Layout = new javax.swing.GroupLayout(jPanel85);
        jPanel85.setLayout(jPanel85Layout);
        jPanel85Layout.setHorizontalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel85Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel85Layout.createSequentialGroup()
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel85Layout.createSequentialGroup()
                        .addComponent(txtTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(307, 307, 307)
                        .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))))
        );
        jPanel85Layout.setVerticalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel85Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTransaksi, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout pTransaksiLayout = new javax.swing.GroupLayout(pTransaksi);
        pTransaksi.setLayout(pTransaksiLayout);
        pTransaksiLayout.setHorizontalGroup(
            pTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pTransaksiLayout.setVerticalGroup(
            pTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTransaksiLayout.createSequentialGroup()
                .addComponent(jPanel84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pMain.add(pTransaksi, "pTransaksi");

        javax.swing.GroupLayout mainFrameLayout = new javax.swing.GroupLayout(mainFrame);
        mainFrame.setLayout(mainFrameLayout);
        mainFrameLayout.setHorizontalGroup(
            mainFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainFrameLayout.createSequentialGroup()
                .addComponent(pSide, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainFrameLayout.setVerticalGroup(
            mainFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pSide, javax.swing.GroupLayout.PREFERRED_SIZE, 740, Short.MAX_VALUE)
            .addComponent(pMain, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMemberMouseClicked
        // TODO add your handling code here:
        int bar=tabMember.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        idMember=a;
    }//GEN-LAST:event_tabMemberMouseClicked

    private void txtMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMemberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMemberActionPerformed

    private void txtMemberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMemberKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMemberKeyPressed

    private void bAddMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAddMemberMouseClicked
        // TODO add your handling code here:
        regMember pp = new regMember();
        pp.setVisible(true);
    }//GEN-LAST:event_bAddMemberMouseClicked

    private void bDelMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bDelMemberMouseClicked
        // TODO add your handling code here:
        String a = idMember;
        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+a+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from member_gym where id_member='"+a+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data Identitas Member berhasil dihapus");
                tabMember();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_bDelMemberMouseClicked

    private void bCariMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCariMemberMouseClicked
        // TODO add your handling code here:
        tabMember();
    }//GEN-LAST:event_bCariMemberMouseClicked

    private void bEditMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bEditMemberMouseClicked
        // TODO add your handling code here:
        regMember  pp = new regMember();
        //pp.datab = this;
        pp.getDataid(idMember);
        pp.setVisible(true);
    }//GEN-LAST:event_bEditMemberMouseClicked

    private void tabTipeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabTipeMouseClicked
        // TODO add your handling code here:
        int bar=tabTipe.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        idTipe=a;
    }//GEN-LAST:event_tabTipeMouseClicked

    private void txtTipeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipeActionPerformed

    private void txtTipeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipeKeyPressed

    private void bAddTipeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAddTipeMouseClicked
        // TODO add your handling code here:
         regTipe pp = new regTipe();
        pp.setVisible(true);
    }//GEN-LAST:event_bAddTipeMouseClicked

    private void bDelTipeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bDelTipeMouseClicked
        // TODO add your handling code here:
        String a = idTipe;
        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+a+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from tipe_membership where id_tipe_membership='"+a+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data Tipe Membership berhasil dihapus");
                tabMember();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_bDelTipeMouseClicked

    private void bCariTipeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCariTipeMouseClicked
        // TODO add your handling code here:
        tabTipe();
    }//GEN-LAST:event_bCariTipeMouseClicked

    private void bEditTipeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bEditTipeMouseClicked
        // TODO add your handling code here:
        regTipe  pp = new regTipe();
        //pp.datab = this;
        pp.getDataid(idTipe);
        pp.setVisible(true);
    }//GEN-LAST:event_bEditTipeMouseClicked

    private void kButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton2ActionPerformed

    private void tabUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabUserMouseClicked
        // TODO add your handling code here:
        int bar=tabUser.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        idUser=a;
    }//GEN-LAST:event_tabUserMouseClicked

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserActionPerformed

    private void txtUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserKeyPressed

    private void bAddUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAddUserMouseClicked
        // TODO add your handling code here:
        regUser pp = new regUser();
        pp.setVisible(true);
    }//GEN-LAST:event_bAddUserMouseClicked

    private void bDelUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bDelUserMouseClicked
        // TODO add your handling code here:
        String a = idUser;
        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+a+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from user where id_user='"+a+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data Userberhasil dihapus");
                tabUser();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_bDelUserMouseClicked

    private void bCariUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCariUserMouseClicked
        // TODO add your handling code here:
        tabUser();
    }//GEN-LAST:event_bCariUserMouseClicked

    private void bEditUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bEditUserMouseClicked
        // TODO add your handling code here:
        regUser  pp = new regUser();
        //pp.datab = this;
        pp.getDataid(idUser);
        pp.setVisible(true);
    }//GEN-LAST:event_bEditUserMouseClicked

    private void kButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton3ActionPerformed
        // TODO add your handling code here:
        menManagement.setVisible(false);
        menKelas.setVisible(false);
        menMembership.setVisible(false);
        menProduk.setVisible(false);
        
        CardLayout card = (CardLayout)pMain.getLayout();
        card.show(pMain, "pHome");
    }//GEN-LAST:event_kButton3ActionPerformed

    private void kButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton4ActionPerformed
        // TODO add your handling code here:
        menManagement.setVisible(true);
        menKelas.setVisible(false);
        menMembership.setVisible(false);
        menProduk.setVisible(false);
    }//GEN-LAST:event_kButton4ActionPerformed

    private void kButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton6ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout)pMain.getLayout();
        card.show(pMain, "pTrainer");
        tabTrainer();
    }//GEN-LAST:event_kButton6ActionPerformed

    private void kButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton8ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout)pMain.getLayout();
        card.show(pMain, "pUser");
        tabUser();
    }//GEN-LAST:event_kButton8ActionPerformed

    private void tabTrainerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabTrainerMouseClicked
        // TODO add your handling code here:
         int bar=tabTrainer.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        idTrainer=a;
    }//GEN-LAST:event_tabTrainerMouseClicked

    private void txtTrainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrainerActionPerformed
        // TODO add your handling code here:
         
    }//GEN-LAST:event_txtTrainerActionPerformed

    private void txtTrainerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrainerKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrainerKeyPressed

    private void bAddTrainerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAddTrainerMouseClicked
        // TODO add your handling code here:
         regTrainer pp = new regTrainer();
        pp.setVisible(true);
    }//GEN-LAST:event_bAddTrainerMouseClicked

    private void bDelTrainerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bDelTrainerMouseClicked
        // TODO add your handling code here:
        String a = idTrainer;
        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+a+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from instruktur_gym where id_isntruktur='"+a+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data Trainer Berhasil dihapus");
                tabTrainer();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_bDelTrainerMouseClicked

    private void bCariTrainerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCariTrainerMouseClicked
        // TODO add your handling code here:
        tabTrainer();
    }//GEN-LAST:event_bCariTrainerMouseClicked

    private void bEditTrainerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bEditTrainerMouseClicked
        // TODO add your handling code here:
        regTrainer  pp = new regTrainer();
        //pp.datab = this;
        pp.getDataid(idTrainer);
        pp.setVisible(true);
    }//GEN-LAST:event_bEditTrainerMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();

        // membuat titik x dan y
        int x = layar.width / 2  - this.getSize().width / 2;
        int y = layar.height / 2 - this.getSize().height / 2;

        this.setLocation(x, y);
    }//GEN-LAST:event_formWindowOpened

    private void kButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton9ActionPerformed
        // TODO add your handling code here:
        menManagement.setVisible(false);
        menKelas.setVisible(true);
        menMembership.setVisible(false);
         menProduk.setVisible(false);
    }//GEN-LAST:event_kButton9ActionPerformed

    private void kButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton10ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout)pMain.getLayout();
        card.show(pMain, "pKelas");
        tabKelas();
    }//GEN-LAST:event_kButton10ActionPerformed

    private void kButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton11ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout)pMain.getLayout();
        card.show(pMain, "pJadwalKelas");
        tabJadwal();
    }//GEN-LAST:event_kButton11ActionPerformed

    private void kButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton12ActionPerformed
        // TODO add your handling code here:
                // TODO add your handling code here:
        pop.popAbsenKelas pop = new pop.popAbsenKelas();
        pop.setVisible(true);
    }//GEN-LAST:event_kButton12ActionPerformed

    private void tabKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabKelasMouseClicked
        // TODO add your handling code here:
        int bar=tabKelas.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        idKelas=a;
    }//GEN-LAST:event_tabKelasMouseClicked

    private void txtKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKelasActionPerformed

    private void txtKelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKelasKeyPressed

    private void bAddKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAddKelasMouseClicked
        // TODO add your handling code here:
        regKelas pp = new regKelas();
        pp.setVisible(true);
    }//GEN-LAST:event_bAddKelasMouseClicked

    private void bDelKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bDelKelasMouseClicked
        // TODO add your handling code here:
        String a = idKelas;
        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+a+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from kelas_gym where id_kelas='"+a+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data Kelas Gym Berhasil Dihapus");
                tabKelas();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_bDelKelasMouseClicked

    private void bCariKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCariKelasMouseClicked
        // TODO add your handling code here:
        tabKelas();
    }//GEN-LAST:event_bCariKelasMouseClicked

    private void bEditKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bEditKelasMouseClicked
        // TODO add your handling code here:
        regKelas  pp = new regKelas();
        //pp.datab = this;
        pp.getDataid(idKelas);
        pp.setVisible(true);
    }//GEN-LAST:event_bEditKelasMouseClicked

    private void kButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton13ActionPerformed
        // TODO add your handling code here:
        menManagement.setVisible(false);
        menKelas.setVisible(false);
        menMembership.setVisible(true);
         menProduk.setVisible(false);
    }//GEN-LAST:event_kButton13ActionPerformed

    private void kButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton14ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout)pMain.getLayout();
        card.show(pMain, "pTipe");
        tabTipe();
    }//GEN-LAST:event_kButton14ActionPerformed

    private void kButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton15ActionPerformed
        // TODO add your handling code here:
        
        CardLayout card = (CardLayout)pMain.getLayout();
        card.show(pMain, "pMembership");
        tabMembership();
    }//GEN-LAST:event_kButton15ActionPerformed

    private void kButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton16ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout)pMain.getLayout();
        card.show(pMain, "pMember");
        tabMember();
    }//GEN-LAST:event_kButton16ActionPerformed

    private void kButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton7ActionPerformed
        // TODO add your handling code here:
        pop.popHarga pop = new pop.popHarga();
        pop.setVisible(true);
    }//GEN-LAST:event_kButton7ActionPerformed

    private void kButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton17ActionPerformed
        // TODO add your handling code here:
        pop.popCheckIn pop = new pop.popCheckIn();
        pop.setVisible(true);
    }//GEN-LAST:event_kButton17ActionPerformed

    private void tabJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabJadwalMouseClicked
        // TODO add your handling code here:
        int bar=tabJadwal.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        idJadwalKelas=a;
    }//GEN-LAST:event_tabJadwalMouseClicked

    private void txtJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJadwalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJadwalActionPerformed

    private void txtJadwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJadwalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJadwalKeyPressed

    private void bAddJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAddJadwalMouseClicked
        // TODO add your handling code here:
        regJadwalKelas pp = new regJadwalKelas();
        pp.setVisible(true);
    }//GEN-LAST:event_bAddJadwalMouseClicked

    private void bDelJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bDelJadwalMouseClicked
        // TODO add your handling code here:
        
        String a = idJadwalKelas;
        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+a+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from jadwal_kelas where id='"+a+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data Kelas Gym Berhasil Dihapus");
                tabKelas();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_bDelJadwalMouseClicked

    private void bCariJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCariJadwalMouseClicked
        // TODO add your handling code here:
        tabJadwal();
    }//GEN-LAST:event_bCariJadwalMouseClicked

    private void bEditJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bEditJadwalMouseClicked
        // TODO add your handling code here:
         regJadwalKelas  pp = new regJadwalKelas();
        pp.getDataid(idJadwalKelas);
        pp.setVisible(true);
    }//GEN-LAST:event_bEditJadwalMouseClicked

    private void kButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton18ActionPerformed
        // TODO add your handling code here:
         menManagement.setVisible(false);
        menKelas.setVisible(false);
        menMembership.setVisible(false);
         menProduk.setVisible(true);
    }//GEN-LAST:event_kButton18ActionPerformed

    private void kButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton19ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout)pMain.getLayout();
        card.show(pMain, "pProduk");
        tabProduk();
    }//GEN-LAST:event_kButton19ActionPerformed

    private void kButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton20ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout)pMain.getLayout();
        card.show(pMain, "pTransaksi");
        tabTransaksi();
    }//GEN-LAST:event_kButton20ActionPerformed

    private void tabProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabProdukMouseClicked
        // TODO add your handling code here:
         int bar=tabProduk.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        idProduk=a;
    }//GEN-LAST:event_tabProdukMouseClicked

    private void txtProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProdukActionPerformed

    private void txtProdukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProdukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProdukKeyPressed

    private void bAddProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAddProdukMouseClicked
        // TODO add your handling code here:
        regProduk pp = new regProduk();
        pp.setVisible(true);
    }//GEN-LAST:event_bAddProdukMouseClicked

    private void bDelProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bDelProdukMouseClicked
        // TODO add your handling code here:
        String a = idProduk;
        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+a+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from produk where id_produk='"+a+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data Produk Berhasil Dihapus");
                tabProduk();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_bDelProdukMouseClicked

    private void bCariProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCariProdukMouseClicked
        // TODO add your handling code here:
        tabProduk();
    }//GEN-LAST:event_bCariProdukMouseClicked

    private void bEditProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bEditProdukMouseClicked
        // TODO add your handling code here:
        regProduk  pp = new regProduk();
        pp.getDataid(idProduk);
        pp.setVisible(true);
    }//GEN-LAST:event_bEditProdukMouseClicked

    private void repTrainerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repTrainerMouseClicked
        // TODO add your handling code here:
       try{     
            String path="./src/report/Trainer.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
  
    }//GEN-LAST:event_repTrainerMouseClicked

    private void cbAbsensiKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbAbsensiKelasMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cbAbsensiKelasMouseClicked

    private void cbAbsensiKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAbsensiKelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAbsensiKelasActionPerformed

    private void repHargaTrainerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repHargaTrainerMouseClicked
        // TODO add your handling code here:
        try{     
            String path="./src/report/hargaTrainer.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
        
    }//GEN-LAST:event_repHargaTrainerMouseClicked

    private void repUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repUserMouseClicked
        // TODO add your handling code here:
        try{     
            String path="./src/report/User.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
        
    }//GEN-LAST:event_repUserMouseClicked

    private void repKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repKelasMouseClicked
        // TODO add your handling code here:
          try{     
            String path="./src/report/Kelas.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
        
    }//GEN-LAST:event_repKelasMouseClicked

    private void repJadwalKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repJadwalKelasMouseClicked
        // TODO add your handling code here:
             int bulan= jadwalKelasMonth.getMonth()+1;
             int tahun  = jadwalKelasYear.getYear();
         try{     
            String path="./src/report/jadwalKelas.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            parameter.put("periode",bulan);
            parameter.put("tahun",tahun);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }//GEN-LAST:event_repJadwalKelasMouseClicked

    private void repAbsensiKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repAbsensiKelasMouseClicked
        // TODO add your handling code here:
        String b=cbAbsensiKelas.getSelectedItem().toString();
        String trimmedJadwal = b.replaceAll(" .*", "");
        
        try{     
            String path="./src/report/absensiKelas.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            parameter.put("jadwal",trimmedJadwal);
         
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }//GEN-LAST:event_repAbsensiKelasMouseClicked

    private void repMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repMemberMouseClicked
        // TODO add your handling code here:
        try{     
            String path="./src/report/member.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }//GEN-LAST:event_repMemberMouseClicked

    private void repCheckinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repCheckinMouseClicked
        // TODO add your handling code here:
        try{     
            String path="./src/report/checkin.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            parameter.put("mulai",date1+" 00:00:00");
            parameter.put("selesai",date2+" 23:59:59");
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }//GEN-LAST:event_repCheckinMouseClicked

    private void repMembershipMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repMembershipMouseClicked
        // TODO add your handling code here:
        int bulan= membershipMonth.getMonth()+1;
             int tahun  = membershipYear.getYear();
         try{     
            String path="./src/report/periodeMembership.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            parameter.put("periode",bulan);
            parameter.put("tahun",tahun);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
        
    }//GEN-LAST:event_repMembershipMouseClicked

    private void repPenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repPenjualanMouseClicked
        // TODO add your handling code here:
        String b=cbLapMember.getSelectedItem().toString();
        String trimmedMember = b.replaceAll(" .*", "");   
        
        int bulan= memberMonth.getMonth()+1;
        int tahun  = memberYear.getYear();
        
         try{     
            String path="./src/report/penjualan.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            parameter.put("periode",bulan);
            parameter.put("tahun",tahun);
            parameter.put("member",trimmedMember);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }//GEN-LAST:event_repPenjualanMouseClicked

    private void repIncomeMembershipMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repIncomeMembershipMouseClicked
        // TODO add your handling code here:
        int bulan= penMemshipMonth.getMonth()+1;
        int tahun  = penMemshipYear.getYear();
         try{     
            String path="./src/report/pendapatanMembership.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            parameter.put("periode",bulan);
            parameter.put("tahun",tahun);
           
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }//GEN-LAST:event_repIncomeMembershipMouseClicked

    private void repIncomeProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repIncomeProdukMouseClicked
        // TODO add your handling code here:
        int bulan= penProdukMonth.getMonth()+1;
        int tahun  = penProdukYear.getYear();
         try{     
            String path="./src/report/pendapatanProduk.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            parameter.put("periode",bulan);
            parameter.put("tahun",tahun);
           
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }//GEN-LAST:event_repIncomeProdukMouseClicked

    private void date_mulaiPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_date_mulaiPropertyChange
        // TODO add your handling code here:
         if (date_mulai.getDate() !=null){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         date1 = sdf.format(date_mulai.getDate());
        }
    }//GEN-LAST:event_date_mulaiPropertyChange

    private void date_selesaiPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_date_selesaiPropertyChange
        // TODO add your handling code here:
        if (date_selesai.getDate() !=null){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         date2 = sdf.format(date_selesai.getDate());
        }
    }//GEN-LAST:event_date_selesaiPropertyChange

    private void kButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton21ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout)pMain.getLayout();
        card.show(pMain, "pReport");
        comboJadwalKelas();
        combo_member();
    }//GEN-LAST:event_kButton21ActionPerformed

    private void txtUserHome1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserHome1MouseClicked
        // TODO add your handling code here:
          regMember  pp = new regMember();
        pp.setVisible(true);
    }//GEN-LAST:event_txtUserHome1MouseClicked

    private void txtUserHome2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserHome2MouseClicked
        // TODO add your handling code here:
        regSignMembership  pp = new regSignMembership();
        pp.setVisible(true);
    }//GEN-LAST:event_txtUserHome2MouseClicked

    private void txtUserHome3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserHome3MouseClicked
        // TODO add your handling code here:
        pop.popCheckIn  pp = new pop.popCheckIn();
        pp.setVisible(true);
    }//GEN-LAST:event_txtUserHome3MouseClicked

    private void txtUserHome4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserHome4MouseClicked
        // TODO add your handling code here:
        pop.popAbsenKelas  pp = new pop.popAbsenKelas();
        pp.setVisible(true);
    }//GEN-LAST:event_txtUserHome4MouseClicked

    private void txtUserHome5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserHome5MouseClicked
        // TODO add your handling code here:
         regTransaksiProduk  pp = new regTransaksiProduk();
        pp.setVisible(true);
    }//GEN-LAST:event_txtUserHome5MouseClicked

    private void tabMembershipMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMembershipMouseClicked
        // TODO add your handling code here:
        int bar=tabMembership.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        idMembershipBaru=a;
    }//GEN-LAST:event_tabMembershipMouseClicked

    private void txtMembershipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMembershipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMembershipActionPerformed

    private void txtMembershipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMembershipKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMembershipKeyPressed

    private void bAddMembershipMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAddMembershipMouseClicked
        // TODO add your handling code here:
        regSignMembership  pp = new regSignMembership();
        pp.setVisible(true);
    }//GEN-LAST:event_bAddMembershipMouseClicked

    private void bCariMembershipMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCariMembershipMouseClicked
        // TODO add your handling code here:
        tabMembership();
    }//GEN-LAST:event_bCariMembershipMouseClicked

    private void bEditKelas2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bEditKelas2MouseClicked
        // TODO add your handling code here:
       
         try{     
            String path="./src/report/strukMembership.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            parameter.put("idMembership",idMembershipBaru);

            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }//GEN-LAST:event_bEditKelas2MouseClicked

    private void tabTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabTransaksiMouseClicked
        // TODO add your handling code here:
        int bar=tabTransaksi.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        idTrans=a;
    }//GEN-LAST:event_tabTransaksiMouseClicked

    private void txtTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransaksiActionPerformed

    private void txtTransaksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTransaksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransaksiKeyPressed

    private void bAddTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAddTransaksiMouseClicked
        // TODO add your handling code here:
        regTransaksiProduk  pp = new regTransaksiProduk();
        pp.setVisible(true);
    }//GEN-LAST:event_bAddTransaksiMouseClicked

    private void bCariTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCariTransaksiMouseClicked
        // TODO add your handling code here:
        tabTransaksi();
    }//GEN-LAST:event_bCariTransaksiMouseClicked

    private void bEditTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bEditTransaksiMouseClicked
        // TODO add your handling code here:
        try{     
            String path="./src/report/strukProduk.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",txtNama.getText());
            parameter.put("idTransaksi",idTrans);

            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }//GEN-LAST:event_bEditTransaksiMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bAddJadwal;
    private javax.swing.JLabel bAddKelas;
    private javax.swing.JLabel bAddMember;
    private javax.swing.JLabel bAddMembership;
    private javax.swing.JLabel bAddProduk;
    private javax.swing.JLabel bAddTipe;
    private javax.swing.JLabel bAddTrainer;
    private javax.swing.JLabel bAddTransaksi;
    private javax.swing.JLabel bAddUser;
    private javax.swing.JLabel bCariJadwal;
    private javax.swing.JLabel bCariKelas;
    private javax.swing.JLabel bCariMember;
    private javax.swing.JLabel bCariMembership;
    private javax.swing.JLabel bCariProduk;
    private javax.swing.JLabel bCariTipe;
    private javax.swing.JLabel bCariTrainer;
    private javax.swing.JLabel bCariTransaksi;
    private javax.swing.JLabel bCariUser;
    private javax.swing.JLabel bDelJadwal;
    private javax.swing.JLabel bDelKelas;
    private javax.swing.JLabel bDelMember;
    private javax.swing.JLabel bDelProduk;
    private javax.swing.JLabel bDelTipe;
    private javax.swing.JLabel bDelTrainer;
    private javax.swing.JLabel bDelUser;
    private javax.swing.JLabel bEditJadwal;
    private javax.swing.JLabel bEditKelas;
    private javax.swing.JLabel bEditKelas2;
    private javax.swing.JLabel bEditMember;
    private javax.swing.JLabel bEditProduk;
    private javax.swing.JLabel bEditTipe;
    private javax.swing.JLabel bEditTrainer;
    private javax.swing.JLabel bEditTransaksi;
    private javax.swing.JLabel bEditUser;
    private javax.swing.JComboBox<String> cbAbsensiKelas;
    private javax.swing.JComboBox<String> cbLapMember;
    private com.toedter.calendar.JDateChooser date_mulai;
    private com.toedter.calendar.JDateChooser date_selesai;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private com.toedter.calendar.JMonthChooser jadwalKelasMonth;
    private com.toedter.calendar.JYearChooser jadwalKelasYear;
    private com.k33ptoo.components.KButton kButton10;
    private com.k33ptoo.components.KButton kButton11;
    private com.k33ptoo.components.KButton kButton12;
    private com.k33ptoo.components.KButton kButton13;
    private com.k33ptoo.components.KButton kButton14;
    private com.k33ptoo.components.KButton kButton15;
    private com.k33ptoo.components.KButton kButton16;
    private com.k33ptoo.components.KButton kButton17;
    private com.k33ptoo.components.KButton kButton18;
    private com.k33ptoo.components.KButton kButton19;
    private com.k33ptoo.components.KButton kButton2;
    private com.k33ptoo.components.KButton kButton20;
    private com.k33ptoo.components.KButton kButton21;
    private com.k33ptoo.components.KButton kButton3;
    private com.k33ptoo.components.KButton kButton4;
    private com.k33ptoo.components.KButton kButton6;
    private com.k33ptoo.components.KButton kButton7;
    private com.k33ptoo.components.KButton kButton8;
    private com.k33ptoo.components.KButton kButton9;
    private javax.swing.JPanel mainFrame;
    private com.toedter.calendar.JMonthChooser memberMonth;
    private com.toedter.calendar.JYearChooser memberYear;
    private com.toedter.calendar.JMonthChooser membershipMonth;
    private com.toedter.calendar.JYearChooser membershipYear;
    private javax.swing.JPanel menKelas;
    private javax.swing.JPanel menManagement;
    private javax.swing.JPanel menMembership;
    private javax.swing.JPanel menProduk;
    private javax.swing.JPanel pHome;
    private javax.swing.JPanel pJadwalKelas;
    private javax.swing.JPanel pKelas;
    private javax.swing.JPanel pMain;
    private javax.swing.JPanel pMember;
    private javax.swing.JPanel pMembership;
    private javax.swing.JPanel pProduk;
    private javax.swing.JPanel pReport;
    private javax.swing.JPanel pSide;
    private javax.swing.JPanel pTipe;
    private javax.swing.JPanel pTrainer;
    private javax.swing.JPanel pTransaksi;
    private javax.swing.JPanel pUser;
    private javax.swing.JPanel p_perusahaan_header8;
    private javax.swing.JPanel p_perusahaan_main8;
    private com.toedter.calendar.JMonthChooser penMemshipMonth;
    private com.toedter.calendar.JYearChooser penMemshipYear;
    private com.toedter.calendar.JMonthChooser penProdukMonth;
    private com.toedter.calendar.JYearChooser penProdukYear;
    private javax.swing.JLabel repAbsensiKelas;
    private javax.swing.JLabel repCheckin;
    private javax.swing.JLabel repHargaTrainer;
    private javax.swing.JLabel repIncomeMembership;
    private javax.swing.JLabel repIncomeProduk;
    private javax.swing.JLabel repJadwalKelas;
    private javax.swing.JLabel repKelas;
    private javax.swing.JLabel repMember;
    private javax.swing.JLabel repMembership;
    private javax.swing.JLabel repPenjualan;
    private javax.swing.JLabel repTrainer;
    private javax.swing.JLabel repUser;
    private javax.swing.JTable tabJadwal;
    private javax.swing.JTable tabKelas;
    private javax.swing.JTable tabMember;
    private javax.swing.JTable tabMembership;
    private javax.swing.JTable tabProduk;
    private javax.swing.JTable tabTipe;
    private javax.swing.JTable tabTrainer;
    private javax.swing.JTable tabTransaksi;
    private javax.swing.JTable tabUser;
    private javax.swing.JLabel txtEmail;
    private javax.swing.JTextField txtJadwal;
    private javax.swing.JTextField txtKelas;
    private javax.swing.JTextField txtMember;
    private javax.swing.JTextField txtMembership;
    private javax.swing.JLabel txtNama;
    private javax.swing.JTextField txtProduk;
    private javax.swing.JLabel txtTelp;
    private javax.swing.JTextField txtTipe;
    private javax.swing.JLabel txtTipeHome;
    private javax.swing.JTextField txtTrainer;
    private javax.swing.JTextField txtTransaksi;
    private javax.swing.JTextField txtUser;
    private javax.swing.JLabel txtUserHome;
    private javax.swing.JLabel txtUserHome1;
    private javax.swing.JLabel txtUserHome2;
    private javax.swing.JLabel txtUserHome3;
    private javax.swing.JLabel txtUserHome4;
    private javax.swing.JLabel txtUserHome5;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import java.sql.*;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import koneksi.koneksi;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import static view.home.idTrans;
import static view.home.namaUser;

/**
 *
 * @author nopal
 */ 
public class regTransaksiProduk extends javax.swing.JFrame {
private Connection conn = new koneksi().connect();
private DefaultTableModel tabmode;
String date1,date2;
public String idMember, idProduk,max;
int check,durasiMember;
        Long harga,hargaTrainer,totalBarang,totalKelas,total;
        
SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
java.util.Date month = new java.util.Date();
String var2 = formatter.format(month);

//public home datab=null;

    /**
     * Creates new form regTransaksiProduk
     */
    public regTransaksiProduk() {
        initComponents();
        
        aktif();
        autoID();
        
    }
    
     public void autoID(){
         //PM
        String id="";
        String var1 = "TRANS";
         
        //Seq berurut
        String var3 = null;
        newSeqMonth();
        if(check==0) { //check new seq
            var3 = "001";
        } else {
            String getSeqBefore = max; //Get from database seq max today
            int newSeq = Integer.parseInt(getSeqBefore) + 1;    
            if(newSeq >= 100) {
                System.out.println("Out of Maximal Seq");
            } else if (newSeq >= 10) {
                var3 = "0" + newSeq;
            } else if (newSeq >= 1) {
                var3 = "00" + newSeq;
            } else {
                System.out.println("Invalid Seq");
            }               
        }
        id=var1+var2+var3;
        txt_kode.setText(id);
        
     }
     
     public void newSeqMonth() {
         //TODO : Check database is today have seq
         //jika hari ini tidak punya seq, maka harus bikin seq baru = true
         String sql="select id_transaksi from logs_transaksi where id_transaksi like '%"+var2+"%' order by id_transaksi desc limit 1";
         try{
          Statement st = conn.createStatement(); ResultSet ars = st.executeQuery(sql); 
             if (ars.next()){check=1; 
             String cut=(ars.getString(1)); max=cut.substring(11);
                     }
             else {check=0;}             
         }
         catch (SQLException e){
            JOptionPane.showMessageDialog(null,"data gagal dipanggil : "+ e);
            }
     }
    
    protected void aktif(){
        
        b_edit.setVisible(false);
        
        combo_member();
        
    };
    
    public void tabProduk(){
        tabProduk.setAutoCreateRowSorter(true);
        Object[] Baris = {"Kode Produk","Nama","Kuantitas","Total"};
        tabmode = new DefaultTableModel(null,Baris);
        try {
            String sql="SELECT logs_transaksi_detail.id_barang,produk.nama_produk,logs_transaksi_detail.kuantitas,logs_transaksi_detail.total  "
                    + "FROM logs_transaksi_detail inner join produk on logs_transaksi_detail.id_barang=produk.id_produk "
                    + "where logs_transaksi_detail.id_transaksi='"+txt_kode.getText()+"' "
                    + "order by logs_transaksi_detail.id_transaksi asc";
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
            tabProduk.setModel(tabmode);
//            tabKelas.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }  
    }
    
   
    
    public void combo_member() {
        cbMember.removeAllItems();
        cbMember.addItem(" - Pilih Member - ");  
        try{
            String sql = "select id_member,nama from member_gym";           
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery(sql); 
            while (r.next()) {
            cbMember.addItem(r.getString(1)+" - "+r.getString(2));}
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }   
    }
    
    
    public void getDataid(String id){
        b_simpan.setVisible(false);  
        b_edit.setVisible(true);
        jLabel16.setText("          Informasi Transaksi");        
        txt_kode.setText(home.idTransaksi);
        String a = txt_kode.getText();
        txt_kode.setEnabled(false);
        try{            
            String sql="Select logs_transaksi.id_transaksi,logs_transaksi.id_member,member_gym.nama "
                    + " from logs_transaksi inner join member_gym on logs_transaksi.id_member=member_gym.id_member ";
 
            Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql);             
            while (rs.next()){
             txt_kode.setText(rs.getString(1));
             cbMember.addItem(rs.getString(2)+" - "+rs.getString(3));
             cbMember.setSelectedItem(rs.getString(2)+" - "+rs.getString(3));
             }
                        
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"data membership gagal dipanggil : "+ e);
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

        kGradientPanel1 = new com.k33ptoo.components.KGradientPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cbMember = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        b_edit = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        b_simpan = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabProduk = new javax.swing.JTable();
        bTambahKelas = new javax.swing.JLabel();
        bUbahKelas = new javax.swing.JLabel();
        bHapusProduk = new javax.swing.JLabel();
        bRefreshKelas = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txt_kode = new javax.swing.JTextField();

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 253, 227));

        jPanel2.setBackground(new java.awt.Color(21, 52, 98));

        jLabel16.setBackground(new java.awt.Color(204, 204, 204));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 253, 227));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("TRANSAKSI PENJUALAN PRODUK");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 253, 227));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(21, 52, 98)));

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(32, 74, 86));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("ID MEMBER");

        cbMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMemberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbMember, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addComponent(cbMember))
        );

        jPanel4.setBackground(new java.awt.Color(255, 253, 227));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(21, 52, 98));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });

        jLabel24.setBackground(new java.awt.Color(204, 204, 204));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 253, 227));
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_back_24px.png"))); // NOI18N
        jLabel24.setText("   Kembali");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel15.setBackground(new java.awt.Color(162, 34, 39));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel15MouseClicked(evt);
            }
        });

        jLabel23.setBackground(new java.awt.Color(204, 204, 204));
        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 253, 227));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        jLabel23.setText("   Hapus");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 110, -1));

        b_edit.setBackground(new java.awt.Color(21, 52, 98));
        b_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_editMouseClicked(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(204, 204, 204));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 253, 227));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        jLabel17.setText("   Ubah");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout b_editLayout = new javax.swing.GroupLayout(b_edit);
        b_edit.setLayout(b_editLayout);
        b_editLayout.setHorizontalGroup(
            b_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, b_editLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
        b_editLayout.setVerticalGroup(
            b_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        jPanel4.add(b_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        b_simpan.setBackground(new java.awt.Color(21, 52, 98));
        b_simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_simpanMouseClicked(evt);
            }
        });

        jLabel22.setBackground(new java.awt.Color(204, 204, 204));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 253, 227));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_save_24px.png"))); // NOI18N
        jLabel22.setText("   Simpan");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout b_simpanLayout = new javax.swing.GroupLayout(b_simpan);
        b_simpan.setLayout(b_simpanLayout);
        b_simpanLayout.setHorizontalGroup(
            b_simpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, b_simpanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
        );
        b_simpanLayout.setVerticalGroup(
            b_simpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        jPanel4.add(b_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 110, -1));

        jPanel17.setBackground(new java.awt.Color(255, 253, 227));
        jPanel17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(21, 52, 98)));

        jLabel27.setBackground(new java.awt.Color(204, 204, 204));
        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(32, 74, 86));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("KERANJANG BELANJA");

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
                "Kode Produk", "Nama", "Kuantitas", "Total"
            }
        ));
        tabProduk.setToolTipText("");
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
        tabProduk.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tabProdukPropertyChange(evt);
            }
        });
        jScrollPane7.setViewportView(tabProduk);

        bTambahKelas.setBackground(new java.awt.Color(21, 52, 98));
        bTambahKelas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bTambahKelas.setForeground(new java.awt.Color(255, 253, 227));
        bTambahKelas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bTambahKelas.setText("Tambah");
        bTambahKelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTambahKelas.setOpaque(true);
        bTambahKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bTambahKelasMouseClicked(evt);
            }
        });

        bUbahKelas.setBackground(new java.awt.Color(21, 52, 98));
        bUbahKelas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bUbahKelas.setForeground(new java.awt.Color(255, 253, 227));
        bUbahKelas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bUbahKelas.setText("Ubah");
        bUbahKelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bUbahKelas.setOpaque(true);
        bUbahKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bUbahKelasMouseClicked(evt);
            }
        });

        bHapusProduk.setBackground(new java.awt.Color(162, 34, 39));
        bHapusProduk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bHapusProduk.setForeground(new java.awt.Color(255, 253, 227));
        bHapusProduk.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bHapusProduk.setText("Hapus");
        bHapusProduk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHapusProduk.setOpaque(true);
        bHapusProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bHapusProdukMouseClicked(evt);
            }
        });

        bRefreshKelas.setBackground(new java.awt.Color(21, 52, 98));
        bRefreshKelas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bRefreshKelas.setForeground(new java.awt.Color(255, 253, 227));
        bRefreshKelas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bRefreshKelas.setText("Refresh");
        bRefreshKelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bRefreshKelas.setOpaque(true);
        bRefreshKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bRefreshKelasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(bHapusProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bUbahKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bTambahKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(bRefreshKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bRefreshKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bTambahKelas, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(bUbahKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bHapusProduk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        jPanel6.setBackground(new java.awt.Color(255, 253, 227));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(21, 52, 98)));

        jLabel11.setBackground(new java.awt.Color(204, 204, 204));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(32, 74, 86));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("ID TRANSAKSI");

        txt_kode.setForeground(new java.awt.Color(9, 10, 54));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_kode, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txt_kode))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        // TODO add your handling code here:
        this.dispose();       
    }//GEN-LAST:event_jPanel16MouseClicked

    private void b_simpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_simpanMouseClicked
        // TODO add your handling code here:
       
        String asql="insert into logs_transaksi (id_transaksi,id_member,total) values (?,?,?)";
       
        String b=cbMember.getSelectedItem().toString();
        String trimmedMember = b.replaceAll(" .*", "");
        String bsql="select sum(total) from logs_transaksi_detail where id_transaksi='"+txt_kode.getText()+"'";
        String csql="select id_barang,kuantitas from logs_transaksi_detail "
                + "where id_transaksi='"+txt_kode.getText()+"'";
        
try{        
                      
                    Statement s = conn.createStatement(); ResultSet r = s.executeQuery(bsql); 
                    while (r.next()) {totalBarang=r.getLong(1);}

                    Statement sc = conn.createStatement(); ResultSet rc = sc.executeQuery(csql); 
                    while (rc.next()) {String xsql="update produk set stok=stok-"+rc.getInt(2)+" where id_produk='"+rc.getString(1)+"'";
                                        PreparedStatement xstat=conn.prepareStatement(xsql); 
                                       xstat.executeUpdate();}
            
                    PreparedStatement astat=conn.prepareStatement(asql);  
                    astat.setString(1,txt_kode.getText());        
                    astat.setString(2,trimmedMember);
                    astat.setLong(3,totalBarang);
                    astat.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null,"Data Transaksi Berhasil Disimpan");
               
                    try{     
            String path="./src/report/strukProduk.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("user",namaUser);
            parameter.put("idTransaksi",txt_kode.getText());

            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
                    
                    this.dispose();
                                   
        }
        
        //str = str.replaceAll("\\s.*", "");
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Data Gagal Disimpan : "+ e);
        }
    }//GEN-LAST:event_b_simpanMouseClicked

    private void b_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_editMouseClicked
        // TODO add your handling code here:
//        String a=txt_kode.getText();
//        String b=cb_dept.getSelectedItem().toString();
//             String trimmed = b.replaceAll(" .*", "");
//        String asql="update kelas_gym set nama_kelas=?, id_instruktur=?, harga_sesi=?, keterangan=? "
//                + "where id_kelas='"+a+"'";       
//       // String atasan = c.replaceAll("\\s.*", "");
//        try{
//            PreparedStatement astat=conn.prepareStatement(asql); 
//                    astat.setString(1,txt_nama.getText());
//                    astat.setString(2,trimmed);                
//                    astat.setString(3,txt_harga.getText());
//                    astat.setString(4,txt_keterangan.getText());     
//            astat.executeUpdate();
//            
//            JOptionPane.showMessageDialog(null,"Data Kelas Berhasil diubah");
//            kosong();
//            this.setVisible(false);
//        }
//        catch (SQLException e){
//            JOptionPane.showMessageDialog(null,"data gagal disimpan : "+ e);
//        }
    }//GEN-LAST:event_b_editMouseClicked

    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked
        // TODO add your handling code here:
//        String a = txt_kode.getText();
//        String b = txt_nama.getText();
//        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+b+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
//        if (ok==0){
//            String sql="delete from kelas_gym where id_kelas='"+a+"'";
//            try{
//                PreparedStatement stat = conn.prepareStatement(sql);
//                stat.executeUpdate();
//                JOptionPane.showMessageDialog(null,"Data Kelas Gym Berhasil Dihapus");
//                kosong();
//                this.setVisible(false);
//            }
//            catch (SQLException e){
//                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
//            }
//        }
    }//GEN-LAST:event_jPanel15MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        // mengambil ukuran layar
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();

        // membuat titik x dan y
        int x = layar.width / 2  - this.getSize().width / 2;
        int y = layar.height / 2 - this.getSize().height / 2;

        this.setLocation(x, y);
    }//GEN-LAST:event_formWindowOpened

    private void tabProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabProdukMouseClicked
        // TODO add your handling code here:
        int bar=tabProduk.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        idProduk=a;
    }//GEN-LAST:event_tabProdukMouseClicked

    private void bTambahKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bTambahKelasMouseClicked
        // TODO add your handling code here
        pop.popBarang pop = new pop.popBarang();
        String a=txt_kode.getText();
        String b=cbMember.getSelectedItem().toString();
       String trimmedMember = b.replaceAll(" .*", "");
        pop.getId(trimmedMember,a);
        pop.setVisible(true);
    }//GEN-LAST:event_bTambahKelasMouseClicked

    private void bRefreshKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bRefreshKelasMouseClicked
        // TODO add your handling code here:
        tabProduk();
        
    }//GEN-LAST:event_bRefreshKelasMouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
       
        int bar=tabProduk.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        String b = tabmode.getValueAt(bar,1).toString();
        String c = tabmode.getValueAt(bar,2).toString();

        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+a+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from user where id_user='"+a+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data User Berhasil Dihapus");
            
                this.setVisible(false);
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_jLabel23MouseClicked

    private void bUbahKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bUbahKelasMouseClicked
        // TODO add your handling code here:
        
        int bar=tabProduk.getSelectedRow();
        String a = tabmode.getValueAt(bar,2).toString();
        int kuantitas = Integer.parseInt(a);
        String namaKelas = tabmode.getValueAt(bar,1).toString();
        String barang = tabmode.getValueAt(bar,0).toString();
        
        
        int ok = JOptionPane.showConfirmDialog(null,"Ubah Kuantitas Produk "+namaKelas+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
        
        
        String asql="update logs_transaksi_detail set kuantitas=?, total=? "
                + "where (id_transaksi='"+txt_kode.getText()+"' and id_barang='"+barang+"')";       
        String bsql="select harga_produk from produk where id_produk='"+barang+"'";
        
        try{
            Statement stat = conn.createStatement(); ResultSet hasil = stat.executeQuery(bsql);
                    while (hasil.next()){
                    harga=hasil.getLong(1);}
                    total=harga*kuantitas;
            
            PreparedStatement astat=conn.prepareStatement(asql); 
            astat.setInt(1,kuantitas);
            astat.setLong(2,total);
            astat.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Data Durasi Kelas Berhasil Diubah");
            tabProduk();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"data gagal diubah : "+ e);
        }
        }
        
    }//GEN-LAST:event_bUbahKelasMouseClicked

    private void bHapusProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bHapusProdukMouseClicked
        // TODO add your handling code here:
        int bar=tabProduk.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        String b = tabmode.getValueAt(bar,1).toString();
        String c = tabmode.getValueAt(bar,2).toString();

        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+b+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from logs_transaksi_detail where (id_barang='"+a+"'and id_transaksi='"+txt_kode.getText()+"')";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data Kelas Tambahan Berhasil Dihapus");
                tabProduk();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_bHapusProdukMouseClicked

    private void tabProdukPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tabProdukPropertyChange
        // TODO add your handling code here:
//        if (cbTrainer.getSelectedItem() !=null){
//             try{
//            String dsql="select (SELECT logs_kelas.jumlah*kelas_gym.harga_sesi from logs_kelas "
//                + "inner join kelas_gym on logs_kelas.id_kelas=kelas_gym.id_kelas"
//                + " WHERE logs_kelas.id_membership='"+txt_kode.getText()+"')";
//            
//            Statement s = conn.createStatement();
//            ResultSet r = s.executeQuery(dsql); 
//            while (r.next()) {
//            txtBiayaKelas.setText(r.getString(1));}
//        }
//        catch (Exception e) {
//            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
//        } 
//         }
    }//GEN-LAST:event_tabProdukPropertyChange

    private void cbMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMemberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMemberActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bHapusProduk;
    private javax.swing.JLabel bRefreshKelas;
    private javax.swing.JLabel bTambahKelas;
    private javax.swing.JLabel bUbahKelas;
    private javax.swing.JPanel b_edit;
    private javax.swing.JPanel b_simpan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbMember;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane7;
    private com.k33ptoo.components.KGradientPanel kGradientPanel1;
    private javax.swing.JTable tabProduk;
    private javax.swing.JTextField txt_kode;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import koneksi.koneksi;
import java.util.ArrayList;
import view.regSignMembership;
import view.regTransaksiProduk;


/**
 *
 * @author PAKUAN-IT
 */
public class popBarang extends javax.swing.JFrame {
private DefaultTableModel tabmode3;
private Connection conn = new koneksi().connect();
public String idMember, idTransaksi,idProduk;
int count=0;
long harga,total;
String []codes={"","","","","","","","","",""};
 private DefaultTableModel tabmode;
//public cetak_qr cetak_qr = null;
    /**
     * Creates new form popup_transbarang
     */

    public popBarang() {
           
        initComponents();
        tabProduk();
        
    }
        
        public void getId(String idmem,String idTrans){
        idMember=idmem;
        idTransaksi=idTrans;
        }
        
        public void tabProduk(){
        tabProduk.setAutoCreateRowSorter(true);
        Object[] Baris = {"Kode Produk","Nama Produk","Jenis Produk","Harga Produk","Stock"};
        tabmode = new DefaultTableModel(null,Baris);
        
        try {
            String sql="SELECT id_produk,nama_produk,jenis_produk,harga_produk,stok "
                    + "FROM produk "
                    + "order by id_produk asc" ;
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
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        b_pilih = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabProduk = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        perusahaan_b_edit5 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(254, 255, 230));

        jPanel12.setBackground(new java.awt.Color(21, 52, 98));

        b_pilih.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        b_pilih.setForeground(new java.awt.Color(231, 238, 126));
        b_pilih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        b_pilih.setText("   PILIH");
        b_pilih.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b_pilih.setIconTextGap(8);
        b_pilih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_pilihMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(b_pilih, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(b_pilih, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        tabProduk.setBackground(new java.awt.Color(254, 255, 230));
        tabProduk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabProduk.setForeground(new java.awt.Color(5, 32, 56));
        tabProduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null"
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
        tabProduk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabProdukKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabProduk);

        jPanel13.setBackground(new java.awt.Color(21, 52, 98));

        perusahaan_b_edit5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        perusahaan_b_edit5.setForeground(new java.awt.Color(231, 238, 126));
        perusahaan_b_edit5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_back_24px.png"))); // NOI18N
        perusahaan_b_edit5.setText("KEMBALI");
        perusahaan_b_edit5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        perusahaan_b_edit5.setIconTextGap(8);
        perusahaan_b_edit5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                perusahaan_b_edit5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(perusahaan_b_edit5, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(perusahaan_b_edit5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jLabel28.setBackground(new java.awt.Color(204, 204, 204));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(21, 52, 98));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("KUANTITAS");
        jLabel28.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(21, 52, 98)));

        txtJumlah.setForeground(new java.awt.Color(9, 10, 54));

        jPanel2.setBackground(new java.awt.Color(21, 52, 98));

        jLabel16.setBackground(new java.awt.Color(204, 204, 204));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 253, 227));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("TAMBAH PEMBELIAN PRODUK");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(20, 20, 20)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
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

    private void b_pilihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_pilihMouseClicked
        // TODO add your handling code here:
        String a = txtJumlah.getText();
        int jumlah=Integer.parseInt(a);
        
        String sql="insert into logs_transaksi_detail values (?,?,?,?)"; 
        String asql="select harga_produk from produk where id_produk='"+idProduk+"' ";
//        String bsql="update produk set stok=stok-"+a+" where id_produk='"+idProduk+"'";
        try{        
                    Statement stat = conn.createStatement(); ResultSet hasil = stat.executeQuery(asql);
                    while (hasil.next()){
                    harga=hasil.getLong(1);}
                    total=harga*jumlah;
                    
                    PreparedStatement astat=conn.prepareStatement(sql);  
                    astat.setString(1,idTransaksi);      
                    astat.setString(2,idProduk);
                    astat.setInt(3,jumlah);
                    astat.setLong(4,total);                  
                    astat.executeUpdate();
                    
//                   PreparedStatement bstat=conn.prepareStatement(bsql); 
//                   bstat.executeUpdate();
                    
                    
                    JOptionPane.showMessageDialog(null,"Data Produk Berhasil Ditambahkan");  
                    
                    this.dispose();                   
                  
        }
        //str = str.replaceAll("\\s.*", "");
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Data Gagal Disimpan : "+ e);
        }
        
        
        
    }//GEN-LAST:event_b_pilihMouseClicked

    private void tabProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabProdukMouseClicked
        // TODO add your handling code here:
        int bar=tabProduk.getSelectedRow();
        idProduk = tabmode.getValueAt(bar,0).toString();
       // codes[count]=tabmode3.getValueAt(bar,0).toString();
       
        
       
////        count=count+1;
//        
//       /** inisiasi arraylist**/ ArrayList kode_array = new ArrayList ();
//        
////        int bar=tab_transaksi.getSelectedRow();
////        kode_array.add(tabmode3.getValueAt(bar,0).toString());
//       
//        for (int i=0;i<kode_array.size();i++){
//            kode_arr=kode_arr+kode_array.get(i);
//        }
//        txt_test.setText(kode_arr);
    }//GEN-LAST:event_tabProdukMouseClicked

    private void perusahaan_b_edit5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_perusahaan_b_edit5MouseClicked

        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_perusahaan_b_edit5MouseClicked

    private void tabProdukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabProdukKeyPressed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_tabProdukKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
         Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();

        // membuat titik x dan y
        int x = layar.width / 2  - this.getSize().width / 2;
        int y = layar.height / 2 - this.getSize().height / 2;

        this.setLocation(x, y);
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel b_pilih;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel perusahaan_b_edit5;
    private javax.swing.JTable tabProduk;
    private javax.swing.JTextField txtJumlah;
    // End of variables declaration//GEN-END:variables
}

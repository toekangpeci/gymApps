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
import koneksi.koneksi;


/**
 *
 * @author PAKUAN-IT
 */
public class popHarga extends javax.swing.JFrame {
private DefaultTableModel tabmode3;
private Connection conn = new koneksi().connect();
private view.home hom = new view.home();
public String kode_user;
int count,para;


    /**
     * Creates new form popup_transbarang
     */

    public void getId(String id){
        kode_user=id;
     }
     
    public popHarga() {
           
        initComponents();
                // TODO add your handling code here:
         harga();
            
           }
    
    public void harga(){
     
             String sql="select harga "
                     + "from instruktur_harga order by id asc ";
            
         try{
          Statement st = conn.createStatement(); ResultSet ars = st.executeQuery(sql); 
         while (ars.next()){
          txt_harga.setText(ars.getString(1));}
         }
         catch (SQLException e){
            JOptionPane.showMessageDialog(null,"data gagal dipanggil : "+ e);
            }}
    
        
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
        jPanel13 = new javax.swing.JPanel();
        perusahaan_b_edit5 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        perusahaan_b_edit6 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        txt_harga = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(254, 255, 230));

        jPanel12.setBackground(new java.awt.Color(5, 32, 56));

        b_pilih.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        b_pilih.setForeground(new java.awt.Color(231, 238, 126));
        b_pilih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_save_24px.png"))); // NOI18N
        b_pilih.setText("   CETAK");
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

        jPanel13.setBackground(new java.awt.Color(5, 32, 56));

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

        jPanel14.setBackground(new java.awt.Color(21, 52, 98));

        perusahaan_b_edit6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        perusahaan_b_edit6.setForeground(new java.awt.Color(255, 253, 227));
        perusahaan_b_edit6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        perusahaan_b_edit6.setText("HARGA PERSONAL TRAINER PER-SESI");
        perusahaan_b_edit6.setIconTextGap(8);
        perusahaan_b_edit6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                perusahaan_b_edit6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(perusahaan_b_edit6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(perusahaan_b_edit6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel69.setBackground(new java.awt.Color(32, 74, 86));
        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(32, 74, 86));
        jLabel69.setText("HARGA TRAINER PER-SESI");

        txt_harga.setForeground(new java.awt.Color(9, 10, 54));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69)
                .addGap(18, 18, 18)
                .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(txt_harga))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
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
        
        String asql="insert into instruktur_harga (harga) values (?)";       
       // String atasan = c.replaceAll("\\s.*", "");
        try{
            PreparedStatement astat=conn.prepareStatement(asql); 
            astat.setString(1,txt_harga.getText());
            astat.executeUpdate();
            JOptionPane.showMessageDialog(null,"Data Harga Trainer Per-sesi Berhasil diubah");}
          catch (SQLException e){
            JOptionPane.showMessageDialog(null,"data gagal disimpan : "+ e);
        }

//        
     
    }//GEN-LAST:event_b_pilihMouseClicked

    private void perusahaan_b_edit5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_perusahaan_b_edit5MouseClicked

        // TODO add your handling code here:
       this.dispose();
       

    }//GEN-LAST:event_perusahaan_b_edit5MouseClicked

    private void perusahaan_b_edit6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_perusahaan_b_edit6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_perusahaan_b_edit6MouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();

        // membuat titik x dan y
        int x = layar.width / 2  - this.getSize().width / 2;
        int y = layar.height / 2 - this.getSize().height / 2;

        this.setLocation(x, y);
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel b_pilih;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JLabel perusahaan_b_edit5;
    private javax.swing.JLabel perusahaan_b_edit6;
    private javax.swing.JTextField txt_harga;
    // End of variables declaration//GEN-END:variables
}

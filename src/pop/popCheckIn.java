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
public class popCheckIn extends javax.swing.JFrame {
private DefaultTableModel tabmode3;
private Connection conn = new koneksi().connect();
private view.home hom = new view.home();
public String kode_user;
int count,para,a,sesi;


    /**
     * Creates new form popup_transbarang
     */

    public void getId(String id){
        kode_user=id;
     }
     
    public popCheckIn() {
           
        initComponents();
                // TODO add your handling code here:
         combo_member();
            
           }
    
    public void combo_member() {
        cbKode.removeAllItems();
        cbKode.addItem(" - Pilih Member - ");  
        try{
            String sql = "select id_member,nama from member_gym";           
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery(sql); 
            while (r.next()) {
            cbKode.addItem(r.getString(1)+" - "+r.getString(2));}
        }
        catch (Exception e) {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        b_pilih = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        perusahaan_b_edit5 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        perusahaan_b_edit6 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        cbKode = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        rYes = new javax.swing.JRadioButton();
        rNo = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(254, 255, 230));

        jPanel12.setBackground(new java.awt.Color(21, 52, 98));

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

        jPanel14.setBackground(new java.awt.Color(21, 52, 98));

        perusahaan_b_edit6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        perusahaan_b_edit6.setForeground(new java.awt.Color(255, 253, 227));
        perusahaan_b_edit6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        perusahaan_b_edit6.setText("CHECK-IN HARIAN MEMBER");
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
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setText("ID MEMBER GYM");
        jLabel69.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(21, 52, 98)));

        cbKode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel70.setBackground(new java.awt.Color(32, 74, 86));
        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(32, 74, 86));
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setText("SESI PRIVATE TRAINER");
        jLabel70.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(21, 52, 98)));

        buttonGroup1.add(rYes);
        rYes.setText("IYA");

        buttonGroup1.add(rNo);
        rNo.setText("TIDAK");
        rNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rNoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbKode, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(rYes, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rNo)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbKode))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(rYes)
                    .addComponent(rNo))
                .addGap(29, 29, 29)
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
        String b=cbKode.getSelectedItem().toString();
      
        if (rYes.isSelected()){a=1;}
        else if (rNo.isSelected()){a=0;}
        
        String trimmedMember = b.replaceAll(" .*", "");
        String sql="insert into logs_checkin (id_user,trainer) values (?,?)";       
        String asql="select id from logs_membership "
                + "where (id_member='"+trimmedMember+"' and (curdate() between mulai and akhir)) "
                + "order by id desc limit 1 ";
        String bsql="update logs_sesi_trainer set sesi=sesi-1 where id_member='"+trimmedMember+"'";
        String csql="select sesi from logs_sesi_trainer where id_member='"+trimmedMember+"' limit 1";
       // String atasan = c.replaceAll("\\s.*", "");
        try{ 
            Statement sc = conn.createStatement(); ResultSet rc = sc.executeQuery(csql);
            while (rc.next()){sesi=rc.getInt(1);}
            
             if(a==1){
                 if(sesi>0){
                Statement sa = conn.createStatement(); ResultSet ra = sa.executeQuery(asql);
                if (ra.next()) {PreparedStatement astat=conn.prepareStatement(sql); 
                astat.setString(1,trimmedMember);
                astat.setString(2,"Iya");
                astat.executeUpdate();           
                
                PreparedStatement bstat=conn.prepareStatement(bsql); 
                bstat.executeUpdate();
                
                JOptionPane.showMessageDialog(null,"Berhasil Checkin Dengan Private Trainer, Member ID  : "+b);
                sesi=0;}              
                else {JOptionPane.showMessageDialog(null,"Data Membership Tidak Ditemukan/ Membership Kadaluwarsa"); }
                 }
                 else{JOptionPane.showMessageDialog(null,"Gagal Check-in : Sesi Trainer Tidak Cukup.");}
             }
             else if (a==0) {
                 Statement sa = conn.createStatement(); ResultSet ra = sa.executeQuery(asql);
                 if (ra.next()) {PreparedStatement astat=conn.prepareStatement(sql); 
                astat.setString(1,trimmedMember);
                astat.setString(2,"Tidak");
                astat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Berhasil Checkin, Member ID  : "+b);}
                else {JOptionPane.showMessageDialog(null,"Data Membership Tidak Ditemukan/ Membership Kadaluwarsa.");}
                 
             }
        }
            
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

    private void rNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rNoActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel b_pilih;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbKode;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JLabel perusahaan_b_edit5;
    private javax.swing.JLabel perusahaan_b_edit6;
    private javax.swing.JRadioButton rNo;
    private javax.swing.JRadioButton rYes;
    // End of variables declaration//GEN-END:variables
}

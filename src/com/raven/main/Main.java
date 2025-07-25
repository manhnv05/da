package com.raven.main;

import com.raven.event.EventMenu;
import javax.swing.JComponent;
import model.nguoidung.NhanVien;
//import java.awt.Color;
//import javax.swing.JOptionPane;

public class Main extends javax.swing.JFrame {
    private BanHangForm banHangForm = new BanHangForm();
     private NhanVien nhanVien;
    public Main(NhanVien nv) {
        this.nhanVien = nv;
        initComponents();
        init();
        System.out.println("Đăng nhập bởi nhân viên id: " + nhanVien.getId());
        lblUserName.setText(nhanVien.getTenNhanVien());
        lblChucVu.setText(nhanVien.getChucVu());
        
    }
    private void init() {
    menu2.initMoving(this);
    menu2.addEventMenu(new EventMenu() {
        @Override
        public void menuIndexChange(int index) {
            String chucVu = nhanVien.getChucVu().trim();
            // Nếu là Quản lý thì cho truy cập tất cả
            if (chucVu.equalsIgnoreCase("Quản lý")) {
                if (index == 0) {
                    setForm(new QuanLySanPham());
                } else if (index == 1) {
                    setForm(banHangForm);
                } else if (index == 2) {
                    banHangForm.capNhatTongTien();
                    setForm(new QuanLyHoaDon(banHangForm));
                } else if (index == 3) {
                    setForm(new QuanLyGiamGia());
                } else if (index == 4) {
                    setForm(new QuanLyKhachHang());
                } else if (index == 5) {
                    setForm(new QuanLyNhanVien());
                } else if (index == 6) {
                    logout();
                }
            }
            // Nếu là Nhân viên chỉ cho phép bán hàng và hóa đơn
            else if (chucVu.equalsIgnoreCase("Nhân viên")) {
                if (index == 1) {
                    setForm(banHangForm);
                } else if (index == 2) {
                    banHangForm.capNhatTongTien();
                    setForm(new QuanLyHoaDon(banHangForm));
                } else if (index == 6) {
                    logout();
                } else {
                    // Nếu chọn vào các mục không được phép
                    javax.swing.JOptionPane.showMessageDialog(Main.this,
                        "Bạn không có quyền truy cập chức năng này!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                }
            }
            System.out.println("index: " + index + " - quyền: " + chucVu);
        }
    });
}
    
    

    private void setForm(JComponent com) {
        mainJpanel.removeAll();
        mainJpanel.add(com);
        mainJpanel.repaint();
        mainJpanel.revalidate();
    }

    private void logout() {
        // Close the current window
        new login().setVisible(true);
        this.dispose();

        // Open the login form
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.raven.swing.PanelBorder();
        menu2 = new com.raven.component.Menu();
        mainJpanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblUserName = new javax.swing.JLabel();
        lblChucVu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainJpanel.setOpaque(false);
        mainJpanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblUserName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUserName.setText("Mạnh");

        lblChucVu.setText("Admin");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblChucVu)
                    .addComponent(lblUserName))
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblUserName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblChucVu)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainJpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1047, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu2, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainJpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainJpanel.getAccessibleContext().setAccessibleParent(mainJpanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JPanel mainJpanel;
    private com.raven.component.Menu menu2;
    private com.raven.swing.PanelBorder panelBorder1;
    // End of variables declaration//GEN-END:variables
}

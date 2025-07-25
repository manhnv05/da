/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.nguoidung.NhanVien;
import repo.nguoidung.NhanVienRepo;

/**
 *
 * @author nguye
 */
public class QuanLyNhanVien extends javax.swing.JPanel {
private NhanVienRepo nhanVienRepo;
private DefaultTableModel dtmNhanVien;
    /**
     * Creates new form QuanLyNhanVien
     */
    public QuanLyNhanVien() {
        initComponents();
        textSearch();
        nhanVienRepo = new NhanVienRepo();
        dtmNhanVien = (DefaultTableModel) tblNhanVien.getModel();
        fillTableNhanVien(nhanVienRepo.getAll());
    }
    
        public void fillTableNhanVien(ArrayList<NhanVien> list){
        dtmNhanVien.setRowCount(0);
        for (NhanVien nhanVien : list) {
            dtmNhanVien.addRow(new Object[]{
                nhanVien.getId(),
                nhanVien.getMaNhanVien(),
                nhanVien.getTenNhanVien(),
                nhanVien.getChucVu() != null && nhanVien.getChucVu().equalsIgnoreCase("Quản lí") ? "Quản lí" : "Nhân viên",
                nhanVien.getNgaySinh() == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(nhanVien.getNgaySinh()),
                nhanVien.getSoDienThoai(),
                nhanVien.getEmail(),
                nhanVien.getDiaChi(),
                nhanVien.getTaiKhoan(),
                nhanVien.getMatKhau(),
                nhanVien.getGioiTinh() != null && nhanVien.getGioiTinh() ? "Nam" : "Nữ",
                nhanVien.getTrangThai() != null && nhanVien.getTrangThai() ? "Đang làm việc" : "Ngừng làm việc"
            });
        }
    }

    private NhanVien getForm(){
        String ma = txtMaNV.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập mã");
            txtMaNV.requestFocus();
            return null;
        }
        String tenNV = txtTenNV.getText().trim();
        if (tenNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập tên");
            txtTenNV.requestFocus();
            return null;
        }
        String sdt = txtSDT.getText().trim();
        if (sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập số điện thoại");
            txtSDT.requestFocus();
            return null;
        }
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập email");
            txtEmail.requestFocus();
            return null;
        }
        String diaChi = txtDC.getText().trim();
        if (diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập địa chỉ");
            txtDC.requestFocus();
            return null;
        }
        String ngaySinhStr = txtNgaySinh.getText().trim();
        Date ngaySinh = null;
        if (ngaySinhStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập ngày sinh");
            txtNgaySinh.requestFocus();
            return null;
        } else {
            try {
                ngaySinh = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinhStr);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày sinh chưa đúng (yyyy-MM-dd)");
                txtNgaySinh.requestFocus();
                return null;
            }
        }
        String user = txtUsername.getText().trim();
        if (user.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập tên đăng nhập");
            txtUsername.requestFocus();
            return null;
        }
        String password = txtPassword.getText().trim();
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập mật khẩu");
            txtUsername.requestFocus();
            return null;
        }
        String chucVu = rdoQuanLi.isSelected() ? "Quản lí" : "Nhân viên";
        Boolean gioiTinh = rdoNam.isSelected();
        Boolean trangThai = true; // mặc định đang làm việc
        return new NhanVien(null, ma, tenNV, user, password, sdt, email, ngaySinh, chucVu, diaChi, gioiTinh, trangThai);
    }

    public void showTable(){
        int selectedIndex = tblNhanVien.getSelectedRow();
        txtMaNV.setText(tblNhanVien.getValueAt(selectedIndex, 1).toString());
        txtTenNV.setText(tblNhanVien.getValueAt(selectedIndex, 2).toString());
        txtNgaySinh.setText(tblNhanVien.getValueAt(selectedIndex, 4).toString());
        txtSDT.setText(tblNhanVien.getValueAt(selectedIndex, 5).toString());
        txtEmail.setText(tblNhanVien.getValueAt(selectedIndex, 6).toString());
        txtDC.setText(tblNhanVien.getValueAt(selectedIndex, 7).toString());
        if(tblNhanVien.getValueAt(selectedIndex, 3).toString().equals("Quản lí")) {
            rdoQuanLi.setSelected(true);
        }else {
            rdoNhanVien.setSelected(true);
        }
        if(tblNhanVien.getValueAt(selectedIndex, 8).toString().equals("Nam")) {
            rdoNam.setSelected(true);
        }else {
            rdoNu.setSelected(true);
        }
        txtUsername.setText(tblNhanVien.getValueAt(selectedIndex, 9).toString());
        txtPassword.setText(tblNhanVien.getValueAt(selectedIndex, 10).toString());

    }

    private void textSearch() {
        txtTimKiemNV.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { timKiem(); }
            @Override
            public void removeUpdate(DocumentEvent e) { timKiem(); }
            @Override
            public void changedUpdate(DocumentEvent e) { timKiem(); }
        });
    }

    private void timKiem() {
        String timKiem = txtTimKiemNV.getText().trim();
        fillTableNhanVien(nhanVienRepo.searchNV(timKiem));
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtDC = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        rdoQuanLi = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        txtTimKiemNV = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Quản lí nhân viên");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Mã nhân viên");

        jLabel3.setText("Tên nhân viên");

        jLabel4.setText("Số điện thoại");

        jLabel5.setText("Email");

        jLabel6.setText("Địa chỉ");

        jLabel8.setText("Vai trò");

        buttonGroup1.add(rdoQuanLi);
        rdoQuanLi.setText("Quản lí");

        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setText("Nhân viên");

        jLabel9.setText("Giới tính");

        buttonGroup2.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup2.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel7.setText("Ngày sinh");

        btnMoi.setText("Moi");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setText("Sua");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setText("Them ");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnThem)
                .addGap(55, 55, 55)
                .addComponent(btnSua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(btnXoa)
                .addGap(33, 33, 33)
                .addComponent(btnMoi)
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoi)
                    .addComponent(btnXoa)
                    .addComponent(btnSua)
                    .addComponent(btnThem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setText("Tên đăng nhập");

        jLabel12.setText("Mật khẩu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(77, 77, 77)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNV)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                        .addGap(95, 95, 95)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel11))
                        .addGap(76, 76, 76)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDC, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                                .addComponent(txtEmail))
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel12)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoNam)
                            .addComponent(rdoQuanLi))
                        .addGap(77, 77, 77)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoNhanVien)
                            .addComponent(rdoNu)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                        .addComponent(txtNgaySinh))
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(rdoQuanLi)
                    .addComponent(rdoNhanVien)
                    .addComponent(jLabel11)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Ma nhan vien", "Ten nhan vien", "Chuc vu", "Ngày sinh", "SDT", "Email", "Dia chi", "Ten dang nhap", "Mật khẩu", "Gioi tinh", "Trang thai"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(0).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(1).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(2).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(3).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(4).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(5).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(6).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(7).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(8).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(9).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(10).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(11).setResizable(false);
        }

        jLabel10.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTimKiemNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(444, 444, 444))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        showTable();
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
         if(getForm()!= null){
           if (nhanVienRepo.checkTrung(txtMaNV.getText())!=null) {
               JOptionPane.showMessageDialog(this, "trung ma");
               txtMaNV.requestFocus();
           }else{
               nhanVienRepo.themNhanVien(getForm());
               JOptionPane.showConfirmDialog(this, "Ban co muon them khong");
               fillTableNhanVien(nhanVienRepo.getAll());
               JOptionPane.showMessageDialog(this, "them thanh cong");
           }    
       }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
              String ma = txtMaNV.getText().trim();
        if (getForm()!= null) {
            if (nhanVienRepo.suaNhanVien(getForm(), ma)) {
                JOptionPane.showMessageDialog(this, "Sua thanh cong");
                this.fillTableNhanVien(nhanVienRepo.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Sua that bai");
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
          NhanVien nv = nhanVienRepo.getAll().get(tblNhanVien.getSelectedRow());
        nhanVienRepo.delete(nv.getMaNhanVien());
        fillTableNhanVien(nhanVienRepo.getAll());
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtNgaySinh.setText("");
        txtDC.setText("");
        buttonGroup1.clearSelection();
        buttonGroup2.clearSelection();
    }//GEN-LAST:event_btnMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoQuanLi;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDC;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiemNV;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}

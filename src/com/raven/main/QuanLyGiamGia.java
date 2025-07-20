/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.main;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.giamgia.GiamGia;
import repo.giamgia.GiamGiaRepo;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JTextField;

/**
 *
 * @author nguye
 */
public final class QuanLyGiamGia extends javax.swing.JPanel {
 private final GiamGiaRepo giamGiaRepo;
    private final DefaultTableModel dtmGG;
    private final DecimalFormat df = new DecimalFormat("###,###");
    /**
     * Creates new form QuanLyKhachHang
     */
    public QuanLyGiamGia() {
        initComponents();
        giamGiaRepo = new GiamGiaRepo();
        dtmGG = (DefaultTableModel) tblGiamGia.getModel();
        fillGGTable(giamGiaRepo.getAll());
        this.textSearch();
        formatInputMoney(txtGiaTriTT);
        formatInputMoney(txtGiaTriTD);
        formatInputMoney(txtGiamPT);
        // Nếu là % thì không cần
    }

    // Format số tiền khi nhập vào các JTextField
private void formatInputMoney(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            private boolean isFormatting = false;

            private void format() {
                if (isFormatting) return;
                isFormatting = true;
                int caretPos = field.getCaretPosition();
                String text = field.getText().replaceAll("[^\\d]", "");
                if (!text.isEmpty()) {
                    try {
                        long value = Long.parseLong(text);
                        String formatted = df.format(value);
                        if (!formatted.equals(field.getText())) {
                            field.setText(formatted);
                            int newPos = formatted.length() - (field.getText().length() - caretPos);
                            newPos = Math.max(0, Math.min(formatted.length(), newPos));
                            field.setCaretPosition(newPos);
                        }
                    } catch (Exception ex) {}
                } else {
                    field.setText("");
                }
                isFormatting = false;
            }
            @Override
            public void insertUpdate(DocumentEvent e) { format(); }
            @Override
            public void removeUpdate(DocumentEvent e) { format(); }
            @Override
            public void changedUpdate(DocumentEvent e) { format(); }
        });
    }

    // Đổ dữ liệu lên bảng
    public void fillGGTable(ArrayList<GiamGia> list) {
        dtmGG.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (GiamGia giamGia : list) {
            String ngayBD = giamGia.getNgayBatDau() != null ? sdf.format(giamGia.getNgayBatDau()) : "";
            String ngayKT = giamGia.getNgayKetThuc() != null ? sdf.format(giamGia.getNgayKetThuc()) : "";
            String giaTriTT = df.format(giamGia.getGiaTriToiThieu());
            String giaTriTD = df.format(giamGia.getGiaTriToiDa());
            String giamPhanTram = giamGia.getPhanTramGiam() % 1 == 0 ?
                String.valueOf((int)giamGia.getPhanTramGiam()) + "%" : String.format("%.2f%%", giamGia.getPhanTramGiam());
            dtmGG.addRow(new Object[]{
                giamGia.getId(),
                giamGia.getMaGiamGia(),
                giamGia.getTenGiamGia(),
                giamPhanTram,
                giaTriTT,
                giaTriTD,
                ngayBD,
                ngayKT,
                giamGia.getTrangThai() == 1 ? "Đang diễn ra" : "Đã kết thúc",
            }); 
        }
    }

    // Lấy dữ liệu từ form để tạo GiamGia
    private GiamGia getForm(){
        String ma = txtMaGG.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập mã");
            txtMaGG.requestFocus();
            return null;
        }
        String ten = txtTenGG.getText().trim();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập tên");
            txtTenGG.requestFocus();
            return null;
        }
        Integer giaTriToiThieu = -1;
        try {
            String rawTT = txtGiaTriTT.getText().replaceAll("[^\\d]", "");
            giaTriToiThieu = Integer.parseInt(rawTT);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập giá trị tối thiểu");
            txtGiaTriTT.requestFocus();
            return null;
        }
        Integer giaTriToiDa = 0;
        try {
            String rawTD = txtGiaTriTD.getText().replaceAll("[^\\d]", "");
            giaTriToiDa = Integer.parseInt(rawTD);
            if (giaTriToiDa <= 499) {
                JOptionPane.showMessageDialog(this, "Giá trị tối đa phải > 499");
                txtGiaTriTD.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập giá trị tối đa");
            txtGiaTriTD.requestFocus();
            return null;
        }

        float giamPhanTram = 0;
        try {
            String input = txtGiamPT.getText().replaceAll("[^\\d.]", "");
            giamPhanTram = Float.parseFloat(input);
            if (giamPhanTram <= 0) {
                JOptionPane.showMessageDialog(this, "Giảm phần trăm phải lớn hơn 0");
                txtGiamPT.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập giá trị phần trăm hợp lệ (vd: 20 hoặc 20%)");
            txtGiamPT.requestFocus();
            return null;
        }
        Date ngayBD = txtNgayBD.getDate();
        Date ngayKT = txtNgayKT.getDate();

        if (ngayBD.after(ngayKT) || !ngayBD.before(ngayKT)) {
            if (ngayKT == null) {
                JOptionPane.showMessageDialog(this, "Mời bạn nhập ngày tháng");
                txtNgayKT.requestFocus();
                return null;
            } else {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc");
                txtNgayKT.requestFocus();
                return null;
            }
        }
        return new GiamGia(null, ma, ten, giamPhanTram, giaTriToiThieu, giaTriToiDa, ngayBD, ngayKT, 1);
    }

    // Hiển thị lên form khi chọn dòng
    private void showTable(int index) {
        GiamGia gg = giamGiaRepo.getAll().get(index);
        txtMaGG.setText(gg.getMaGiamGia());
        txtTenGG.setText(gg.getTenGiamGia());
        txtGiamPT.setText(gg.getPhanTramGiam() % 1 == 0 ?
            String.valueOf((int)gg.getPhanTramGiam()) : String.format("%.2f", gg.getPhanTramGiam()));
        txtGiaTriTT.setText(df.format(gg.getGiaTriToiThieu()));
        txtGiaTriTD.setText(df.format(gg.getGiaTriToiDa()));
        txtNgayBD.setDate(gg.getNgayBatDau());
        txtNgayKT.setDate(gg.getNgayKetThuc());
    }

    // Tìm kiếm realtime
    private void textSearch() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { timKiem(); }
            @Override
            public void removeUpdate(DocumentEvent e) { timKiem(); }
            @Override
            public void changedUpdate(DocumentEvent e) { timKiem(); }
        });
    }

    private void timKiem() {
        String timKiem = txtTimKiem.getText().trim();
        fillGGTable(giamGiaRepo.search(timKiem));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaGG = new javax.swing.JTextField();
        txtTenGG = new javax.swing.JTextField();
        txtGiaTriTT = new javax.swing.JTextField();
        txtGiaTriTD = new javax.swing.JTextField();
        txtGiamPT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNgayBD = new com.toedter.calendar.JDateChooser();
        txtNgayKT = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnTrangThai = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGiamGia = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbbLoc = new javax.swing.JComboBox<>();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản lí giảm giá");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Mã");

        jLabel3.setText("Tên");

        jLabel4.setText("Giá trị giảm");

        jLabel5.setText("Giá trị tối thiểu");

        jLabel6.setText("Giá trị tối đa");

        txtGiaTriTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTriTTActionPerformed(evt);
            }
        });

        jLabel7.setText("Ngày bắt đầu");

        jLabel8.setText("Ngày kết thúc");

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnTrangThai.setText("Đã kết thúc");
        btnTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangThaiActionPerformed(evt);
            }
        });

        jButton1.setText("Mới");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnExcel.setText("Excel");
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaGG)
                    .addComponent(txtTenGG)
                    .addComponent(txtNgayBD, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6))
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGiaTriTT)
                    .addComponent(txtGiaTriTD)
                    .addComponent(txtNgayKT, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtGiamPT, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(66, 66, 66))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaTriTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnThem))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTenGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaTriTD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiamPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(24, 32, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgayKT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addComponent(txtNgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8))
                        .addGap(81, 81, 81))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnTrangThai)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(btnExcel)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblGiamGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã giảm giá", "Tên giảm giá", "Giá trị giảm", "Giá trị tối thiểu", "Giá trị tối đa", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"
            }
        ));
        tblGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGiamGiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGiamGia);

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jLabel9.setText("Tìm kiếm");

        cbbLoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đang diễn ra", "Đã kết thúc" }));
        cbbLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(cbbLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(cbbLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(455, 455, 455))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtGiaTriTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTriTTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTriTTActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
         if(getForm()!= null){
           if (giamGiaRepo.checkTrung(txtMaGG.getText())!=null) {
               JOptionPane.showMessageDialog(this, "Trùng mã");
               txtMaGG.requestFocus();
           }else{
               giamGiaRepo.themGiamGia(getForm());
               JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không");
               fillGGTable(giamGiaRepo.getAll());
               if (BanHangForm.instance !=null) {
                   ArrayList<GiamGia> list = giamGiaRepo.getAll();
                   BanHangForm.instance.fillGiamGia(list);
               }
               JOptionPane.showMessageDialog(this, "Thêm thành công");
           }
         
       }
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGiamGiaMouseClicked
        // TODO add your handling code here:
         showTable(tblGiamGia.getSelectedRow());
    }//GEN-LAST:event_tblGiamGiaMouseClicked

    private void btnTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangThaiActionPerformed
        // TODO add your handling code here:
        GiamGia gg = giamGiaRepo.getAll().get(tblGiamGia.getSelectedRow());
        giamGiaRepo.delete(gg.getMaGiamGia());
        fillGGTable(giamGiaRepo.getAll());
        if (BanHangForm.instance !=null) {
                   ArrayList<GiamGia> list = giamGiaRepo.getAll();
                   BanHangForm.instance.fillGiamGia(list);
               }
    }//GEN-LAST:event_btnTrangThaiActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
           String ma = txtMaGG.getText().trim();
        if (getForm()!= null) {
            if (giamGiaRepo.update(getForm(), ma)) {
                JOptionPane.showMessageDialog(this, "Sửa thành công");
                this.fillGGTable(giamGiaRepo.getAll());
                if (BanHangForm.instance !=null) {
                   ArrayList<GiamGia> list = giamGiaRepo.getAll();
                   BanHangForm.instance.fillGiamGia(list);
               }
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại");
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        txtMaGG.setText("");
        txtTenGG.setText("");
        txtGiamPT.setText("");
        txtGiaTriTT.setText("");
        txtGiaTriTD.setText("");
        txtNgayBD.setDate(null);
        txtNgayKT.setDate(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbbLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLocActionPerformed
        // TODO add your handling code here:
         String selectedItem = cbbLoc.getSelectedItem().toString();
        if (selectedItem.equals("Đang diễn ra")) {
            this.fillGGTable(giamGiaRepo.getAllTrangThai(1));
        } else if (selectedItem.equals("Đã kết thúc")) {
            this.fillGGTable(giamGiaRepo.getAllTrangThai(2));
        } else if (selectedItem.equals("Tất cả")) {
            this.fillGGTable(giamGiaRepo.getAll());
        } else {
            JOptionPane.showMessageDialog(this, "Trạng thái không hợp lệ");
        }
    }//GEN-LAST:event_cbbLocActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExcelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTrangThai;
    private javax.swing.JComboBox<String> cbbLoc;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGiamGia;
    private javax.swing.JTextField txtGiaTriTD;
    private javax.swing.JTextField txtGiaTriTT;
    private javax.swing.JTextField txtGiamPT;
    private javax.swing.JTextField txtMaGG;
    private com.toedter.calendar.JDateChooser txtNgayBD;
    private com.toedter.calendar.JDateChooser txtNgayKT;
    private javax.swing.JTextField txtTenGG;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

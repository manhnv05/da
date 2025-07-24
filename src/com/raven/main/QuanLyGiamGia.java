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
import javax.swing.ImageIcon;
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
        txtNgayBD.setDateFormatString("dd/MM/yyyy");
        txtNgayKT.setDateFormatString("dd/MM/yyyy");
        txtNgayBD1.setDateFormatString("dd/MM/yyyy");
        txtNgayKT1.setDateFormatString("dd/MM/yyyy");
        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/icon/schedule.png"));
        ImageIcon calendarIcon = new ImageIcon(
            rawIcon.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)
        );
        txtNgayBD.setIcon(calendarIcon);
        txtNgayKT.setIcon(calendarIcon);
        txtNgayBD1.setIcon(calendarIcon);
        txtNgayKT1.setIcon(calendarIcon);
        Date today = removeTime(new Date());
        Date tomorrow = new Date(today.getTime() + 24*60*60*1000L);
        txtNgayBD.setDate(today);
        txtNgayKT.setDate(tomorrow);
        txtNgayBD1.setDate(today);
        txtNgayKT1.setDate(tomorrow);
        txtNgayBD.setMinSelectableDate(today);
        txtNgayKT.setMinSelectableDate(today);
        cbbLoc.setVisible(false);
        lbltt.setVisible(false);
        cbbLoaiGiam.setVisible(false);
        lbllg.setVisible(false);
        txtNgayBD1.setVisible(false);
        lblnbd.setVisible(false);
        txtNgayKT1.setVisible(false);
        lblnkt.setVisible(false);
        
        txtNgayBD1.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) locTheoNgay();
        });
        txtNgayKT1.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) locTheoNgay();
        });
    }

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
    int stt = 1; // Biến đếm STT
    for (GiamGia giamGia : list) {
        String ngayBD = giamGia.getNgayBatDau() != null ? sdf.format(giamGia.getNgayBatDau()) : "";
        String ngayKT = giamGia.getNgayKetThuc() != null ? sdf.format(giamGia.getNgayKetThuc()) : "";
        String giaTriTT = giamGia.getGiaTriToiThieu() != null ? df.format(giamGia.getGiaTriToiThieu()) : "";
        String giaTriTD = giamGia.getGiaTriToiDa() != null ? df.format(giamGia.getGiaTriToiDa()) : "";

        String giamGiaStr;
        if ("Phần trăm".equalsIgnoreCase(giamGia.getLoaiGiamGia())) {
            giamGiaStr = giamGia.getGiaTriGiam() % 1 == 0
                ? String.valueOf(giamGia.getGiaTriGiam().intValue()) + "%" 
                : String.format("%.2f%%", giamGia.getGiaTriGiam());
        } else if ("Giá Tiền".equalsIgnoreCase(giamGia.getLoaiGiamGia())) {
            giamGiaStr = df.format(giamGia.getGiaTriGiam()) + " đ";
        } else {
            giamGiaStr = String.valueOf(giamGia.getGiaTriGiam());
        }

        dtmGG.addRow(new Object[]{
            stt++, // STT tăng dần
            giamGia.getMaGiamGia(),
            giamGia.getTenGiamGia(),
            giamGia.getLoaiGiamGia(),
            giamGiaStr,
            giaTriTT + " đ",
            giaTriTD + " đ",
            giamGia.getSoLuong() != null ? giamGia.getSoLuong() : "",
            ngayBD,
            ngayKT,
            giamGia.getTrangThai() != null && giamGia.getTrangThai() == 1 ? "Đang diễn ra" : "Đã kết thúc",
        });
    }
}

    // Lấy dữ liệu từ form để tạo GiamGia
     private GiamGia getForm() {
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
        // Số lượng
        Integer soLuong = null;
        try {
            String rawSL = txtSoLuong.getText().replaceAll("[^\\d]", "");
            soLuong = Integer.parseInt(rawSL);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
                txtSoLuong.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập số lượng hợp lệ");
            txtSoLuong.requestFocus();
            return null;
        }

        // Loại giảm giá
        String loaiGiamGia = null;
        if (rboPhanTram.isSelected()) {
            loaiGiamGia = "Phần trăm";
        } else if (rboTien.isSelected()) {
            loaiGiamGia = "Giá tiền";
        } else {
            JOptionPane.showMessageDialog(this, "Chọn loại giảm giá");
            return null;
        }

        // Giá trị giảm (decimal)
        Double giaTriGiam = null;
        try {
            String input = txtGiamPT.getText().replaceAll("[^\\d.]", "");
            giaTriGiam = Double.parseDouble(input);
            if (giaTriGiam <= 0) {
                JOptionPane.showMessageDialog(this, "Giá trị giảm phải lớn hơn 0");
                txtGiamPT.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập giá trị giảm hợp lệ");
            txtGiamPT.requestFocus();
            return null;
        }

        // Giá trị tối thiểu
        Double giaTriToiThieu = null;
        try {
            String rawTT = txtGiaTriTT.getText().replaceAll("[^\\d.]", "");
            giaTriToiThieu = Double.parseDouble(rawTT);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập giá trị tối thiểu");
            txtGiaTriTT.requestFocus();
            return null;
        }

        // Giá trị tối đa
        Double giaTriToiDa = null;
        try {
            String rawTD = txtGiaTriTD.getText().replaceAll("[^\\d.]", "");
            giaTriToiDa = Double.parseDouble(rawTD);
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

        Date ngayBD = txtNgayBD.getDate();
        Date ngayKT = txtNgayKT.getDate();
        Date today = removeTime(new Date());

        // --- Bổ sung validate ngày ---
        if (ngayBD == null) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập ngày bắt đầu");
            txtNgayBD.requestFocus();
            return null;
        }
        if (ngayKT == null) {
            JOptionPane.showMessageDialog(this, "Mời bạn nhập ngày kết thúc");
            txtNgayKT.requestFocus();
            return null;
        }
//        if (removeTime(ngayBD).before(today)) {
//            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải từ hôm nay trở đi");
//            txtNgayBD.requestFocus();
//            return null;
//        }
//        if (removeTime(ngayKT).before(today)) {
//            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải từ hôm nay trở đi");
//            txtNgayKT.requestFocus();
//            return null;
//        }
        if (!ngayBD.before(ngayKT)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc");
            txtNgayKT.requestFocus();
            return null;
        }
        // --- END Bổ sung validate ngày ---

        // Trạng thái mặc định là 1 (đang diễn ra)
        return GiamGia.builder()
                .id(null)
                .maGiamGia(ma)
                .tenGiamGia(ten)
                .soLuong(soLuong)
                .loaiGiamGia(loaiGiamGia)
                .giaTriGiam(giaTriGiam)
                .giaTriToiThieu(giaTriToiThieu)
                .giaTriToiDa(giaTriToiDa)
                .ngayBatDau(ngayBD)
                .ngayKetThuc(ngayKT)
                .trangThai(1)
                .build();
    }
    
    private Date removeTime(Date date) {
        if (date == null) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(sdf.format(date));
        } catch (Exception e) {
            return date;
        }
    }

    // Hiển thị lên form khi chọn dòng
    private void showTable(int index) {
    GiamGia gg = giamGiaRepo.getAll().get(index);
    txtMaGG.setText(gg.getMaGiamGia());
    txtTenGG.setText(gg.getTenGiamGia());
    txtGiamPT.setText(gg.getGiaTriGiam() % 1 == 0 
        ? String.valueOf(gg.getGiaTriGiam().intValue()) 
        : String.format("%.2f", gg.getGiaTriGiam()));

    // Set loại giảm giá cho ComboBox hoặc RadioButton
    if ("Phần trăm".equalsIgnoreCase(gg.getLoaiGiamGia())) {
        //cbbLoaiGiam.setSelectedItem("Phần trăm");
        rboPhanTram.setSelected(true);
    } else if ("Giá Tiền".equalsIgnoreCase(gg.getLoaiGiamGia())) {
        //cbbLoaiGiam.setSelectedItem("Giá Tiền");
        rboTien.setSelected(true);
    }

    txtGiaTriTT.setText(df.format(gg.getGiaTriToiThieu()));
    txtGiaTriTD.setText(df.format(gg.getGiaTriToiDa()));
    txtNgayBD.setDate(gg.getNgayBatDau());
    txtNgayKT.setDate(gg.getNgayKetThuc());
    txtSoLuong.setText(String.valueOf(gg.getSoLuong()));
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
    
    private void locTheoNgay() {
    Date tuNgay = txtNgayBD1.getDate();
    Date denNgay = txtNgayKT1.getDate();
    ArrayList<GiamGia> ds = giamGiaRepo.getAll();
    ArrayList<GiamGia> dsLoc = new ArrayList<>();
    for (GiamGia gg : ds) {
        Date ngayBD = gg.getNgayBatDau();
        if (tuNgay != null && denNgay != null && ngayBD != null) {
            if (!ngayBD.before(tuNgay) && !ngayBD.after(denNgay)) {
                dsLoc.add(gg);
            }
        } else if (tuNgay != null && ngayBD != null) {
            if (!ngayBD.before(tuNgay)) {
                dsLoc.add(gg);
            }
        } else if (denNgay != null && ngayBD != null) {
            if (!ngayBD.after(denNgay)) {
                dsLoc.add(gg);
            }
        } else if (tuNgay == null && denNgay == null) {
            dsLoc.add(gg);
        }
    }
    fillGGTable(dsLoc);
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
        txtSoLuong = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        rboPhanTram = new javax.swing.JRadioButton();
        rboTien = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGiamGia = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbbLoc = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();
        cbbLoaiGiam = new javax.swing.JComboBox<>();
        txtNgayBD1 = new com.toedter.calendar.JDateChooser();
        txtNgayKT1 = new com.toedter.calendar.JDateChooser();
        lbltt = new javax.swing.JLabel();
        lbllg = new javax.swing.JLabel();
        lblnbd = new javax.swing.JLabel();
        lblnkt = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
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

        jLabel10.setText("Số Lượng");

        jLabel11.setText("Loại Giảm");

        buttonGroup1.add(rboPhanTram);
        rboPhanTram.setText("Phần trăm");

        buttonGroup1.add(rboTien);
        rboTien.setText("Giá tiền");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGiamPT, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rboPhanTram)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rboTien)))
                        .addGap(19, 19, 19)))
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(btnThem)
                    .addComponent(jLabel11)
                    .addComponent(rboTien)
                    .addComponent(rboPhanTram))
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addGap(18, 18, 18)
                .addComponent(btnTrangThai)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaTriTD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiamPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4))
                .addGap(24, 32, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtNgayKT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtNgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addGap(81, 81, 81))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblGiamGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã giảm giá", "Tên giảm giá", "Loại Giảm", "Giá trị giảm", "Giá trị tối thiểu", "Giá trị tối đa", "Số lượng", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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

        btnFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/filter.png"))); // NOI18N
        btnFilter.setText("Bộ lọc");
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        cbbLoaiGiam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Phần trăm", "Giá Tiền" }));
        cbbLoaiGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLoaiGiamActionPerformed(evt);
            }
        });

        lbltt.setText("Trạng thái");

        lbllg.setText("Loại giảm");

        lblnbd.setText("Ngày bắt đầu");

        lblnkt.setText("Ngày kết thúc");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1029, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFilter)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(cbbLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(cbbLoaiGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(lbltt)
                                .addGap(119, 119, 119)
                                .addComponent(lbllg)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(lblnbd)
                                .addGap(86, 86, 86)
                                .addComponent(lblnkt))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(txtNgayBD1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(txtNgayKT1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblnkt)
                    .addComponent(lblnbd)
                    .addComponent(lbllg)
                    .addComponent(lbltt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)
                                .addComponent(btnFilter)
                                .addComponent(cbbLoaiGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNgayKT1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtNgayBD1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbbLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
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
         int selectedIndex = tblGiamGia.getSelectedRow();
    if (selectedIndex == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng trong bảng!");
        return;
    }
    showTable(selectedIndex);
    }//GEN-LAST:event_tblGiamGiaMouseClicked

    private void btnTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangThaiActionPerformed
        int selectedIndex = tblGiamGia.getSelectedRow();
    if (selectedIndex == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để thay đổi trạng thái!");
        return;
    }
    GiamGia gg = giamGiaRepo.getAll().get(selectedIndex);
    giamGiaRepo.delete(gg.getMaGiamGia());
    fillGGTable(giamGiaRepo.getAll());
    if (BanHangForm.instance != null) {
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
        txtMaGG.setText("");
        txtTenGG.setText("");
        txtGiamPT.setText("");
        txtGiaTriTT.setText("");
        txtGiaTriTD.setText("");
        Date today = removeTime(new Date());
        Date tomorrow = new Date(today.getTime() + 24*60*60*1000L);
        txtNgayBD.setDate(today);
        txtNgayKT.setDate(tomorrow);
        txtNgayBD.setMinSelectableDate(today);
        txtNgayKT.setMinSelectableDate(today);
        buttonGroup1.clearSelection();
        txtSoLuong.setText("");
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

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        cbbLoc.setVisible(!cbbLoc.isVisible());
        lbltt.setVisible(!lbltt.isVisible());
        cbbLoaiGiam.setVisible(!cbbLoaiGiam.isVisible());
        lbllg.setVisible(!lbllg.isVisible());
        txtNgayBD1.setVisible(!txtNgayBD1.isVisible());
        lblnbd.setVisible(!lblnbd.isVisible());
        txtNgayKT1.setVisible(!txtNgayKT1.isVisible());
        lblnkt.setVisible(!lblnkt.isVisible());
    }//GEN-LAST:event_btnFilterActionPerformed

    private void cbbLoaiGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLoaiGiamActionPerformed
        String selectedLoai = cbbLoaiGiam.getSelectedItem().toString();
    ArrayList<GiamGia> ds = giamGiaRepo.getAll(); // lấy tất cả
    if (!selectedLoai.equalsIgnoreCase("Tất cả")) {
        ArrayList<GiamGia> dsLoc = new ArrayList<>();
        for (GiamGia gg : ds) {
            // So sánh chính xác loại giảm giá
            if (gg.getLoaiGiamGia() != null && gg.getLoaiGiamGia().equalsIgnoreCase(selectedLoai)) {
                dsLoc.add(gg);
            }
        }
        fillGGTable(dsLoc);
    } else {
        fillGGTable(ds);
    }
    }//GEN-LAST:event_cbbLoaiGiamActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTrangThai;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbLoaiGiam;
    private javax.swing.JComboBox<String> cbbLoc;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel lbllg;
    private javax.swing.JLabel lblnbd;
    private javax.swing.JLabel lblnkt;
    private javax.swing.JLabel lbltt;
    private javax.swing.JRadioButton rboPhanTram;
    private javax.swing.JRadioButton rboTien;
    private javax.swing.JTable tblGiamGia;
    private javax.swing.JTextField txtGiaTriTD;
    private javax.swing.JTextField txtGiaTriTT;
    private javax.swing.JTextField txtGiamPT;
    private javax.swing.JTextField txtMaGG;
    private com.toedter.calendar.JDateChooser txtNgayBD;
    private com.toedter.calendar.JDateChooser txtNgayBD1;
    private com.toedter.calendar.JDateChooser txtNgayKT;
    private com.toedter.calendar.JDateChooser txtNgayKT1;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenGG;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

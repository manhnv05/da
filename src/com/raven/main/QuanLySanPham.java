/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.sanpham.ChatLieu;
import model.sanpham.ChiTietSanPham;
import model.sanpham.CoAo;
import model.sanpham.KhoaAo;
import model.sanpham.KichThuoc;
import model.sanpham.LoaiSanPham;
import model.sanpham.MauSac;
import model.sanpham.SanPham;
import model.sanpham.ThuongHieu;
import repo.sanpham.ChatLieuRepo;
import repo.sanpham.ChiTietSanPhamRepo;
import repo.sanpham.CoAoRepo;
import repo.sanpham.KhoaAoRepo;
import repo.sanpham.KichThuocRepo;
import repo.sanpham.LoaiSanPhamRepo;
import repo.sanpham.MauSacRepo;
import repo.sanpham.SanPhamRepo;
import repo.sanpham.ThuongHieuRepo;
import respon.ChiTietSanPhamRespon;
import respon.ThuocTinhRespon;
import servicee.ThuocTinhService;
import utils.MaGenerator;

/**
 *
 * @author nguye
 */
public class QuanLySanPham extends javax.swing.JPanel {
//San pham
private SanPhamRepo sanPhamRepo;
private DefaultTableModel dtmSanPham;
private DefaultTableModel dtmChiTietSanPham;
private DefaultTableModel thuocTinhTblModel;

//San pham chi tiet
private ChiTietSanPhamRepo chiTietSanPhamRepo;
private LoaiSanPhamRepo loaiSanPhamRepo;
private MauSacRepo mauSacRepo;
private ChatLieuRepo chatLieuRepo;
private ThuongHieuRepo thuongHieuRepo;
private KichThuocRepo kichThuocRepo;
private KhoaAoRepo khoaAoRepo;
private CoAoRepo coAoRepo;
// combobox chi tiet san pham
DefaultComboBoxModel cbmSanPham;
DefaultComboBoxModel cbmLoaiSanPham;
DefaultComboBoxModel cbmMauSac;
DefaultComboBoxModel cbmKichThuoc;
DefaultComboBoxModel cbmChatLieu;
DefaultComboBoxModel cbmThuongHieu;
DefaultComboBoxModel cbmKhoaAo;
DefaultComboBoxModel cbmCoAo;

    /**
     * Creates new form QuanLySanPham
     */

    public QuanLySanPham() {
        initComponents();
        this.textSearch();
     
    sanPhamRepo = new SanPhamRepo();
    dtmSanPham = (DefaultTableModel) tblSanPham.getModel();
    thuocTinhTblModel = (DefaultTableModel) tblThuocTinhSanPham.getModel();
    chiTietSanPhamRepo = new ChiTietSanPhamRepo();
    dtmChiTietSanPham = (DefaultTableModel) tblSanPhamChiTiet.getModel();
    cbmSanPham = (DefaultComboBoxModel) cbbSanPhamCT.getModel();
    cbmLoaiSanPham = (DefaultComboBoxModel) cbbLoaiSanPham.getModel();
    cbmMauSac = (DefaultComboBoxModel) cbbMauSac.getModel();
    cbmChatLieu = (DefaultComboBoxModel) cbbChatLieu.getModel();
    cbmThuongHieu = (DefaultComboBoxModel) cbbThuongHieu.getModel();
    cbmCoAo = (DefaultComboBoxModel) cbbCoAo.getModel();
    cbmKhoaAo = (DefaultComboBoxModel) cbbKhoaAo.getModel();
    cbmKichThuoc = (DefaultComboBoxModel) cbbKichThuoc.getModel();
    //
    loaiSanPhamRepo = new LoaiSanPhamRepo();
    mauSacRepo = new MauSacRepo();
    chatLieuRepo = new ChatLieuRepo();
    kichThuocRepo = new KichThuocRepo();
    thuongHieuRepo = new ThuongHieuRepo();
    khoaAoRepo = new KhoaAoRepo();
    coAoRepo = new CoAoRepo();

    fillTableSanPham(sanPhamRepo.getAll());
    fillTableChiTietSanPham(chiTietSanPhamRepo.getAll());
    fillComboBox(
    sanPhamRepo.getAll(),      
    loaiSanPhamRepo.getAll(),      
    mauSacRepo.getAll(),      
    kichThuocRepo.getAll(),   
    chatLieuRepo.getAll(),    
    thuongHieuRepo.getAll(),   
    khoaAoRepo.getAll(),      
    coAoRepo.getAll()       

    );
}
    
public void reloadDisplays() {
    ArrayList<SanPham> listSanPham = sanPhamRepo.getAll();
    ArrayList<LoaiSanPham> listLoaiSanPham = loaiSanPhamRepo.getAll();
    ArrayList<MauSac> listMauSac = mauSacRepo.getAll();
    ArrayList<KichThuoc> listKichThuoc = kichThuocRepo.getAll();
    ArrayList<ChatLieu> listChatLieu = chatLieuRepo.getAll();
    ArrayList<ThuongHieu> listThuongHieu = thuongHieuRepo.getAll();
    ArrayList<KhoaAo> listKhoaAo = khoaAoRepo.getAll();
    ArrayList<CoAo> listCoAo = coAoRepo.getAll();
    fillComboBox(
        listSanPham,      
        listLoaiSanPham,      
        listMauSac,      
        listKichThuoc,    
        listChatLieu,     
        listThuongHieu,   
        listKhoaAo,       
        listCoAo   
    );
}
   public void fillTableSanPham(ArrayList<SanPham> list){
       Map<Integer, String> mapLoaiSanPham = new HashMap<>();
    for (LoaiSanPham lsp : loaiSanPhamRepo.getAll()) {
        mapLoaiSanPham.put(lsp.getId(), lsp.getTen());
    }

    dtmSanPham.setRowCount(0);
   for (SanPham sanPham : list) {
    if (sanPham.getTrangThai() == 1) { 
        String tenLoai = mapLoaiSanPham.get(sanPham.getIdLoaiSanPham());
        dtmSanPham.addRow(new Object[]{
            sanPham.getId(),
            sanPham.getMa(),
            sanPham.getTen(),
            tenLoai,
            "Hoạt động"
        });
    }
}
}

 public void fillTableChiTietSanPham(ArrayList<ChiTietSanPhamRespon> list) {
    dtmChiTietSanPham.setRowCount(0);
    
    for (ChiTietSanPhamRespon chiTietSanPhamRespon : list) {
        String maSPCT = chiTietSanPhamRespon.getMaSanPhamChiTiet();
        if (maSPCT == null || maSPCT.isBlank() || isMaSPCTTrung(maSPCT, list)) {
            maSPCT = generateMaSPCT();
            chiTietSanPhamRespon.setMaSanPhamChiTiet(maSPCT);
            chiTietSanPhamRepo.updateMaSPCTInDatabase(chiTietSanPhamRespon.getId(), maSPCT); 
        }
        System.out.println("Mã SPCT trên bảng: " + maSPCT); 

        dtmChiTietSanPham.addRow(new Object[]{
            chiTietSanPhamRespon.getId(),
            maSPCT,
            chiTietSanPhamRespon.getTenSanPham(),
            chiTietSanPhamRespon.getKichThuoc(),
            chiTietSanPhamRespon.getMauSac(),
            chiTietSanPhamRespon.getChatLieu(),
            chiTietSanPhamRespon.getThuongHieu(),
            chiTietSanPhamRespon.getKhoaAo(),
            chiTietSanPhamRespon.getCoAo(),
            chiTietSanPhamRespon.getSoLuong(),
            chiTietSanPhamRespon.getDonGia(),
        });
    }
}
private boolean isMaSPCTTrung(String maSPCT, ArrayList<ChiTietSanPhamRespon> list) {
    for (ChiTietSanPhamRespon sp : list) {
        if (sp.getMaSanPhamChiTiet() != null && sp.getMaSanPhamChiTiet().equals(maSPCT)) {
            return true;
        }
    }
    return false;
}
public void fillComboBox(
    ArrayList<SanPham> listSanPham,
    ArrayList<LoaiSanPham> listLoaiSanPham,
    ArrayList<MauSac> listMauSac,
    ArrayList<KichThuoc> listKichThuoc,
    ArrayList<ChatLieu> listChatLieu,
    ArrayList<ThuongHieu> listThuongHieu,
    ArrayList<KhoaAo> listKhoaAo,
    ArrayList<CoAo> listCoAo
) {
    DefaultComboBoxModel<String> modelSanPham = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> modelLoaiSanPham = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> modelMauSac = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> modelKichThuoc = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> modelChatLieu = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> modelCoAo = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> modelThuongHieu = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> modelKhoaAo = new DefaultComboBoxModel<>();

    cbbSanPhamCT.setModel(modelSanPham);
    cbbLoaiSanPham.setModel(modelLoaiSanPham);
    cbbMauSac.setModel(modelMauSac);
    cbbKichThuoc.setModel(modelKichThuoc);
    cbbChatLieu.setModel(modelChatLieu);
    cbbCoAo.setModel(modelCoAo);
    cbbThuongHieu.setModel(modelThuongHieu);
    cbbKhoaAo.setModel(modelKhoaAo);

    listSanPham.forEach(sp -> modelSanPham.addElement(sp.getTen()));
    listLoaiSanPham.forEach(lsp -> modelLoaiSanPham.addElement(lsp.getTen()));
    listMauSac.forEach(ms -> modelMauSac.addElement(ms.getTen()));
    listKichThuoc.forEach(kt -> modelKichThuoc.addElement(kt.getTen()));
    listChatLieu.forEach(cl -> modelChatLieu.addElement(cl.getTen()));
    listCoAo.forEach(ca -> modelCoAo.addElement(ca.getTen()));
    listKhoaAo.forEach(ka -> modelKhoaAo.addElement(ka.getTen()));
    listThuongHieu.forEach(th -> modelThuongHieu.addElement(th.getTen()));
}

public SanPham getFormSanPham() {
    Integer id = null;

    try {
        String txtIDValue = txtID.getText().trim();
        if (!txtIDValue.isBlank()) {
            id = Integer.valueOf(txtIDValue);
            if (id < 0) {
                JOptionPane.showMessageDialog(null, "ID không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "ID phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return null;
    }
    String ma = txtMaSanPham.getText().trim();
    String ten = txtTenSanPham.getText().trim();
    if (ma.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Mã sản phẩm không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return null;
    }
    if (ten.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Tên sản phẩm không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return null;
    }
    Integer idLoaiSanPham = loaiSanPhamRepo.getAll().get(cbbLoaiSanPham.getSelectedIndex()).getId();
    Integer trangThai = rdoHoatDong.isSelected() ? 1 : 0;

    return new SanPham(id, ma, ten, idLoaiSanPham, trangThai);
}

public ChiTietSanPham getFormChiTietSanPham() {
    try {
        Integer id = txtIDSPCT.getText().isBlank() ? null : Integer.valueOf(txtIDSPCT.getText());
        String maSPCT = (id == null) ? generateMaSPCT() : txtIDSPCT.getText(); 
        
        int soLuong, donGia;
        
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            if (soLuong <= 0) {  
                JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!");
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên hợp lệ!");
            return null;
        }

        try {
            donGia = Integer.parseInt(txtDonGia.getText().trim());
            if (donGia <= 0) { 
                JOptionPane.showMessageDialog(null, "Đơn giá phải lớn hơn 0!");
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Đơn giá phải là số nguyên hợp lệ!");
            return null;
        }
        if (cbbSanPhamCT.getSelectedIndex() < 0 || cbbChatLieu.getSelectedIndex() < 0 || cbbMauSac.getSelectedIndex() < 0 ||
            cbbKichThuoc.getSelectedIndex() < 0 || cbbThuongHieu.getSelectedIndex() < 0 || cbbKhoaAo.getSelectedIndex() < 0 || 
            cbbCoAo.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn đầy đủ thông tin sản phẩm!");
            return null;
        }
        Integer idSanPham = sanPhamRepo.getAll().get(cbbSanPhamCT.getSelectedIndex()).getId();
        Integer idChatLieu = chatLieuRepo.getAll().get(cbbChatLieu.getSelectedIndex()).getId();
        Integer idMauSac = mauSacRepo.getAll().get(cbbMauSac.getSelectedIndex()).getId();
        Integer idKichThuoc = kichThuocRepo.getAll().get(cbbKichThuoc.getSelectedIndex()).getId();
        Integer idThuongHieu = thuongHieuRepo.getAll().get(cbbThuongHieu.getSelectedIndex()).getId();
        Integer idKhoaAo = khoaAoRepo.getAll().get(cbbKhoaAo.getSelectedIndex()).getId();
        Integer idCoAo = coAoRepo.getAll().get(cbbCoAo.getSelectedIndex()).getId();
        return new ChiTietSanPham(id, maSPCT, idSanPham, idMauSac, idKichThuoc, idChatLieu, idThuongHieu, idKhoaAo, idCoAo, soLuong, donGia);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Lỗi khi nhập dữ liệu: " + e.getMessage());
        return null;
    }
}

private String generateMaSPCT() {
    int count = dtmChiTietSanPham.getRowCount() + 1; 
    return "SPCT" + String.format("%03d", count);
}
private void renderThuocTinhTbl(String table) {
    if (table == null || table.trim().isEmpty()) {
        System.out.println("Tên bảng truyền vào bị null hoặc rỗng!");
        return;
    }
    List<ThuocTinhRespon> list = new ThuocTinhService().selectAllByTable(table);
    if (list == null || list.isEmpty()) {
        System.out.println("Không có dữ liệu thuộc tính nào từ bảng: " + table);
        return;
    }
    if (thuocTinhTblModel == null) {
        System.out.println("Ban chua nhap thuoc tinh!");
        return;
    }
    thuocTinhTblModel.setRowCount(0);
    int num = 1;
    for (ThuocTinhRespon item : list) {
        thuocTinhTblModel.addRow(new Object[]{num, item.getMa(), item.getTen()});
        num++;
    }
    thuocTinhTblModel.fireTableDataChanged();
}
private void textSearch() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                timKiem();
            }

        }
        );
    }
    private void timKiem() {
        String maCTSP = txtTimKiem.getText().trim();
        fillTableChiTietSanPham(chiTietSanPhamRepo.search(maCTSP));
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTenSanPham = new javax.swing.JTextField();
        txtID = new javax.swing.JTextField();
        txtMaSanPham = new javax.swing.JTextField();
        rdoHoatDong = new javax.swing.JRadioButton();
        rdoKhongHoatDong = new javax.swing.JRadioButton();
        btnThemSanPham = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoaSP = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        cbbLoaiSanPham = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbbChatLieu = new javax.swing.JComboBox<>();
        cbbMauSac = new javax.swing.JComboBox<>();
        cbbThuongHieu = new javax.swing.JComboBox<>();
        cbbSanPhamCT = new javax.swing.JComboBox<>();
        cbbCoAo = new javax.swing.JComboBox<>();
        cbbKhoaAo = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        txtIDSPCT = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPhamChiTiet = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnThemSPCT = new javax.swing.JButton();
        btnSuaSPCT = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbbKichThuoc = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtTenThuocTinh = new javax.swing.JTextField();
        rdoMS = new javax.swing.JRadioButton();
        rdoCL = new javax.swing.JRadioButton();
        rdoTH = new javax.swing.JRadioButton();
        rdoKT = new javax.swing.JRadioButton();
        rdoKA = new javax.swing.JRadioButton();
        rdoCA = new javax.swing.JRadioButton();
        rdoLSP = new javax.swing.JRadioButton();
        btnThemThuocTinh = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThuocTinhSanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Mã sản phẩm");

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setResizable(false);
            tblSanPham.getColumnModel().getColumn(1).setResizable(false);
            tblSanPham.getColumnModel().getColumn(2).setResizable(false);
            tblSanPham.getColumnModel().getColumn(3).setResizable(false);
            tblSanPham.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel4.setText("Tên sản phẩm");

        jLabel5.setText("Trạng thái");

        jLabel6.setText("ID");

        txtMaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSanPhamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoHoatDong);
        rdoHoatDong.setText("Hoạt động");

        buttonGroup1.add(rdoKhongHoatDong);
        rdoKhongHoatDong.setText("Không hoạt động");
        rdoKhongHoatDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKhongHoatDongActionPerformed(evt);
            }
        });

        btnThemSanPham.setText("Thêm");
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoaSP.setText("Xóa");
        btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPActionPerformed(evt);
            }
        });

        jLabel17.setText("Loại sản phẩm");

        cbbLoaiSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addGap(67, 67, 67)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(93, 93, 93)
                                .addComponent(jLabel17)
                                .addGap(30, 30, 30)
                                .addComponent(cbbLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoHoatDong)
                                .addGap(44, 44, 44)
                                .addComponent(rdoKhongHoatDong)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnThemSanPham)))
                        .addGap(46, 46, 46)
                        .addComponent(btnXoaSP)
                        .addGap(60, 60, 60)
                        .addComponent(btnSua)
                        .addGap(114, 114, 114))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(cbbLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnThemSanPham)
                    .addComponent(btnXoaSP)
                    .addComponent(btnSua))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoHoatDong)
                    .addComponent(jLabel5)
                    .addComponent(rdoKhongHoatDong))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Sản phẩm", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("ID");

        jLabel9.setText("San pham");

        jLabel10.setText("Chat lieu");

        jLabel11.setText("Mau sac");

        jLabel12.setText("Thuong hieu");

        jLabel13.setText("Co ao");

        jLabel14.setText("Khoa ao");

        cbbChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMauSacActionPerformed(evt);
            }
        });

        cbbThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbSanPhamCT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbCoAo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbKhoaAo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setText("Don gia");

        txtDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaActionPerformed(evt);
            }
        });

        txtIDSPCT.setEditable(false);

        tblSanPhamChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Ma san pham", "Ten San pham ", "Kich thuoc", "Mau sac", "Chat lieu", "Thuong hieu", "Khoa ao", "Co ao", "So luong", "Don gia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPhamChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamChiTietMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPhamChiTiet);
        if (tblSanPhamChiTiet.getColumnModel().getColumnCount() > 0) {
            tblSanPhamChiTiet.getColumnModel().getColumn(0).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(1).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(1).setHeaderValue("Ma san pham");
            tblSanPhamChiTiet.getColumnModel().getColumn(2).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(3).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(4).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(5).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(6).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(7).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(8).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(9).setResizable(false);
            tblSanPhamChiTiet.getColumnModel().getColumn(10).setResizable(false);
        }

        btnThemSPCT.setText("Thêm");
        btnThemSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPCTActionPerformed(evt);
            }
        });

        btnSuaSPCT.setText("Sửa");
        btnSuaSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSPCTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnSuaSPCT, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(btnThemSPCT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnThemSPCT)
                .addGap(18, 18, 18)
                .addComponent(btnSuaSPCT)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel3.setText("Kich thuoc");

        cbbKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Tim kiem");

        jLabel19.setText("So luong");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9))
                        .addGap(96, 96, 96)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbSanPhamCT, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbbMauSac, 0, 135, Short.MAX_VALUE)
                                .addComponent(cbbChatLieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtIDSPCT)
                                .addComponent(cbbKichThuoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(201, 201, 201)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cbbKhoaAo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addGap(63, 63, 63)
                            .addComponent(cbbThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addGap(82, 82, 82)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbbCoAo, 0, 135, Short.MAX_VALUE)
                                .addComponent(txtSoLuong)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtIDSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel12)
                                            .addComponent(cbbThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(48, 48, 48))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbbSanPhamCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel14)
                                        .addComponent(cbbKhoaAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9)))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13)
                                .addComponent(cbbCoAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Sản phẩm chi tiết", jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Thuộc tính sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        jLabel16.setText("Tên thuộc tính");

        buttonGroup1.add(rdoMS);
        rdoMS.setText("Màu sắc");
        rdoMS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoMSMouseClicked(evt);
            }
        });
        rdoMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMSActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoCL);
        rdoCL.setText("Chất liệu");
        rdoCL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoCLMouseClicked(evt);
            }
        });
        rdoCL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoCLActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoTH);
        rdoTH.setText("Thương hiệu");
        rdoTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTHActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoKT);
        rdoKT.setText("Kích thước");
        rdoKT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKTActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoKA);
        rdoKA.setText("Khóa áo");
        rdoKA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKAActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoCA);
        rdoCA.setText("Cổ áo");
        rdoCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoCAActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoLSP);
        rdoLSP.setText("Tên loại sản phẩm");
        rdoLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoLSPActionPerformed(evt);
            }
        });

        btnThemThuocTinh.setText("Thêm");
        btnThemThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemThuocTinhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(35, 35, 35)
                        .addComponent(txtTenThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(rdoMS)
                                .addGap(23, 23, 23)
                                .addComponent(rdoCL)
                                .addGap(43, 43, 43)
                                .addComponent(rdoTH)
                                .addGap(64, 64, 64)
                                .addComponent(rdoKT))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(rdoCA)
                                .addGap(51, 51, 51)
                                .addComponent(rdoLSP)))
                        .addGap(58, 58, 58)
                        .addComponent(rdoKA)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThemThuocTinh)
                .addGap(57, 57, 57))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTenThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(btnThemThuocTinh)
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoMS)
                    .addComponent(rdoCL)
                    .addComponent(rdoTH)
                    .addComponent(rdoKT)
                    .addComponent(rdoKA))
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoCA)
                    .addComponent(rdoLSP))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        tblThuocTinhSanPham.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblThuocTinhSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Ma thuoc tinh", "Tên thuộc tính"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblThuocTinhSanPham);
        if (tblThuocTinhSanPham.getColumnModel().getColumnCount() > 0) {
            tblThuocTinhSanPham.getColumnModel().getColumn(0).setResizable(false);
            tblThuocTinhSanPham.getColumnModel().getColumn(1).setResizable(false);
            tblThuocTinhSanPham.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Thuộc tính", jPanel5);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Quản lý sản phẩm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 985, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(415, 415, 415))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        int selectedIndex = tblSanPham.getSelectedRow();
        txtID.setText(tblSanPham.getValueAt(selectedIndex, 0).toString());
        txtMaSanPham.setText(tblSanPham.getValueAt(selectedIndex, 1).toString());
         txtTenSanPham.setText(tblSanPham.getValueAt(selectedIndex, 2).toString());
        cbbLoaiSanPham.setSelectedItem(tblSanPham.getValueAt(selectedIndex, 3).toString());
        if(tblSanPham.getValueAt(selectedIndex, 4).toString().equals("Hoạt động")) {
            rdoHoatDong.setSelected(true);
        } else {
            rdoKhongHoatDong.setSelected(true);
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        // TODO add your handling code her}
    SanPham sp = this.getFormSanPham();
        if (sp == null) {
            JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ, vui lòng kiểm tra lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (sanPhamRepo.isSanPhamTonTai(sp.getMa(), sp.getTen(), 0)) {
            JOptionPane.showMessageDialog(null, "Mã hoặc ten sản phẩm đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
            sanPhamRepo.themSanPham(sp);
        fillTableSanPham(sanPhamRepo.getAll());
        JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!");
        reloadDisplays();
    }//GEN-LAST:event_btnThemSanPhamActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
    int selectedRow = tblSanPham.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        null, 
        "Bạn có chắc chắn muốn cập nhật sản phẩm này không?", 
        "Xác nhận sửa sản phẩm", 
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        SanPham sanPham = this.getFormSanPham();
        if (sanPham != null) {
            if (sanPhamRepo.isSanPhamTonTai(sanPham.getMa(), sanPham.getTen(), sanPham.getId())) {
                JOptionPane.showMessageDialog(null, "Mã hoặc Tên sản phẩm đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            sanPhamRepo.suaSanPham(sanPham);
            fillTableSanPham(sanPhamRepo.getAll());
            JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ, vui lòng kiểm tra lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
}
    }//GEN-LAST:event_btnSuaActionPerformed

    private void cbbMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbMauSacActionPerformed

    private void txtDonGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaActionPerformed

    private void tblSanPhamChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamChiTietMouseClicked
        // TODO add your handling code here:
         int selectedIndex = tblSanPhamChiTiet.getSelectedRow();
        txtIDSPCT.setText(tblSanPhamChiTiet.getValueAt(selectedIndex, 0).toString());
        cbbSanPhamCT.setSelectedItem(tblSanPhamChiTiet.getValueAt(selectedIndex, 2).toString());
        cbbKichThuoc.setSelectedItem(tblSanPhamChiTiet.getValueAt(selectedIndex, 3).toString());
        cbbMauSac.setSelectedItem(tblSanPhamChiTiet.getValueAt(selectedIndex, 4).toString());
        cbbChatLieu.setSelectedItem(tblSanPhamChiTiet.getValueAt(selectedIndex, 5).toString());
        cbbThuongHieu.setSelectedItem(tblSanPhamChiTiet.getValueAt(selectedIndex, 6).toString());
        cbbKhoaAo.setSelectedItem(tblSanPhamChiTiet.getValueAt(selectedIndex, 7).toString());
        cbbCoAo.setSelectedItem(tblSanPhamChiTiet.getValueAt(selectedIndex, 8).toString());
        txtSoLuong.setText(tblSanPhamChiTiet.getValueAt(selectedIndex, 9).toString());
        txtDonGia.setText(tblSanPhamChiTiet.getValueAt(selectedIndex, 10).toString());          
    }//GEN-LAST:event_tblSanPhamChiTietMouseClicked

    private void rdoKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKTActionPerformed
        // TODO add your handling code here:
        renderThuocTinhTbl("KichThuoc");
    }//GEN-LAST:event_rdoKTActionPerformed

    private void rdoMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMSActionPerformed
        // TODO add your handling code here:
          renderThuocTinhTbl("MauSac");
    }//GEN-LAST:event_rdoMSActionPerformed

    private void btnThemSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPCTActionPerformed
        // TODO add your handling code here:
       int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm sản phẩm chi tiết không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ChiTietSanPham chiTietSanPham = this.getFormChiTietSanPham();
            if (chiTietSanPham != null) { 
                chiTietSanPhamRepo.themChiTietSanPham(chiTietSanPham);
                fillTableChiTietSanPham(chiTietSanPhamRepo.getAll());
                JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!");
                if (BanHangForm.instance != null) {
                BanHangForm.instance.loadTableSanPham();
        }
            } else {
                JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ, vui lòng kiểm tra lại!");
            }       
}
    }//GEN-LAST:event_btnThemSPCTActionPerformed

    private void rdoMSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoMSMouseClicked
        // TODO add your handling code here:
//         renderThuocTinhTbl(rdoMS.getActionCommand());
    }//GEN-LAST:event_rdoMSMouseClicked

    private void rdoCLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoCLMouseClicked
        // TODO add your handling code here:
//         renderThuocTinhTbl(rdoCL.getActionCommand());
    }//GEN-LAST:event_rdoCLMouseClicked

    private void btnSuaSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSPCTActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblSanPhamChiTiet.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm chi tiết để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn cập nhật sản phẩm chi tiết không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            ChiTietSanPham chiTietSanPham = this.getFormChiTietSanPham();
            if (chiTietSanPham != null) { // Kiểm tra dữ liệu có hợp lệ không
                chiTietSanPhamRepo.suaChiTietSanPham(chiTietSanPham);
                fillTableChiTietSanPham(chiTietSanPhamRepo.getAll());
                JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ, vui lòng kiểm tra lại!");
            }
        }
    }//GEN-LAST:event_btnSuaSPCTActionPerformed

    private void txtMaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSanPhamActionPerformed

    private void btnXoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblSanPham.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer id = (Integer) tblSanPham.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(
            null, "Bạn có chắc chắn muốn xóa sản phẩm này không?","Xác nhận xóa", JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            sanPhamRepo.xoaSanPham(id);
            fillTableSanPham(sanPhamRepo.getAll());
            JOptionPane.showMessageDialog(null, "Xóa sản phẩm thành công!");
        }
    }//GEN-LAST:event_btnXoaSPActionPerformed

    private void btnThemThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemThuocTinhActionPerformed
        // TODO add your handling code here:
        rdoLSP.setActionCommand("LoaiSanPham");
        rdoMS.setActionCommand("MauSac");
        rdoCL.setActionCommand("ChatLieu");
        rdoTH.setActionCommand("ThuongHieu");
        rdoKA.setActionCommand("KhoaAo");
        rdoCA.setActionCommand("CoAo");
        rdoKT.setActionCommand("KichThuoc");
    try {
    if (buttonGroup1.getSelection() == null) {
        JOptionPane.showMessageDialog(this, "Lỗi: Vui lòng chọn loại thuộc tính!");
        return;
    }
    String table = buttonGroup1.getSelection().getActionCommand();
    if (table == null || table.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Lỗi: Không xác định được loại thuộc tính!");
        return;
    }
    System.out.println("Bảng đang chọn: " + table);
    if (txtTenThuocTinh == null) {
        JOptionPane.showMessageDialog(this, "Lỗi: Ô nhập liệu không tồn tại!");
        return;
    }
    String newName = txtTenThuocTinh.getText().trim();
    if (newName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên thuộc tính!");
        return;
    }
    int confirm = JOptionPane.showConfirmDialog(this, "Thêm?", "Xác nhận", JOptionPane.OK_CANCEL_OPTION);
    if (confirm != JOptionPane.OK_OPTION) {
        return;
    }
    // Xử lý thêm thuộc tính
    try {
        switch (table) {
            case "LoaiSanPham":
                new LoaiSanPhamRepo().creatLoaiSanPham(LoaiSanPham.builder().ma(MaGenerator.generate("LSP")).ten(newName).build());
                break;
            case "MauSac":
                new MauSacRepo().creatMauSac(MauSac.builder().ma(MaGenerator.generate("MS")).ten(newName).build());
                break;
            case "ChatLieu":
                new ChatLieuRepo().creatChatLieu(ChatLieu.builder().ma(MaGenerator.generate("CL")).ten(newName).build());
                break;
            case "ThuongHieu":
                new ThuongHieuRepo().creatThuongHieu(ThuongHieu.builder().ma(MaGenerator.generate("TH")).ten(newName).build());
                break;
            case "KhoaAo":
                new KhoaAoRepo().creatKhoaAo(KhoaAo.builder().ma(MaGenerator.generate("KA")).ten(newName).build());
                break;
            case "CoAo":
                new CoAoRepo().creatCoAo(CoAo.builder().ma(MaGenerator.generate("CA")).ten(newName).build());
                break;
            case "KichThuoc":
                new KichThuocRepo().creatKichThuoc(KichThuoc.builder().ma(MaGenerator.generate("KT")).ten(newName).build());
                break;
            default:
                JOptionPane.showMessageDialog(this, "Lỗi: Thuộc tính không tồn tại!");
                return;
        }
        JOptionPane.showMessageDialog(this, "Thêm thành công!");
        renderThuocTinhTbl(table);
        txtTenThuocTinh.setText("");
        reloadDisplays();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi thêm dữ liệu: " + e.getMessage());
        e.printStackTrace();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi không xác định: " + e.getMessage());
        e.printStackTrace();
        }
    }//GEN-LAST:event_btnThemThuocTinhActionPerformed

    private void rdoCLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCLActionPerformed
        // TODO add your handling code here:
        renderThuocTinhTbl("ChatLieu");
    }//GEN-LAST:event_rdoCLActionPerformed

    private void rdoTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTHActionPerformed
        // TODO add your handling code here:
        renderThuocTinhTbl("ThuongHieu");
    }//GEN-LAST:event_rdoTHActionPerformed

    private void rdoKAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKAActionPerformed
        // TODO add your handling code here:
        renderThuocTinhTbl("KhoaAo");
    }//GEN-LAST:event_rdoKAActionPerformed

    private void rdoCAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCAActionPerformed
        // TODO add your handling code here:
        renderThuocTinhTbl("CoAo");
    }//GEN-LAST:event_rdoCAActionPerformed

    private void rdoLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoLSPActionPerformed
        // TODO add your handling code here:
        renderThuocTinhTbl("LoaiSanPham");
    }//GEN-LAST:event_rdoLSPActionPerformed

    private void rdoKhongHoatDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKhongHoatDongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoKhongHoatDongActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaSPCT;
    private javax.swing.JButton btnThemSPCT;
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JButton btnThemThuocTinh;
    private javax.swing.JButton btnXoaSP;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbbChatLieu;
    private javax.swing.JComboBox<String> cbbCoAo;
    private javax.swing.JComboBox<String> cbbKhoaAo;
    private javax.swing.JComboBox<String> cbbKichThuoc;
    private javax.swing.JComboBox<String> cbbLoaiSanPham;
    private javax.swing.JComboBox<String> cbbMauSac;
    private javax.swing.JComboBox<String> cbbSanPhamCT;
    private javax.swing.JComboBox<String> cbbThuongHieu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JRadioButton rdoCA;
    private javax.swing.JRadioButton rdoCL;
    private javax.swing.JRadioButton rdoHoatDong;
    private javax.swing.JRadioButton rdoKA;
    private javax.swing.JRadioButton rdoKT;
    private javax.swing.JRadioButton rdoKhongHoatDong;
    private javax.swing.JRadioButton rdoLSP;
    private javax.swing.JRadioButton rdoMS;
    private javax.swing.JRadioButton rdoTH;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblSanPhamChiTiet;
    private javax.swing.JTable tblThuocTinhSanPham;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDSPCT;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtTenThuocTinh;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

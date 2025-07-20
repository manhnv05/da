/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.main;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import model.giamgia.GiamGia;
import model.hoadon.HoaDon;
import model.hoadon.HoaDonChiTiet;
import model.sanpham.ChiTietSanPham;
import repo.giamgia.GiamGiaRepo;
import repo.hoadon.HoaDonChiTietRepo;
import repo.hoadon.HoaDonRepo;
import repo.nguoidung.KhachHangRepo;
import repo.sanpham.ChiTietSanPhamRepo;
import respon.ChiTietSanPhamRespon;
import respon.HoaDonChiTietRespon;

/**
 *
 * @author nguye
 */
public class BanHangForm extends javax.swing.JPanel {
private HoaDonRepo hoaDonRepo;
private HoaDonChiTietRepo hoaDonChiTietRepo;
private KhachHangRepo khachHangRepo;
private DefaultTableModel dtmHoaDon;
private DefaultTableModel dtmHoaDonChiTiet;
private DefaultTableModel dtmChiTietSanPham;
private DefaultComboBoxModel cbmGiamGia;
private GiamGiaRepo giamGiaRepo;
 private ChiTietSanPhamRepo chiTietSanPhamRepo;
    /**
     * Creates new form BanHangForm
     */
    int idHoaDon = -1;
    private int tongTienMuaHang = 0;
    private int tongTienSauGiam = 0;
 public static BanHangForm instance;

    public BanHangForm() {
        initComponents();
        instance = this;
        textSearch();
        addEvents();
        addPriceFilterListeners();
        addEventCbbGiamGia();
   
        
       
        hoaDonRepo = new HoaDonRepo();
        hoaDonChiTietRepo = new HoaDonChiTietRepo();
        dtmHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        fillHoaDonTable(hoaDonRepo.getAllChuaThanhToan());
        
        chiTietSanPhamRepo = new ChiTietSanPhamRepo();
        dtmChiTietSanPham = (DefaultTableModel) tblDanhSachSanPhamChiTiet.getModel();
        dtmHoaDonChiTiet = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        fillTableChiTietSanPham(chiTietSanPhamRepo.getAll());
        
        giamGiaRepo = new GiamGiaRepo();
        cbmGiamGia = (DefaultComboBoxModel) cbbGiamGia.getModel();
        fillGiamGia(giamGiaRepo.getAll());
        
           btnKhachHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableKhachHang1().setVisible(true);
            }

        });
        setVisible(true);
    
}
public int gettongTienMuaHang() {
    return tongTienMuaHang;
}
public int gettongTienSauGiam() {
    return tongTienSauGiam;
}

private void fillHoaDonTable(ArrayList<HoaDon> list) {
    dtmHoaDon.setRowCount(0);
    for (HoaDon hoaDon : list) {
        if (hoaDon.getTrangThai() != 3) {
            String trangThai = (hoaDon.getTrangThai() == 1) ? "Chưa thanh toán" :(hoaDon.getTrangThai() == 2) ? "Đã thanh toán" : "Không xác định";
            dtmHoaDon.addRow(new Object[]{
                hoaDon.getId(),
                hoaDon.getTenNguoiNhan(),
                hoaDon.getSoDienThoai(),
                hoaDon.getNgayTao(),
                trangThai
            });
        }
    }
}
   private void fillTableChiTietSanPham(ArrayList<ChiTietSanPhamRespon> danhSach) {
    dtmChiTietSanPham.setRowCount(0);
    for (ChiTietSanPhamRespon ctsp : danhSach) {
            dtmChiTietSanPham.addRow(new Object[]{
                ctsp.getId(),
                ctsp.getMaSanPhamChiTiet(),
                ctsp.getTenSanPham(),
                ctsp.getMauSac(),
                ctsp.getChatLieu(),
                ctsp.getKichThuoc(),
                ctsp.getCoAo(),
                ctsp.getSoLuong(),
                ctsp.getDonGia(),
            });
        }
    } 
    public void loadTableSanPham() {
    ArrayList<ChiTietSanPhamRespon> danhSach = chiTietSanPhamRepo.getAll();
    fillTableChiTietSanPham(danhSach);
}
    private void fillTableGiohang(ArrayList<HoaDonChiTietRespon> danhSach) {
    dtmHoaDonChiTiet.setRowCount(0);
    for(HoaDonChiTietRespon hdct: danhSach) {
      dtmHoaDonChiTiet.addRow(new Object[]{
         hdct.getId(),
         hdct.getIdChiTietSanPham(),
         hdct.getTenSanPham(),
         hdct.getTenMauSac(),
         hdct.getTenChatLieu(),
         hdct.getTenKichThuoc(),
         hdct.getTenCoAo(),
         hdct.getSoLuong(),
         hdct.getDonGia(),
         hdct.getThanhTien()
      });
    }
}
    private void addEvents() {
    txtTienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
        public void insertUpdate(DocumentEvent e) { tinhTienThua(); }
        public void removeUpdate(DocumentEvent e) { tinhTienThua(); }
        public void changedUpdate(DocumentEvent e) { tinhTienThua(); }
    });
    }
  private void tinhTienThua() {
    List<HoaDonChiTietRespon> danhSachHoaDonChiTiet = hoaDonChiTietRepo.getAll(idHoaDon);
try {
    String input = txtTienKhachDua.getText().trim().replaceAll("[^\\d]", "");

    if (input.isEmpty()) {
        lblTienThua.setText("0");
        return;
    }
    int tienKhachDua = Integer.parseInt(input);

    String tongTienText = lblTongTien.getText().split(" ")[0]; 
    tongTienText = tongTienText.replace(",", "").trim();
    long tongTienSauGiam = Long.parseLong(tongTienText);
    System.out.println("Tiền khách đưa: " + tienKhachDua);
    System.out.println("Tổng tiền sau giảm (label): " + tongTienSauGiam);
    long tienThua = tienKhachDua - tongTienSauGiam;
    if (tienThua < 0) {
        lblTienThua.setText("Chưa đủ tiền");
    } else {
        lblTienThua.setText( String.format("%,d VNĐ", tienThua));
    }
} catch (NumberFormatException e) {
    lblTienThua.setText("0");
    JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền hợp lệ!");
}
}   
    private void textSearch() {
            txtTimKiemSPCT.getDocument().addDocumentListener(new DocumentListener() {
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
        String tenSPCT = txtTimKiemSPCT.getText().trim();
        fillTableChiTietSanPham(chiTietSanPhamRepo.search(tenSPCT));
    }
    
    private void addPriceFilterListeners() {
        txtMin.setText("0");
        txtMin.setEditable(false);
        ((AbstractDocument) txtMax.getDocument()).setDocumentFilter(new DocumentFilter() {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string != null && string.matches("[0-9]*")) {
            super.insertString(fb, offset, string, attr);
        }
    }
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null && text.matches("[0-9]*")) {
            super.replace(fb, offset, length, text, attrs);
        }
        }
    });
    DocumentListener docListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            filterByPriceRange();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filterByPriceRange();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            filterByPriceRange();
        }
    };

    txtMin.getDocument().addDocumentListener(docListener);
    txtMax.getDocument().addDocumentListener(docListener);
}

    private void filterByPriceRange() {
    try {
        String minText = txtMin.getText().trim();
        String maxText = txtMax.getText().trim();

        if (minText.isEmpty() && maxText.isEmpty()) {
            return;
        }

        int min = minText.isEmpty() ? 0 : Integer.parseInt(minText);
        int max = maxText.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(maxText);

        if (min < 0 || max < 0 || min > max) {
            return;
        }

        ArrayList<ChiTietSanPhamRespon> results = chiTietSanPhamRepo.searchByPriceRange(min, max);
        fillTableChiTietSanPham(results);

    } catch (NumberFormatException e) {
        
    }
}
public void fillGiamGia(ArrayList<GiamGia> list) {
    DefaultComboBoxModel<String> modelGiamGia = new DefaultComboBoxModel<>();
    modelGiamGia.addElement("Không giảm giá");
    if (list != null && !list.isEmpty()) {
        for (GiamGia gg : list) {
            Float phanTram = gg.getPhanTramGiam();
            Integer trangThai = gg.getTrangThai(); 
            if (phanTram != null && trangThai != null && trangThai == 1) {
                modelGiamGia.addElement(phanTram + "%");
            }
        }
    }
    cbbGiamGia.setModel(modelGiamGia);
}
public void addEventCbbGiamGia() {
    cbbGiamGia.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                capNhatTongTien(); 
            }
        }
    });
}
public void capNhatTongTien() {
    List<HoaDonChiTietRespon> danhSachHoaDonChiTiet = hoaDonChiTietRepo.getAll(idHoaDon);
    int tongTienMuaHangLocal = 0;
    for (HoaDonChiTietRespon hoaDonChiTiet : danhSachHoaDonChiTiet) {
        int soLuong = hoaDonChiTiet.getSoLuong();
        int donGiaSanPham = hoaDonChiTiet.getDonGia();
        tongTienMuaHangLocal += soLuong * donGiaSanPham;
    }
    this.tongTienMuaHang = tongTienMuaHangLocal;
    String selected = (String) cbbGiamGia.getSelectedItem();
    float giamGia = 0f;
    try {
        if (selected != null && !selected.equalsIgnoreCase("Không giảm giá")) {
            selected = selected.replace("%", "").trim();
            String[] parts = selected.split(" ");
            giamGia = Float.parseFloat(parts[0]);
        }
    } catch (NumberFormatException e) {
        giamGia = 0f;
    }

    float tienGiamFloat = tongTienMuaHangLocal * (giamGia / 100f);
    int tienGiam = Math.round(tienGiamFloat);

    float tongTienSauGiamFloat = tongTienMuaHangLocal - tienGiam;
    this.tongTienSauGiam = Math.round(tongTienSauGiamFloat);
    lblTienTruocGiam.setText(String.format("%,d VNĐ", tongTienMuaHangLocal));
    lblSoTienGiam1.setText(String.format("- %s VNĐ (%.0f%%)", String.format("%,d", tienGiam), giamGia));
    lblTongTien.setText(String.format("%,d VNĐ", tongTienSauGiam));
}
private void suaSoLuongTrongGio() {
    int selectedRow = tblHoaDonChiTiet.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trong giỏ để sửa!");
        return;
    }
    int idChiTietSanPham = Integer.parseInt(tblHoaDonChiTiet.getValueAt(selectedRow, 1).toString());
    int soLuongCu = Integer.parseInt(tblHoaDonChiTiet.getValueAt(selectedRow, 7).toString()); 

    String input = JOptionPane.showInputDialog(this, "Nhập số lượng mới:");
    if (input == null || input.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Số lượng không được để trống!");
        return;
    }
    int soLuongMoi;
    try {
        soLuongMoi = Integer.parseInt(input);
        if (soLuongMoi <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số lượng phải là số hợp lệ!");
        return;
    }
    int chenhlech = soLuongMoi - soLuongCu;
    ChiTietSanPham chiTiet = chiTietSanPhamRepo.getById(idChiTietSanPham); // cần có hàm này trong repo
    int soLuongTrongKho = chiTiet.getSoLuong();
    if (chenhlech > 0 && chenhlech > soLuongTrongKho) {
        JOptionPane.showMessageDialog(this, "Không đủ hàng trong kho để cập nhật số lượng!");
        return;
    }
    hoaDonChiTietRepo.capNhatSoLuong(
        HoaDonChiTiet.builder()
                .idHoaDon(idHoaDon)
                .idChiTietSanPham(idChiTietSanPham)
                .soLuong(soLuongMoi)
                .build()
    );
    int soLuongMoiTrongKho = soLuongTrongKho - chenhlech; 
    chiTietSanPhamRepo.suaSoLuong(
        ChiTietSanPham.builder()
                .id(idChiTietSanPham)
                .soLuong(soLuongMoiTrongKho)
                .build()
    );
    fillTableGiohang(hoaDonChiTietRepo.getAll(idHoaDon));
    fillTableChiTietSanPham(chiTietSanPhamRepo.getAll());
    capNhatTongTien();
    tinhTienThua();
}
public void xuatHoaDonPDF(HoaDon hd, ArrayList<HoaDonChiTietRespon> dsChiTiet) {
    if (dsChiTiet == null || dsChiTiet.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Không có sản phẩm trong hóa đơn để in!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu hóa đơn PDF");
        fileChooser.setSelectedFile(new File("HoaDon_" + hd.getId() + ".pdf"));
        int option = fileChooser.showSaveDialog(null);
        if (option != JFileChooser.APPROVE_OPTION) return;

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileChooser.getSelectedFile()));
        document.open();

        BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontTitle = new Font(baseFont, 20, Font.BOLD);
        Font fontHeader = new Font(baseFont, 12, Font.BOLD);
        Font fontContent = new Font(baseFont, 12, Font.NORMAL);

        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG\n\n", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("Mã hóa đơn: " + hd.getId(), fontContent));
        document.add(new Paragraph("Ngày tạo: " + hd.getNgayTao(), fontContent));
        document.add(new Paragraph("Khách hàng: " + hd.getTenNguoiNhan(), fontContent));
        document.add(new Paragraph("Số điện thoại: " + hd.getSoDienThoai(), fontContent));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 3, 2, 2, 2, 1, 2, 2});
        
        String[] headers = {
            "STT", "Tên sản phẩm", "Kích thước", "Màu sắc", "Chất liệu",
            "Số lượng", "Đơn giá", "Thành tiền"
        };
        for (String h : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(h, fontHeader));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
        }
        int tongTien = 0;
        int stt = 1;
        for (HoaDonChiTietRespon ct : dsChiTiet) {
            int thanhTien = ct.getSoLuong() * ct.getDonGia();
            tongTien += thanhTien;

            table.addCell(new Phrase(String.valueOf(stt++), fontContent));
            table.addCell(new Phrase(ct.getTenSanPham(), fontContent));
            table.addCell(new Phrase(ct.getTenKichThuoc(), fontContent));
            table.addCell(new Phrase(ct.getTenMauSac(), fontContent));
            table.addCell(new Phrase(ct.getTenChatLieu(), fontContent));
            table.addCell(new Phrase(String.valueOf(ct.getSoLuong()), fontContent));
            table.addCell(new Phrase(String.format("%,d", ct.getDonGia()), fontContent));
            table.addCell(new Phrase(String.format("%,d", thanhTien), fontContent));
        }
        PdfPCell cellTong = new PdfPCell(new Phrase("Tổng tiền", fontHeader));
        cellTong.setColspan(7);
        cellTong.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellTong.setPadding(5);
        cellTong.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cellTong);

        PdfPCell cellTien = new PdfPCell(new Phrase(String.format("%,d", tongTien), fontHeader));
        cellTien.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellTien.setPadding(5);
        cellTien.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cellTien);

        String selected = (String) cbbGiamGia.getSelectedItem();
        float giamGia = 0f;
        if (selected != null && !selected.equalsIgnoreCase("Không giảm giá")) {
            selected = selected.replace("%", "").trim();
            String[] parts = selected.split(" ");
            if (parts.length > 0) {
                try {
                    giamGia = Float.parseFloat(parts[0]);
                } catch (NumberFormatException ignored) {}
            }
        }

        int soTienGiam = Math.round(tongTien * (giamGia / 100f));
        int tongSauGiam = tongTien - soTienGiam;

        PdfPCell cellGiam = new PdfPCell(new Phrase("Giảm giá (" + (int) giamGia + "%)", fontHeader));
        cellGiam.setColspan(7);
        cellGiam.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellGiam.setPadding(5);
        cellGiam.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cellGiam);

        PdfPCell cellSoTienGiam = new PdfPCell(new Phrase("-" + String.format("%,d", soTienGiam), fontHeader));
        cellSoTienGiam.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellSoTienGiam.setPadding(5);
        cellSoTienGiam.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cellSoTienGiam);

        PdfPCell cellSauGiam = new PdfPCell(new Phrase("Tổng tiền sau giảm", fontHeader));
        cellSauGiam.setColspan(7);
        cellSauGiam.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellSauGiam.setPadding(5);
        cellSauGiam.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cellSauGiam);

        PdfPCell cellTongSauGiam = new PdfPCell(new Phrase(String.format("%,d", tongSauGiam), fontHeader));
        cellTongSauGiam.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellTongSauGiam.setPadding(5);
        cellTongSauGiam.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cellTongSauGiam);

        document.add(table);

        document.add(new Paragraph("\n\nCảm ơn quý khách đã mua hàng!", fontContent));
        document.add(new Paragraph("Người lập hóa đơn: ", fontContent));
        document.add(new Paragraph("Ngày in: " + LocalDate.now(), fontContent));

        document.close();
        JOptionPane.showMessageDialog(null, "Xuất hóa đơn thành công!");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Xuất hóa đơn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();
        btnXoaGioHang = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachSanPhamChiTiet = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtTimKiemSPCT = new javax.swing.JTextField();
        txtMin = new javax.swing.JTextField();
        txtMax = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbbHTTT = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        lblTienThua = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        cbbGiamGia = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        btnHuyHD = new javax.swing.JButton();
        btnXuatHD = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        lblTienTruocGiam = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblSoTienGiam = new javax.swing.JLabel();
        lblSoTienGiam1 = new javax.swing.JLabel();
        btnKhachHang = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Bán hàng");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Hóa đơn"));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên khách hàng", "Số điện thoại", "Ngày tạo", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setResizable(false);
            tblHoaDon.getColumnModel().getColumn(1).setResizable(false);
            tblHoaDon.getColumnModel().getColumn(2).setResizable(false);
            tblHoaDon.getColumnModel().getColumn(3).setResizable(false);
            tblHoaDon.getColumnModel().getColumn(4).setResizable(false);
        }

        btnTaoHoaDon.setBackground(new java.awt.Color(255, 204, 51));
        btnTaoHoaDon.setText("Tạo hóa đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTaoHoaDon)
                        .addGap(0, 771, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTaoHoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Giỏ hàng"));

        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "IDSPCT", "Tên sản phẩm", "Màu sắc", "Chất liệu", "Kích thước", "Cổ áo", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChiTietMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDonChiTiet);
        if (tblHoaDonChiTiet.getColumnModel().getColumnCount() > 0) {
            tblHoaDonChiTiet.getColumnModel().getColumn(0).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(1).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(2).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(3).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(4).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(5).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(6).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(7).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(8).setResizable(false);
            tblHoaDonChiTiet.getColumnModel().getColumn(9).setResizable(false);
        }

        btnXoaGioHang.setText("X");
        btnXoaGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaGioHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnXoaGioHang)))
                .addGap(8, 8, 8))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Danh sách sản phẩm chi tiết"));

        tblDanhSachSanPhamChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Ma SPCT", "Tên sản phẩm", "Màu sắc", "Chất liệu", "Kích thước", "Cổ áo", "Số lượng", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachSanPhamChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachSanPhamChiTietMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachSanPhamChiTiet);
        if (tblDanhSachSanPhamChiTiet.getColumnModel().getColumnCount() > 0) {
            tblDanhSachSanPhamChiTiet.getColumnModel().getColumn(0).setResizable(false);
            tblDanhSachSanPhamChiTiet.getColumnModel().getColumn(1).setResizable(false);
            tblDanhSachSanPhamChiTiet.getColumnModel().getColumn(2).setResizable(false);
            tblDanhSachSanPhamChiTiet.getColumnModel().getColumn(3).setResizable(false);
            tblDanhSachSanPhamChiTiet.getColumnModel().getColumn(4).setResizable(false);
            tblDanhSachSanPhamChiTiet.getColumnModel().getColumn(5).setResizable(false);
            tblDanhSachSanPhamChiTiet.getColumnModel().getColumn(6).setResizable(false);
            tblDanhSachSanPhamChiTiet.getColumnModel().getColumn(7).setResizable(false);
            tblDanhSachSanPhamChiTiet.getColumnModel().getColumn(8).setResizable(false);
        }

        jLabel8.setText("Tìm kiếm");

        jLabel10.setText("Khoảng giá từ");

        jLabel11.setText("đến");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimKiemSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtMin, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtMax, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTimKiemSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Khách hàng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel2.setText("Tên khách hàng");

        txtTenKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKhachHangActionPerformed(evt);
            }
        });

        jLabel3.setText("Số điện thoại");

        btnThanhToan.setBackground(new java.awt.Color(102, 255, 153));
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jLabel4.setText("HTTT");

        jLabel5.setText("Tiền khách đưa");

        cbbHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản" }));

        jLabel6.setText("Tiền thừa");

        jLabel7.setText("Tổng tiền");

        cbbGiamGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Giảm giá");

        btnHuyHD.setBackground(new java.awt.Color(255, 51, 102));
        btnHuyHD.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyHD.setText("Hủy thanh toán");
        btnHuyHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHDActionPerformed(evt);
            }
        });

        btnXuatHD.setBackground(new java.awt.Color(255, 204, 204));
        btnXuatHD.setText("Xuất hóa đơn");
        btnXuatHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatHDActionPerformed(evt);
            }
        });

        jLabel12.setText("Tiền trước giảm");

        jLabel13.setText("Số tiền giảm");

        btnKhachHang.setText("Khach");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(btnXuatHD, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbbHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(185, 185, 185))
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblTienTruocGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel6)
                                                    .addComponent(jLabel9))
                                                .addGap(20, 20, 20)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cbbGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnHuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblSoTienGiam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSoTienGiam1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnKhachHang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnKhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbbHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTienTruocGiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSoTienGiam1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSoTienGiam)
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuyHD)
                    .addComponent(btnThanhToan))
                .addGap(18, 18, 18)
                .addComponent(btnXuatHD)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(613, 613, 613))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 58, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        // TODO add your handling code here:
String tenKhachHang = txtTenKhachHang.getText().trim();
    String soDienThoai = txtSDT.getText().trim();
    if (tenKhachHang.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (soDienThoai.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (!soDienThoai.matches("^(0[3|5|7|8|9])+([0-9]{8})$")) {
        JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    hoaDonRepo.themHoaDon(new HoaDon(
        null,  
        tenKhachHang,
        soDienThoai,
        dateFormat.format(date), 
        null
    ));
    fillHoaDonTable(hoaDonRepo.getAllChuaThanhToan());
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void tblDanhSachSanPhamChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachSanPhamChiTietMouseClicked
        // TODO add your handling code here:
    int selectedIndex = tblDanhSachSanPhamChiTiet.getSelectedRow();
    if (selectedIndex == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để thêm vào giỏ hàng!");
        return;
    }
    String inputSoLuong = JOptionPane.showInputDialog(this, "Nhập số lượng mua");
    if (inputSoLuong == null || inputSoLuong.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Số lượng mua không được để trống!");
        return;
    }
    int soLuongMua;
    try {
        soLuongMua = Integer.parseInt(inputSoLuong);
        if (soLuongMua <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng mua phải lớn hơn 0!");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số lượng mua phải là một số hợp lệ!");
        return;
    }
    int idSanPhamChiTiet = Integer.valueOf(tblDanhSachSanPhamChiTiet.getValueAt(selectedIndex, 0).toString());
    int soLuongHienTai = Integer.valueOf(tblDanhSachSanPhamChiTiet.getValueAt(selectedIndex, 7).toString());
    if (soLuongMua > soLuongHienTai) {
        JOptionPane.showMessageDialog(this, "Số lượng trong kho không đủ để đáp ứng yêu cầu!");
        return;
    }
    int soLuongMoi = soLuongHienTai - soLuongMua;
    chiTietSanPhamRepo.suaSoLuong(
        ChiTietSanPham.builder()
                .id(idSanPhamChiTiet)
                .soLuong(soLuongMoi)
                .build()
);
    fillTableChiTietSanPham(chiTietSanPhamRepo.getAll());

    int donGia = Integer.valueOf(tblDanhSachSanPhamChiTiet.getValueAt(selectedIndex, 8).toString());
    List<HoaDonChiTietRespon> gioHang = hoaDonChiTietRepo.getAll(idHoaDon);
    boolean daCoTrongGio = false;

    for (HoaDonChiTietRespon item : gioHang) {
        if (item.getIdChiTietSanPham() == idSanPhamChiTiet) {
            int soLuongHienTaiTrongGio = item.getSoLuong();
            int soLuongMoiTrongGio = soLuongHienTaiTrongGio + soLuongMua;

            hoaDonChiTietRepo.capNhatSoLuong(
                    HoaDonChiTiet.builder()
                            .idHoaDon(idHoaDon)
                            .idChiTietSanPham(idSanPhamChiTiet)
                            .soLuong(soLuongMoiTrongGio)
                            .build()
            );
            daCoTrongGio = true;
            break;
        }
    }
    if (!daCoTrongGio) {
        hoaDonChiTietRepo.themHoaDonChiTiet(
                HoaDonChiTiet.builder()
                        .idChiTietSanPham(idSanPhamChiTiet)
                        .idHoaDon(idHoaDon)
                        .soLuong(soLuongMua)
                        .donGia(donGia)
                        .build()
        );
    }
    fillTableGiohang(hoaDonChiTietRepo.getAll(idHoaDon));
    tinhTienThua();
    capNhatTongTien();
    }//GEN-LAST:event_tblDanhSachSanPhamChiTietMouseClicked

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        int selectedIndex = tblHoaDon.getSelectedRow();
        txtTenKhachHang.setText(tblHoaDon.getValueAt(selectedIndex, 1).toString());
        txtSDT.setText(tblHoaDon.getValueAt(selectedIndex, 2).toString());
        idHoaDon = Integer.valueOf(tblHoaDon.getValueAt(selectedIndex, 0).toString());
        fillTableGiohang(hoaDonChiTietRepo.getAll(idHoaDon));
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
    int selectedRow = tblHoaDon.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần thanh toán!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (dtmHoaDonChiTiet.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Giỏ hàng trống! Vui lòng thêm sản phẩm trước khi thanh toán.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this,  "Bạn có chắc chắn muốn thanh toán hóa đơn này không?",  "Xác nhận thanh toán",  JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        int idHoaDon = Integer.parseInt(tblHoaDon.getValueAt(selectedRow, 0).toString());
        hoaDonRepo.thanhToanHoaDon(HoaDon.builder().id(idHoaDon).build());
        fillHoaDonTable(hoaDonRepo.getAllChuaThanhToan());
        dtmHoaDonChiTiet.setRowCount(0);
        JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
    }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void tblHoaDonChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChiTietMouseClicked
        // TODO add your handling code here:
        suaSoLuongTrongGio();
    }//GEN-LAST:event_tblHoaDonChiTietMouseClicked

    private void btnXoaGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaGioHangActionPerformed
        // TODO add your handling code here:
   int selectedRow = tblHoaDonChiTiet.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa khỏi giỏ hàng!");
        return;
    }
    int idChiTietSanPham = Integer.parseInt(tblHoaDonChiTiet.getValueAt(selectedRow, 1).toString());
    
    ChiTietSanPham sp = chiTietSanPhamRepo.getById(idChiTietSanPham);
    int soLuongTonKhoHienTai = sp.getSoLuong();
    int soLuongDaMua = hoaDonChiTietRepo.xoaVaTraVeSoLuong1(idHoaDon, idChiTietSanPham);
    int soLuongMoi = soLuongTonKhoHienTai + soLuongDaMua;
    chiTietSanPhamRepo.capNhatSoLuongMoi(idChiTietSanPham, soLuongMoi);
    fillTableGiohang(hoaDonChiTietRepo.getAll(idHoaDon));
    fillTableChiTietSanPham(chiTietSanPhamRepo.getAll());
    tinhTienThua();
    capNhatTongTien();
    }//GEN-LAST:event_btnXoaGioHangActionPerformed

    private void btnHuyHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHDActionPerformed
        // TODO add your handling code here:
      int selectedRow = tblHoaDon.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần hủy!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int idHoaDon = Integer.parseInt(tblHoaDon.getValueAt(selectedRow, 0).toString());
    for (int i = 0; i < tblHoaDonChiTiet.getRowCount(); i++) {
        int idChiTietSanPham = Integer.parseInt(tblHoaDonChiTiet.getValueAt(i, 1).toString());
        ChiTietSanPham sp = chiTietSanPhamRepo.getById(idChiTietSanPham);
        if (sp != null) {
            int soLuongTonKhoHienTai = sp.getSoLuong();
            int soLuongDaMua = hoaDonChiTietRepo.xoaVaTraVeSoLuong(idHoaDon, idChiTietSanPham);
            int soLuongMoi = soLuongTonKhoHienTai + soLuongDaMua;
            chiTietSanPhamRepo.capNhatSoLuongMoi(idChiTietSanPham, soLuongMoi);
        }
    }
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Bạn có chắc chắn muốn hủy hóa đơn này không?", 
        "Xác nhận hủy thanh toán", 
        JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        hoaDonRepo.huyToanHoaDon(HoaDon.builder().id(idHoaDon).build());
    fillHoaDonTable(hoaDonRepo.getAllChuaThanhToan());
    dtmHoaDonChiTiet.setRowCount(0); 
    fillTableGiohang(hoaDonChiTietRepo.getAll(idHoaDon)); 
    fillTableChiTietSanPham(chiTietSanPhamRepo.getAll());  
    JOptionPane.showMessageDialog(this, "Hủy thanh toán thành công!");
    txtTenKhachHang.setText("");
    txtSDT.setText("");
    lblTongTien.setText("");
    } else {
        JOptionPane.showMessageDialog(this, "Hủy hóa đơn đã bị hủy!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    }//GEN-LAST:event_btnHuyHDActionPerformed

    private void btnXuatHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatHDActionPerformed
        // TODO add your handling code here:
    int selectedRow = tblHoaDon.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return; 
    }
     idHoaDon = Integer.parseInt(tblHoaDon.getValueAt(selectedRow, 0).toString());
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Bạn có chắc chắn muốn xuất hóa đơn này?", 
        "Xác nhận xuất hóa đơn", 
        JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.NO_OPTION) {
        JOptionPane.showMessageDialog(this, "Hành động xuất hóa đơn đã bị hủy.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    HoaDon hoaDon = hoaDonRepo.getById(idHoaDon);
    if (hoaDon == null) {
        JOptionPane.showMessageDialog(this, "Hóa đơn không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    ArrayList<HoaDonChiTietRespon> chiTietList = hoaDonChiTietRepo.getAllByHoaDonId(idHoaDon);
    if (chiTietList == null || chiTietList.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Hóa đơn không có chi tiết để xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    xuatHoaDonPDF(hoaDon, chiTietList);
    }//GEN-LAST:event_btnXuatHDActionPerformed

    private void txtTenKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKhachHangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuyHD;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXoaGioHang;
    private javax.swing.JButton btnXuatHD;
    private javax.swing.JComboBox<String> cbbGiamGia;
    private javax.swing.JComboBox<String> cbbHTTT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblSoTienGiam;
    private javax.swing.JLabel lblSoTienGiam1;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTienTruocGiam;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tblDanhSachSanPhamChiTiet;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTextField txtMax;
    private javax.swing.JTextField txtMin;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTimKiemSPCT;
    // End of variables declaration//GEN-END:variables
}

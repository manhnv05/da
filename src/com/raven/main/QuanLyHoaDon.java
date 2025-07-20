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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.giamgia.GiamGia;
import model.hoadon.HoaDon;
import repo.hoadon.HoaDonChiTietRepo;
import repo.hoadon.HoaDonRepo;
import respon.HoaDonChiTietRespon;

/**
 *
 * @author nguye
 */
public class QuanLyHoaDon extends javax.swing.JPanel {
private HoaDonRepo hoaDonRepo;
private HoaDonChiTietRepo hoaDonChiTietRepo;
private DefaultTableModel dtmHoaDon;
private DefaultTableModel dtmHoaDonChiTiet;
private BanHangForm banHangForm;
    /**
     * Creates new form QuanLyHoaDon
     */
     int idHoaDon = -1;
    public QuanLyHoaDon(BanHangForm banHangForm) {
        initComponents();
        this.banHangForm = banHangForm;
        long tienGiam = banHangForm.gettongTienMuaHang();
        int tienMua = banHangForm.gettongTienSauGiam();
    System.out.println(">>> Số tiền lấy từ BanHangForm: " + tienMua);
    System.out.println(">>> Số tiền lấy từ BanHangForm: " + tienGiam);
//    lblTongTien.setText(tienGiam + " VNĐ");
//    lblTongTien.setText(tienMua + " VNĐ");
        
        hoaDonRepo = new HoaDonRepo();
        hoaDonChiTietRepo = new HoaDonChiTietRepo();
        
        dtmHoaDon = (DefaultTableModel) tblHoaDonn.getModel();
        dtmHoaDonChiTiet = (DefaultTableModel) tblGioHangg.getModel();
        fillTableHoaDon(hoaDonRepo.getAll());
        
         addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                fillTableHoaDon(hoaDonRepo.getAll());
            }
        });
         textSearch();
       
    }
     private void fillTableGiohang(ArrayList<HoaDonChiTietRespon> danhSach) {
          dtmHoaDonChiTiet.setRowCount(0);
          for(HoaDonChiTietRespon hdct: danhSach) {
              dtmHoaDonChiTiet.addRow(new Object[]{
                 hdct.getId(),
                 hdct.getTenSanPham(),
                 hdct.getTenMauSac(),
                 hdct.getTenChatLieu(),
                 hdct.getTenKichThuoc(),
                 hdct.getTenCoAo(),
                 hdct.getSoLuong(),
                 hdct.getDonGia()
              });
          }
      }
private void fillTableHoaDon(ArrayList<HoaDon> list) {
    dtmHoaDon.setRowCount(0); // Xóa hết các dòng cũ trong bảng
    for (HoaDon hoaDon : list) {
        // Lọc hóa đơn không có trạng thái "Đã hủy" nếu bạn chỉ muốn hiển thị chưa thanh toán và đã thanh toán
        String trangThai = (hoaDon.getTrangThai() == 1) ? "Chưa thanh toán" :
                           (hoaDon.getTrangThai() == 2) ? "Đã thanh toán" :
                           (hoaDon.getTrangThai() == 3) ? "Đã hủy" : "Không xác định"; // Thêm "Không xác định" nếu cần

        dtmHoaDon.addRow(new Object[]{
            hoaDon.getId(),
            hoaDon.getTenNguoiNhan(),
            hoaDon.getSoDienThoai(),
            hoaDon.getNgayTao(),
            trangThai
        });
    }
}

// private void showTable(int index) {
//        HoaDon hd = hoaDonRepo.getAll().get(index);
//        HoaDonChiTietRespon hdct = hoaDonChiTietRepo.getAll(idHoaDon).get(index);
//        lblTenKH.setText(hd.getTenNguoiNhan());
//        lblSDT.setText(hd.getSoDienThoai());
//        lblNgayTao.setText(hd.getNgayTao()+"");
//        lblTenSP.setText(hdct.getTenSanPham()+ "");
//        lblSoLuong.setText(hdct.getSoLuong()+ "");
////        lblTongTien.setText(hd.getNgayBatDau());
//
//    }


    public void xuatHoaDonPDF(HoaDon hd, ArrayList<HoaDonChiTietRespon> dsChiTiet) {
    try {
        // Tạo hộp thoại chọn nơi lưu file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu hóa đơn PDF");
        fileChooser.setSelectedFile(new File("HoaDon_" + hd.getId() + ".pdf"));
        int option = fileChooser.showSaveDialog(null);
        if (option != JFileChooser.APPROVE_OPTION) return;

        // Tạo file PDF
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileChooser.getSelectedFile()));
        document.open();

        // Font tiêu đề
        BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontTitle = new Font(baseFont, 20, Font.BOLD);
        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG\n\n", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Font nội dung
BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
Font fontHeader = new Font(bf, 12, Font.BOLD);
Font fontContent = new Font(bf, 12, Font.NORMAL);
        // Thông tin hóa đơn
        document.add(new Paragraph("Mã hóa đơn: " + hd.getId(), fontContent));
        document.add(new Paragraph("Ngày tạo: " + hd.getNgayTao(), fontContent));
        document.add(new Paragraph("Khách hàng: " + hd.getTenNguoiNhan(), fontContent));
        document.add(new Paragraph("Số điện thoại: " + hd.getSoDienThoai(), fontContent));
//        document.add(new Paragraph("Tổng tiền: " + String.format("%,d", hd.getTongTien()) + " VND", fontContent));
        document.add(new Paragraph("\n"));
        

        // Bảng sản phẩm
       // Khai báo font hỗ trợ tiếng Việt (VD: Arial Unicode)


// Tạo bảng
// Khởi tạo bảng với 4 cột
PdfPTable table = new PdfPTable(5);
table.setWidthPercentage(100);
table.setSpacingBefore(10f);
table.setSpacingAfter(10f);
table.setWidths(new float[]{3, 1, 2, 2}); // Tỷ lệ độ rộng cột

// ==== Thêm tiêu đề cột ====
PdfPCell soTTSPHeader = new PdfPCell(new Phrase("STT", fontHeader));
soTTSPHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
soTTSPHeader.setPadding(5);
soTTSPHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
table.addCell(soTTSPHeader);

PdfPCell tenSPHeader = new PdfPCell(new Phrase("Tên sản phẩm", fontHeader));
tenSPHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
tenSPHeader.setPadding(5);
tenSPHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
table.addCell(tenSPHeader);

PdfPCell soLuongHeader = new PdfPCell(new Phrase("Số lượng", fontHeader));
soLuongHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
soLuongHeader.setPadding(5);
soLuongHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
table.addCell(soLuongHeader);

PdfPCell donGiaHeader = new PdfPCell(new Phrase("Đơn giá", fontHeader));
donGiaHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
donGiaHeader.setPadding(5);
donGiaHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
table.addCell(donGiaHeader);

PdfPCell thanhTienHeader = new PdfPCell(new Phrase("Thành tiền", fontHeader));
thanhTienHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
thanhTienHeader.setPadding(5);
thanhTienHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
table.addCell(thanhTienHeader);

// ==== Dữ liệu bảng ====
int tongTien = 0;
for (HoaDonChiTietRespon ct : dsChiTiet) {
    int thanhTien = ct.getSoLuong() * ct.getDonGia();
    tongTien += thanhTien;

    PdfPCell tenSPCell = new PdfPCell(new Phrase(ct.getTenSanPham(), fontContent));
    tenSPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
    tenSPCell.setPadding(5);
    table.addCell(tenSPCell);

    PdfPCell soLuongCell = new PdfPCell(new Phrase(String.valueOf(ct.getSoLuong()), fontContent));
    soLuongCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    soLuongCell.setPadding(5);
    table.addCell(soLuongCell);

    PdfPCell donGiaCell = new PdfPCell(new Phrase(String.format("%,d", ct.getDonGia()), fontContent));
    donGiaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    donGiaCell.setPadding(5);
    table.addCell(donGiaCell);

    PdfPCell thanhTienCell = new PdfPCell(new Phrase(String.format("%,d", thanhTien), fontContent));
    thanhTienCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    thanhTienCell.setPadding(5);
    table.addCell(thanhTienCell);
}

// ==== Dòng tổng tiền ====
PdfPCell cellTong = new PdfPCell(new Phrase("Tổng tiền", fontHeader));
cellTong.setColspan(3);
cellTong.setHorizontalAlignment(Element.ALIGN_RIGHT);
cellTong.setPadding(5);
cellTong.setBackgroundColor(BaseColor.LIGHT_GRAY);
table.addCell(cellTong);

PdfPCell cellTien = new PdfPCell(new Phrase(String.format("%,d", tongTien), fontHeader));
cellTien.setHorizontalAlignment(Element.ALIGN_RIGHT);
cellTien.setPadding(5);
cellTien.setBackgroundColor(BaseColor.LIGHT_GRAY);
table.addCell(cellTien);

// Thêm bảng vào document
        document.add(table);
        document.close();

        JOptionPane.showMessageDialog(null, "Xuất hóa đơn thành công!");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Xuất hóa đơn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
      private void textSearch() {
        txtTimKiemHD.getDocument().addDocumentListener(new DocumentListener() {
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
    String input = txtTimKiemHD.getText().trim();
        fillTableHoaDon(hoaDonRepo.search(input));
}
public void loadTableHoaDon(ArrayList<HoaDon> list) {
    DefaultTableModel model = (DefaultTableModel) tblHoaDonn.getModel();
    model.setRowCount(0); // Xóa dữ liệu cũ

    for (HoaDon hd : list) {
           String trangThai = (hd.getTrangThai() == 1) ? "Chưa thanh toán" :
                           (hd.getTrangThai() == 2) ? "Đã thanh toán" :
                           (hd.getTrangThai() == 3) ? "Đã hủy" : "Không xác định"; 
        model.addRow(new Object[]{
            hd.getId(),
            hd.getTenNguoiNhan(),
            hd.getSoDienThoai(),
            hd.getNgayTao(),
            trangThai
            // Thêm các cột khác nếu có
        });
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
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonn = new javax.swing.JTable();
        cbbLocHD = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtTimKiemHD = new javax.swing.JTextField();
        jdcLoc = new com.toedter.calendar.JDateChooser();
        btnLocNgay = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblGioHangg = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Quản lý hoá đơn");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Thông tin hóa đơn"));

        tblHoaDonn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Ten khach hang", "SDT", "Ngay tao", "Trang thai"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonnMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDonn);
        if (tblHoaDonn.getColumnModel().getColumnCount() > 0) {
            tblHoaDonn.getColumnModel().getColumn(0).setResizable(false);
            tblHoaDonn.getColumnModel().getColumn(1).setResizable(false);
            tblHoaDonn.getColumnModel().getColumn(2).setResizable(false);
            tblHoaDonn.getColumnModel().getColumn(3).setResizable(false);
            tblHoaDonn.getColumnModel().getColumn(4).setResizable(false);
        }

        cbbLocHD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Chưa thanh toán", "Đã thanh toán", "Đã hủy" }));
        cbbLocHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLocHDActionPerformed(evt);
            }
        });

        jLabel2.setText("Tìm kiếm");

        btnLocNgay.setText("Loc");
        btnLocNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocNgayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(cbbLocHD, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jdcLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(btnLocNgay)
                .addContainerGap(212, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbLocHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jdcLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLocNgay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Hóa đơn chi tiết"));

        tblGioHangg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên sản phẩm", "Màu sắc", "Chất liệu", "Kích thước", "Cổ áo", "Số lượng", "Đơn giá"
            }
        ));
        jScrollPane3.setViewportView(tblGioHangg);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(475, 475, 475))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonnMouseClicked
        // TODO add your handling code here:
        int selectedIndex = tblHoaDonn.getSelectedRow();
        idHoaDon = Integer.valueOf(tblHoaDonn.getValueAt(selectedIndex, 0).toString());
        fillTableGiohang(hoaDonChiTietRepo.getAll(idHoaDon));
//        showTable(tblHoaDonn.getSelectedRow());
int selectedRow = tblHoaDonn.getSelectedRow();
if (selectedRow == -1) {
    JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn.");
    return;
}

// Lấy ID hóa đơn từ bảng
int idHoaDon = Integer.parseInt(tblHoaDonn.getValueAt(selectedRow, 0).toString());

// Lấy thông tin hóa đơn từ danh sách (nếu bạn có listHoaDon riêng thì dùng nó, nếu không thì lấy từ bảng như ở trên)
HoaDon hd = hoaDonRepo.getById(idHoaDon); // hoặc từ danh sách bạn đang dùng

// Gọi chi tiết hóa đơn
ArrayList<HoaDonChiTietRespon> listHDCT = hoaDonChiTietRepo.getAll(idHoaDon);
if (listHDCT.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Không có sản phẩm nào trong hóa đơn.");
    return;
}

// Lấy sản phẩm đầu tiên hoặc theo logic bạn muốn (ở đây lấy dòng đầu)
HoaDonChiTietRespon hdct = listHDCT.get(0);
// Set lên các label




//lblTongTien.setText();
    }//GEN-LAST:event_tblHoaDonnMouseClicked

    private void cbbLocHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLocHDActionPerformed
        // TODO add your handling code here:
         String selectedItem = cbbLocHD.getSelectedItem().toString();
        if (selectedItem.equals("Chưa thanh toán")) {
            this.fillTableHoaDon(hoaDonRepo.getAllTrangThai(1));
        } else if (selectedItem.equals("Đã thanh toán")) {
            this.fillTableHoaDon(hoaDonRepo.getAllTrangThai(2));
        } else if (selectedItem.equals("Đã hủy")) {
            this.fillTableHoaDon(hoaDonRepo.getAllTrangThai(3));
        } else if (selectedItem.equals("Tất cả")) {
            this.fillTableHoaDon(hoaDonRepo.getAll());
        } else {
            // Xử lý trường hợp mặc định hoặc không hợp lệ nếu cần
            JOptionPane.showMessageDialog(this, "Trạng thái không hợp lệ");
        }
    }//GEN-LAST:event_cbbLocHDActionPerformed

    private void btnLocNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocNgayActionPerformed
        // TODO add your handling code here:
         Date selectedDate = jdcLoc.getDate();

    if (selectedDate != null) {
        // Bước 2: Chuyển sang java.sql.Date để truy vấn SQL
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        // Bước 3: Gọi phương thức lọc
        ArrayList<HoaDon> danhSach = hoaDonRepo.locTheoNgay(sqlDate);

        // Bước 4: Hiển thị lên bảng
        loadTableHoaDon(danhSach);
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày để lọc!");
    }
    }//GEN-LAST:event_btnLocNgayActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLocNgay;
    private javax.swing.JComboBox<String> cbbLocHD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.toedter.calendar.JDateChooser jdcLoc;
    private javax.swing.JTable tblGioHangg;
    private javax.swing.JTable tblHoaDonn;
    private javax.swing.JTextField txtTimKiemHD;
    // End of variables declaration//GEN-END:variables
}

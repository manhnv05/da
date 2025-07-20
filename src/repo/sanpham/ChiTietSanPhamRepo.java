/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.sanpham;
import java.util.ArrayList;
import utils.DBConnect;
import java.sql.*;
import model.sanpham.ChiTietSanPham;
import respon.ChiTietSanPhamRespon;
/**
 *
 * @author nguye
 */
public class ChiTietSanPhamRepo {
     private Connection conn = null;
     
    public ChiTietSanPhamRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<ChiTietSanPhamRespon> getAll() {
        ArrayList<ChiTietSanPhamRespon> listCTSP = new ArrayList<>();
        String sql = """
                       select spct.id, sp.ma, sp.ten, ms.ten, kt.ten, cl.ten, th.ten, ka.ten, ca.ten,spct.so_luong, spct.don_gia
                        from SanPhamChiTiet spct
                       join SanPham sp on spct.id_san_pham = sp.id
                       join MauSac ms on spct.id_mau_sac = ms.id
                       join KichThuoc kt on spct.id_kich_thuoc= kt.id
                       join ChatLieu cl on spct.id_chat_lieu = cl.id
                       join ThuongHieu th on spct.id_thuong_hieu = th.id
                       join KhoaAo ka on spct.id_khoa_ao = ka.id
                       join CoAo ca on spct.id_co_ao = ca.id
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                listCTSP.add(new ChiTietSanPhamRespon(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),                   
                        rs.getInt(10),                   
                        rs.getInt(11)
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listCTSP;
    }
    
   public void themChiTietSanPham(ChiTietSanPham chiTietSanPham) {
    if (chiTietSanPham == null) {
        System.out.println("Lỗi: Sản phẩm chi tiết bị null!");
        return;
    }

    // Kiểm tra các thuộc tính bắt buộc
    if (chiTietSanPham.getIdSanPham() == null) {
        System.out.println("Lỗi: ID sản phẩm bị null!");
        return;
    }

    String maSPCT = generateMaSPCT(chiTietSanPham);
    System.out.println("Thêm sản phẩm chi tiết với mã: " + maSPCT);

    String sql = """
        INSERT INTO [dbo].[SanPhamChiTiet]
            ([ma_san_pham_chi_tiet], [id_san_pham], [id_mau_sac], 
            [id_kich_thuoc], [id_chat_lieu], [id_thuong_hieu], 
            [id_khoa_ao], [id_co_ao], [so_luong], [don_gia]) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, maSPCT);
        ps.setInt(2, chiTietSanPham.getIdSanPham());
        ps.setInt(3, chiTietSanPham.getIdMauSac());
        ps.setInt(4, chiTietSanPham.getIdKichThuoc());
        ps.setInt(5, chiTietSanPham.getIdChatLieu());
        ps.setInt(6, chiTietSanPham.getIdThuongHieu());
        ps.setInt(7, chiTietSanPham.getIdKhoaAo());
        ps.setInt(8, chiTietSanPham.getIdCoAo());
        ps.setInt(9, chiTietSanPham.getSoLuong());
        ps.setInt(10, chiTietSanPham.getDonGia());

        int rows = ps.executeUpdate();
        System.out.println("Số dòng thêm vào: " + rows);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void updateMaSPCTInDatabase(int id, String newMaSPCT) {
    String sql = "UPDATE [dbo].[SanPhamChiTiet] SET ma_san_pham_chi_tiet = ? WHERE id = ?";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newMaSPCT);
        ps.setInt(2, id);
        ps.executeUpdate();
        System.out.println("Cập nhật mã SPCT thành: " + newMaSPCT);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

     
      public void suaChiTietSanPham(ChiTietSanPham chiTietSanPham) {
        String sql = """
                        UPDATE [dbo].[SanPhamChiTiet]
                           SET [ma_san_pham_chi_tiet] = ?
                              ,[id_san_pham] = ?
                              ,[id_mau_sac] = ?
                              ,[id_kich_thuoc] = ?
                              ,[id_chat_lieu] = ?
                              ,[id_thuong_hieu] = ?
                              ,[id_khoa_ao] = ?
                              ,[id_co_ao] = ?
                              ,[so_luong] = ?
                              ,[don_gia] = ?
                         WHERE id=?
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, chiTietSanPham.getMaSanPhamChiTiet());
            ps.setInt(2, chiTietSanPham.getIdSanPham());
            ps.setInt(3, chiTietSanPham.getIdMauSac());
            ps.setInt(4, chiTietSanPham.getIdKichThuoc());
            ps.setInt(5, chiTietSanPham.getIdChatLieu());
            ps.setInt(6, chiTietSanPham.getIdThuongHieu());
            ps.setInt(7, chiTietSanPham.getIdKhoaAo());
            ps.setInt(8, chiTietSanPham.getIdCoAo());
            ps.setInt(9, chiTietSanPham.getSoLuong());
            ps.setInt(10, chiTietSanPham.getDonGia());
            ps.setInt(11, chiTietSanPham.getId());
            ps.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
      
 public void deleteSanPhamChiTiet(ChiTietSanPham chiTiet) {
    if (chiTiet == null) {
        System.out.println("Không có sản phẩm chi tiết nào được chọn!");
        return;
    }

    try {
        // 1️⃣ Xóa dữ liệu liên quan trước (theo thứ tự tránh lỗi ràng buộc khóa ngoại)
        String sql1 = "DELETE FROM HoaDonChiTiet WHERE id_san_pham_chi_tiet = ?";
//        String sql2 = "DELETE FROM KhoHang WHERE id_san_pham_chi_tiet = ?";
//        String sql3 = "DELETE FROM PhieuNhapChiTiet WHERE id_san_pham_chi_tiet = ?";
//        String sql4 = "DELETE FROM GioHangChiTiet WHERE id_san_pham_chi_tiet = ?";
        
        // 2️⃣ Xóa sản phẩm chi tiết sau khi đã xóa hết các bảng liên quan
        String sql2 = "DELETE FROM SanPhamChiTiet WHERE id = ?";

        // Thực hiện xóa từng bảng
        PreparedStatement ps1 = conn.prepareStatement(sql1);
        ps1.setInt(1, chiTiet.getId());
        ps1.executeUpdate();
        // Cuối cùng xóa sản phẩm chi tiết
        PreparedStatement ps2 = conn.prepareStatement(sql2);
        ps2.setInt(1, chiTiet.getId());
        int rowsAffected = ps2.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Xóa sản phẩm chi tiết thành công!");
        } else {
            System.out.println("Không tìm thấy sản phẩm để xóa!");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public ArrayList<ChiTietSanPhamRespon> search(String tenSP) {
    String sql = """
        SELECT spct.id, sp.ma, sp.ten, ms.ten, kt.ten, cl.ten, th.ten, ka.ten, ca.ten, spct.so_luong, spct.don_gia
        FROM SanPhamChiTiet spct
        JOIN SanPham sp ON spct.id_san_pham = sp.id
        JOIN MauSac ms ON spct.id_mau_sac = ms.id
        JOIN KichThuoc kt ON spct.id_kich_thuoc= kt.id
        JOIN ChatLieu cl ON spct.id_chat_lieu = cl.id
        JOIN ThuongHieu th ON spct.id_thuong_hieu = th.id
        JOIN KhoaAo ka ON spct.id_khoa_ao = ka.id
        JOIN CoAo ca ON spct.id_co_ao = ca.id
        WHERE sp.ten LIKE ? OR ms.ten LIKE ? OR kt.ten LIKE ? OR cl.ten LIKE ? OR th.ten LIKE ?
    """;

    ArrayList<ChiTietSanPhamRespon> lists = new ArrayList<>();
    try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        for (int i = 1; i <= 5; i++) {
            ps.setObject(i, "%" + tenSP + "%");
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lists.add(new ChiTietSanPhamRespon(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getInt(11)
            ));
        }
    } catch (Exception e) {
        e.printStackTrace(System.out);
    }
    return lists;
}

 public void suaSoLuong(ChiTietSanPham chiTietSP) {
        String sql = """
                        UPDATE [dbo].[SanPhamChiTiet]
                        SET [so_luong] = ?
                        WHERE id = ?
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, chiTietSP.getSoLuong());
            ps.setInt(2, chiTietSP.getId());
            ps.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
 
 public void capNhatSoLuongMoi(int idChiTietSanPham, int soLuongMoi) {
    String sql = "UPDATE SanPhamChiTiet SET so_luong = ? WHERE id = ?";
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, soLuongMoi);
        ps.setInt(2, idChiTietSanPham);
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public ArrayList<ChiTietSanPhamRespon> searchByPriceRange(int minPrice, int maxPrice) {
    ArrayList<ChiTietSanPhamRespon> list = new ArrayList<>();
    String sql = """
        SELECT spct.id, sp.ma, sp.ten, ms.ten, kt.ten, cl.ten, th.ten, ka.ten, ca.ten, spct.so_luong, spct.don_gia
        FROM SanPhamChiTiet spct
        JOIN SanPham sp ON spct.id_san_pham = sp.id
        JOIN MauSac ms ON spct.id_mau_sac = ms.id
        JOIN KichThuoc kt ON spct.id_kich_thuoc= kt.id
        JOIN ChatLieu cl ON spct.id_chat_lieu = cl.id
        JOIN ThuongHieu th ON spct.id_thuong_hieu = th.id
        JOIN KhoaAo ka ON spct.id_khoa_ao = ka.id
        JOIN CoAo ca ON spct.id_co_ao = ca.id
        WHERE spct.don_gia BETWEEN ? AND ?
    """;

    try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, minPrice);
        ps.setInt(2, maxPrice);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new ChiTietSanPhamRespon(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getInt(11)
            ));
        }
    } catch (Exception e) {
        e.printStackTrace(System.out);
    }
    return list;
}
       
           
public String generateMaSPCT(ChiTietSanPham spct) {
   if (spct == null) {
    System.out.println("Lỗi: Không tìm thấy sản phẩm chi tiết!");
    return "SPCT_ERR"; // Trả về mã lỗi hoặc xử lý phù hợp
   }

   int idSanPham = spct.getIdSanPham(); // Tránh lỗi NullPointerException

   // Logic tạo mã SPCT
   String newCode = "SPCT" + String.format("%03d", idSanPham);
   System.out.println("Cập nhật mã SPCT thành: " + newCode);
   return newCode;
}

public ChiTietSanPham getById(int id) {
    String sql = "SELECT * FROM SanPhamChiTiet WHERE id = ?";
    ChiTietSanPham ctsp = null;

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            ctsp = ChiTietSanPham.builder()
                    .id(rs.getInt("id"))
                    .soLuong(rs.getInt("so_luong"))
                    .donGia(rs.getInt("don_gia"))
                    // thêm các trường khác nếu cần
                    .build();
        } else {
            System.out.println("❌ Không tìm thấy sản phẩm với ID: " + id);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return ctsp;
}


      
}

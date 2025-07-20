/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.hoadon;
import java.sql.*;
import java.util.ArrayList;
import model.hoadon.HoaDonChiTiet;
import respon.HoaDonChiTietRespon;
import utils.DBConnect;
/**
 *
 * @author nguye
 */
public class HoaDonChiTietRepo {
    private Connection conn = null;
    
    public HoaDonChiTietRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<HoaDonChiTietRespon> getAll(Integer idHoaDon) {
        ArrayList<HoaDonChiTietRespon> danhSach = new ArrayList<>();
        String sql = """
                    SELECT 
                        hdct.id,
                        spct.id,
                        sp.ten,
                        ms.ten,
                        cl.ten,
                        kt.ten,
                        ca.ten,
                        hdct.so_luong,
                        hdct.don_gia,
                        hdct.so_luong * hdct.don_gia AS thanh_tien
                    FROM HoaDonChiTiet hdct
                    INNER JOIN SanPhamChiTiet spct ON hdct.id_san_pham_chi_tiet = spct.id
                    INNER JOIN SanPham sp ON spct.id_san_pham = sp.id 
                    INNER JOIN MauSac ms ON spct.id_mau_sac = ms.id
                    INNER JOIN ChatLieu cl ON spct.id_chat_lieu = cl.id
                    INNER JOIN KichThuoc kt ON spct.id_kich_thuoc = kt.id
                    INNER JOIN CoAo ca ON spct.id_co_ao = ca.id
                    where hdct.id_hoa_don=?
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idHoaDon);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                danhSach.add(new HoaDonChiTietRespon(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getInt(10)
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }
     public void themHoaDonChiTiet(HoaDonChiTiet hoaDonChiTiet) {
        String sql = """
                       INSERT INTO [dbo].[HoaDonChiTiet]
                                  ([id_san_pham_chi_tiet]
                                  ,[id_hoa_don]
                                  ,[so_luong]
                       		  ,[don_gia])
                            VALUES
                                  (?,?,?,?)
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, hoaDonChiTiet.getIdChiTietSanPham());
            ps.setInt(2, hoaDonChiTiet.getIdHoaDon());
            ps.setInt(3, hoaDonChiTiet.getSoLuong());
            ps.setInt(4, hoaDonChiTiet.getDonGia());
            ps.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
     
    public ArrayList<HoaDonChiTietRespon> getAllByHoaDonId(int hoaDonId) {
    ArrayList<HoaDonChiTietRespon> list = new ArrayList<>();
    String sql = """
                  SELECT 
                    hdct.id,
                    spct.id,
                    sp.ten,
                    ms.ten,
                    cl.ten,
                    kt.ten,
                    ca.ten,
                    hdct.so_luong,
                    hdct.don_gia,
                    hdct.so_luong * hdct.don_gia AS thanh_tien
                FROM HoaDonChiTiet hdct
                INNER JOIN SanPhamChiTiet spct ON hdct.id_san_pham_chi_tiet = spct.id
                INNER JOIN SanPham sp ON spct.id_san_pham = sp.id 
                INNER JOIN MauSac ms ON spct.id_mau_sac = ms.id
                INNER JOIN ChatLieu cl ON spct.id_chat_lieu = cl.id
                INNER JOIN KichThuoc kt ON spct.id_kich_thuoc = kt.id
                INNER JOIN CoAo ca ON spct.id_co_ao = ca.id
                where hdct.id_hoa_don=?
                 """;

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, hoaDonId);
        ResultSet rs = ps.executeQuery();

         while(rs.next()) {
                list.add(new HoaDonChiTietRespon(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getInt(10)
                ));
            }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
public void capNhatSoLuong(HoaDonChiTiet hoaDonChiTiet) {
    String sql = "UPDATE HoaDonChiTiet SET so_luong = ? WHERE id_hoa_don = ? AND id_san_pham_chi_tiet = ?";
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, hoaDonChiTiet.getSoLuong());
        ps.setInt(2, hoaDonChiTiet.getIdHoaDon());
        ps.setInt(3, hoaDonChiTiet.getIdChiTietSanPham());
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public int xoaVaTraVeSoLuong1(int idHoaDon, int idChiTietSanPham) {
    int soLuongDaMua = 0;
    String selectSQL = "SELECT so_luong FROM HoaDonChiTiet WHERE id_hoa_don = ? AND id_san_pham_chi_tiet = ?";
    String deleteSQL = "DELETE FROM HoaDonChiTiet WHERE id_hoa_don = ? AND id_san_pham_chi_tiet = ?";

    try (Connection conn = DBConnect.getConnection()) {
        // Lấy số lượng đã mua
        try (PreparedStatement ps = conn.prepareStatement(selectSQL)) {
            ps.setInt(1, idHoaDon);
            ps.setInt(2, idChiTietSanPham);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                soLuongDaMua = rs.getInt("so_luong");
            }
        }

        // Xóa dòng trong giỏ
        try (PreparedStatement ps = conn.prepareStatement(deleteSQL)) {
            ps.setInt(1, idHoaDon);
            ps.setInt(2, idChiTietSanPham);
            ps.executeUpdate();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return soLuongDaMua; // Trả về để cộng ngược lại vào kho
}

public int xoaVaTraVeSoLuong(int idHoaDon, int idChiTietSanPham) {
    int soLuongDaMua = 0;
    String selectSQL = "SELECT so_luong FROM HoaDonChiTiet WHERE id_hoa_don = ? AND id_san_pham_chi_tiet = ?";
    String updateSQL = "UPDATE HoaDonChiTiet SET trang_thai = 0 WHERE id_hoa_don = ? AND id_san_pham_chi_tiet = ?";

    try (Connection conn = DBConnect.getConnection()) {
        // Lấy số lượng đã mua
        try (PreparedStatement ps = conn.prepareStatement(selectSQL)) {
            ps.setInt(1, idHoaDon);
            ps.setInt(2, idChiTietSanPham);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                soLuongDaMua = rs.getInt("so_luong");
            }
        }

        // Cập nhật trạng thái đã xóa
        try (PreparedStatement ps = conn.prepareStatement(updateSQL)) {
            ps.setInt(1, idHoaDon);
            ps.setInt(2, idChiTietSanPham);
            ps.executeUpdate();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return soLuongDaMua;
}





 public void xoaTheoIdHoaDon(int idHoaDonCT) {
        String sql = "DELETE FROM HoaDonChiTiet WHERE id_hoa_don = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idHoaDonCT);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
 }
 
}

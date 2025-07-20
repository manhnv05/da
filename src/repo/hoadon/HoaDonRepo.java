/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.hoadon;
import java.sql.*;
import java.util.ArrayList;
import model.hoadon.HoaDon;
import utils.DBConnect;

/**
 *
 * @author nguye
 */
public class HoaDonRepo {
     private Connection conn = null;
    
    public HoaDonRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<HoaDon> getAll() {
        ArrayList<HoaDon> danhSach = new ArrayList<>();
        String sql = """
                        SELECT [id]
                              ,[ten_nguoi_nhan]
                              ,[so_dien_thoai]
                              ,[ngay_tao]
                              ,[trang_thai]
                          FROM [dbo].[HoaDon]
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                danhSach.add(new HoaDon(
                        rs.getInt("id"),
                        rs.getString("ten_nguoi_nhan"),
                        rs.getString("so_dien_thoai"),
                        rs.getString("ngay_tao"),
                        rs.getInt("trang_thai")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }
      public ArrayList<HoaDon> getAllTrangThai( int trangThai) {
        String sql = """
                     SELECT [id]
                            ,[ten_nguoi_nhan]
                            ,[so_dien_thoai]
                            ,[ngay_tao]
                            ,[trang_thai]
                     FROM [dbo].[HoaDon] where [trang_thai] = ?
                     """;
        ArrayList<HoaDon> listHD = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon response = HoaDon.builder()
                        .id(rs.getInt(1))
                        .tenNguoiNhan(rs.getString(2))
                        .soDienThoai(rs.getString(3))
                        .ngayTao(rs.getString(4))
                        .trangThai(rs.getInt(5))           
                        .build();
                listHD.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return listHD;
    }
    
    public ArrayList<HoaDon> getAllChuaThanhToan() {
        ArrayList<HoaDon> danhSach = new ArrayList<>();
        String sql = """
                        SELECT [id]
                            ,[ten_nguoi_nhan]
                            ,[so_dien_thoai]
                            ,[ngay_tao]
                            ,[trang_thai]
                        FROM [dbo].[HoaDon]
                        WHERE trang_thai = 1;
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                danhSach.add(new HoaDon(
                        rs.getInt("id"),
                        rs.getString("ten_nguoi_nhan"),
                        rs.getString("so_dien_thoai"),
                        rs.getString("ngay_tao"),
                        rs.getInt("trang_thai")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }
//    
    public void themHoaDon(HoaDon hoaDon) {
        String sql = """
                        INSERT INTO [dbo].[HoaDon]
                                   (
                        	   [ten_nguoi_nhan]
                                   ,[so_dien_thoai]
                                   ,[ngay_tao]
                                   ,[trang_thai])
                             VALUES
                                   (?,?,?,1)
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hoaDon.getTenNguoiNhan());
            ps.setString(2, hoaDon.getSoDienThoai());
            ps.setString(3, hoaDon.getNgayTao());
            ps.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void thanhToanHoaDon(HoaDon hoaDon) {
        String sql = """
                        UPDATE HoaDon
                        SET trang_thai = 2
                        WHERE id = ?;
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, hoaDon.getId());
            ps.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

public HoaDon getById(int id) {
    HoaDon hoaDon = null;
    String sql = "SELECT * FROM HoaDon WHERE id = ?";

    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);  // Đặt tham số vào câu lệnh SQL
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            hoaDon = new HoaDon(
                rs.getInt("id"),
                rs.getString("ten_nguoi_nhan"),
                rs.getString("so_dien_thoai"),
                rs.getString("ngay_tao"),
                rs.getInt("trang_thai")
            );
        }

        rs.close();
        ps.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return hoaDon;
}
public void xoaHoaDon(int idHoaDon) {
        String sql = "DELETE FROM HoaDon WHERE id = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idHoaDon);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Lỗi khi xóa hóa đơn: " + e.getMessage());
        }
    }
public void huyToanHoaDon(HoaDon hoaDon) {
        String sql = """
                        UPDATE HoaDon
                        SET trang_thai = 3
                        WHERE id = ?;
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, hoaDon.getId());
            ps.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HoaDon> locHoaDon(String ma) {
        ArrayList<HoaDon> list = new ArrayList<>();
        String sql = """
                    SELECT [id]
                           ,[ten_nguoi_nhan]
                           ,[so_dien_thoai]
                     	   ,[ngay_tao]
                           ,[trang_thai]
                    FROM [dbo].[HoaDon] where [trang_thai] = ?
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, ma);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                 HoaDon response = HoaDon.builder()
                        .id(rs.getInt(1))
                        .tenNguoiNhan(rs.getString(2))
                        .soDienThoai(rs.getString(3))
                        .ngayTao(rs.getString(4))
                        .trangThai(rs.getInt(5))           
                        .build();
                 list.add(response);              
            }
             return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }
    public ArrayList<HoaDon> search(String id) {
    String sql = """
        SELECT [id],
               [ten_nguoi_nhan],
               [so_dien_thoai],
               [ngay_tao],
               [trang_thai]
        FROM [dbo].[HoaDon]
        WHERE ten_nguoi_nhan LIKE ? OR so_dien_thoai LIKE ?
    """;
    ArrayList<HoaDon> lists = new ArrayList<>();
    try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setObject(1, "%" + id + "%");
        ps.setObject(2, "%" + id + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lists.add(new HoaDon(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5)
            ));
        }
    } catch (Exception e) {
        e.printStackTrace(System.out);
    }
    return lists;
}
    public ArrayList<HoaDon> locTheoNgay(java.sql.Date ngayLoc) {
    ArrayList<HoaDon> list = new ArrayList<>();
    String sql = "SELECT * FROM HoaDon WHERE CONVERT(date, ngay_tao) = ?";

    try (Connection con = DBConnect.getConnection(); 
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setDate(1, ngayLoc);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            HoaDon hd = new HoaDon();
            hd.setId(rs.getInt("id"));
            hd.setTenNguoiNhan(rs.getString("ten_nguoi_nhan"));
            hd.setSoDienThoai(rs.getString("so_dien_thoai"));
            hd.setNgayTao(rs.getString("ngay_tao")); // hoặc getDate tùy kiểu bạn dùng
            hd.setTrangThai(rs.getInt("trang_thai")); // hoặc getDate tùy kiểu bạn dùng
            // Thêm các field khác tùy vào cấu trúc của bạn
            list.add(hd);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}
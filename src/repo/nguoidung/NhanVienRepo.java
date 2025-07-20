/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.nguoidung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.nguoidung.KhachHang;
import model.nguoidung.NhanVien;
import model.sanpham.SanPham;
import utils.DBConnect;

/**
 *
 * @author nguye
 */
public class NhanVienRepo {
    private Connection conn = null;
    
    public NhanVienRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<NhanVien> getAll() {
        ArrayList<NhanVien> list = new ArrayList<>();
        String sql = """
                       SELECT [id]
                             ,[ma_nhan_vien]
                             ,[ten_nhan_vien]
                             ,[mat_khau]
                             ,[so_dien_thoai]
                             ,[email]
                             ,[ngay_sinh]
                             ,[chuc_vu]
                             ,[dia_chi]
                             ,[gioi_tinh]
                             ,[ten_dang_nhap]
                             ,[trang_thai]
                         FROM [dbo].[NhanVien]
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new NhanVien(
                        rs.getInt("id"),
                        rs.getString("ma_nhan_vien"),
                        rs.getString("ten_nhan_vien"),
                        rs.getString("mat_khau"),
                        rs.getString("so_dien_thoai"),
                        rs.getString("email"),
                        rs.getString("ngay_sinh"),
                        rs.getBoolean("chuc_vu"),
                        rs.getString("dia_chi"),
                        rs.getBoolean("gioi_tinh"),
                        rs.getString("ten_dang_nhap"),
                        rs.getInt("trang_thai")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
     public Boolean login(String ma, String matKhau){
        Boolean result = null;
        String sql = """
                     SELECT * FROM NhanVien WHERE ten_dang_nhap = ? AND mat_khau=?
                     """;
        
        try {
            PreparedStatement ps  = conn.prepareStatement(sql);
            ps.setString(1, ma);
            ps.setString(2, matKhau);
            ResultSet rs = ps.executeQuery();
            result = rs.next() ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public void themNhanVien(NhanVien nhanVien) {
        String sql = """
                    INSERT INTO [dbo].[NhanVien]
                                    ([ma_nhan_vien]
                                    ,[ten_nhan_vien]
                                    ,[mat_khau]
                                    ,[so_dien_thoai]
                                    ,[email]
                                    ,[ngay_sinh]
                                    ,[chuc_vu]
                                    ,[dia_chi]
                                    ,[gioi_tinh]
                                    ,[ten_dang_nhap]
                                    ,[trang_thai])
                              VALUES
                                    (?,?,?,?,?,?,?,?,?,?,1)
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nhanVien.getMaNV());
            ps.setString(2, nhanVien.getTenNV());
            ps.setString(3, nhanVien.getMatKhau());
            ps.setString(4, nhanVien.getSoDT());
            ps.setString(5, nhanVien.getEmail());
            ps.setString(6, nhanVien.getNgaySinh());
            ps.setBoolean(7, nhanVien.isChucVu());
            ps.setString(8, nhanVien.getDiaChi());
            ps.setBoolean(9, nhanVien.isGioiTinh());
            ps.setString(10, nhanVien.getTenDN());
            ps.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean suaNhanVien(NhanVien nhanVien, String ma) {
        int check = 0;
        String sql = """
                      UPDATE [dbo].[NhanVien]
                           SET 
                              [ten_nhan_vien] = ?
                              ,[so_dien_thoai] = ?
                              ,[email] = ?
                              ,[ngay_sinh] = ?
                              ,[chuc_vu] = ?
                              ,[dia_chi] = ?
                              ,[gioi_tinh] = ?
                              ,[trang_thai] = ?
                         WHERE ma_nhan_vien=?
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nhanVien.getTenNV());
            ps.setString(2, nhanVien.getSoDT());
            ps.setString(3, nhanVien.getEmail());
            ps.setString(4, nhanVien.getNgaySinh());
            ps.setBoolean(5, nhanVien.isChucVu());
            ps.setString(6, nhanVien.getDiaChi());
            ps.setBoolean(7, nhanVien.isGioiTinh());
            ps.setInt(8, nhanVien.getTrangThai());
            ps.setString(9, ma);
            check = ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return check >0;
    }
    public NhanVien checkTrung(String maCheck) {
         NhanVien nv = null;
        String sql = """
                    SELECT [id]
                        ,[ma_nhan_vien]
                        ,[ten_nhan_vien]
                        ,[mat_khau]
                        ,[so_dien_thoai]
                        ,[email]
                        ,[ngay_sinh]
                        ,[chuc_vu]
                        ,[dia_chi]
                        ,[gioi_tinh]
                        ,[ten_dang_nhap]
                        ,[trang_thai]
                    FROM [dbo].[NhanVien] where ma_nhan_vien=?
                     """;
       
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
           
            ps.setObject(1, maCheck);
            ResultSet rs = ps.executeQuery();
           
            while (rs.next()) {                
                int id = rs.getInt(1);
                String maNV = rs.getString(2);
                String tenNV = rs.getString(3);
                String matKhau = rs.getString(4);     
                String soDT = rs.getString(5);
                String email = rs.getString(6);
                String ngaySinh = rs.getString(7);
                boolean chucVu = rs.getBoolean(8);
                String diaChi = rs.getString(9);
                boolean gioiTinh = rs.getBoolean(10);
                String tenDN = rs.getString(11);
                int trangThai = rs.getInt(12);
                nv = new NhanVien(id, maNV, tenNV, matKhau, soDT, email, ngaySinh, chucVu, diaChi, gioiTinh, tenDN, trangThai);
            }
            return nv;
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return null;
    }
    
    public boolean delete(String ma) {
        int check = 0;
        String sql = """
                   UPDATE [dbo].[NhanVien]
                      SET 
                        [trang_thai] = 0
                    WHERE ma_nhan_vien=?""";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
//    public boolean update(KhachHang newKH, String ma) {
//        int check = 0;
//        String sql = """
//                   UPDATE [dbo].[KhachHang]
//                      SET 
//                          [ten_khach_hang] = ?
//                         ,[so_dien_thoai] = ?
//                         ,[dia_chi] = ?
//                         ,[trang_thai] = ?
//                    WHERE ma_khach_hang=?
//                     """;
//        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setObject(1, newKH.getTenKhachHang());
//            ps.setObject(2, newKH.getSoDienThoai());
//            ps.setObject(3, newKH.getDiaChi());
//            ps.setObject(4, newKH.getTrangThai());
//            ps.setObject(5, ma);
//            check = ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
//        return check > 0;
//    }
    public List<KhachHang> locKH(String ma) {
        List<KhachHang> list = new ArrayList<>();
        String sql = """
                       SELECT [id]
                            ,[ma_khach_hang]
                            ,[ten_khach_hang]
                            ,[so_dien_thoai]
                            ,[dia_chi]
                            ,[trang_thai]
                        FROM [dbo].[KhachHang] where [trang_thai] = ?
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, ma);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                 KhachHang response = KhachHang.builder()
                        .maKhachHang(rs.getString(1))
                        .tenKhachHang(rs.getString(2))
                        .soDienThoai(rs.getString(3))
                        .diaChi(rs.getString(4))
                        .trangThai(rs.getInt(5))            
                        .build();
                 list.add(response);              
            }
             return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }
   public ArrayList<NhanVien> searchNV(String keyword) {
    String sql = """
        SELECT [id]
            ,[ma_nhan_vien]
            ,[ten_nhan_vien]
            ,[mat_khau]
            ,[so_dien_thoai]
            ,[email]
            ,[ngay_sinh]
            ,[chuc_vu]
            ,[dia_chi]
            ,[gioi_tinh]
            ,[ten_dang_nhap]
            ,[trang_thai]
        FROM [dbo].[NhanVien]
        WHERE ma_nhan_vien LIKE ? OR so_dien_thoai LIKE ?
    """;

    ArrayList<NhanVien> lists = new ArrayList<>();
    try (Connection con = DBConnect.getConnection(); 
         PreparedStatement ps = con.prepareStatement(sql)) {

        String searchKeyword = "%" + keyword + "%";
        ps.setString(1, searchKeyword);
        ps.setString(2, searchKeyword);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lists.add(new NhanVien(
                        rs.getInt("id"),
                        rs.getString("ma_nhan_vien"),
                        rs.getString("ten_nhan_vien"),
                        rs.getString("mat_khau"),
                        rs.getString("so_dien_thoai"),
                        rs.getString("email"),
                        rs.getString("ngay_sinh"),
                        rs.getBoolean("chuc_vu"),
                        rs.getString("dia_chi"),
                        rs.getBoolean("gioi_tinh"),
                        rs.getString("ten_dang_nhap"),
                        rs.getInt("trang_thai")
                ));
        }
    } catch (Exception e) {
        e.printStackTrace(System.out);
    }
    return lists;
}

}

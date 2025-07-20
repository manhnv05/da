/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.nguoidung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.giamgia.GiamGia;
import model.nguoidung.KhachHang;
import utils.DBConnect;

/**
 *
 * @author nguye
 */
public class KhachHangRepo {
    private Connection conn = null;
    public KhachHangRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<KhachHang> getAll() {
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = """
                       SELECT [id]
                             ,[ma_khach_hang]
                             ,[ten_khach_hang]
                             ,[so_dien_thoai]
                             ,[dia_chi]
                             ,[trang_thai]
                         FROM [dbo].[KhachHang]
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new KhachHang(
                        rs.getInt("id"),
                        rs.getString("ma_khach_hang"),
                        rs.getString("ten_khach_hang"),
                        rs.getString("so_dien_thoai"),
                        rs.getString("dia_chi"),
                        rs.getInt("trang_thai")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
     public void themKhachHang(KhachHang khachHang) {
        String sql = """
            INSERT INTO [dbo].[KhachHang]
                               ([ma_khach_hang]
                               ,[ten_khach_hang]
                               ,[so_dien_thoai]
                               ,[dia_chi]
                               ,[trang_thai])
                         VALUES
                               (?,?,?,?,1)
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, khachHang.getMaKhachHang());
            ps.setObject(2, khachHang.getTenKhachHang());
            ps.setObject(3, khachHang.getSoDienThoai());
            ps.setObject(4, khachHang.getDiaChi());
            ps.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
     
       public KhachHang checkTrung(String maCheck) {
         KhachHang kh = null;
        String sql = """
                   SELECT [id]
                    ,[ma_khach_hang]
                    ,[ten_khach_hang]
                    ,[so_dien_thoai]
                    ,[dia_chi]
                    ,[trang_thai]
                FROM [dbo].[KhachHang] where ma_khach_hang=?
                     """;
       
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
           
            ps.setObject(1, maCheck);
            ResultSet rs = ps.executeQuery();
           
            while (rs.next()) {                
                int id = rs.getInt(1);
                String maKH = rs.getString(2);
                String tenKH = rs.getString(3);
                String soDT = rs.getString(4);     
                String diaChi = rs.getString(5);
                int trangThai = rs.getInt(6);
                kh = new KhachHang(id, maKH, tenKH, soDT, diaChi, trangThai);
            }
            return kh;
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return null;
    }
    
    public boolean delete(String ma) {
        int check = 0;
        String sql = """
                   UPDATE [dbo].[KhachHang]
                      SET 
                        [trang_thai] = 0
                    WHERE ma_khach_hang=?""";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
    public boolean update(KhachHang newKH, String ma) {
        int check = 0;
        String sql = """
                   UPDATE [dbo].[KhachHang]
                      SET 
                          [ten_khach_hang] = ?
                         ,[so_dien_thoai] = ?
                         ,[dia_chi] = ?
                         ,[trang_thai] = ?
                    WHERE ma_khach_hang=?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, newKH.getTenKhachHang());
            ps.setObject(2, newKH.getSoDienThoai());
            ps.setObject(3, newKH.getDiaChi());
            ps.setObject(4, newKH.getTrangThai());
            ps.setObject(5, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
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
   public ArrayList<KhachHang> searchKH(String keyword) {
    String sql = """
        SELECT [id],
               [ma_khach_hang],
               [ten_khach_hang],
               [so_dien_thoai],
               [dia_chi],
               [trang_thai]
        FROM [dbo].[KhachHang]
        WHERE ma_khach_hang LIKE ? OR so_dien_thoai LIKE ?
    """;

    ArrayList<KhachHang> lists = new ArrayList<>();
    try (Connection con = DBConnect.getConnection(); 
         PreparedStatement ps = con.prepareStatement(sql)) {

        String searchKeyword = "%" + keyword + "%";
        ps.setString(1, searchKeyword);
        ps.setString(2, searchKeyword);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lists.add(new KhachHang(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getInt(6)
            ));
        }
    } catch (Exception e) {
        e.printStackTrace(System.out);
    }
    return lists;
}

}

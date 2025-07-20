/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.giamgia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.giamgia.GiamGia;
import utils.DBConnect;

/**a
 *
 * @author nguye
 */
public class GiamGiaRepo {
     private Connection conn = null;
    
    public GiamGiaRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<GiamGia> getAll() {
       ArrayList<GiamGia> danhSach = new ArrayList<>();
        String sql = """
            SELECT id, ma_giam_gia, ten_giam_gia, gia_tri_giam, gia_tri_toi_thieu, gia_tri_toi_da, ngay_bat_dau, ngay_ket_thuc, trang_thai
            FROM dbo.phieu_giam_gia
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                danhSach.add(new GiamGia(
                    rs.getInt("id"),
                    rs.getString("ma_giam_gia"),
                    rs.getString("ten_giam_gia"),
                    rs.getFloat("gia_tri_giam"),
                    rs.getInt("gia_tri_toi_thieu"),
                    rs.getInt("gia_tri_toi_da"),
                    rs.getDate("ngay_bat_dau"),
                    rs.getDate("ngay_ket_thuc"),
                    rs.getInt("trang_thai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }
      public ArrayList<GiamGia> getAllTrangThai( int trangThai) {
        String sql = """
                    SELECT
                        [id]
                     	,[ma_giam_gia]
                        ,[ten_giam_gia]
                        ,[gia_tri_giam]
                        ,[gia_tri_toi_thieu]
                        ,[gia_tri_toi_da]
                        ,[ngay_bat_dau]
                        ,[ngay_ket_thuc]
                        ,[trang_thai]
                    FROM [dbo].[phieu_giam_gia] where [trang_thai]=?""";
        ArrayList<GiamGia> listGG = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GiamGia response = GiamGia.builder()
                        .id(rs.getInt(1))
                        .maGiamGia(rs.getString(2))
                        .tenGiamGia(rs.getString(3))
                        .phanTramGiam(rs.getFloat(4))
                        .giaTriToiDa(rs.getInt(5))
                        .giaTriToiThieu(rs.getInt(6))
                        .ngayBatDau(rs.getDate(7))
                        .ngayKetThuc(rs.getDate(8))
                        .trangThai(rs.getInt(9))            
                        .build();
                listGG.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return listGG;
    }
     public void themGiamGia(GiamGia giamGia) {
        String sql = """
                    INSERT INTO [dbo].[phieu_giam_gia]
                               ([ma_giam_gia]
                               ,[ten_giam_gia]
                               ,[gia_tri_giam]
                               ,[gia_tri_toi_thieu]
                               ,[gia_tri_toi_da]
                               ,[ngay_bat_dau]
                               ,[ngay_ket_thuc]
                               ,[trang_thai])
                    VALUES
                       (?,?,?,?,?,?,?,1)
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, giamGia.getMaGiamGia());
            ps.setObject(2, giamGia.getTenGiamGia());
            ps.setObject(3, giamGia.getPhanTramGiam());
            ps.setObject(4, giamGia.getGiaTriToiThieu());
            ps.setObject(5, giamGia.getGiaTriToiDa());
            ps.setObject(6, new Date());
            ps.setObject(7, giamGia.getNgayKetThuc());
            ps.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
       public GiamGia checkTrung(String maCheck) {
         GiamGia gg = null;
        String sql = """
                     SELECT [id]
                           ,[ma_giam_gia]
                           ,[ten_giam_gia]
                           ,[gia_tri_giam]
                           ,[gia_tri_toi_thieu]
                           ,[gia_tri_toi_da]
                           ,[ngay_bat_dau]
                           ,[ngay_ket_thuc]
                           ,[trang_thai]
                       FROM [dbo].[phieu_giam_gia] where ma_giam_gia=?
                     """;
       
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
           
            ps.setObject(1, maCheck);
            ResultSet rs = ps.executeQuery();
           
            while (rs.next()) {                
                int id = rs.getInt(1);
                String maVC = rs.getString(2);
                String tenVC = rs.getString(3);
                float giamPT = rs.getFloat(4);
                int giaTriToiThieu = rs.getInt(5);
                int giaTriToiDa = rs.getInt(6);
                Date ngayBD = rs.getDate(7);
                Date ngayKT = rs.getDate(8);     
                int trangThai = rs.getInt(9);
                gg = new GiamGia(id, maVC, tenVC, giamPT, giaTriToiThieu, giaTriToiDa, ngayBD, ngayKT, trangThai);
            }
            return gg;
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return null;
    }
    
    public boolean delete(String ma) {
        int check = 0;
    String sql = "DELETE FROM [dbo].[phieu_giam_gia] WHERE ma_giam_gia = ?";
    try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, ma);
        check = ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace(System.out);
    }
    return check > 0;
    }
    
    public boolean update(GiamGia newGiamGia, String ma) {
        int check = 0;
        String sql = """
                     UPDATE [dbo].[phieu_giam_gia]
                        SET 
                            [ten_giam_gia] = ?
                           ,[gia_tri_giam] = ?
                           ,[gia_tri_toi_thieu] = ?
                           ,[gia_tri_toi_da] = ?
                           ,[ngay_bat_dau] = ?
                           ,[ngay_ket_thuc] = ?
                      WHERE ma_giam_gia=?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, newGiamGia.getTenGiamGia());
            ps.setObject(2, newGiamGia.getPhanTramGiam());
            ps.setObject(3, newGiamGia.getGiaTriToiThieu());
            ps.setObject(4, newGiamGia.getGiaTriToiDa());
            ps.setObject(5, newGiamGia.getNgayBatDau());
            ps.setObject(6, newGiamGia.getNgayKetThuc());
            ps.setObject(7, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
    public List<GiamGia> locVoucher(String ma) {
        List<GiamGia> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                            ,[ma_giam_gia]
                            ,[ten_giam_gia]
                            ,[gia_tri_giam]
                            ,[gia_tri_toi_thieu]
                            ,[gia_tri_toi_da]
                            ,[ngay_bat_dau]
                            ,[ngay_ket_thuc]
                            ,[trang_thai]
                        FROM [dbo].[phieu_giam_gia] where [trang_thai] = ?
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, ma);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                 GiamGia response = GiamGia.builder()
                        .maGiamGia(rs.getString(1))
                        .tenGiamGia(rs.getString(2))
                        .phanTramGiam(rs.getFloat(3))
                        .giaTriToiDa(rs.getInt(4))
                        .giaTriToiThieu(rs.getInt(5))
                        .ngayBatDau(rs.getDate(9))
                        .ngayKetThuc(rs.getDate(10))
                        .trangThai(rs.getInt(11))            
                        .build();
                 list.add(response);              
            }
             return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }
    public ArrayList<GiamGia> search(String maVC) {
        String sql = """
         SELECT [id]
                ,[ma_giam_gia]
                ,[ten_giam_gia]
                ,[gia_tri_giam]
                ,[gia_tri_toi_thieu]
                ,[gia_tri_toi_da]
                ,[ngay_bat_dau]
                ,[ngay_ket_thuc]
                ,[trang_thai]
            FROM [dbo].[phieu_giam_gia]  where ma_giam_gia like ?""";
        ArrayList<GiamGia> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, "%" + maVC + "%"); // like => %%: contans : Kiem tra chua
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lists.add(new GiamGia(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getInt(9)
                     
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
}

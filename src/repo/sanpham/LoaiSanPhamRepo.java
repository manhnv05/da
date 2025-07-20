/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.sanpham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.sanpham.LoaiSanPham;
import utils.DBConnect;

/**
 *
 * @author nguye
 */
public class LoaiSanPhamRepo {
    private Connection conn = null;
    
    public LoaiSanPhamRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<LoaiSanPham> getAll() {
        ArrayList<LoaiSanPham> danhSach = new ArrayList<>();
        String sql = """
                       SELECT [id]
                              ,[ma]
                              ,[ten]
                       FROM [dbo].[LoaiSanPham]
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                danhSach.add(new LoaiSanPham(
                        rs.getInt("id"),
                        rs.getString("ma"),
                        rs.getString("ten")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }
    public void creatLoaiSanPham(LoaiSanPham newLoaiSanPham) {
     String sql = """
                     INSERT INTO [dbo].[LoaiSanPham]
                                ([ma]
                                ,[ten])
                          VALUES
                                (?,?)
                  """;
     try (PreparedStatement ps = conn.prepareStatement(sql)) {
         ps.setString(1, newLoaiSanPham.getMa());
         ps.setString(2, newLoaiSanPham.getTen());
         
         // Execute the update (insert operation)
         ps.executeUpdate();
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

public boolean deleteLoaiSanPhamById(int id) {
    String deleteSanPhamSQL = "DELETE FROM SanPham WHERE id_loai_san_pham = ?";
    String deleteLoaiSanPhamSQL = "DELETE FROM LoaiSanPham WHERE id = ?";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement psSanPham = conn.prepareStatement(deleteSanPhamSQL);
         PreparedStatement psLoaiSanPham = conn.prepareStatement(deleteLoaiSanPhamSQL)) {

        // Xóa tất cả sản phẩm có id_loai_san_pham trước
        psSanPham.setInt(1, id);
        psSanPham.executeUpdate();

        // Sau đó mới xóa LoaiSanPham
        psLoaiSanPham.setInt(1, id);
        int rowsDeleted = psLoaiSanPham.executeUpdate();

        return rowsDeleted > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}




}

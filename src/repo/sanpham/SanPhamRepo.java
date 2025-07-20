/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.sanpham;

import java.util.ArrayList;
import model.sanpham.SanPham;
import utils.DBConnect;
import java.sql.*;


/**
 *
 * @author nguye
 */
public class SanPhamRepo {
    private Connection conn = null;
    
    public SanPhamRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<SanPham> getAll() {
        ArrayList<SanPham> danhSach = new ArrayList<>();
        String sql = """
                      SELECT sp.id, sp.ma, sp.ten, sp.id_loai_san_pham, lsp.ten AS ten_loai, sp.trang_thai
                      FROM SanPham sp
                      LEFT JOIN LoaiSanPham lsp ON sp.id_loai_san_pham = lsp.id;
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                danhSach.add(new SanPham(
                        rs.getInt("id"),
                        rs.getString("ma"),
                        rs.getString("ten"),
                        rs.getInt("id_loai_san_pham"),
                        rs.getInt("trang_thai")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }
    
public void themSanPham(SanPham sanPham) {
    // Chèn sản phẩm vào bảng SanPham
    String sql = """
        INSERT INTO [dbo].[SanPham]
                   ([ma]
                   ,[ten]
                   ,[id_loai_san_pham]
                   ,[trang_thai])
             VALUES
                   (?,?,?,1)
                 """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, sanPham.getMa());
        ps.setString(2, sanPham.getTen());
        ps.setInt(3, sanPham.getIdLoaiSanPham());

        int rows = ps.executeUpdate();
        System.out.println("Số dòng thêm vào: " + rows);

    } catch (SQLException e) {
        e.printStackTrace();
       
    }
    return ;
}



// Hàm thêm loại sản phẩm mới

    public void suaSanPham(SanPham sanPham) {
        String sql = """
                       UPDATE [dbo].[SanPham]
                          SET [ma] = ?
                             ,[ten] = ?
                             ,[id_loai_san_pham] = ?
                             ,[trang_thai] =?
                        WHERE id=?
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sanPham.getMa());
            ps.setString(2, sanPham.getTen());
            ps.setInt(3, sanPham.getIdLoaiSanPham());
            ps.setInt(4, sanPham.getTrangThai());
            ps.setInt(5, sanPham.getId());
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
//     public void xoaSanPham(int id) {
//         int check = 0;
//        String sql = """
//                        DELETE FROM [dbo].[SanPham]
//                              WHERE id=?
//                     """;
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, id);
//            check = ps.executeUpdate();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
  public void xoaSanPham(int id) {
    String sqlUpdateSanPham = "UPDATE SanPham SET trang_thai = 0 WHERE id = ?";

    try {
        PreparedStatement ps2 = conn.prepareStatement(sqlUpdateSanPham);
        ps2.setInt(1, id);
        int rows = ps2.executeUpdate();
        ps2.close();

        if (rows > 0) {
            System.out.println("Xóa mềm sản phẩm thành công!");
        } else {
            System.out.println("Không tìm thấy sản phẩm để xóa.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    
//      public SanPham checkTrung(String maCheck) {
//        SanPham sp = null;
//        String sql = """
//                   SELECT 
//                         [ma]
//                         ,[ten]
//                         ,[trang_thai]
//                     FROM [dbo].[SanPham]
//                     WHERE [ma] = ? """;
//       
//        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//           
//            ps.setObject(1, maCheck);
//             ResultSet rs = ps.executeQuery();
//           
//            while (rs.next()) {             
//                String maSP,tenSP;
//                Integer trangThai;
//                   
//                maSP = rs.getString(1);
//                tenSP = rs.getString(2);
//                trangThai = rs.getInt(3);
//                
//                sp = new SanPham(null, maSP, tenSP, trangThai);
//            }
//            return sp;
//        } catch (Exception e) {
//            e.printStackTrace(System.out); // nem loi khi xay ra 
//        }
//        return null;
//    }
    
   public boolean isSanPhamTonTai(String ma, String ten, int id) {
    ArrayList<SanPham> danhSachSanPham = getAll(); // Lấy danh sách sản phẩm hiện có

    for (SanPham sp : danhSachSanPham) {
        if ((sp.getMa().equalsIgnoreCase(ma) || sp.getTen().equalsIgnoreCase(ten)) && sp.getId() != id) {
            return true; // Mã hoặc tên đã tồn tại trong sản phẩm khác
        }
    }
    return false;
}


}

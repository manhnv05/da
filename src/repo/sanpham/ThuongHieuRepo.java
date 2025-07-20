/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.sanpham;
import java.util.ArrayList;
import utils.DBConnect;
import java.sql.*;
import model.sanpham.MauSac;
import model.sanpham.ThuongHieu;
/**
 *
 * @author nguye
 */
public class ThuongHieuRepo {
     private Connection conn = null;
    
    public ThuongHieuRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<ThuongHieu> getAll() {
        ArrayList<ThuongHieu> listTH = new ArrayList<>();
        String sql = """
                        SELECT [id]
                              ,[ma]
                              ,[ten]
                          FROM [dbo].[ThuongHieu]
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                listTH.add(new ThuongHieu(
                        rs.getInt("id"),
                        rs.getString("ma"),
                        rs.getString("ten")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listTH;
    }
    
    public void creatThuongHieu(ThuongHieu newThuongHieu) {
     String sql = """
                     INSERT INTO [dbo].[ThuongHieu]
                                ([ma], [ten])
                          VALUES
                                (?, ?)
                  """;
     try (PreparedStatement ps = conn.prepareStatement(sql)) {
         ps.setString(1, newThuongHieu.getMa());
         ps.setString(2, newThuongHieu.getTen());
         
         // Execute the update (insert operation)
         ps.executeUpdate();
     } catch (Exception e) {
         e.printStackTrace();
     }
 }
}

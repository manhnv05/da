/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.sanpham;
import java.util.ArrayList;
import utils.DBConnect;
import java.sql.*;
import model.sanpham.CoAo;
import model.sanpham.KichThuoc;
import model.sanpham.MauSac;
/**
 *
 * @author nguye
 */
public class KichThuocRepo {
     private Connection conn = null;
    
    public KichThuocRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<KichThuoc> getAll() {
        ArrayList<KichThuoc> listKT = new ArrayList<>();
        String sql = """
                        SELECT [id]
                              ,[ma]
                              ,[ten]
                          FROM [dbo].[KichThuoc]
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                listKT.add(new KichThuoc(
                        rs.getInt("id"),
                        rs.getString("ma"),
                        rs.getString("ten")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listKT;
    }
    
    public void creatKichThuoc(KichThuoc newKichThuoc) {
     String sql = """
                     INSERT INTO [dbo].[KichThuoc]
                                ([ma], [ten])
                          VALUES
                                (?, ?)
                  """;
     try (PreparedStatement ps = conn.prepareStatement(sql)) {
         ps.setString(1, newKichThuoc.getMa());
         ps.setString(2, newKichThuoc.getTen());
         
         // Execute the update (insert operation)
         ps.executeUpdate();
     } catch (Exception e) {
         e.printStackTrace();
     }
 }
}

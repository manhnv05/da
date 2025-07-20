/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.sanpham;
import java.util.ArrayList;
import utils.DBConnect;
import java.sql.*;
import model.sanpham.ChatLieu;
import model.sanpham.KhoaAo;
import model.sanpham.MauSac;
/**
 *
 * @author nguye
 */
public class KhoaAoRepo {
     private Connection conn = null;
    
    public KhoaAoRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<KhoaAo> getAll() {
        ArrayList<KhoaAo> listKA = new ArrayList<>();
        String sql = """
                        SELECT [id]
                              ,[ma]
                              ,[ten]
                          FROM [dbo].[KhoaAo]
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                listKA.add(new KhoaAo(
                        rs.getInt("id"),
                        rs.getString("ma"),
                        rs.getString("ten")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listKA;
    }
    
     public void creatKhoaAo(KhoaAo newKhoaAo) {
     String sql = """
                     INSERT INTO [dbo].[KhoaAo]
                                ([ma], [ten])
                          VALUES
                                (?, ?)
                  """;
     try (PreparedStatement ps = conn.prepareStatement(sql)) {
         ps.setString(1, newKhoaAo.getMa());
         ps.setString(2, newKhoaAo.getTen());
         
         // Execute the update (insert operation)
         ps.executeUpdate();
     } catch (Exception e) {
         e.printStackTrace();
     }
 }
}

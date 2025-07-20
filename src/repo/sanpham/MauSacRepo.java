/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.sanpham;
import java.util.ArrayList;
import utils.DBConnect;
import java.sql.*;
import model.sanpham.MauSac;
/**
 *
 * @author nguye
 */
public class MauSacRepo {
    private Connection conn = null;
    
    public MauSacRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<MauSac> getAll() {
        ArrayList<MauSac> danhSach = new ArrayList<>();
        String sql = """
                       SELECT [id]
                              ,[ma]
                              ,[ten]
       
                          FROM [dbo].[MauSac]
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                danhSach.add(new MauSac(
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
    public void creatMauSac(MauSac newMauSac) {
     String sql = """
                     INSERT INTO [dbo].[MauSac]
                                ([ma], [ten])
                          VALUES
                                (?, ?)
                  """;
     try (PreparedStatement ps = conn.prepareStatement(sql)) {
         ps.setString(1, newMauSac.getMa());
         ps.setString(2, newMauSac.getTen());
         
         // Execute the update (insert operation)
         ps.executeUpdate();
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.sanpham;
import java.util.ArrayList;
import utils.DBConnect;
import java.sql.*;
import model.sanpham.ChatLieu;
import model.sanpham.CoAo;
import model.sanpham.MauSac;
/**
 *
 * @author nguye
 */
public class CoAoRepo {
     private Connection conn = null;
    
    public CoAoRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<CoAo> getAll() {
        ArrayList<CoAo> listCA = new ArrayList<>();
        String sql = """
                       SELECT [id]
                              ,[ma]
                              ,[ten]
                          FROM [dbo].[CoAo]
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                listCA.add(new CoAo(
                        rs.getInt("id"),
                        rs.getString("ma"),
                        rs.getString("ten")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listCA;
    }
  public void creatCoAo(CoAo newCoAo) {
     String sql = """
                     INSERT INTO [dbo].[CoAo]
                                ([ma], [ten])
                          VALUES
                                (?, ?)
                  """;
     try (PreparedStatement ps = conn.prepareStatement(sql)) {
         ps.setString(1, newCoAo.getMa());
         ps.setString(2, newCoAo.getTen());
         
         // Execute the update (insert operation)
         ps.executeUpdate();
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

}

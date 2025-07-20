/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repo.sanpham;
import java.util.ArrayList;
import utils.DBConnect;
import java.sql.*;
import model.sanpham.ChatLieu;
import model.sanpham.MauSac;
/**
 *
 * @author nguye
 */
public class ChatLieuRepo {
     private Connection conn = null;
    
    public ChatLieuRepo() {
        conn = DBConnect.getConnection();
    }
    
    public ArrayList<ChatLieu> getAll() {
        ArrayList<ChatLieu> list = new ArrayList<>();
        String sql = """
                        SELECT [id]
                              ,[ma]
                              ,[ten]
                          FROM [dbo].[ChatLieu]
                     """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new ChatLieu(
                        rs.getInt("id"),
                        rs.getString("ma"),
                        rs.getString("ten")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
     public void creatChatLieu(ChatLieu newChatLieu) {
     String sql = """
                     INSERT INTO [dbo].[ChatLieu]
                                ([ma], [ten])
                          VALUES
                                (?, ?)
                  """;
     try (PreparedStatement ps = conn.prepareStatement(sql)) {
         ps.setString(1, newChatLieu.getMa());
         ps.setString(2, newChatLieu.getTen());
         
         // Execute the update (insert operation)
         ps.executeUpdate();
     } catch (Exception e) {
         e.printStackTrace();
     }
 }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import respon.ThuocTinhRespon;
import utils.DBConnect;

/**
 *
 * @author nguye
 */
public class ThuocTinhService {
     public List<ThuocTinhRespon> selectAllByTable(String table) {
        List<ThuocTinhRespon> list = new ArrayList<>();
        String sql = "SELECT [id],[ten],[ma] FROM " + table;
        
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                list.add(ThuocTinhRespon.builder()
                        .id(rs.getInt("id"))
                        .ten(rs.getString("ten"))
                        .ma(rs.getString("ma"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
            
        }
    }
}

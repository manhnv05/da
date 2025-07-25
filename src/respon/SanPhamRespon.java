/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package respon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author nguye
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanPhamRespon {
    private Integer id;
    private String ma;
    private String ten;
    private String loaiSanPham;
    private Integer trangThai;
}

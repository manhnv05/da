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
public class ChiTietSanPhamRespon {
    private Integer id;
    private String maSanPhamChiTiet;
    private String tenSanPham;
    private String mauSac;
    private String kichThuoc;
    private String chatLieu;
    private String thuongHieu;
    private String khoaAo;
    private String coAo;
    private Integer soLuong;
    private Integer donGia;
}

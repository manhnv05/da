/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package respon;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class HoaDonChiTietRespon {
    private Integer id;
    private Integer idChiTietSanPham;
    private String tenSanPham;
    private String tenMauSac;
    private String tenChatLieu;
    private String tenKichThuoc;
    private String tenCoAo;
    private Integer soLuong;
    private Integer donGia;
    private Integer thanhTien;
}

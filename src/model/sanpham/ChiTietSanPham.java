/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.sanpham;

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
public class ChiTietSanPham {
    private Integer id;
    private String maSanPhamChiTiet;
    private Integer idSanPham;
    private Integer idMauSac;
    private Integer idKichThuoc;
    private Integer idChatLieu;
    private Integer idThuongHieu;
    private Integer idKhoaAo;
    private Integer idCoAo;
    private Integer soLuong;
    private Integer donGia;
}

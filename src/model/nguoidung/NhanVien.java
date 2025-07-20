/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.nguoidung;

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
public class NhanVien {
    private Integer id;
    private String maNV;
    private String tenNV;
    private String matKhau;
    private String soDT;
    private String email;
    private String ngaySinh;
    private boolean chucVu;
    private String diaChi;
    private boolean gioiTinh;
    private String tenDN;
    private Integer trangThai;   
}

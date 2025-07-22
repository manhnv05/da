package model.nguoidung;

import java.util.Date;
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
    private String maNhanVien;  
    private String tenNhanVien; 
    private String taiKhoan;  
    private String matKhau; 
    private String soDienThoai;
    private String email;              
    private Date ngaySinh;    
    private String chucVu;            
    private String diaChi;             
    private Boolean gioiTinh;          
    private Boolean trangThai;       
}
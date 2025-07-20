/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package respon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author nguye
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class ThuocTinhRespon {
    private Integer id;
    private String ma;
    private String ten;
    private String idLoaiSP;
    private String trangThai;
}

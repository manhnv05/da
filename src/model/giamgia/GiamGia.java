/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.giamgia;

import java.util.Date;
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
public class GiamGia {
    private Integer id;
    private String maGiamGia;
    private String tenGiamGia;
    private float phanTramGiam;
    private Integer giaTriToiThieu;
    private Integer giaTriToiDa;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Integer trangThai;
}

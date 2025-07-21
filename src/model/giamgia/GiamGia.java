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
    private Integer soLuong;               // Số lượng phiếu
    private String loaiGiamGia;            // Loại giảm giá ("Phần trăm", "Tiền")
    private Double giaTriGiam;             // Giá trị giảm (decimal)
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Double giaTriToiThieu;
    private Double giaTriToiDa;
    private Integer trangThai;             // 1: Đang diễn ra, 0: Kết thúc, ...
}
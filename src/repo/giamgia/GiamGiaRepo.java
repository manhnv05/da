package repo.giamgia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.giamgia.GiamGia;
import utils.DBConnect;

/**
 *
 * @author nguye
 */
public class GiamGiaRepo {
    private Connection conn = null;

    public GiamGiaRepo() {
        conn = DBConnect.getConnection();
    }

    // Lấy tất cả phiếu giảm giá
    public ArrayList<GiamGia> getAll() {
        ArrayList<GiamGia> danhSach = new ArrayList<>();
        String sql = """
            SELECT id, ma_giam_gia, ten_giam_gia, so_luong, loai_giam_gia, gia_tri_giam, gia_tri_toi_thieu, gia_tri_toi_da, ngay_bat_dau, ngay_ket_thuc, ngay_tao, ngay_cap_nhat, trang_thai
            FROM dbo.phieu_giam_gia
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                danhSach.add(GiamGia.builder()
                    .id(rs.getInt("id"))
                    .maGiamGia(rs.getString("ma_giam_gia"))
                    .tenGiamGia(rs.getString("ten_giam_gia"))
                    .soLuong(rs.getInt("so_luong"))
                    .loaiGiamGia(rs.getString("loai_giam_gia"))
                    .giaTriGiam(rs.getDouble("gia_tri_giam"))
                    .giaTriToiThieu(rs.getDouble("gia_tri_toi_thieu"))
                    .giaTriToiDa(rs.getDouble("gia_tri_toi_da"))
                    .ngayBatDau(rs.getDate("ngay_bat_dau"))
                    .ngayKetThuc(rs.getDate("ngay_ket_thuc"))
                    .trangThai(rs.getInt("trang_thai"))
                    .build()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    // Lấy phiếu giảm giá theo trạng thái
    public ArrayList<GiamGia> getAllTrangThai(int trangThai) {
        String sql = """
            SELECT id, ma_giam_gia, ten_giam_gia, so_luong, loai_giam_gia, gia_tri_giam, gia_tri_toi_thieu, gia_tri_toi_da, ngay_bat_dau, ngay_ket_thuc, ngay_tao, ngay_cap_nhat, trang_thai
            FROM dbo.phieu_giam_gia WHERE trang_thai = ?
        """;
        ArrayList<GiamGia> listGG = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GiamGia response = GiamGia.builder()
                    .id(rs.getInt("id"))
                    .maGiamGia(rs.getString("ma_giam_gia"))
                    .tenGiamGia(rs.getString("ten_giam_gia"))
                    .soLuong(rs.getInt("so_luong"))
                    .loaiGiamGia(rs.getString("loai_giam_gia"))
                    .giaTriGiam(rs.getDouble("gia_tri_giam"))
                    .giaTriToiThieu(rs.getDouble("gia_tri_toi_thieu"))
                    .giaTriToiDa(rs.getDouble("gia_tri_toi_da"))
                    .ngayBatDau(rs.getDate("ngay_bat_dau"))
                    .ngayKetThuc(rs.getDate("ngay_ket_thuc"))
                    .trangThai(rs.getInt("trang_thai"))
                    .build();
                listGG.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return listGG;
    }

    // Thêm phiếu giảm giá mới
    public void themGiamGia(GiamGia giamGia) {
        String sql = """
            INSERT INTO dbo.phieu_giam_gia
                (ma_giam_gia, ten_giam_gia, so_luong, loai_giam_gia, gia_tri_giam, gia_tri_toi_thieu, gia_tri_toi_da, ngay_bat_dau, ngay_ket_thuc, ngay_tao, ngay_cap_nhat, trang_thai)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, giamGia.getMaGiamGia());
            ps.setObject(2, giamGia.getTenGiamGia());
            ps.setObject(3, giamGia.getSoLuong());
            ps.setObject(4, giamGia.getLoaiGiamGia());
            ps.setObject(5, giamGia.getGiaTriGiam());
            ps.setObject(6, giamGia.getGiaTriToiThieu());
            ps.setObject(7, giamGia.getGiaTriToiDa());
            ps.setObject(8, giamGia.getNgayBatDau());
            ps.setObject(9, giamGia.getNgayKetThuc());
            ps.setObject(10, new Date());
            ps.setObject(11, new Date());
            ps.setObject(12, giamGia.getTrangThai());
            ps.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra trùng mã giảm giá
    public GiamGia checkTrung(String maCheck) {
        GiamGia gg = null;
        String sql = """
            SELECT id, ma_giam_gia, ten_giam_gia, so_luong, loai_giam_gia, gia_tri_giam, gia_tri_toi_thieu, gia_tri_toi_da, ngay_bat_dau, ngay_ket_thuc, ngay_tao, ngay_cap_nhat, trang_thai
            FROM dbo.phieu_giam_gia WHERE ma_giam_gia = ?
        """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maCheck);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                gg = GiamGia.builder()
                    .id(rs.getInt("id"))
                    .maGiamGia(rs.getString("ma_giam_gia"))
                    .tenGiamGia(rs.getString("ten_giam_gia"))
                    .soLuong(rs.getInt("so_luong"))
                    .loaiGiamGia(rs.getString("loai_giam_gia"))
                    .giaTriGiam(rs.getDouble("gia_tri_giam"))
                    .giaTriToiThieu(rs.getDouble("gia_tri_toi_thieu"))
                    .giaTriToiDa(rs.getDouble("gia_tri_toi_da"))
                    .ngayBatDau(rs.getDate("ngay_bat_dau"))
                    .ngayKetThuc(rs.getDate("ngay_ket_thuc"))
                    .trangThai(rs.getInt("trang_thai"))
                    .build();
            }
            return gg;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    // Xóa phiếu giảm giá theo mã
    public boolean delete(String ma) {
        int check = 0;
        String sql = "DELETE FROM dbo.phieu_giam_gia WHERE ma_giam_gia = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    // Cập nhật phiếu giảm giá theo mã
    public boolean update(GiamGia newGiamGia, String ma) {
        int check = 0;
        String sql = """
            UPDATE dbo.phieu_giam_gia
            SET 
                ten_giam_gia = ?,
                so_luong = ?,
                loai_giam_gia = ?,
                gia_tri_giam = ?,
                gia_tri_toi_thieu = ?,
                gia_tri_toi_da = ?,
                ngay_bat_dau = ?,
                ngay_ket_thuc = ?,
                ngay_cap_nhat = ?,
                trang_thai = ?
            WHERE ma_giam_gia = ?
        """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, newGiamGia.getTenGiamGia());
            ps.setObject(2, newGiamGia.getSoLuong());
            ps.setObject(3, newGiamGia.getLoaiGiamGia());
            ps.setObject(4, newGiamGia.getGiaTriGiam());
            ps.setObject(5, newGiamGia.getGiaTriToiThieu());
            ps.setObject(6, newGiamGia.getGiaTriToiDa());
            ps.setObject(7, newGiamGia.getNgayBatDau());
            ps.setObject(8, newGiamGia.getNgayKetThuc());
            ps.setObject(9, new Date());
            ps.setObject(10, newGiamGia.getTrangThai());
            ps.setObject(11, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    // Lọc voucher theo trạng thái (sửa lại cho đúng kiểu trạng thái là int)
    public List<GiamGia> locVoucher(int trangThai) {
        List<GiamGia> list = new ArrayList<>();
        String sql = """
            SELECT id, ma_giam_gia, ten_giam_gia, so_luong, loai_giam_gia, gia_tri_giam, gia_tri_toi_thieu, gia_tri_toi_da, ngay_bat_dau, ngay_ket_thuc, ngay_tao, ngay_cap_nhat, trang_thai
            FROM dbo.phieu_giam_gia WHERE trang_thai = ?
        """;
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setInt(1, trangThai);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                GiamGia response = GiamGia.builder()
                    .id(rs.getInt("id"))
                    .maGiamGia(rs.getString("ma_giam_gia"))
                    .tenGiamGia(rs.getString("ten_giam_gia"))
                    .soLuong(rs.getInt("so_luong"))
                    .loaiGiamGia(rs.getString("loai_giam_gia"))
                    .giaTriGiam(rs.getDouble("gia_tri_giam"))
                    .giaTriToiThieu(rs.getDouble("gia_tri_toi_thieu"))
                    .giaTriToiDa(rs.getDouble("gia_tri_toi_da"))
                    .ngayBatDau(rs.getDate("ngay_bat_dau"))
                    .ngayKetThuc(rs.getDate("ngay_ket_thuc"))
                    .trangThai(rs.getInt("trang_thai"))
                    .build();
                list.add(response);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi db", e);
        }
    }

    // Tìm kiếm theo mã giảm giá
    public ArrayList<GiamGia> search(String maVC) {
        String sql = """
            SELECT id, ma_giam_gia, ten_giam_gia, so_luong, loai_giam_gia, gia_tri_giam, gia_tri_toi_thieu, gia_tri_toi_da, ngay_bat_dau, ngay_ket_thuc, trang_thai
            FROM dbo.phieu_giam_gia WHERE ma_giam_gia LIKE ?
        """;
        ArrayList<GiamGia> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, "%" + maVC + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lists.add(GiamGia.builder()
                    .id(rs.getInt("id"))
                    .maGiamGia(rs.getString("ma_giam_gia"))
                    .tenGiamGia(rs.getString("ten_giam_gia"))
                    .soLuong(rs.getInt("so_luong"))
                    .loaiGiamGia(rs.getString("loai_giam_gia"))
                    .giaTriGiam(rs.getDouble("gia_tri_giam"))
                    .giaTriToiThieu(rs.getDouble("gia_tri_toi_thieu"))
                    .giaTriToiDa(rs.getDouble("gia_tri_toi_da"))
                    .ngayBatDau(rs.getDate("ngay_bat_dau"))
                    .ngayKetThuc(rs.getDate("ngay_ket_thuc"))
                    .trangThai(rs.getInt("trang_thai"))
                    .build()
                );
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return lists;
    }
}
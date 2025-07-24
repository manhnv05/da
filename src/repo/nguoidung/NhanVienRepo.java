package repo.nguoidung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.nguoidung.NhanVien;
import utils.DBConnect;

// Thêm import Password4j
import com.password4j.Password;
import com.password4j.Argon2Function;
import com.password4j.types.Argon2;

/**
 *
 * @author nguye
 */
public class NhanVienRepo {
    private Connection conn = null;

    public NhanVienRepo() {
        conn = DBConnect.getConnection();
    }

    // Lấy toàn bộ nhân viên
    public ArrayList<NhanVien> getAll() {
        ArrayList<NhanVien> list = new ArrayList<>();
        String sql = """
            SELECT [id]
                  ,[ma_nhan_vien]
                  ,[ten_nhan_vien]
                  ,[tai_khoan]
                  ,[mat_khau]
                  ,[so_dien_thoai]
                  ,[email]
                  ,[ngay_sinh]
                  ,[chuc_vu]
                  ,[dia_chi]
                  ,[gioi_tinh]
                  ,[trang_thai]
            FROM [dbo].[nhan_vien]
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new NhanVien(
                    rs.getInt("id"),
                    rs.getString("ma_nhan_vien"),
                    rs.getString("ten_nhan_vien"),
                    rs.getString("tai_khoan"),
                    rs.getString("mat_khau"),
                    rs.getString("so_dien_thoai"),
                    rs.getString("email"),
                    rs.getDate("ngay_sinh"),
                    rs.getString("chuc_vu"),
                    rs.getString("dia_chi"),
                    rs.getBoolean("gioi_tinh"),
                    rs.getBoolean("trang_thai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đăng nhập: kiểm tra bằng Argon2id
public NhanVien login(String taiKhoan, String matKhau) {
    NhanVien nv = null;
    String sql = "SELECT * FROM nhan_vien WHERE tai_khoan = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, taiKhoan);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String hash = rs.getString("mat_khau");
            System.out.println("Hash in DB: " + hash);
            System.out.println("Password input: " + matKhau);
            boolean match = false;
            if (hash != null && hash.startsWith("$argon2id$")) {
                Argon2Function argon2 = Argon2Function.getInstance(
                    65536, 3, 1, 32, Argon2.ID, Argon2Function.ARGON2_VERSION_13
                );
                match = Password.check(matKhau, hash).with(argon2);
                System.out.println("Check result: " + match);
            } else {
                match = matKhau.equals(hash);
                System.out.println("Plain compare result: " + match);
            }
            if (match) {
                nv = new NhanVien(
                    rs.getInt("id"),
                    rs.getString("ma_nhan_vien"),
                    rs.getString("ten_nhan_vien"),
                    rs.getString("tai_khoan"),
                    rs.getString("mat_khau"),
                    rs.getString("so_dien_thoai"),
                    rs.getString("email"),
                    rs.getDate("ngay_sinh"),
                    rs.getString("chuc_vu"),
                    rs.getString("dia_chi"),
                    rs.getBoolean("gioi_tinh"),
                    rs.getBoolean("trang_thai")
                );
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return nv; // Trả về null nếu đăng nhập thất bại, trả về NhanVien nếu thành công
}

    // Thêm nhân viên: mã hóa mật khẩu bằng Argon2id trước khi lưu
    public void themNhanVien(NhanVien nv) {
        String rawPassword = nv.getMatKhau();
        // Tạo Argon2id function với 6 tham số kiểu int (theo Password4j 1.8.4)
        Argon2Function argon2 = Argon2Function.getInstance(
            65536,         // memory (KB)
            3,             // iterations
            1,             // parallelism
            32,            // hash length
            Argon2.ID,     // type (enum)
            Argon2Function.ARGON2_VERSION_13 // version (hoặc 0x13)
        );

        String hash = Password.hash(rawPassword).with(argon2).getResult(); // lấy chuỗi hash
        nv.setMatKhau(hash); // Gán lại mật khẩu đã hash

        String sql = """
            INSERT INTO [dbo].[nhan_vien]
                ([ma_nhan_vien]
                ,[ten_nhan_vien]
                ,[tai_khoan]
                ,[mat_khau]
                ,[so_dien_thoai]
                ,[email]
                ,[ngay_sinh]
                ,[chuc_vu]
                ,[dia_chi]
                ,[gioi_tinh]
                ,[trang_thai])
            VALUES (?,?,?,?,?,?,?,?,?,?,?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNhanVien());
            ps.setString(2, nv.getTenNhanVien());
            ps.setString(3, nv.getTaiKhoan());
            ps.setString(4, nv.getMatKhau()); // Lưu hash vào DB
            ps.setString(5, nv.getSoDienThoai());
            ps.setString(6, nv.getEmail());
            if (nv.getNgaySinh() != null)
                ps.setDate(7, new java.sql.Date(nv.getNgaySinh().getTime()));
            else
                ps.setDate(7, null);
            ps.setString(8, nv.getChucVu());
            ps.setString(9, nv.getDiaChi());
            ps.setBoolean(10, nv.getGioiTinh() != null ? nv.getGioiTinh() : false);
            ps.setBoolean(11, nv.getTrangThai() != null ? nv.getTrangThai() : true);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sửa nhân viên (nếu thay đổi mật khẩu thì mã hóa lại)
    public boolean suaNhanVien(NhanVien nv, String maNhanVien) {
        int check = 0;
        String rawPassword = nv.getMatKhau();
        Argon2Function argon2 = Argon2Function.getInstance(
            65536,         // memory (KB)
            3,             // iterations
            1,             // parallelism
            32,            // hash length
            Argon2.ID,     // type (enum)
            Argon2Function.ARGON2_VERSION_13 // version (hoặc 0x13)
        );
        String hash = Password.hash(rawPassword).with(argon2).getResult(); // lấy chuỗi hash
        nv.setMatKhau(hash);

        String sql = """
            UPDATE [dbo].[nhan_vien]
                SET [ten_nhan_vien] = ?
                    ,[tai_khoan] = ?
                    ,[mat_khau] = ?
                    ,[so_dien_thoai] = ?
                    ,[email] = ?
                    ,[ngay_sinh] = ?
                    ,[chuc_vu] = ?
                    ,[dia_chi] = ?
                    ,[gioi_tinh] = ?
                    ,[trang_thai] = ?
            WHERE ma_nhan_vien = ?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getTenNhanVien());
            ps.setString(2, nv.getTaiKhoan());
            ps.setString(3, nv.getMatKhau());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getEmail());
            if (nv.getNgaySinh() != null)
                ps.setDate(6, new java.sql.Date(nv.getNgaySinh().getTime()));
            else
                ps.setDate(6, null);
            ps.setString(7, nv.getChucVu());
            ps.setString(8, nv.getDiaChi());
            ps.setBoolean(9, nv.getGioiTinh() != null ? nv.getGioiTinh() : false);
            ps.setBoolean(10, nv.getTrangThai() != null ? nv.getTrangThai() : true);
            ps.setString(11, maNhanVien);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    // Kiểm tra trùng mã nhân viên
    public NhanVien checkTrung(String maNhanVien) {
        NhanVien nv = null;
        String sql = """
            SELECT [id]
                  ,[ma_nhan_vien]
                  ,[ten_nhan_vien]
                  ,[tai_khoan]
                  ,[mat_khau]
                  ,[so_dien_thoai]
                  ,[email]
                  ,[ngay_sinh]
                  ,[chuc_vu]
                  ,[dia_chi]
                  ,[gioi_tinh]
                  ,[trang_thai]
            FROM [dbo].[nhan_vien] WHERE ma_nhan_vien = ?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nv = new NhanVien(
                    rs.getInt("id"),
                    rs.getString("ma_nhan_vien"),
                    rs.getString("ten_nhan_vien"),
                    rs.getString("tai_khoan"),
                    rs.getString("mat_khau"),
                    rs.getString("so_dien_thoai"),
                    rs.getString("email"),
                    rs.getDate("ngay_sinh"),
                    rs.getString("chuc_vu"),
                    rs.getString("dia_chi"),
                    rs.getBoolean("gioi_tinh"),
                    rs.getBoolean("trang_thai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }

    // Xóa mềm nhân viên (chuyển trạng thái về false)
    public boolean delete(String maNhanVien) {
        int check = 0;
        String sql = "UPDATE [dbo].[nhan_vien] SET [trang_thai] = 0 WHERE ma_nhan_vien = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    // Tìm kiếm nhân viên theo mã hoặc số điện thoại
    public ArrayList<NhanVien> searchNV(String keyword) {
        ArrayList<NhanVien> lists = new ArrayList<>();
        String sql = """
            SELECT [id]
                  ,[ma_nhan_vien]
                  ,[ten_nhan_vien]
                  ,[tai_khoan]
                  ,[mat_khau]
                  ,[so_dien_thoai]
                  ,[email]
                  ,[ngay_sinh]
                  ,[chuc_vu]
                  ,[dia_chi]
                  ,[gioi_tinh]
                  ,[trang_thai]
            FROM [dbo].[nhan_vien]
            WHERE ma_nhan_vien LIKE ? OR so_dien_thoai LIKE ?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lists.add(new NhanVien(
                    rs.getInt("id"),
                    rs.getString("ma_nhan_vien"),
                    rs.getString("ten_nhan_vien"),
                    rs.getString("tai_khoan"),
                    rs.getString("mat_khau"),
                    rs.getString("so_dien_thoai"),
                    rs.getString("email"),
                    rs.getDate("ngay_sinh"),
                    rs.getString("chuc_vu"),
                    rs.getString("dia_chi"),
                    rs.getBoolean("gioi_tinh"),
                    rs.getBoolean("trang_thai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }
    
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM nhan_vien WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Đổi mật khẩu khi quên mật khẩu (tìm theo email, mã hóa bằng Argon2id)
    public boolean updatePasswordByEmail(String email, String newPassword) {
        int check = 0;
        Argon2Function argon2 = Argon2Function.getInstance(
            65536,         // memory (KB)
            3,             // iterations
            1,             // parallelism
            32,            // hash length
            Argon2.ID,     // type (enum)
            Argon2Function.ARGON2_VERSION_13 // version (hoặc 0x13)
        );
        String hash = Password.hash(newPassword).with(argon2).getResult(); // lấy chuỗi hash
        String sql = "UPDATE nhan_vien SET mat_khau = ? WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hash);
            ps.setString(2, email);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
}
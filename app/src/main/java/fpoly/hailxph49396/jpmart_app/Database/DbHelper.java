package fpoly.hailxph49396.jpmart_app.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "JPMart.db";
    public static final int DB_VERSION = 1;

//     Table names
    public static final String TABLE_TAI_KHOAN = "TaiKhoan";
    public static final String KHACH_HANG = "KhachHang";
    public static final String TABLE_NHAN_VIEN = "NhanVien";
    public static final String TABLE_DANH_MUC = "DanhMuc";
    public static final String TABLE_SAN_PHAM = "SanPham";
    public static final String TABLE_HOA_DON = "HoaDon";
    public static final String TABLE_CHI_TIET_HOA_DON = "ChiTietHoaDon";
    public static final String TABLE_GIO_HANG = "GioHang";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TAI_KHOAN
        db.execSQL("CREATE TABLE TAI_KHOAN (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, " +
                "ten TEXT NOT NULL, " +
                "ho_va_ten_dem TEXT, " +
                "gioi_tinh TEXT, " +
                "so_dien_thoai TEXT, " +
                "email TEXT, " +
                "dia_chi TEXT)");
        // KHACH_HANG
        db.execSQL("CREATE TABLE KHACH_HANG (" +
                "MaKhachHang TEXT PRIMARY KEY NOT NULL, " +
                "TenKhachHang TEXT NOT NULL, " +
                "DiaChi TEXT NOT NULL, " +
                "SoDienThoai TEXT NOT NULL, " +
                "Email TEXT NOT NULL)");

        // NHAN_VIEN
        db.execSQL("CREATE TABLE NHAN_VIEN (" +
                "MaNhanVien TEXT PRIMARY KEY, " +
                "TenNhanVien TEXT NOT NULL, " +
                "DiaChi TEXT NOT NULL, " +
                "ChucVu INTEGER NOT NULL, " +       // 1: Quản lý, 0: Nhân viên
                "Luong DOUBLE NOT NULL, " +
                "MatKhau TEXT NOT NULL)");

        // DANH_MUC
        db.execSQL("CREATE TABLE DANH_MUC (" +
                "MaDanhMuc TEXT PRIMARY KEY NOT NULL, " +
                "TenDanhMuc TEXT NOT NULL)");

        // SAN_PHAM
        db.execSQL("CREATE TABLE SAN_PHAM (" +
                "MaSanPham TEXT PRIMARY KEY NOT NULL, " +
                "TenSanPham TEXT NOT NULL, " +
                "Gia INTEGER NOT NULL, " +
                "SoLuong INTEGER NOT NULL, " +
                "DonViTinh TEXT NOT NULL, " +
                "NgayNhap TEXT NOT NULL, " +
                "MaDanhMuc TEXT NOT NULL, " +
                "FOREIGN KEY (MaDanhMuc) REFERENCES DANH_MUC(MaDanhMuc))");

        // HOA_DON
        db.execSQL("CREATE TABLE HOA_DON (" +
                "MaHoaDon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MaKhachHang TEXT NOT NULL, " +
                "MaNhanVien TEXT NOT NULL, " +
                "NgayLap TEXT NOT NULL, " +
                "TongTien INTEGER NOT NULL, " +
                "FOREIGN KEY (MaKhachHang) REFERENCES KHACH_HANG(MaKhachHang), " +
                "FOREIGN KEY (MaNhanVien) REFERENCES NHAN_VIEN(MaNhanVien))");

        // CHI_TIET_HOA_DON
        db.execSQL("CREATE TABLE CHI_TIET_HOA_DON (" +
                "MaCTHD TEXT PRIMARY KEY NOT NULL, " +
                "MaHoaDon TEXT NOT NULL, " +
                "MaSanPham TEXT NOT NULL, " +
                "SoLuong INTEGER NOT NULL, " +
                "DonGia INTEGER NOT NULL, " +
                "FOREIGN KEY (MaHoaDon) REFERENCES HOA_DON(MaHoaDon), " +
                "FOREIGN KEY (MaSanPham) REFERENCES SAN_PHAM(MaSanPham))");


        db.execSQL("CREATE TABLE GIO_HANG (" +
                "id INTEGER PRIMARY KEY NOT NULL, " +
                "MaKhachHang TEXT, " +
                "MaNhanVien TEXT, " +
                "MaSanPham TEXT NOT NULL, " +
                "TenSanPham TEXT NOT NULL, " +
                "SoLuong INTEGER NOT NULL, " +
                "Gia INTEGER NOT NULL, " +
                "DonViTinh TEXT, " +
                "NgayNhap TEXT, " +
                "FOREIGN KEY (MaSanPham) REFERENCES SAN_PHAM(MaSanPham)," +
                "FOREIGN KEY (MaKhachHang) REFERENCES KHACH_HANG(MaKhachHang)," +
                "FOREIGN KEY (MaNhanVien) REFERENCES NHAN_VIEN(MaNhanVien))");


        // Dữ liệu mẫu KHACH_HANG
        db.execSQL("INSERT INTO KHACH_HANG VALUES " +
                "('KH01', 'Nguyễn Văn A', 'Hà Nội', '0901234567', 'vana@gmail.com')," +
                "('KH02', 'Trần Thị B', 'Đà Nẵng', '0912345678', 'thib@gmail.com')");

        // Dữ liệu mẫu NHAN_VIEN
        db.execSQL("INSERT INTO NHAN_VIEN VALUES " +
                "('NV01', 'Lê Xuân Hải', 'Hà Nội', 1, 12000000, 'adminpass')," +
                "('NV02', 'Phạm Thị Hoa', 'Sài Gòn', 0, 9000000, 'nvpass')");

        // Dữ liệu mẫu DANH_MUC
        db.execSQL("INSERT INTO DANH_MUC VALUES " +
                "('DM01', 'Điện thoại')," +
                "('DM02', 'Laptop')");

        // Dữ liệu mẫu SAN_PHAM
        db.execSQL("INSERT INTO SAN_PHAM VALUES " +
                "('SP01', 'iPhone 15', 25000000, 10, 'Cái', '2025-07-01', 'DM01')," +
                "('SP02', 'MacBook Air M2', 32000000, 5, 'Cái', '2025-07-03', 'DM02')");

        // Dữ liệu mẫu HOA_DON
//        db.execSQL("INSERT INTO HOA_DON VALUES " +
//                "('HD01', 'KH01', 'NV01', '2025-07-10', 25000000)," +
//                "('HD02', 'KH02', 'NV02', '2025-07-11', 32000000)");

        db.execSQL("INSERT INTO HOA_DON (MaKhachHang, MaNhanVien, NgayLap, TongTien) VALUES " +
                "('KH01', 'NV01', '2025-07-10', 25000000)," +
                "('KH02', 'NV02', '2025-07-11', 32000000)");


        // Dữ liệu mẫu CHI_TIET_HOA_DON
        db.execSQL("INSERT INTO CHI_TIET_HOA_DON VALUES " +
                "('CT01', '1', 'SP01', 1, 25000000)," +
                "('CT02', '2', 'SP02', 1, 32000000)");

        // Dữ liệu mẫu TAI_KHOAN
        db.execSQL("INSERT INTO TAI_KHOAN VALUES " +
                "('admin', 'admin', 'Hải', 'Lê Xuân', 'Nam', '0912345678', 'admin@example.com', 'Hà Nội')," +
                "('user01', 'pass01', 'Trang', 'Nguyễn Thị', 'Nữ', '0987654321', 'trang@example.com', 'Hồ Chí Minh')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TAI_KHOAN");
        db.execSQL("DROP TABLE IF EXISTS CHI_TIET_HOA_DON");
        db.execSQL("DROP TABLE IF EXISTS HOA_DON");
        db.execSQL("DROP TABLE IF EXISTS SAN_PHAM");
        db.execSQL("DROP TABLE IF EXISTS DANH_MUC");
        db.execSQL("DROP TABLE IF EXISTS NHAN_VIEN");
        db.execSQL("DROP TABLE IF EXISTS KHACH_HANG");
        db.execSQL("DROP TABLE IF EXISTS GIO_HANG");
        onCreate(db);
    }
}
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


        // Dữ liệu mẫu KHACH_HANG (10 khách hàng)
        db.execSQL("INSERT INTO KHACH_HANG VALUES " +
                "('KH01', 'Nguyễn Văn A', 'Hà Nội', '0901234567', 'vana@gmail.com')," +
                "('KH02', 'Trần Thị B', 'Đà Nẵng', '0912345678', 'thib@gmail.com')," +
                "('KH03', 'Phạm Văn C', 'Hải Phòng', '0923456789', 'vanc@gmail.com')," +
                "('KH04', 'Hoàng Thị D', 'Huế', '0934567890', 'thid@gmail.com')," +
                "('KH05', 'Đỗ Văn E', 'Cần Thơ', '0945678901', 'vane@gmail.com')," +
                "('KH06', 'Ngô Thị F', 'Hà Nội', '0956789012', 'thif@gmail.com')," +
                "('KH07', 'Bùi Văn G', 'Đà Lạt', '0967890123', 'vang@gmail.com')," +
                "('KH08', 'Vũ Thị H', 'Quảng Ninh', '0978901234', 'thih@gmail.com')," +
                "('KH09', 'Trịnh Văn I', 'Nam Định', '0989012345', 'vani@gmail.com')," +
                "('KH10', 'Lưu Thị J', 'Nghệ An', '0990123456', 'thij@gmail.com')");

// Dữ liệu mẫu NHAN_VIEN (10 nhân viên)
        db.execSQL("INSERT INTO NHAN_VIEN VALUES " +
                "('NV01', 'Lê Xuân Hải', 'Hà Nội', 1, 12000000, 'adminpass')," +
                "('NV02', 'Phạm Thị Hoa', 'Sài Gòn', 0, 9000000, 'nvpass')," +
                "('NV03', 'Nguyễn Văn An', 'Đà Nẵng', 1, 10000000, 'pass03')," +
                "('NV04', 'Trần Thị Bình', 'Huế', 0, 9500000, 'pass04')," +
                "('NV05', 'Hoàng Văn Cường', 'Hải Phòng', 1, 11000000, 'pass05')," +
                "('NV06', 'Nguyễn Thị Dung', 'Cần Thơ', 0, 8700000, 'pass06')," +
                "('NV07', 'Đỗ Văn Em', 'Hà Nội', 1, 10500000, 'pass07')," +
                "('NV08', 'Ngô Thị Phương', 'Quảng Nam', 0, 9200000, 'pass08')," +
                "('NV09', 'Bùi Văn Hùng', 'Đà Lạt', 1, 11500000, 'pass09')," +
                "('NV10', 'Vũ Thị Kiều', 'Nam Định', 0, 8900000, 'pass10')");

// Dữ liệu mẫu DANH_MUC (10 danh mục)
        db.execSQL("INSERT INTO DANH_MUC VALUES " +
                "('DM01', 'Điện thoại')," +
                "('DM02', 'Laptop')," +
                "('DM03', 'Máy tính bảng')," +
                "('DM04', 'Phụ kiện')," +
                "('DM05', 'Tivi')," +
                "('DM06', 'Tai nghe')," +
                "('DM07', 'Đồng hồ thông minh')," +
                "('DM08', 'Camera')," +
                "('DM09', 'Loa Bluetooth')," +
                "('DM10', 'Máy in')");

// Dữ liệu mẫu SAN_PHAM (10 sản phẩm)
        db.execSQL("INSERT INTO SAN_PHAM VALUES " +
                "('SP01', 'iPhone 15', 25000000, 10, 'Cái', '2025-07-01', 'DM01')," +
                "('SP02', 'MacBook Air M2', 32000000, 5, 'Cái', '2025-07-03', 'DM02')," +
                "('SP03', 'iPad Pro 12.9', 28000000, 7, 'Cái', '2025-07-05', 'DM03')," +
                "('SP04', 'AirPods Pro 2', 5500000, 15, 'Cái', '2025-07-06', 'DM04')," +
                "('SP05', 'Samsung Galaxy S24', 23000000, 12, 'Cái', '2025-07-07', 'DM01')," +
                "('SP06', 'Dell XPS 13', 34000000, 6, 'Cái', '2025-07-08', 'DM02')," +
                "('SP07', 'Sony WH-1000XM5', 9000000, 8, 'Cái', '2025-07-09', 'DM06')," +
                "('SP08', 'Apple Watch Ultra 2', 22000000, 9, 'Cái', '2025-07-10', 'DM07')," +
                "('SP09', 'Canon EOS R10', 28000000, 4, 'Cái', '2025-07-11', 'DM08')," +
                "('SP10', 'JBL Flip 6', 3500000, 20, 'Cái', '2025-07-12', 'DM09')");

// Dữ liệu mẫu HOA_DON (10 hóa đơn)
        db.execSQL("INSERT INTO HOA_DON (MaKhachHang, MaNhanVien, NgayLap, TongTien) VALUES " +
                "('KH01', 'NV01', '2025-07-10', 25000000)," +
                "('KH02', 'NV02', '2025-07-11', 32000000)," +
                "('KH03', 'NV03', '2025-07-12', 28000000)," +
                "('KH04', 'NV04', '2025-07-13', 5500000)," +
                "('KH05', 'NV05', '2025-07-14', 23000000)," +
                "('KH06', 'NV06', '2025-07-15', 34000000)," +
                "('KH07', 'NV07', '2025-07-16', 9000000)," +
                "('KH08', 'NV08', '2025-07-17', 22000000)," +
                "('KH09', 'NV09', '2025-07-18', 28000000)," +
                "('KH10', 'NV10', '2025-07-19', 3500000)");

// Dữ liệu mẫu CHI_TIET_HOA_DON (10 chi tiết)
        db.execSQL("INSERT INTO CHI_TIET_HOA_DON VALUES " +
                "('CT01', '1', 'SP01', 1, 25000000)," +
                "('CT02', '2', 'SP02', 1, 32000000)," +
                "('CT03', '3', 'SP03', 1, 28000000)," +
                "('CT04', '4', 'SP04', 1, 5500000)," +
                "('CT05', '5', 'SP05', 1, 23000000)," +
                "('CT06', '6', 'SP06', 1, 34000000)," +
                "('CT07', '7', 'SP07', 1, 9000000)," +
                "('CT08', '8', 'SP08', 1, 22000000)," +
                "('CT09', '9', 'SP09', 1, 28000000)," +
                "('CT10', '10', 'SP10', 1, 3500000)");

// Dữ liệu mẫu TAI_KHOAN (10 tài khoản)
        db.execSQL("INSERT INTO TAI_KHOAN VALUES " +
                "('admin', 'admin', 'Hải', 'Lê Xuân', 'Nam', '0912345678', 'admin@example.com', 'Hà Nội')," +
                "('user01', 'pass01', 'Trang', 'Nguyễn Thị', 'Nữ', '0987654321', 'trang@example.com', 'Hồ Chí Minh')," +
                "('user02', 'pass02', 'Bình', 'Trần Văn', 'Nam', '0971234567', 'binh@example.com', 'Đà Nẵng')," +
                "('user03', 'pass03', 'Lan', 'Phạm Thị', 'Nữ', '0962345678', 'lan@example.com', 'Huế')," +
                "('user04', 'pass04', 'Cường', 'Hoàng Văn', 'Nam', '0953456789', 'cuong@example.com', 'Hải Phòng')," +
                "('user05', 'pass05', 'Dung', 'Nguyễn Thị', 'Nữ', '0944567890', 'dung@example.com', 'Cần Thơ')," +
                "('user06', 'pass06', 'Hùng', 'Đỗ Văn', 'Nam', '0935678901', 'hung@example.com', 'Hà Nội')," +
                "('user07', 'pass07', 'Phương', 'Ngô Thị', 'Nữ', '0926789012', 'phuong@example.com', 'Quảng Nam')," +
                "('user08', 'pass08', 'Em', 'Bùi Văn', 'Nam', '0917890123', 'em@example.com', 'Đà Lạt')," +
                "('user09', 'pass09', 'Kiều', 'Vũ Thị', 'Nữ', '0908901234', 'kieu@example.com', 'Nam Định')");

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
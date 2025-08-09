package fpoly.hailxph49396.jpmart_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import fpoly.hailxph49396.jpmart_app.Database.DbHelper;
import fpoly.hailxph49396.jpmart_app.DTO.GioHangDTO;

public class GioHangDAO {
    private SQLiteDatabase db;

    public GioHangDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(GioHangDTO gioHang) {
        ContentValues values = new ContentValues();
        values.put("MaKhachHang", gioHang.getMaKhachHang());
        values.put("MaNhanVien", gioHang.getMaNhanVien());
        values.put("MaSanPham", gioHang.getMaSanPham());
        values.put("TenSanPham", gioHang.getTenSanPham());
        values.put("SoLuong", gioHang.getSoLuong());
        values.put("Gia", gioHang.getGia());
        values.put("DonViTinh", gioHang.getDonViTinh());
        values.put("NgayNhap", gioHang.getNgayNhap());
        return db.insert( "GIO_HANG", null, values);
    }

    public int updateSoLuong(int id, int soLuongMoi) {
        ContentValues values = new ContentValues();
        values.put("SoLuong", soLuongMoi);
        return db.update( "GIO_HANG", values, "id=?", new String[]{String.valueOf(id)});
    }

    public int delete(int id) {
        return db.delete("GIO_HANG", "id=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<GioHangDTO> getAll() {
        ArrayList<GioHangDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM GIO_HANG", null);
        if (c.moveToFirst()) {
            do {
                GioHangDTO gh = new GioHangDTO();
                gh.setId(c.getInt(c.getColumnIndexOrThrow("id")));
                gh.setMaKhachHang(c.getString(c.getColumnIndexOrThrow("MaKhachHang")));
                gh.setMaNhanVien(c.getString(c.getColumnIndexOrThrow("MaNhanVien")));
                gh.setMaSanPham(c.getString(c.getColumnIndexOrThrow("MaSanPham")));
                gh.setTenSanPham(c.getString(c.getColumnIndexOrThrow("TenSanPham")));
                gh.setSoLuong(c.getInt(c.getColumnIndexOrThrow("SoLuong")));
                gh.setGia(c.getInt(c.getColumnIndexOrThrow("Gia")));
                gh.setDonViTinh(c.getString(c.getColumnIndexOrThrow("DonViTinh")));
                gh.setNgayNhap(c.getString(c.getColumnIndexOrThrow("NgayNhap")));
                list.add(gh);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public int getTongTien() {
        int tong = 0;
        Cursor c = db.rawQuery("SELECT SoLuong, Gia FROM GIO_HANG", null);
        if (c.moveToFirst()) {
            do {
                tong += c.getInt(0) * c.getInt(1);
            } while (c.moveToNext());
        }
        c.close();
        return tong;
    }

    public void clearGioHang() {
        db.delete("GIO_HANG", null, null);
    }

//    public boolean thanhToan() {
//        int tongTien = getTongTien();
//        ContentValues values = new ContentValues();
//        values.put("TongTien", tongTien);
//        long row = db.insert("HOA_DON", null, values);
//
//        if (row > 0) {
//            clearGioHang();
//            return true;
//        } else {
//            return false;
//            }
//    }
public boolean thanhToan() {
    int tongTien = getTongTien();

    ContentValues values = new ContentValues();
    values.put("MaKhachHang", "KH01"); // hoặc lấy từ giỏ hàng
    values.put("MaNhanVien", "NV01");  // hoặc lấy từ session đăng nhập
    values.put("NgayLap", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
    values.put("TongTien", tongTien);

    // Bỏ MaHoaDon đi, để SQLite tự tạo
    long rowId = db.insert("HOA_DON", null, values);

    if (rowId > 0) {
        clearGioHang();
        return true;
    } else {
        return false;
    }
}


}

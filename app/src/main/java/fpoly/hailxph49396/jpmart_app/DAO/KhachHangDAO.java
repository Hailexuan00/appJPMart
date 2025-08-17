package fpoly.hailxph49396.jpmart_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import fpoly.hailxph49396.jpmart_app.Database.DbHelper;
import fpoly.hailxph49396.jpmart_app.DTO.KhachHangDTO;

public class KhachHangDAO {
    DbHelper dbHelper;

    public KhachHangDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<KhachHangDTO> getAll() {
        ArrayList<KhachHangDTO> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM KHACH_HANG", null);
        if (c.moveToFirst()) {
            do {
                list.add(new KhachHangDTO(
                        c.getString(c.getColumnIndexOrThrow("MaKhachHang")),
                        c.getString(c.getColumnIndexOrThrow("TenKhachHang")),
                        c.getString(c.getColumnIndexOrThrow("DiaChi")),
                        c.getString(c.getColumnIndexOrThrow("SoDienThoai")),
                        c.getString(c.getColumnIndexOrThrow("Email"))
                ));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public boolean insert(KhachHangDTO kh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaKhachHang", kh.getMaKhachHang());
        values.put("TenKhachHang", kh.getTenKhachHang());
        values.put("DiaChi", kh.getDiaChi());
        values.put("SoDienThoai", kh.getSoDienThoai());
        values.put("Email", kh.getEmail());
        long kq = db.insert("KHACH_HANG", null, values);
        db.close();
        return kq != -1;
    }

    public boolean update(KhachHangDTO kh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenKhachHang", kh.getTenKhachHang());
        values.put("DiaChi", kh.getDiaChi());
        values.put("SoDienThoai", kh.getSoDienThoai());
        values.put("Email", kh.getEmail());
        long kq = db.update("KHACH_HANG", values, "MaKhachHang=?", new String[]{kh.getMaKhachHang()});
        db.close();
        return kq > 0;
    }

    public boolean delete(String maKH) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long kq = db.delete("KHACH_HANG", "MaKhachHang=?", new String[]{maKH});
        db.close();
        return kq > 0;
    }

    public ArrayList<String> getAllKhachHangForSpinner() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT MaKhachHang, TenKhachHang, SoDienThoai FROM KHACH_HANG", null);
        if (c.moveToFirst()) {
            do {
                String ma = c.getString(0);
                String ten = c.getString(1);
                String sdt = c.getString(2);
                list.add(ma + " - " + ten + " - " + sdt);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    // Hàm lấy top khách hàng theo tổng chi tiêu
    public ArrayList<HashMap<String, Object>> getTopKhachHang(String tuNgay, String denNgay, int soLuong) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT KH.MaKhachHang, KH.TenKhachHang, SUM(HD.TongTien) as TongChiTieu " +
                "FROM HOA_DON HD " +
                "JOIN KHACH_HANG KH ON KH.MaKhachHang = HD.MaKhachHang " +
                "WHERE HD.NgayLap BETWEEN ? AND ? " +
                "GROUP BY KH.MaKhachHang, KH.TenKhachHang " +
                "ORDER BY TongChiTieu DESC " +
                "LIMIT ?";

        Cursor c = db.rawQuery(sql, new String[]{tuNgay, denNgay, String.valueOf(soLuong)});
        if (c.moveToFirst()) {
            do {
                HashMap<String, Object> map = new HashMap<>();
                map.put("MaKhachHang", c.getString(0));
                map.put("TenKhachHang", c.getString(1));
                map.put("TongChiTieu", c.getInt(2));
                list.add(map);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
}

package fpoly.hailxph49396.jpmart_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

}

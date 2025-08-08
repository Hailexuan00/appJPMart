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
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4)
                ));
            } while (c.moveToNext());
        }
        c.close();
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
        return kq > 0;
    }

    public boolean delete(String maKH) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long kq = db.delete("KHACH_HANG", "MaKhachHang=?", new String[]{maKH});
        return kq > 0;
    }
}

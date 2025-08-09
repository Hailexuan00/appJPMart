package fpoly.hailxph49396.jpmart_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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
        values.put("MaSanPham", gioHang.getMaSanPham());
        values.put("TenSanPham", gioHang.getTenSanPham());
        values.put("SoLuong", gioHang.getSoLuong());
        values.put("Gia", gioHang.getGia());
        values.put("DonViTinh", gioHang.getDonViTinh());
        values.put("NgayNhap", gioHang.getNgayNhap());
        return db.insert("TABLE_GIO_HANG", null, values);
    }

    public int updateSoLuong(int id, int soLuongMoi) {
        ContentValues values = new ContentValues();
        values.put("SoLuong", soLuongMoi);
        return db.update("TABLE_GIO_HANG", values, "id=?", new String[]{String.valueOf(id)});
    }

    public int delete(int id) {
        return db.delete("TABLE_GIO_HANG", "id=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<GioHangDTO> getAll() {
        ArrayList<GioHangDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM TABLE_GIO_HANG", null);
        if (c.moveToFirst()) {
            do {
                GioHangDTO gh = new GioHangDTO();
                gh.setId(c.getInt(0));
                gh.setMaSanPham(c.getString(1));
                gh.setTenSanPham(c.getString(2));
                gh.setSoLuong(c.getInt(3));
                gh.setGia(c.getInt(4));
                gh.setDonViTinh(c.getString(5));
                gh.setNgayNhap(c.getString(6));
                list.add(gh);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public int getTongTien() {
        int tong = 0;
        Cursor c = db.rawQuery("SELECT SoLuong, Gia FROM TABLE_GIO_HANG", null);
        if (c.moveToFirst()) {
            do {
                tong += c.getInt(0) * c.getInt(1);
            } while (c.moveToNext());
        }
        c.close();
        return tong;
    }

    public void clearGioHang() {
        db.delete("TABLE_GIO_HANG", null, null);
    }
}

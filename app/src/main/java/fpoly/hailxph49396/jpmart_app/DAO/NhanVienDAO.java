package fpoly.hailxph49396.jpmart_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Database.DbHelper;
import fpoly.hailxph49396.jpmart_app.DTO.NhanVienDTO;

public class NhanVienDAO {
    private SQLiteDatabase db;

    public NhanVienDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<NhanVienDTO> getAll() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM NHAN_VIEN", null);
        if (c.moveToFirst()) {
            do {
                list.add(new NhanVienDTO(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getInt(3),
                        c.getDouble(4),
                        c.getString(5)
                ));
            } while (c.moveToNext());
        }
        return list;
    }

    public long insert(NhanVienDTO nv) {
        ContentValues values = new ContentValues();
        values.put("MaNhanVien", nv.getMaNhanVien());
        values.put("TenNhanVien", nv.getTenNhanVien());
        values.put("DiaChi", nv.getDiaChi());
        values.put("ChucVu", nv.getChucVu());
        values.put("Luong", nv.getLuong());
        values.put("MatKhau", nv.getMatKhau());
        return db.insert("NHAN_VIEN", null, values);
    }

    public int update(NhanVienDTO nv) {
        ContentValues values = new ContentValues();
        values.put("TenNhanVien", nv.getTenNhanVien());
        values.put("DiaChi", nv.getDiaChi());
        values.put("ChucVu", nv.getChucVu());
        values.put("Luong", nv.getLuong());
        values.put("MatKhau", nv.getMatKhau());
        return db.update("NHAN_VIEN", values, "MaNhanVien=?", new String[]{nv.getMaNhanVien()});
    }

    public int delete(String maNV) {
        return db.delete("NHAN_VIEN", "MaNhanVien=?", new String[]{maNV});
    }
    public NhanVienDTO getID(String maNV) {
        Cursor cursor = db.rawQuery("SELECT * FROM NHAN_VIEN WHERE MaNhanVien = ?", new String[]{maNV});
        if (cursor.moveToFirst()) {
            NhanVienDTO nv = new NhanVienDTO(
                    cursor.getString(0), // MaNhanVien
                    cursor.getString(1), // TenNhanVien
                    cursor.getString(2), // DiaChi
                    cursor.getInt(3),    // ChucVu
                    cursor.getDouble(4), // Luong
                    cursor.getString(5)  // MatKhau
            );
            cursor.close();
            return nv;
        }
        cursor.close();
        return null;
    }


}

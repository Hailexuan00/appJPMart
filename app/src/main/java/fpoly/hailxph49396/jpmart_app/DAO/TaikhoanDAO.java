package fpoly.hailxph49396.jpmart_app.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.DTO.TaikhoanDTO;
import fpoly.hailxph49396.jpmart_app.Database.DbHelper;
import fpoly.hailxph49396.jpmart_app.DTO.NhanVienDTO;

public class TaikhoanDAO {

    private DbHelper dbHelper;

    public TaikhoanDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Đăng nhập
    public boolean checkLogin(String maNV, String matKhau) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NHAN_VIEN WHERE MaNhanVien = ? AND MatKhau = ?",
                new String[]{maNV, matKhau});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    // Lấy tất cả nhân viên
    @SuppressLint("Range")
    public ArrayList<NhanVienDTO> getAllNhanVien() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NHAN_VIEN", null);
        if (cursor.moveToFirst()) {
            do {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNhanVien(cursor.getString(cursor.getColumnIndex("MaNhanVien")));
                nv.setTenNhanVien(cursor.getString(cursor.getColumnIndex("TenNhanVien")));
                nv.setDiaChi(cursor.getString(cursor.getColumnIndex("DiaChi")));
                nv.setChucVu(cursor.getInt(cursor.getColumnIndex("ChucVu")));
                nv.setLuong(cursor.getDouble(cursor.getColumnIndex("Luong")));
                nv.setMatKhau(cursor.getString(cursor.getColumnIndex("MatKhau")));
                list.add(nv);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Lấy 1 nhân viên theo mã
    @SuppressLint("Range")
    public NhanVienDTO getNhanVienById(String maNV) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NHAN_VIEN WHERE MaNhanVien = ?", new String[]{maNV});
        NhanVienDTO nv = null;
        if (cursor.moveToFirst()) {
            nv = new NhanVienDTO();
            nv.setMaNhanVien(cursor.getString(cursor.getColumnIndex("MaNhanVien")));
            nv.setTenNhanVien(cursor.getString(cursor.getColumnIndex("TenNhanVien")));
            nv.setDiaChi(cursor.getString(cursor.getColumnIndex("DiaChi")));
            nv.setChucVu(cursor.getInt(cursor.getColumnIndex("ChucVu")));
            nv.setLuong(cursor.getDouble(cursor.getColumnIndex("Luong")));
            nv.setMatKhau(cursor.getString(cursor.getColumnIndex("MatKhau")));
        }
        cursor.close();
        return nv;
    }

    // Thêm nhân viên
    public boolean insertNhanVien(NhanVienDTO nv) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaNhanVien", nv.getMaNhanVien());
        values.put("TenNhanVien", nv.getTenNhanVien());
        values.put("DiaChi", nv.getDiaChi());
        values.put("ChucVu", nv.getChucVu());
        values.put("Luong", nv.getLuong());
        values.put("MatKhau", nv.getMatKhau());
        long result = db.insert("NHAN_VIEN", null, values);
        return result != -1;
    }

    // Cập nhật nhân viên
    public boolean updateNhanVien(NhanVienDTO nv) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenNhanVien", nv.getTenNhanVien());
        values.put("DiaChi", nv.getDiaChi());
        values.put("ChucVu", nv.getChucVu());
        values.put("Luong", nv.getLuong());
        values.put("MatKhau", nv.getMatKhau());
        int result = db.update("NHAN_VIEN", values, "MaNhanVien = ?", new String[]{nv.getMaNhanVien()});
        return result > 0;
    }

    // Xóa nhân viên
    public boolean deleteNhanVien(String maNV) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("NHAN_VIEN", "MaNhanVien = ?", new String[]{maNV});
        return result > 0;
    }

    // Đổi mật khẩu
    public boolean doiMatKhau(String maNV, String oldPass, String newPass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NHAN_VIEN WHERE MaNhanVien = ? AND MatKhau = ?",
                new String[]{maNV, oldPass});
        if (cursor != null && cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put("MatKhau", newPass);
            int result = db.update("NHAN_VIEN", values, "MaNhanVien = ?", new String[]{maNV});
            cursor.close();
            return result > 0;
        }
        if (cursor != null) cursor.close();
        return false;
    }

    @SuppressLint("Range")
    public NhanVienDTO getUserByUsername(String maNV) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NHAN_VIEN WHERE MaNhanVien = ?", new String[]{maNV});
        NhanVienDTO nv = null;
        if (cursor.moveToFirst()) {
            nv = new NhanVienDTO();
            nv.setMaNhanVien(cursor.getString(cursor.getColumnIndex("MaNhanVien")));
            nv.setTenNhanVien(cursor.getString(cursor.getColumnIndex("TenNhanVien")));
            nv.setDiaChi(cursor.getString(cursor.getColumnIndex("DiaChi")));
            nv.setChucVu(cursor.getInt(cursor.getColumnIndex("ChucVu")));
            nv.setLuong(cursor.getDouble(cursor.getColumnIndex("Luong")));
            nv.setMatKhau(cursor.getString(cursor.getColumnIndex("MatKhau")));
        }
        cursor.close();
        return nv;
    }


}

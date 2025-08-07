package fpoly.hailxph49396.jpmart_app.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Database.DbHelper;
import fpoly.hailxph49396.jpmart_app.DTO.TaikhoanDTO;

public class TaikhoanDAO {

    private DbHelper dbHelper;

    public TaikhoanDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertTaikhoan(TaikhoanDTO tk) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", tk.getUsername());
        values.put("password", tk.getPassword());
        values.put("ten", tk.getTen());
        values.put("ho_va_ten_dem", tk.getHo_va_ten_dem());
        values.put("gioi_tinh", tk.getGioi_tinh());
        values.put("so_dien_thoai", tk.getSo_dien_thoai());
        values.put("email", tk.getEmail());
        values.put("dia_chi", tk.getDia_chi());
        long result = db.insert("TAI_KHOAN", null, values);
        return result != -1;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAI_KHOAN WHERE username = ? AND password = ?", new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    @SuppressLint("Range")
    public ArrayList<TaikhoanDTO> getAllTaikhoan() {
        ArrayList<TaikhoanDTO> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAI_KHOAN", null);
        if (cursor.moveToFirst()) {
            do {
                TaikhoanDTO tk = new TaikhoanDTO();
                tk.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                tk.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                tk.setTen(cursor.getString(cursor.getColumnIndex("ten")));
                tk.setHo_va_ten_dem(cursor.getString(cursor.getColumnIndex("ho_va_ten_dem")));
                tk.setGioi_tinh(cursor.getString(cursor.getColumnIndex("gioi_tinh")));
                tk.setSo_dien_thoai(cursor.getString(cursor.getColumnIndex("so_dien_thoai")));
                tk.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                tk.setDia_chi(cursor.getString(cursor.getColumnIndex("dia_chi")));
                list.add(tk);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    @SuppressLint("Range")
    public TaikhoanDTO getUserByUsername(String username) {
        if (username == null) return null; // tránh truyền null gây lỗi
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAI_KHOAN WHERE username = ?", new String[]{username});
        TaikhoanDTO tk = null;
        if (cursor.moveToFirst()) {
            tk = new TaikhoanDTO();
            tk.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            tk.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            tk.setTen(cursor.getString(cursor.getColumnIndex("ten")));
            tk.setHo_va_ten_dem(cursor.getString(cursor.getColumnIndex("ho_va_ten_dem")));
            tk.setGioi_tinh(cursor.getString(cursor.getColumnIndex("gioi_tinh")));
            tk.setSo_dien_thoai(cursor.getString(cursor.getColumnIndex("so_dien_thoai")));
            tk.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            tk.setDia_chi(cursor.getString(cursor.getColumnIndex("dia_chi")));
        }
        cursor.close();
        return tk;
    }

    public boolean updateTaikhoan(TaikhoanDTO tk) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", tk.getPassword());
        values.put("ten", tk.getTen());
        values.put("ho_va_ten_dem", tk.getHo_va_ten_dem());
        values.put("gioi_tinh", tk.getGioi_tinh());
        values.put("so_dien_thoai", tk.getSo_dien_thoai());
        values.put("email", tk.getEmail());
        values.put("dia_chi", tk.getDia_chi());
        int result = db.update("TAI_KHOAN", values, "username = ?", new String[]{tk.getUsername()});
        return result > 0;
    }

    public boolean deleteTaikhoan(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("TAI_KHOAN", "username = ?", new String[]{username});
        return result > 0;
    }

    public boolean doiMatKhau(String username, String oldPass, String newPass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAI_KHOAN WHERE username = ? AND password = ?", new String[]{username, oldPass});

        if (cursor != null && cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put("password", newPass);
            int result = db.update("TAI_KHOAN", values, "username = ?", new String[]{username});
            cursor.close();
            Log.d("DOI_MK", "Cập nhật thành công");
            return result > 0;
        }

        Log.d("DOI_MK", "Sai mật khẩu hoặc username: " + username + ", " + oldPass);
        if (cursor != null) cursor.close();
        return false;
    }


}

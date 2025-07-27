package fpoly.hailxph49396.jpmart_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Database.DbHelper;
import fpoly.hailxph49396.jpmart_app.DTO.TaikhoanDTO;

public class TaikhoanDAO {

    private DbHelper dbHelper;

    public TaikhoanDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Thêm tài khoản mới
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

    // Kiểm tra đăng nhập
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAI_KHOAN WHERE username = ? AND password = ?", new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    // Lấy toàn bộ danh sách tài khoản
    public ArrayList<TaikhoanDTO> getAllTaikhoan() {
        ArrayList<TaikhoanDTO> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAI_KHOAN", null);
        if (cursor.moveToFirst()) {
            do {
                TaikhoanDTO tk = new TaikhoanDTO();
                tk.setUsername(cursor.getString(0));
                tk.setPassword(cursor.getString(1));
                tk.setTen(cursor.getString(2));
                tk.setHo_va_ten_dem(cursor.getString(3));
                tk.setGioi_tinh(cursor.getString(4));
                tk.setSo_dien_thoai(cursor.getString(5));
                tk.setEmail(cursor.getString(6));
                tk.setDia_chi(cursor.getString(7));
                list.add(tk);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Cập nhật tài khoản
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

    // Xóa tài khoản
    public boolean deleteTaikhoan(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("TAI_KHOAN", "username = ?", new String[]{username});
        return result > 0;
    }
}

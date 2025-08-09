package fpoly.hailxph49396.jpmart_app.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Database.DbHelper;
import fpoly.hailxph49396.jpmart_app.DTO.HoaDonDTO;

public class HoaDonDAO {
    private DbHelper dbHelper;

    public HoaDonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<HoaDonDTO> getAllHoaDon() {
        ArrayList<HoaDonDTO> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT hd.MaHoaDon, nv.TenNhanVien, kh.TenKhachHang, hd.NgayLap, hd.TongTien " +
                "FROM HOA_DON hd " +
                "JOIN NHAN_VIEN nv ON hd.MaNhanVien = nv.MaNhanVien " +
                "JOIN KHACH_HANG kh ON hd.MaKhachHang = kh.MaKhachHang";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            list.add(new HoaDonDTO(
                    c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getInt(4)
            ));
        }
        c.close();
        return list;
    }

    public int deleteHoaDon(String maHoaDon) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("HOA_DON", "MaHoaDon=?", new String[]{maHoaDon});
    }
}

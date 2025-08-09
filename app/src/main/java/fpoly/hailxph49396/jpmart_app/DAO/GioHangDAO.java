package fpoly.hailxph49396.jpmart_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Database.DbHelper;
import fpoly.hailxph49396.jpmart_app.DTO.GioHangDTO;

public class GioHangDAO {
    private final SQLiteDatabase db;

    public GioHangDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm sản phẩm vào giỏ hàng (nếu đã tồn tại thì tăng số lượng)
    public boolean themVaoGio(GioHangDTO gioHang) {
        GioHangDTO spTonTai = getSanPhamTrongGio(gioHang.getMaSanPham());
        if (spTonTai != null) {
            int soLuongMoi = spTonTai.getSoLuong() + gioHang.getSoLuong();
            return capNhatSoLuong(gioHang.getMaSanPham(), soLuongMoi) > 0;
        }

        // Nếu số lượng <= 0 thì set mặc định là 1
        if (gioHang.getSoLuong() <= 0) {
            gioHang.setSoLuong(1);
        }

        ContentValues values = new ContentValues();
        values.put("maSanPham", gioHang.getMaSanPham());
        values.put("tenSanPham", gioHang.getTenSanPham());
        values.put("soLuong", gioHang.getSoLuong());
        values.put("gia", gioHang.getGia());

        long result = db.insert("TABLE_GIO_HANG", null, values);
        return result != -1;
    }

    // Lấy danh sách tất cả sản phẩm trong giỏ
    public ArrayList<GioHangDTO> getDSGioHang() {
        ArrayList<GioHangDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM TABLE_GIO_HANG", null);
        if (c.moveToFirst()) {
            do {
                GioHangDTO gioHang = new GioHangDTO(
                        c.getString(0), // maSanPham
                        c.getString(1), // tenSanPham
                        c.getInt(2),    // soLuong
                        c.getInt(3)     // gia
                );
                list.add(gioHang);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // Lấy một sản phẩm theo mã
    public GioHangDTO getSanPhamTrongGio(String maSanPham) {
        Cursor c = db.rawQuery("SELECT * FROM TABLE_GIO_HANG WHERE maSanPham = ?", new String[]{maSanPham});
        if (c.moveToFirst()) {
            GioHangDTO gioHang = new GioHangDTO(
                    c.getString(0),
                    c.getString(1),
                    c.getInt(2),
                    c.getInt(3)
            );
            c.close();
            return gioHang;
        }
        c.close();
        return null;
    }

    // Cập nhật số lượng sản phẩm
    public int capNhatSoLuong(String maSanPham, int soLuongMoi) {
        ContentValues values = new ContentValues();
        values.put("soLuong", soLuongMoi);
        return db.update("TABLE_GIO_HANG", values, "maSanPham = ?", new String[]{maSanPham});
    }

    // Xóa một sản phẩm khỏi giỏ
    public int xoaSanPham(String maSanPham) {
        return db.delete("TABLE_GIO_HANG", "maSanPham = ?", new String[]{maSanPham});
    }

    // Xóa toàn bộ giỏ hàng
    public void xoaToanBoGioHang() {
        db.execSQL("DELETE FROM TABLE_GIO_HANG");
    }

    // Tính tổng tiền giỏ hàng
    public int tinhTongTien() {
        int tong = 0;
        Cursor c = db.rawQuery("SELECT soLuong, gia FROM TABLE_GIO_HANG", null);
        if (c.moveToFirst()) {
            do {
                int soLuong = c.getInt(0);
                int gia = c.getInt(1);
                tong += soLuong * gia;
            } while (c.moveToNext());
        }
        c.close();
        return tong;
    }
}

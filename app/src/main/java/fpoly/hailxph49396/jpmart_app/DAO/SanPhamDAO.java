package fpoly.hailxph49396.jpmart_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Database.DbHelper;
import fpoly.hailxph49396.jpmart_app.DTO.SanPhamDTO;

public class SanPhamDAO {
    private SQLiteDatabase db;

    public SanPhamDAO(Context context) {
        DbHelper helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public ArrayList<SanPhamDTO> getAllSanPham() {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT MaSanPham, TenSanPham, Gia, SoLuong, DonViTinh, NgayNhap, MaDanhMuc FROM SAN_PHAM", null);
        if (c.moveToFirst()) {
            do {
                SanPhamDTO sp = new SanPhamDTO();
                sp.setMaSanPham(c.getString(0));
                sp.setTenSanPham(c.getString(1));
                sp.setGia(c.getInt(2));
                sp.setSoLuong(c.getInt(3));
                sp.setDonViTinh(c.getString(4));
                sp.setNgayNhap(c.getString(5));
                sp.setMaDanhMuc(c.getString(6));
                list.add(sp);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public boolean themSanPham(SanPhamDTO sp) {
        ContentValues values = new ContentValues();
        values.put("MaSanPham", sp.getMaSanPham());
        values.put("TenSanPham", sp.getTenSanPham());
        values.put("Gia", sp.getGia());
        values.put("SoLuong", sp.getSoLuong());
        values.put("DonViTinh", sp.getDonViTinh());
        values.put("NgayNhap", sp.getNgayNhap());
        values.put("MaDanhMuc", sp.getMaDanhMuc());
        long row = db.insert("SAN_PHAM", null, values);
        return row > 0;
    }

    public boolean suaSanPham(SanPhamDTO sp) {
        ContentValues values = new ContentValues();
        values.put("TenSanPham", sp.getTenSanPham());
        values.put("Gia", sp.getGia());
        values.put("SoLuong", sp.getSoLuong());
        values.put("DonViTinh", sp.getDonViTinh());
        values.put("NgayNhap", sp.getNgayNhap());
        values.put("MaDanhMuc", sp.getMaDanhMuc());
        int row = db.update("SAN_PHAM", values, "MaSanPham = ?", new String[]{sp.getMaSanPham()});
        return row > 0;
    }

    public boolean xoaSanPham(String maSanPham) {
        int row = db.delete("SAN_PHAM", "MaSanPham = ?", new String[]{maSanPham});
        return row > 0;
    }

    public ArrayList<SanPhamDTO> timKiemSanPham(String keyword) {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        if (keyword == null) keyword = "";
        String q = "SELECT MaSanPham, TenSanPham, Gia, SoLuong, DonViTinh, NgayNhap, MaDanhMuc FROM SAN_PHAM WHERE TenSanPham LIKE ? OR MaSanPham LIKE ?";
        Cursor c = db.rawQuery(q, new String[]{"%" + keyword + "%", "%" + keyword + "%"});
        if (c.moveToFirst()) {
            do {
                SanPhamDTO sp = new SanPhamDTO();
                sp.setMaSanPham(c.getString(0));
                sp.setTenSanPham(c.getString(1));
                sp.setGia(c.getInt(2));
                sp.setSoLuong(c.getInt(3));
                sp.setDonViTinh(c.getString(4));
                sp.setNgayNhap(c.getString(5));
                sp.setMaDanhMuc(c.getString(6));
                list.add(sp);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

//    public boolean themVaoGio(SanPhamDTO sp) {
//        ContentValues values = new ContentValues();
//        values.put("MaSanPham", sp.getMaSanPham());
//        values.put("TenSanPham", sp.getTenSanPham());
//        values.put("Gia", sp.getGia());
//        values.put("SoLuong", sp.getSoLuong());
//        values.put("DonViTinh", sp.getDonViTinh());
//        values.put("NgayNhap", sp.getNgayNhap());
//
//        long row = db.insert("GIO_HANG", null, values);
//        return row > 0;
//    }
public boolean themVaoGio(SanPhamDTO sp) {
    Cursor cursor = db.rawQuery("SELECT SoLuong FROM GIO_HANG WHERE MaSanPham = ?", new String[]{sp.getMaSanPham()});

    if (cursor.moveToFirst()) {
        int soLuongGioHang = cursor.getInt(0);
        cursor.close();

        // Kiểm tra nếu tăng 1 sẽ vượt quá tồn kho
        if (soLuongGioHang + 1 > sp.getSoLuong()) {
            // Không tăng nữa, có thể báo lỗi hoặc thông báo người dùng
            return false;
        }

        ContentValues values = new ContentValues();
        values.put("SoLuong", soLuongGioHang + 1);
        int row = db.update("GIO_HANG", values, "MaSanPham = ?", new String[]{sp.getMaSanPham()});
        return row > 0;

    } else {
        cursor.close();
        // Thêm sản phẩm mới vào giỏ với số lượng là 1
        ContentValues values = new ContentValues();
        values.put("MaSanPham", sp.getMaSanPham());
        values.put("TenSanPham", sp.getTenSanPham());
        values.put("Gia", sp.getGia());
        values.put("SoLuong", 1);  // Bắt đầu với 1
        values.put("DonViTinh", sp.getDonViTinh());
        values.put("NgayNhap", sp.getNgayNhap());
        long row = db.insert("GIO_HANG", null, values);
        return row > 0;
    }
}


}

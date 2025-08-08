package fpoly.hailxph49396.jpmart_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.DTO.SanPhamDTO;
import fpoly.hailxph49396.jpmart_app.Database.DbHelper;

public class SanPhamDAO {
    private SQLiteDatabase db;

    public SanPhamDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<SanPhamDTO> getAll() {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM SAN_PHAM", null);
        while (c.moveToNext()) {
            list.add(new SanPhamDTO(
                    c.getString(0),
                    c.getString(1),
                    c.getInt(2),
                    c.getInt(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6)
            ));
        }
        return list;
    }

    public long insert(SanPhamDTO sp) {
        ContentValues values = new ContentValues();
        values.put("MaSanPham", sp.getMaSanPham());
        values.put("TenSanPham", sp.getTenSanPham());
        values.put("Gia", sp.getGia());
        values.put("SoLuong", sp.getSoLuong());
        values.put("DonViTinh", sp.getDonViTinh());
        values.put("NgayNhap", sp.getNgayNhap());
        values.put("MaDanhMuc", sp.getMaDanhMuc());
        return db.insert("SAN_PHAM", null, values);
    }

    public int update(SanPhamDTO sp) {
        ContentValues values = new ContentValues();
        values.put("TenSanPham", sp.getTenSanPham());
        values.put("Gia", sp.getGia());
        values.put("SoLuong", sp.getSoLuong());
        values.put("DonViTinh", sp.getDonViTinh());
        values.put("NgayNhap", sp.getNgayNhap());
        values.put("MaDanhMuc", sp.getMaDanhMuc());
        return db.update("SAN_PHAM", values, "MaSanPham=?", new String[]{sp.getMaSanPham()});
    }

    public int delete(String ma) {
        return db.delete("SAN_PHAM", "MaSanPham=?", new String[]{ma});
    }
}


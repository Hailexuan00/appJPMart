package fpoly.hailxph49396.jpmart_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Database.DbHelper;
import fpoly.hailxph49396.jpmart_app.DTO.DanhMucDTO;

public class DanhMucDAO {
    private SQLiteDatabase db;

    public DanhMucDAO(Context context) {
        DbHelper helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public ArrayList<DanhMucDTO> getAll() {
        ArrayList<DanhMucDTO> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM DANH_MUC", null);
        if (c.moveToFirst()) {
            do {
                list.add(new DanhMucDTO(
                        c.getString(0),
                        c.getString(1)
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public boolean insert(DanhMucDTO dm) {
        ContentValues values = new ContentValues();
        values.put("MaDanhMuc", dm.getMaDanhMuc());
        values.put("TenDanhMuc", dm.getTenDanhMuc());
        long row = db.insert("DANH_MUC", null, values);
        return row > 0;
    }

    public boolean update(DanhMucDTO dm) {
        ContentValues values = new ContentValues();
        values.put("TenDanhMuc", dm.getTenDanhMuc());
        long row = db.update("DANH_MUC", values, "MaDanhMuc = ?", new String[]{dm.getMaDanhMuc()});
        return row > 0;
    }

    public boolean delete(String maDanhMuc) {
        long row = db.delete("DANH_MUC", "MaDanhMuc = ?", new String[]{maDanhMuc});
        return row > 0;
    }
}

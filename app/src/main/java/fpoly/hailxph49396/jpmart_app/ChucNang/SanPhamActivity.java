package fpoly.hailxph49396.jpmart_app.ChucNang;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Adapter.SanPhamAdapter;
import fpoly.hailxph49396.jpmart_app.DAO.DanhMucDAO;
import fpoly.hailxph49396.jpmart_app.DAO.SanPhamDAO;
import fpoly.hailxph49396.jpmart_app.DTO.DanhMucDTO;
import fpoly.hailxph49396.jpmart_app.DTO.SanPhamDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class SanPhamActivity extends AppCompatActivity {

    private RecyclerView recyclerSanPham;
    private EditText edtSearchSP;
    private ImageView imgBack, imgGioHang;
    private FloatingActionButton fabAdd;
    private SanPhamDAO sanPhamDAO;
    private SanPhamAdapter adapter;
    private ArrayList<SanPhamDTO> list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

        recyclerSanPham = findViewById(R.id.recyclerSanPham);
        edtSearchSP = findViewById(R.id.edtSearchSP);
        imgBack = findViewById(R.id.imgBack);
        imgGioHang = findViewById(R.id.imgGioHang);
        fabAdd = findViewById(R.id.fabAdd);

        sanPhamDAO = new SanPhamDAO(this);
        loadData();

        imgBack.setOnClickListener(v -> finish());
        // imgGioHang xử lý theo app của bạn
        imgGioHang.setOnClickListener(v ->
                startActivity(new Intent(this, GioHangActivity.class))
        );

        fabAdd.setOnClickListener(v -> showSanPhamDialog(null));

        edtSearchSP.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim();
                list = (ArrayList<SanPhamDTO>) sanPhamDAO.timKiemSanPham(keyword);
                adapter.setData(list);
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void loadData() {
        list = (ArrayList<SanPhamDTO>) sanPhamDAO.getAllSanPham();
        adapter = new SanPhamAdapter(this, list, new SanPhamAdapter.OnSanPhamClickListener() {
            @Override
            public void onAddToCart(SanPhamDTO sanPham) {
                // Thêm vào giỏ hàng: dùng GioHangDAO nếu cần
                boolean ok = sanPhamDAO.themVaoGio(sanPham);
                if (!ok) {
                    Toast.makeText(SanPhamActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(SanPhamActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEdit(SanPhamDTO sanPham) {
                showSanPhamDialog(sanPham);
            }

            @Override
            public void onDelete(SanPhamDTO sanPham) {
                new AlertDialog.Builder(SanPhamActivity.this)
                        .setTitle("Xóa sản phẩm")
                        .setMessage("Bạn có chắc muốn xóa " + sanPham.getTenSanPham() + " ?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            boolean ok = sanPhamDAO.xoaSanPham(sanPham.getMaSanPham());
                            if (ok) {
                                Toast.makeText(SanPhamActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                                loadData();
                            } else {
                                Toast.makeText(SanPhamActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        recyclerSanPham.setLayoutManager(new LinearLayoutManager(this));
        recyclerSanPham.setAdapter(adapter);
    }

    private void showSanPhamDialog(SanPhamDTO spEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_san_pham, null);

        Spinner spDanhMuc = view.findViewById(R.id.spDanhMuc);
        EditText edTenSP = view.findViewById(R.id.edTenSP);
        EditText edGia = view.findViewById(R.id.edGia);
        EditText edSoLuong = view.findViewById(R.id.edSoLuong);
        EditText edDonViTinh = view.findViewById(R.id.edDonViTinh);
        EditText edNgayNhap = view.findViewById(R.id.edNgayNhap);

        // Load danh mục
        DanhMucDAO danhMucDAO = new DanhMucDAO(this);
        ArrayList<DanhMucDTO> listDM = danhMucDAO.getAll();
        // Adapter cho Spinner hiển thị tên; ta override toString() hoặc tạo list tên.
        ArrayList<String> listDMNames = new ArrayList<>();
        for (DanhMucDTO d : listDM) listDMNames.add(d.getTenDanhMuc());
        ArrayAdapter<String> adapterDM = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listDMNames);
        spDanhMuc.setAdapter(adapterDM);

        if (spEdit != null) {
            edTenSP.setText(spEdit.getTenSanPham());
            edGia.setText(String.valueOf(spEdit.getGia()));
            edSoLuong.setText(String.valueOf(spEdit.getSoLuong()));
            edDonViTinh.setText(spEdit.getDonViTinh());
            edNgayNhap.setText(spEdit.getNgayNhap());

            // set danh mục chọn theo mã
            for (int i = 0; i < listDM.size(); i++) {
                if (listDM.get(i).getMaDanhMuc().equals(spEdit.getMaDanhMuc())) {
                    spDanhMuc.setSelection(i);
                    break;
                }
            }
        }

        builder.setView(view);
        builder.setTitle(spEdit == null ? "Thêm sản phẩm" : "Sửa sản phẩm");
        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String tenSP = edTenSP.getText().toString().trim();
            String giaStr = edGia.getText().toString().trim();
            String soLuongStr = edSoLuong.getText().toString().trim();
            String donViTinh = edDonViTinh.getText().toString().trim();
            String ngayNhap = edNgayNhap.getText().toString().trim();

            if (tenSP.isEmpty() || giaStr.isEmpty() || soLuongStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int gia = Integer.parseInt(giaStr);
            int soLuong = Integer.parseInt(soLuongStr);
            DanhMucDTO dm = listDM.get(spDanhMuc.getSelectedItemPosition());

            if (spEdit == null) {
                // Tạo mã đơn giản
                String maSP = "SP" + System.currentTimeMillis();
                SanPhamDTO sp = new SanPhamDTO(maSP, tenSP, gia, soLuong, donViTinh, ngayNhap, dm.getMaDanhMuc());
                boolean ok = sanPhamDAO.themSanPham(sp);
                if (ok) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                spEdit.setTenSanPham(tenSP);
                spEdit.setGia(gia);
                spEdit.setSoLuong(soLuong);
                spEdit.setDonViTinh(donViTinh);
                spEdit.setNgayNhap(ngayNhap);
                spEdit.setMaDanhMuc(dm.getMaDanhMuc());
                boolean ok = sanPhamDAO.suaSanPham(spEdit);
                if (ok) {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}

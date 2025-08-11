package fpoly.hailxph49396.jpmart_app.ChucNang;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Adapter.NhanVienAdapter;
import fpoly.hailxph49396.jpmart_app.DAO.NhanVienDAO;
import fpoly.hailxph49396.jpmart_app.DTO.NhanVienDTO;
import fpoly.hailxph49396.jpmart_app.MenuActivity;
import fpoly.hailxph49396.jpmart_app.R;

public class NhanVienActivity extends AppCompatActivity {

    RecyclerView rcvNhanVien;
    FloatingActionButton btnAdd;
    NhanVienAdapter adapter;
    ArrayList<NhanVienDTO> list = new ArrayList<>();
    NhanVienDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nhan_vien);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rcvNhanVien = findViewById(R.id.rcvNhanVien);
        btnAdd = findViewById(R.id.btnAdd);

        dao = new NhanVienDAO(this);
        loadData();

        btnAdd.setOnClickListener(v -> showDialog(null));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý nhân viên");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void loadData() {
        list = dao.getAll();
        adapter = new NhanVienAdapter(this, list, new NhanVienAdapter.OnNhanVienListener() {
            @Override
            public void onEdit(NhanVienDTO nv) {
                showDialog(nv);
            }

            @Override
            public void onDelete(NhanVienDTO nv) {
                new AlertDialog.Builder(NhanVienActivity.this)
                        .setTitle("Xóa nhân viên")
                        .setMessage("Bạn có chắc muốn xóa nhân viên này?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            int result = dao.delete(nv.getMaNhanVien());
                            if (result > 0) {
                                Toast.makeText(NhanVienActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                loadData();
                            } else {
                                Toast.makeText(NhanVienActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        rcvNhanVien.setLayoutManager(new LinearLayoutManager(this));
        rcvNhanVien.setAdapter(adapter);
    }

    private void showDialog(NhanVienDTO nv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_nhanvien, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edMa = view.findViewById(R.id.edMaNV);
        EditText edTen = view.findViewById(R.id.edTenNV);
        EditText edDiaChi = view.findViewById(R.id.edDiaChiNV);
        EditText edLuong = view.findViewById(R.id.edLuongNV);
        EditText edChucVu = view.findViewById(R.id.edChucVuNV);
        view.findViewById(R.id.btnHuyNV).setOnClickListener(v -> dialog.dismiss());

        // Nếu là sửa
        if (nv != null) {
            edMa.setText(nv.getMaNhanVien());
            edTen.setText(nv.getTenNhanVien());
            edDiaChi.setText(nv.getDiaChi());
            edLuong.setText(String.valueOf(nv.getLuong()));
            edChucVu.setText(String.valueOf(nv.getChucVu()));
        }

        view.findViewById(R.id.btnLuuNV).setOnClickListener(v -> {
            String ma = edMa.getText().toString().trim();
            String ten = edTen.getText().toString().trim();
            String diaChi = edDiaChi.getText().toString().trim();
            String luongStr = edLuong.getText().toString().trim();
            String chucVuStr = edChucVu.getText().toString().trim();

            if (ten.isEmpty() || diaChi.isEmpty() || luongStr.isEmpty() || chucVuStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double luong = Double.parseDouble(luongStr);
                int chucVu = Integer.parseInt(chucVuStr);

                NhanVienDTO newNV = new NhanVienDTO(
                        ma,
                        ten,
                        diaChi,
                        chucVu,
                        luong,
                        "123" // mật khẩu mặc định
                );

                long result;
                if (nv == null) {
                    result = dao.insert(newNV);
                } else {
                    result = dao.update(newNV);
                }

                if (result > 0) {
                    Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    loadData();
                } else {
                    Toast.makeText(this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Lương hoặc chức vụ không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}

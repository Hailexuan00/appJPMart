package fpoly.hailxph49396.jpmart_app.ChucNang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Adapter.KhachHangAdapter;
import fpoly.hailxph49396.jpmart_app.DAO.KhachHangDAO;
import fpoly.hailxph49396.jpmart_app.DTO.KhachHangDTO;
import fpoly.hailxph49396.jpmart_app.MenuActivity;
import fpoly.hailxph49396.jpmart_app.R;

public class KhachHangActivity extends AppCompatActivity {

    RecyclerView rvKhachHang;
    FloatingActionButton fabAdd;
    KhachHangDAO khachHangDAO;
    KhachHangAdapter adapter;
    ArrayList<KhachHangDTO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        // Xử lý EdgeToEdge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvKhachHang = findViewById(R.id.rvKhachHang);
        fabAdd = findViewById(R.id.fabAdd);

        khachHangDAO = new KhachHangDAO(this);
        list = khachHangDAO.getAll();
        adapter = new KhachHangAdapter(this, list);
        rvKhachHang.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> openDialog(null));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý khách hàng");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Mở dialog thêm hoặc sửa khách hàng
     * @param kh nếu khác null thì là sửa, null là thêm mới
     */
    public void openDialog(KhachHangDTO kh) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(kh == null ? "Thêm khách hàng" : "Sửa khách hàng");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_khach_hang, null);
        builder.setView(view);

        EditText edtMaKH = view.findViewById(R.id.edtMaKH);
        EditText edtTenKH = view.findViewById(R.id.edtTenKH);
        EditText edtDiaChi = view.findViewById(R.id.edtDiaChi);
        EditText edtSdt = view.findViewById(R.id.edtSdt);
        EditText edtEmail = view.findViewById(R.id.edtEmail);

        if (kh != null) {
            edtMaKH.setText(kh.getMaKhachHang());
            edtMaKH.setEnabled(false); // Khóa mã khách hàng khi sửa
            edtTenKH.setText(kh.getTenKhachHang());
            edtDiaChi.setText(kh.getDiaChi());
            edtSdt.setText(kh.getSoDienThoai());
            edtEmail.setText(kh.getEmail());
        }

        builder.setPositiveButton(kh == null ? "Thêm" : "Cập nhật", null);
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        // Override để prevent đóng dialog khi input chưa hợp lệ
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String maKH = edtMaKH.getText().toString().trim();
            String tenKH = edtTenKH.getText().toString().trim();
            String diaChi = edtDiaChi.getText().toString().trim();
            String sdt = edtSdt.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            // Kiểm tra dữ liệu hợp lệ
            if (maKH.isEmpty()) {
                edtMaKH.setError("Không được để trống mã khách hàng");
                return;
            }
            if (tenKH.isEmpty()) {
                edtTenKH.setError("Không được để trống tên khách hàng");
                return;
            }
            if (diaChi.isEmpty()) {
                edtDiaChi.setError("Không được để trống địa chỉ");
                return;
            }
            if (sdt.isEmpty()) {
                edtSdt.setError("Không được để trống số điện thoại");
                return;
            }
            if (email.isEmpty()) {
                edtEmail.setError("Không được để trống email");
                return;
            }

            KhachHangDTO newKh = new KhachHangDTO(maKH, tenKH, diaChi, sdt, email);

            boolean success;
            if (kh == null) {
                // Thêm mới
                success = khachHangDAO.insert(newKh);
                if (success) {
                    list.add(newKh);
                    adapter.notifyItemInserted(list.size() - 1);
                    Toast.makeText(this, "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Thêm khách hàng thất bại. Mã khách hàng đã tồn tại?", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Cập nhật
                success = khachHangDAO.update(newKh);
                if (success) {
                    int index = list.indexOf(kh);
                    list.set(index, newKh);
                    adapter.notifyItemChanged(index);
                    Toast.makeText(this, "Cập nhật khách hàng thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Cập nhật khách hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

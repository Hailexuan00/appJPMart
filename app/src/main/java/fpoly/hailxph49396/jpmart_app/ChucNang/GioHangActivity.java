package fpoly.hailxph49396.jpmart_app.ChucNang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import fpoly.hailxph49396.jpmart_app.Adapter.GioHangAdapter;
import fpoly.hailxph49396.jpmart_app.DAO.GioHangDAO;
import fpoly.hailxph49396.jpmart_app.DTO.GioHangDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class GioHangActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTongTien;
    private Button btnThanhToan;

    private GioHangDAO gioHangDAO;
    private ArrayList<GioHangDTO> listGioHang;
    private GioHangAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        // Ánh xạ view
        recyclerView = findViewById(R.id.rcvGioHang);
        tvTongTien = findViewById(R.id.tvTongTien);
        btnThanhToan = findViewById(R.id.btnThanhToan);

        // Khởi tạo DAO và lấy dữ liệu
        gioHangDAO = new GioHangDAO(this);
        listGioHang = gioHangDAO.getAll();

        // Thiết lập RecyclerView với LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo adapter với constructor đúng
        adapter = new GioHangAdapter(this, listGioHang, new GioHangAdapter.OnCartChangeListener() {
            @Override
            public void onCartUpdated() {
                capNhatTongTien();
            }
        });
        recyclerView.setAdapter(adapter);

        // Cập nhật tổng tiền ban đầu
        capNhatTongTien();

        // Xử lý nút thanh toán
        btnThanhToan.setOnClickListener(v -> {
            if (listGioHang == null || listGioHang.isEmpty()) {
                Toast.makeText(GioHangActivity.this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GioHangActivity.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                // Xóa giỏ hàng sau khi thanh toán
                gioHangDAO.clearGioHang();
                listGioHang.clear();
                adapter.notifyDataSetChanged();
                capNhatTongTien();
            }
        });
    }

    // Hàm tính và cập nhật tổng tiền
    private void capNhatTongTien() {
        int tong = 0;
        if (listGioHang != null) {
            for (GioHangDTO item : listGioHang) {
                tong += item.getGia() * item.getSoLuong();
            }
        }
        // Định dạng VNĐ
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvTongTien.setText("Tổng: " + format.format(tong));
    }
}

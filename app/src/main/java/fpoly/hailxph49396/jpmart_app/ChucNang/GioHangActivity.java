package fpoly.hailxph49396.jpmart_app.ChucNang;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    private Toolbar toolbar;
    private TextView tvTongTien;
    private RecyclerView rcvGioHang;
    private Button btnThanhToan;

    private GioHangDAO gioHangDAO;
    private GioHangAdapter adapter;
    private ArrayList<GioHangDTO> listGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang); // tên file XML bạn gửi

        // Ánh xạ view
        toolbar = findViewById(R.id.toolbar);
        tvTongTien = findViewById(R.id.tvTongTien);
        rcvGioHang = findViewById(R.id.rcvGioHang);
        btnThanhToan = findViewById(R.id.btnThanhToan);

        // Setup Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Khởi tạo DAO và load dữ liệu
        gioHangDAO = new GioHangDAO(this);
        loadData();

        // Sự kiện thanh toán
        btnThanhToan.setOnClickListener(v -> {
            if (listGioHang.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            } else {
                boolean check = gioHangDAO.thanhToan();
                if (check) {
                    Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadData() {
        listGioHang = gioHangDAO.getAll();
        adapter = new GioHangAdapter(this, listGioHang, gioHangDAO, this::updateTongTien);
        rcvGioHang.setLayoutManager(new LinearLayoutManager(this));
        rcvGioHang.setAdapter(adapter);
        updateTongTien();
    }

    private void updateTongTien() {
        double tongTien = 0;
        for (GioHangDTO gh : listGioHang) {
            tongTien += gh.getSoLuong() * gh.getGia();
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvTongTien.setText("Tổng tiền: " + format.format(tongTien));
    }

    // Xử lý nút back trên Toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

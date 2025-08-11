package fpoly.hailxph49396.jpmart_app.ChucNang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import fpoly.hailxph49396.jpmart_app.Adapter.GioHangAdapter;
import fpoly.hailxph49396.jpmart_app.DAO.GioHangDAO;
import fpoly.hailxph49396.jpmart_app.DAO.KhachHangDAO;
import fpoly.hailxph49396.jpmart_app.DTO.GioHangDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class GioHangActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvTongTien;
    private RecyclerView rcvGioHang;
    private Button btnThanhToan;
    private Spinner spinnerKhachHang;

    private GioHangDAO gioHangDAO;
    private KhachHangDAO khachHangDAO;
    private GioHangAdapter adapter;
    private ArrayList<GioHangDTO> listGioHang;
    private ArrayList<String> listKH;

    private String maNhanVien; // lấy từ đăng nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        // Ánh xạ
        toolbar = findViewById(R.id.toolbar);
        tvTongTien = findViewById(R.id.tvTongTien);
        rcvGioHang = findViewById(R.id.rcvGioHang);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        spinnerKhachHang = findViewById(R.id.spinnerLuaChon);

        // Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Lấy mã nhân viên từ SharedPreferences (đã lưu khi đăng nhập)
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        maNhanVien = pref.getString("MaNhanVien", "");

        gioHangDAO = new GioHangDAO(this);
        khachHangDAO = new KhachHangDAO(this);

        loadSpinnerKhachHang();
        loadData();

//        btnThanhToan.setOnClickListener(v -> {
//            if (listGioHang.isEmpty()) {
//                Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            String khSelected = listKH.get(spinnerKhachHang.getSelectedItemPosition()).split(" - ")[0]; // Lấy mã KH
//
//            boolean check = gioHangDAO.thanhToan(khSelected, maNhanVien);
//            if (check) {
//                Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
//                loadData();
//            } else {
//                Toast.makeText(this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
//            }
//        });

        btnThanhToan.setOnClickListener(v -> {
            if (listGioHang.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            String khInfo = listKH.get(spinnerKhachHang.getSelectedItemPosition());
            String maKH = khInfo.split(" - ")[0];
            String tenKH = khInfo.split(" - ")[1];
            String sdtKH = khInfo.split(" - ")[2];

            boolean check = gioHangDAO.thanhToan(maKH, maNhanVien); // dùng biến maNhanVien ở trên

            if (check) {

                String tenNV = getIntent().getStringExtra("TenNhanVien");

                String thongTinHD = "Hóa đơn thanh toán thành công!\n\n" +
                        "Khách hàng: " + tenKH + " (" + sdtKH + ")\n" +
                        "Nhân viên: " + tenNV +  "\n" +
                        "Tổng tiền: " + tvTongTien.getText();

                new AlertDialog.Builder(this)
                        .setTitle("Thông tin hóa đơn")
                        .setMessage(thongTinHD)
                        .setPositiveButton("OK", (dialog, which) -> loadData())
                        .show();
            } else {
                Toast.makeText(this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadSpinnerKhachHang() {
        listKH = khachHangDAO.getAllKhachHangForSpinner(); // trả về dạng "MaKH - TênKH - SDT"
        ArrayAdapter<String> adapterKH = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listKH);
        adapterKH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKhachHang.setAdapter(adapterKH);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

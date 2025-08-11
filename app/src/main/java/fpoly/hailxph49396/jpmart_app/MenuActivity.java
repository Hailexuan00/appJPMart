package fpoly.hailxph49396.jpmart_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Adapter.MenuAdapter;
import fpoly.hailxph49396.jpmart_app.ChucNang.DanhMucActivity;
import fpoly.hailxph49396.jpmart_app.ChucNang.DoiMatKhauActivity;
import fpoly.hailxph49396.jpmart_app.ChucNang.GioHangActivity;
import fpoly.hailxph49396.jpmart_app.ChucNang.HoaDonActivity;
import fpoly.hailxph49396.jpmart_app.ChucNang.KhachHangActivity;
import fpoly.hailxph49396.jpmart_app.ChucNang.NhanVienActivity;
import fpoly.hailxph49396.jpmart_app.ChucNang.SanPhamActivity;
import fpoly.hailxph49396.jpmart_app.ChucNang.ThongKeActivity;
import fpoly.hailxph49396.jpmart_app.DAO.TaikhoanDAO;
import fpoly.hailxph49396.jpmart_app.DTO.MenuDTO;
import fpoly.hailxph49396.jpmart_app.DTO.NhanVienDTO;

public class MenuActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private GridView gridThongKe, gridQuanLy, gridNguoiDung;
    private TextView tvWelcome;
    private ActionBarDrawerToggle toggle;
    private TaikhoanDAO dao;

    private ArrayList<MenuDTO> listThongKe = new ArrayList<>();
    private ArrayList<MenuDTO> listQuanLy = new ArrayList<>();
    private ArrayList<MenuDTO> listNguoiDung = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Ánh xạ
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nvView);
        tvWelcome = findViewById(R.id.tvWelcome);
        gridThongKe = findViewById(R.id.gridThongKe);
        gridQuanLy = findViewById(R.id.gridQuanLy);
        gridNguoiDung = findViewById(R.id.gridNguoiDung);


        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Khởi tạo DAO
        dao = new TaikhoanDAO(this);

        // Lấy mã nhân viên từ Intent
        String maNV = getIntent().getStringExtra("MaNhanVien");
        if (maNV == null) {
            Log.e("MenuActivity", "Không tìm thấy mã nhân viên trong Intent");
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));

            return;
        }

        // Lấy thông tin nhân viên
        NhanVienDTO user = dao.getNhanVienById(maNV);
        if (user == null) {
            Log.e("MenuActivity", "User không tồn tại với MaNhanVien = " + maNV);
            Toast.makeText(this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));

            return;
        }

        // Hiển thị tên người dùng
        tvWelcome.setText("Chào, " + user.getTenNhanVien() + " (" + maNV + ")");

        // Phân quyền hiển thị menu theo ChucVu
        if (user.getChucVu() == 1) { // Quản lý
            listThongKe.add(new MenuDTO("Thống kê", R.drawable.thongke));
            listThongKe.add(new MenuDTO("Top sản phẩm", R.drawable.dinosaur));
            listThongKe.add(new MenuDTO("Top khách hàng", R.drawable.dinosaur));

            listQuanLy.add(new MenuDTO("Sản phẩm", R.drawable.sanpham));
            listQuanLy.add(new MenuDTO("Khách hàng", R.drawable.khach));
            listQuanLy.add(new MenuDTO("Hóa đơn", R.drawable.hoadon));
            listQuanLy.add(new MenuDTO("Danh mục", R.drawable.danhmuc));
            listQuanLy.add(new MenuDTO("Nhân viên", R.drawable.nv));
        } else { // Nhân viên
            listQuanLy.add(new MenuDTO("Sản phẩm", R.drawable.sanpham));
            listQuanLy.add(new MenuDTO("Khách hàng", R.drawable.khach));
            listQuanLy.add(new MenuDTO("Hóa đơn", R.drawable.hoadon));
            listQuanLy.add(new MenuDTO("Danh mục", R.drawable.danhmuc));
        }

        // Menu dùng chung
        listNguoiDung.add(new MenuDTO("Đổi mật khẩu", R.drawable.pass));
        listNguoiDung.add(new MenuDTO("Đăng xuất", R.drawable.ic_logout));

        // Set adapter
        gridThongKe.setAdapter(new MenuAdapter(this, listThongKe));
        gridQuanLy.setAdapter(new MenuAdapter(this, listQuanLy));
        gridNguoiDung.setAdapter(new MenuAdapter(this, listNguoiDung));

        // Xử lý click GridView
        gridThongKe.setOnItemClickListener((parent, view, position, id) -> {
            String title = listThongKe.get(position).getTitle();
            Toast.makeText(this, "Mở " + title, Toast.LENGTH_SHORT).show();
            switch (title) {
                case "Thống kê":
                    startActivity(new Intent(MenuActivity.this, ThongKeActivity.class));
                    break;
                case "Top sản phẩm":
//                    startActivity(new Intent(MenuActivity.this, TopSanPhamActivity.class));
                    break;
                case "Top khách hàng":
//                    startActivity(new Intent(MenuActivity.this, TopKhachHangActivity.class));
                    break;
                default:
                    Toast.makeText(this, "Mở " + title, Toast.LENGTH_SHORT).show();
            }
        });

        gridQuanLy.setOnItemClickListener((parent, view, position, id) -> {
            String title = listQuanLy.get(position).getTitle();
            switch (title) {
                case "Sản phẩm":
//                    startActivity(new Intent(MenuActivity.this, SanPhamActivity.class));
                    // Trong MenuActivity khi mở GioHangActivity
                    Intent intent = new Intent(MenuActivity.this, SanPhamActivity.class);
                    intent.putExtra("MaNhanVien", getIntent().getStringExtra("MaNhanVien"));
                    intent.putExtra("TenNhanVien", getIntent().getStringExtra("TenNhanVien"));
                    startActivity(intent);
                    break;
                case "Khách hàng":
                    startActivity(new Intent(MenuActivity.this, KhachHangActivity.class));

                    break;
                case "Hóa đơn":
                    startActivity(new Intent(MenuActivity.this, HoaDonActivity.class));

                    break;
                case "Danh mục":
                    startActivity(new Intent(MenuActivity.this, DanhMucActivity.class));

                    break;
                case "Nhân viên":
                    startActivity(new Intent(MenuActivity.this, NhanVienActivity.class));

                    break;
                default:
                    Toast.makeText(this, "Mở " + title, Toast.LENGTH_SHORT).show();
            }
        });

        gridNguoiDung.setOnItemClickListener((parent, view, position, id) -> {
            String title = listNguoiDung.get(position).getTitle();
            if (title.equals("Đăng xuất")) {
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));

            } else if (title.equals("Đổi mật khẩu")) {
                startActivity(new Intent(MenuActivity.this, DoiMatKhauActivity.class));
            } else {
                Toast.makeText(this, "Mở " + title, Toast.LENGTH_SHORT).show();
            }
        });

        // Navigation Drawer (nếu dùng)
        navView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}

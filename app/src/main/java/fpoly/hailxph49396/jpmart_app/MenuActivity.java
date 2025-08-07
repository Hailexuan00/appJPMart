package fpoly.hailxph49396.jpmart_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Adapter.MenuAdapter;
import fpoly.hailxph49396.jpmart_app.ChucNang.DoiMatKhauActivity;
import fpoly.hailxph49396.jpmart_app.ChucNang.HoaDonActivity;
import fpoly.hailxph49396.jpmart_app.ChucNang.NhanVienActivity;
import fpoly.hailxph49396.jpmart_app.ChucNang.SanPhamActivity;
import fpoly.hailxph49396.jpmart_app.DAO.TaikhoanDAO;
import fpoly.hailxph49396.jpmart_app.DTO.MenuDTO;
import fpoly.hailxph49396.jpmart_app.DTO.TaikhoanDTO;

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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        // Khởi tạo DAO
        dao = new TaikhoanDAO(this);

        // Lấy username từ Intent
        String username = getIntent().getStringExtra("username");
        if (username == null) {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Lấy thông tin người dùng từ DAO
        TaikhoanDTO user = dao.getUserByUsername(username);
        if (user == null) {
            Log.e("MenuActivity", "User không tồn tại với username = " + username);
            Toast.makeText(this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Hiển thị tên người dùng
        tvWelcome.setText("Chào, " + user.getTen() + " (" + username + ")");

        // Phân quyền hiển thị menu
        if ("admin".equals(username)) {
            listThongKe.add(new MenuDTO("Thống kê", R.drawable.dinosaur));
            listThongKe.add(new MenuDTO("Top sản phẩm", R.drawable.dinosaur));
            listThongKe.add(new MenuDTO("Top khách hàng", R.drawable.dinosaur));

            listQuanLy.add(new MenuDTO("Sản phẩm", R.drawable.dinosaur));
            listQuanLy.add(new MenuDTO("Khách hàng", R.drawable.dinosaur));
            listQuanLy.add(new MenuDTO("Hóa đơn", R.drawable.dinosaur));
            listQuanLy.add(new MenuDTO("Danh mục", R.drawable.dinosaur));
            listQuanLy.add(new MenuDTO("Nhân viên", R.drawable.nv));
        } else {
            listQuanLy.add(new MenuDTO("Sản phẩm", R.drawable.dinosaur));
            listQuanLy.add(new MenuDTO("Khách hàng", R.drawable.dinosaur));
            listQuanLy.add(new MenuDTO("Hóa đơn", R.drawable.dinosaur));
            listQuanLy.add(new MenuDTO("Danh mục", R.drawable.dinosaur));
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
        });

        gridQuanLy.setOnItemClickListener((parent, view, position, id) -> {
            String title = listQuanLy.get(position).getTitle();
            if (title.equals("Sản phẩm")) {
//                startActivity(new Intent(MenuActivity.this, SanPhamActivity.class));
            } else if (title.equals("Khách hàng")) {
//                startActivity(new Intent(MenuActivity.this, KhachHangActivity.class));
            } else if (title.equals("Hóa đơn")) {
//                startActivity(new Intent(MenuActivity.this, HoaDonActivity.class));
            } else if (title.equals("Danh mục")) {
//                startActivity(new Intent(MenuActivity.this, DanhMucActivity.class));
            } else if (title.equals("Nhân viên")) {
                startActivity(new Intent(MenuActivity.this, NhanVienActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Mở " + title, Toast.LENGTH_SHORT).show();
            }

        });

        gridNguoiDung.setOnItemClickListener((parent, view, position, id) -> {
            String title = listNguoiDung.get(position).getTitle();
            if (title.equals("Đăng xuất")) {
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                finish();
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

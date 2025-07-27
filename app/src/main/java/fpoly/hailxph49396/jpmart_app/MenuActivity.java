package fpoly.hailxph49396.jpmart_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import fpoly.hailxph49396.jpmart_app.DAO.TaikhoanDAO;
import fpoly.hailxph49396.jpmart_app.DTO.MenuDTO;
import fpoly.hailxph49396.jpmart_app.DTO.TaikhoanDTO;

public class MenuActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private GridView gridView;
    private TextView tvWelcome;
    private ActionBarDrawerToggle toggle;
    private ArrayList<MenuDTO> menuList = new ArrayList<>();
    private MenuAdapter adapter;
    private TaikhoanDAO dao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Ánh xạ view
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nvView);
        gridView = findViewById(R.id.gridView);
        tvWelcome = findViewById(R.id.tvWelcome);

        // Ánh xạ toolbar và set SupportActionBar đầu tiên
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Tạo toggle với toolbar để tự động quản lý hamburger icon
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Lấy action bar để set nút home (back/hamburger)
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        // DAO
        dao = new TaikhoanDAO(this);

        // Nhận username từ Intent
        String username = getIntent().getStringExtra("username");
        TaikhoanDTO user = dao.getUserByUsername(username);

        // Hiển thị tên người dùng
        if (user != null) {
            tvWelcome.setText("Chào, " + user.getTen() + " (" + username + ")");
        } else {
            Toast.makeText(this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
        }

        // Hiển thị menu theo vai trò
        if ("admin".equals(username)) {
            menuList.add(new MenuDTO("Quản lý tài khoản", R.drawable.dinosaur));
            menuList.add(new MenuDTO("Quản lý sản phẩm", R.drawable.dinosaur));
            menuList.add(new MenuDTO("Thống kê", R.drawable.dinosaur));
            menuList.add(new MenuDTO("Đăng xuất", R.drawable.dinosaur));
        } else {
            menuList.add(new MenuDTO("Xem sản phẩm", R.drawable.dinosaur));
            menuList.add(new MenuDTO("Thông tin cá nhân", R.drawable.dinosaur));
            menuList.add(new MenuDTO("Đăng xuất", R.drawable.dinosaur));
        }

        // Adapter
        adapter = new MenuAdapter(this, menuList);
        gridView.setAdapter(adapter);

        // Xử lý click item menu
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            String title = menuList.get(position).getTitle();
            switch (title) {
                case "Đăng xuất":
                    startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                    finish();
                    break;
                case "Quản lý tài khoản":
                    Toast.makeText(this, "Mở màn hình Quản lý tài khoản", Toast.LENGTH_SHORT).show();
                    break;
                case "Xem sản phẩm":
                case "Quản lý sản phẩm":
                    Toast.makeText(this, "Mở màn hình Sản phẩm", Toast.LENGTH_SHORT).show();
                    break;
                case "Thống kê":
                    Toast.makeText(this, "Mở màn hình Thống kê", Toast.LENGTH_SHORT).show();
                    break;
                case "Thông tin cá nhân":
                    Toast.makeText(this, "Mở màn hình thông tin cá nhân", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        // Xử lý click item NavigationView nếu cần
        navView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
            return true;
        });
    }

    // Xử lý toggle mở/đóng drawer khi bấm nút trên toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}

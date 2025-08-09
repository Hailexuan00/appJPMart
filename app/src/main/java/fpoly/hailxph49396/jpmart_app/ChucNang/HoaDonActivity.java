package fpoly.hailxph49396.jpmart_app.ChucNang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Adapter.HoaDonAdapter;
import fpoly.hailxph49396.jpmart_app.DAO.HoaDonDAO;
import fpoly.hailxph49396.jpmart_app.DTO.HoaDonDTO;
import fpoly.hailxph49396.jpmart_app.MenuActivity;
import fpoly.hailxph49396.jpmart_app.R;

public class HoaDonActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HoaDonDAO hoaDonDAO;
    HoaDonAdapter adapter;
    ArrayList<HoaDonDTO> list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        recyclerView = findViewById(R.id.recyclerHoaDon);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hoaDonDAO = new HoaDonDAO(this);
        list = hoaDonDAO.getAllHoaDon();
        adapter = new HoaDonAdapter(this, list);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý Hoa don");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        });
    }
}

package fpoly.hailxph49396.jpmart_app.ChucNang;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Adapter.DanhMucAdapter;
import fpoly.hailxph49396.jpmart_app.DAO.DanhMucDAO;
import fpoly.hailxph49396.jpmart_app.DTO.DanhMucDTO;
import fpoly.hailxph49396.jpmart_app.MenuActivity;
import fpoly.hailxph49396.jpmart_app.R;

public class DanhMucActivity extends AppCompatActivity {
    RecyclerView rcv;
    FloatingActionButton fab;
    DanhMucDAO dao;
    ArrayList<DanhMucDTO> list;
    DanhMucAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);

        rcv = findViewById(R.id.rcvDM);
        fab = findViewById(R.id.fabAddDM);
        dao = new DanhMucDAO(this);

        loadData();

        fab.setOnClickListener(v -> openDialogAdd());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý danh mục");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
//            startActivity(new Intent(this, MenuActivity.class));
            finish();
        });
    }

    private void loadData() {
        list = dao.getAll();
        adapter = new DanhMucAdapter(this, list, dao);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(adapter);
    }

    private void openDialogAdd() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_danh_muc, null);
        EditText edMa = dialogView.findViewById(R.id.edMaDM);
        EditText edTen = dialogView.findViewById(R.id.edTenDM);

        new AlertDialog.Builder(this)
                .setTitle("Thêm Danh Mục")
                .setView(dialogView)
                .setPositiveButton("Thêm", (d, w) -> {
                    String ma = edMa.getText().toString().trim();
                    String ten = edTen.getText().toString().trim();
                    if (ma.isEmpty() || ten.isEmpty()) {
                        Toast.makeText(this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DanhMucDTO dm = new DanhMucDTO(ma, ten);
                    if (dao.insert(dm)) {
                        list.add(dm);
                        adapter.notifyItemInserted(list.size() - 1);
                        Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Thêm thất bại (mã trùng?)", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

}

package fpoly.hailxph49396.jpmart_app.ChucNang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.Adapter.GioHangAdapter;
import fpoly.hailxph49396.jpmart_app.DAO.GioHangDAO;
import fpoly.hailxph49396.jpmart_app.DTO.GioHangDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class GioHangActivity extends AppCompatActivity {
    private RecyclerView rcvGioHang;
    private TextView tvTongTien;
    private Button btnThanhToan;
    private GioHangAdapter adapter;
    private GioHangDAO gioHangDAO;
    private ArrayList<GioHangDTO> listGioHang;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        rcvGioHang = findViewById(R.id.rcvGioHang);
        tvTongTien = findViewById(R.id.tvTongTien);
        btnThanhToan = findViewById(R.id.btnThanhToan);

        gioHangDAO = new GioHangDAO(this);
        listGioHang = gioHangDAO.getDSGioHang();

        adapter = new GioHangAdapter(this, listGioHang);
        rcvGioHang.setLayoutManager(new LinearLayoutManager(this));
        rcvGioHang.setAdapter(adapter);

        capNhatTongTien();

        btnThanhToan.setOnClickListener(v -> {
            gioHangDAO.xoaToanBoGioHang();
            listGioHang.clear();
            adapter.notifyDataSetChanged();
            capNhatTongTien();
            Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
        });
    }

    private void capNhatTongTien() {
        int tongTien = 0;
        for (GioHangDTO item : listGioHang) {
            tongTien += item.getThanhTien();
        }
        tvTongTien.setText("Tổng tiền: " + tongTien + " đ");
    }
}

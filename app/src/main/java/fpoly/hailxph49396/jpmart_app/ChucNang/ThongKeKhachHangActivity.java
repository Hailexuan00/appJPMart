package fpoly.hailxph49396.jpmart_app.ChucNang;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import fpoly.hailxph49396.jpmart_app.Adapter.ThongKeKhachHangAdapter;
import fpoly.hailxph49396.jpmart_app.DAO.KhachHangDAO;
import fpoly.hailxph49396.jpmart_app.R;

public class ThongKeKhachHangActivity extends AppCompatActivity {
    EditText edtTuNgay, edtDenNgay, edtSoLuong;
    Button btnThongKe;
    RecyclerView rcv;
    KhachHangDAO khachHangDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_khach_hang);

        edtTuNgay = findViewById(R.id.edtTuNgay);
        edtDenNgay = findViewById(R.id.edtDenNgay);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        btnThongKe = findViewById(R.id.btnThongKe);
        rcv = findViewById(R.id.rcvThongKeKhachHang);

        khachHangDAO = new KhachHangDAO(this);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        // mở DatePicker khi nhấn vào EditText
        edtTuNgay.setOnClickListener(v -> showDatePickerDialog(edtTuNgay));
        edtDenNgay.setOnClickListener(v -> showDatePickerDialog(edtDenNgay));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thống kê khách hàng");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        btnThongKe.setOnClickListener(v -> {
            String tuNgay = edtTuNgay.getText().toString().trim();
            String denNgay = edtDenNgay.getText().toString().trim();
            String soLuongStr = edtSoLuong.getText().toString().trim();

            if (tuNgay.isEmpty() || denNgay.isEmpty() || soLuongStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int soLuong;
            try {
                soLuong = Integer.parseInt(soLuongStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<HashMap<String, Object>> list =
                    khachHangDAO.getTopKhachHang(tuNgay, denNgay, soLuong);

            ThongKeKhachHangAdapter adapter = new ThongKeKhachHangAdapter(this, list);
            rcv.setAdapter(adapter);
        });
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    String dateStr = sdf.format(calendar.getTime());
                    editText.setText(dateStr);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }
}

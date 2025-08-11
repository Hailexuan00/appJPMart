package fpoly.hailxph49396.jpmart_app.ChucNang;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fpoly.hailxph49396.jpmart_app.Database.DbHelper;
import fpoly.hailxph49396.jpmart_app.R;

public class ThongKeActivity extends AppCompatActivity {

    EditText edtTuNgay, edtDenNgay;
    Button btnThongKe;
    TextView tvDoanhThu;
    DbHelper dbHelper;


    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        edtTuNgay = findViewById(R.id.edtTuNgay);
        edtDenNgay = findViewById(R.id.edtDenNgay);
        btnThongKe = findViewById(R.id.btnThongKeDoanhThu);
        tvDoanhThu = findViewById(R.id.tvDoanhThu);

        dbHelper = new DbHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thống kê Doanh Thu");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        edtTuNgay.setOnClickListener(v -> showDatePickerDialog(edtTuNgay));
        edtDenNgay.setOnClickListener(v -> showDatePickerDialog(edtDenNgay));

        btnThongKe.setOnClickListener(v -> thongKeDoanhThu());
    }

    private void showDatePickerDialog(EditText editText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    calendar.set(year1, month1, dayOfMonth);
                    editText.setText(sdf.format(calendar.getTime()));
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void thongKeDoanhThu() {
        String tuNgay = edtTuNgay.getText().toString().trim();
        String denNgay = edtDenNgay.getText().toString().trim();

        if (tuNgay.isEmpty() || denNgay.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn đầy đủ ngày bắt đầu và kết thúc", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT SUM(TongTien) FROM HOA_DON " +
                "WHERE NgayLap BETWEEN ? AND ?";

        Cursor cursor = db.rawQuery(sql, new String[]{tuNgay, denNgay});
        if (cursor.moveToFirst()) {
            int doanhThu = cursor.getInt(0);
            tvDoanhThu.setText("Doanh thu: " + doanhThu + " VND");
        } else {
            tvDoanhThu.setText("Doanh thu: 0 VND");
        }
        cursor.close();
    }
}

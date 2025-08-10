package fpoly.hailxph49396.jpmart_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.hailxph49396.jpmart_app.DAO.TaikhoanDAO;
import fpoly.hailxph49396.jpmart_app.DTO.NhanVienDTO;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUser, edtPass;
    private CheckBox chkRemember;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;
    private TaikhoanDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edtMaNV);
        edtPass = findViewById(R.id.edtMatKhau);
        chkRemember = findViewById(R.id.chkRemember);
        btnLogin = findViewById(R.id.btnLogin);

        dao = new TaikhoanDAO(this);
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        loadSavedLogin();

        btnLogin.setOnClickListener(v -> {
            String user = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            NhanVienDTO nv = dao.getNhanVienById(user);
            if (nv != null && pass.equals(nv.getMatKhau())) {
                if (chkRemember.isChecked()) {
                    sharedPreferences.edit()
                            .putString("username", user)
                            .putString("password", pass)
                            .putBoolean("remember", true)
                            .apply();
                } else {
                    sharedPreferences.edit().clear().apply();
                }

                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                intent.putExtra("MaNhanVien", nv.getMaNhanVien());
                intent.putExtra("TenNhanVien", nv.getTenNhanVien());
                intent.putExtra("ChucVu", nv.getChucVu());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Sai mã nhân viên hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSavedLogin() {
        if (sharedPreferences.getBoolean("remember", false)) {
            edtUser.setText(sharedPreferences.getString("username", ""));
            edtPass.setText(sharedPreferences.getString("password", ""));
            chkRemember.setChecked(true);
        }
    }
}

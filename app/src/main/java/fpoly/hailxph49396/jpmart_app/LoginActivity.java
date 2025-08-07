package fpoly.hailxph49396.jpmart_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.hailxph49396.jpmart_app.DAO.TaikhoanDAO;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    CheckBox chkRemember;
    Button btnLogin;
    SharedPreferences sharedPreferences;
    TaikhoanDAO dao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Ánh xạ view
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        chkRemember = findViewById(R.id.chkRemember);
        btnLogin = findViewById(R.id.btnLogin);

        // Khởi tạo DAO và SharedPreferences
        dao = new TaikhoanDAO(this);
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        // Load dữ liệu nếu trước đó đã chọn "Ghi nhớ đăng nhập"
        loadSavedLogin();

        // Xử lý insets cho thiết bị có tai thỏ
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bắt sự kiện đăng nhập
        btnLogin.setOnClickListener(view -> {
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isValid = dao.checkLogin(user, pass);
            if (isValid) {
                // Nếu chọn ghi nhớ thì lưu cả username và password
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", user);
                if (chkRemember.isChecked()) {
                    editor.putString("password", pass);
                    editor.putBoolean("remember", true);
                } else {
                    editor.remove("password");
                    editor.putBoolean("remember", false);
                }
                editor.apply();

                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                // Truyền username sang MenuActivity
                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra("username", user);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm tải lại thông tin đã lưu (nếu có)
    private void loadSavedLogin() {
        boolean remember = sharedPreferences.getBoolean("remember", false);
        if (remember) {
            edtUsername.setText(sharedPreferences.getString("username", ""));
            edtPassword.setText(sharedPreferences.getString("password", ""));
            chkRemember.setChecked(true);
        }
    }
}
